package jp.rough_diamond.account.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.rough_diamond.account.service.TransactionService;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.InnerJoin;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PostPersist;
import jp.rough_diamond.commons.service.annotation.PostUpdate;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.service.annotation.PreUpdate;
import jp.rough_diamond.framework.service.ServiceLocator;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �g�����U�N�V������Hibernate�}�b�s���O�N���X
**/
public class Transaction extends jp.rough_diamond.account.entity.base.BaseTransaction {
    private static final long serialVersionUID = 1L;

    private final static Log log = LogFactory.getLog(Transaction.class);
    
    public Transaction() {
    	setActual(true);
        setRegisterDate(new Date());    //dummy
    }
    
    public static TransactionService getService() {
        return ServiceLocator.getService(TransactionService.class);
    }

    public void addTransaction(Transaction t) throws MessagesIncludingException {
        getService().addAfterTransaction(this, t);
    }
    
    @Deprecated
    public void addActual(Transaction actual) throws MessagesIncludingException {
    	addTransaction(actual);
    }
    
    /**
     * �ŐV�g�����U�N�V�������擾���邽�߂̃x�[�X�ƂȂ�Extractor��ǉ�����
     * �ΏۂƂȂ�Transaction��Alias��null�ATransactionMapper��Alias��tm���w�肵�Ă��邽�߁A
     * ���̑��̃e�[�u���������InnerJoin������ꍇ�͂���ȊO��Alias�����Ă����邱�ƁB
     * @param cl �擾����g�����U�N�V�����̃^�C�v
     * @return �ŐV�̃g�����U�N�V�������擾���邽�߂̏������Z�b�g���ꂽExtractor�I�u�W�F�N�g
     */
    public static Extractor getNewestTransactionExtractorBase(Class<? extends Transaction> cl) {
        Extractor ex = new Extractor(cl);
        addNewestTransactionCondition(ex, cl, null, null);
        return ex;
    }

    /**
     * �w�肳�ꂽExtractor�ɍŐV�̃g�����U�N�V�����݂̂��擾�ΏۂƂ��邽�߂̏�����t�^����
     * @param ex		�t�^���錳�ƂȂ�Extractor�I�u�W�F�N�g
     * @param cl�@�@�@�@�@�@TransactionMapper��before�I�u�W�F�N�g�ƃ����N��\�邽�߂̃I�u�W�F�N�g��ێ�����N���X
     * @param property	TransactionMapper��before�I�u�W�F�N�g�ƃ����N��\�邽�߂̃v���p�e�B�Bnull���w�肷��ƁA
     * 					cl�Ŏw�肳�ꂽ�N���X���̂ƃ����N������
     * @param alias		TransactionMapper�ƃ����N��\�邽�߂̃N���X�Ɏw�肵���G�C���A�X�B�G�C���A�X�𖾂Ɏw�肵�Ă��Ȃ��ꍇ��
     * 					null���Z�b�g����
     */
    public static void addNewestTransactionCondition(Extractor ex, Class<?> cl, String property, String alias) {
        ex.addInnerJoin(new InnerJoin(cl, property, alias, TransactionMapper.class, TransactionMapper.BEFORE, "tm"));
        ex.add(Condition.isNull(new Property(TransactionMapper.class, "tm", TransactionMapper.AFTER)));
    }
    
    private Boolean isNewestTransaction = null;

    @Deprecated
    public void checkNewestTransaction() {
        Extractor e = new Extractor(TransactionMapper.class);
        e.add(Condition.eq(TransactionMapper.BEFORE, this));
        e.add(Condition.isNull(TransactionMapper.AFTER));
        isNewestTransaction = (BasicService.getService().getCountByExtractor(e) > 0L);
    }
    
    //XXX �����ɂ͔r�������邯�ǂ܂�������B�B�B
    public boolean isNewestTransaction() {
    	if(isNewestTransaction == null) {
    		if(getId() == null) {
    			return false;
    		}
    		checkNewestTransaction();
    	}
        return isNewestTransaction;
    }

    @PrePersist
    public void refreshRegistDate() {
        setRegisterDate(new Date());
    }

    @PreUpdate(priority=10)
    public void deleteOldEntries() throws VersionUnmuchException, MessagesIncludingException {
        Extractor e = new Extractor(Entry.class);
        e.add(Condition.eq(new Property(Entry.TRANSACTION), this));
        BasicService.getService().deleteByExtractor(e);
    }

    @PostPersist(priority=0)
    @PostUpdate(priority=0)
    public void insertEntries() throws MessagesIncludingException {
        log.debug("PK:" + getId());
        BasicService service = BasicService.getService();
        Transaction t = service.findByPK(Transaction.class, getId());
        log.debug(t.getClass().getName());
        for(Entry e : entries) {
            e.setId(null);
            e.setTransaction(t);
            service.insert(e);
        }
    }
    
    
    private List<Entry> entries;
    public List<Entry> getEntries() {
        if(entries == null) {
            entries = loadEntries();
        }
        return entries;
    }
    
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getFromEntries() {
        List<Entry> ret = new ArrayList<Entry>();
        for(Entry e : getEntries()) {
            if(e.getQuantity() < 0L) {
                ret.add(e);
            }
        }
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public List<Entry> getToEntries() {
        List<Entry> all = getEntries();
        List<Entry> from = getFromEntries();
        return ListUtils.subtract(all, from);
    }
    
    private List<Entry> loadEntries() {
        if(getId() == null) {
            return new ArrayList<Entry>();
        }
        Extractor e = new Extractor(Entry.class);
        e.add(Condition.eq(new Property(Entry.TRANSACTION), this));
        e.addOrder(Order.asc(new Property(Entry.ID)));
        return BasicService.getService().findByExtractor(e, true);
    }

    @PrePersist(priority=100)
    @PreUpdate(priority=100)
    public void insertNullAccount() throws MessagesIncludingException {
        BasicService service = BasicService.getService();
        for(Entry e : getEntries()) {
            Account a = e.getAccount();
            if(a.getId() == null) {
                service.insert(a);
            }
        }
    }

    private Long oldId;
    @Override
    protected void update() throws MessagesIncludingException, VersionUnmuchException {
    	oldId = getId();
    	setId(null);
    	//�O�̂���
    	BasicService.getService().clearCache(this);
    	insert();
    }
    
    @PostPersist
    public void insertTerminateMapper() throws MessagesIncludingException {
        TransactionMapper tm = new TransactionMapper();
        tm.setBefore(this);
        BasicService.getService().insert(tm);
    }
    
    @PostPersist(priority=1)
    public void chainMapper() throws VersionUnmuchException, MessagesIncludingException {
    	if(oldId == null) {
    		return;
    	}
    	try {
			Extractor e = new Extractor(TransactionMapper.class);
			e.add(Condition.eq(new Property(TransactionMapper.BEFORE + "." + Transaction.ID), oldId));
			e.add(Condition.isNull(new Property(TransactionMapper.AFTER)));
			List<TransactionMapper> tmList = BasicService.getService().findByExtractor(e, true);
			if(tmList.size() == 0) {
		        log.warn("�o�O�����E�E�E���O�̃}�b�p�[������܂���B");
				return;
			}
			TransactionMapper tm = tmList.get(0);
			tm.setAfter(this);
			BasicService.getService().update(tm);
    	} finally {
    		oldId = null;
    	}
    }

//TODO ActualTransaction�̈�i�B������ƒx���񂾂�˂��B��[�R�����g�A�E�g
//    @Verifier
//    public Messages checkBalance() {
//    	Messages m = new Messages();
//    	for(Entry e : getEntries()) {
//    		if(e.getAccount().getPlace().isVirtual()) {
//    			continue;
//    		}
//    		long balance = e.getAccount().getBalance(getProcessDate());
//    		if(balance + e.getQuantity() < 0) {
//    			m.add("quantity", new Message(
//    					"errors.blance.under.zero",
//    					e.getAccount().getPlace().getName(),
//    					e.getAccount().getItem().getName()));
//    		}
//    	}
//    	return m;
//    }
}
