package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Count;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;
import jp.rough_diamond.framework.service.Service;
import jp.rough_diamond.framework.service.ServiceLocator;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

public class TransactionTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testSaveWhenUpdate() throws Exception {
		Extractor tCountEx = new Extractor(Transaction.class);
		long tCount = BasicService.getService().getCountByExtractor(tCountEx);
		Extractor eCountEx = new Extractor(Entry.class);
		long eCount = BasicService.getService().getCountByExtractor(eCountEx);
		Transaction t = doInsert();
		assertEquals("Transaction�̌���������Ă��܂��B", tCount + 1, BasicService.getService().getCountByExtractor(tCountEx));
		assertEquals("Entry�̌���������Ă��܂��B", eCount + 2, BasicService.getService().getCountByExtractor(eCountEx));
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE + "." + Transaction.ID), 10L));
		ex.add(Condition.eq(new Property(TransactionMapper.AFTER), t));
		assertEquals("TransactionMapper���o�^����Ă��܂���", 1, BasicService.getService().getCountByExtractor(ex));
	}
	
	public void testSaveWhenUpdateInTransaction() throws Exception {
		Extractor tCountEx = new Extractor(Transaction.class);
		long tCount = BasicService.getService().getCountByExtractor(tCountEx);
		Extractor eCountEx = new Extractor(Entry.class);
		long eCount = BasicService.getService().getCountByExtractor(eCountEx);
		Transaction t = ServiceLocator.getService(TransactionTestService.class).doIt();
		assertEquals("Transaction�̌���������Ă��܂��B", tCount + 1, BasicService.getService().getCountByExtractor(tCountEx));
		assertEquals("Entry�̌���������Ă��܂��B", eCount + 2, BasicService.getService().getCountByExtractor(eCountEx));
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE + "." + Transaction.ID), 10L));
		ex.add(Condition.eq(new Property(TransactionMapper.AFTER), t));
		assertEquals("TransactionMapper���o�^����Ă��܂���", 1, BasicService.getService().getCountByExtractor(ex));
	}
	
	static Transaction doInsert() throws VersionUnmuchException, MessagesIncludingException {
		Transaction t1 = BasicService.getService().findByPK(Transaction.class, 1L);
		System.out.println(t1.getRegisterDate());
		Transaction t = BasicService.getService().findByPK(Transaction.class, 10L);
		t.getFromEntries().get(0).setQuantity(-20L);
		t.getToEntries().get(0).setQuantity(20L);
		t.save();
		return t;
	}
	
	public static class TransactionTestService implements Service {
		public Transaction doIt() throws VersionUnmuchException, MessagesIncludingException {
			return doInsert();
		}
	}

	public void testInsert() throws Exception {
		Transaction pt = new Transaction();
		pt.setActual(false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		pt.setProcessDate(sdf.parse("20080827101112"));
		pt.setRegisterDate(sdf.parse("20070827101112"));
		Entry e = new Entry();
		BasicService service = BasicService.getService();
		e.setAccount(service.findByPK(Account.class, 1L));
		e.setQuantity(10L);
		pt.setEntries(Arrays.asList(new Entry[]{e}));
		service.insert(pt);
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE), pt));
		List<TransactionMapper> list = service.findByExtractor(ex);
		assertEquals("TransactionMapper����������Ă��܂���B", 1, list.size());
		assertNull("After��null�ɂȂ��Ă��܂���B", list.get(0).getAfter());
	}

	public void testInsertAndChain() throws Exception {
		Transaction pt = new Transaction();
		pt.setActual(false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		pt.setProcessDate(sdf.parse("20080827101112"));
		pt.setRegisterDate(sdf.parse("20070827101112"));
		Entry e = new Entry();
		BasicService service = BasicService.getService();
		e.setAccount(service.findByPK(Account.class, 1L));
		e.setQuantity(10L);
		pt.setEntries(Arrays.asList(new Entry[]{e}));

		TransactionMapper tm = service.findByPK(TransactionMapper.class, 10L);
		tm.setAfter(pt);
		
		service.update(tm);
		
		Transaction t = service.findByPK(Transaction.class, pt.getId());
		assertNotNull("�������ۑ�����Ă��܂��B", t);
		
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE), pt));
		List<TransactionMapper> list = service.findByExtractor(ex);
		assertEquals("TransactionMapper����������Ă��܂���B", 1, list.size());
		assertNull("After��null�ɂȂ��Ă��܂���B", list.get(0).getAfter());
	}

	public void testIsNewestTransaction() throws Exception {
		Transaction pt = BasicService.getService().findByPK(Transaction.class, 3L);
		assertTrue("�ŐV����Ȃ��ƌ����Ă��܂��B", pt.isNewestTransaction());
		pt = BasicService.getService().findByPK(Transaction.class, 5L);
		assertFalse("�ŐV���ƌ����Ă��܂��B", pt.isNewestTransaction());
	}

	public void testGetNewestTransactionExtractorBase() {
		Extractor ex2 = Transaction.getNewestTransactionExtractorBase(Transaction.class);
		assertEquals("Target������Ă��܂��B", Transaction.class, ex2.target);
		List<Transaction> list = BasicService.getService().findByExtractor(ex2);
		assertEquals("�ŐV�f�[�^�̂ݎ擾�ł��Ă��܂���B", 9, list.size());
	}

	//�����Framework�Ō��؂ł��Ȃ��������߃e�X�g�Ƃ��Ă����Ƃɉ����Ă݂�
	public void test�����ɓ��t�������ďW�v�֐����g�p�������ʂ̌������擾���Ă݂�() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080501");
		Extractor ex = new Extractor(Transaction.class);
		ex.add(Condition.gt(new Property(Transaction.REGISTER_DATE), d));
		ex.addExtractValue(new ExtractValue("date", new Property(Transaction.REGISTER_DATE)));
		ex.addExtractValue(new ExtractValue("count", new Count()));
		assertEquals("�ԋp��������Ă��܂��B", 4, BasicService.getService().getCountByExtractor(ex));
	}

//TODO ActualTransaction�̈�i�B���\�򉻂������������̂ň�[�R�����g�A�E�g
//	public void testVerify() throws Exception {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Date d = sdf.parse("20080421");
//		BasicService service = BasicService.getService();
//		Account from = service.findByPK(Account.class, 3L);
//		Account to = service.findByPK(Account.class, 5L);
//		Transaction at = makeTransaction(from, to, 70, d);
//		Messages msgs = service.validate(at, WhenVerifier.INSERT);
//		System.out.println(msgs);
//		assertFalse("�G���[���������Ă��܂��B", msgs.hasError());
//		at = makeTransaction(from, to, 71, d);
//		msgs = service.validate(at, WhenVerifier.INSERT);
//		System.out.println(msgs);
//		assertTrue("�G���[���������Ă��܂���B", msgs.hasError());
//	}
//
//	Transaction makeTransaction(Account from, Account to, long quantity, Date d) {
//		Transaction ret = new Transaction();
//		ret.setActual(true);
//		Entry fromE = new Entry();
//		fromE.setAccount(from);
//		Entry toE = new Entry();
//		toE.setAccount(to);
//		fromE.setQuantity(quantity * -1);
//		toE.setQuantity(quantity);
//		ret.setEntries(Arrays.asList(fromE, toE));
//		ret.setProcessDate(d);
//		return ret;
//	}
}
