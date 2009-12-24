package jp.rough_diamond.account.entity;

import java.util.Date;
import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Max;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PostLoad;
import jp.rough_diamond.commons.service.annotation.PostPersist;
import jp.rough_diamond.commons.service.annotation.PostUpdate;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.service.annotation.PreUpdate;

/**
 * 所有者のHibernateマッピングクラス
**/
public class Party extends jp.rough_diamond.account.entity.base.BaseParty {
    private static final long serialVersionUID = 1L;

    public static enum Status {
    	/**
    	 * 未定（初期値用）
    	 */
    	UNKNOWN("00"),
    	/**
    	 * テスト運用
    	 */
    	TEST("10"),
    	/**
    	 * 稼働中
    	 */
    	AVAILABLE("20"),
    	/**
    	 * 停止中
    	 */
    	STOP("30"),
    	/**
    	 * 削除
    	 */
    	DELETED("40");
    	public final String code;
    	private Status(String code) {
    		this.code = code;
    	}
    	
    	public String getCode() {
    		return code;
    	}
    	
    	public static Status getStstusByCode(String code) {
    		if(code == null) {
    			return UNKNOWN;
    		}
    		for(Status s : values()) {
    			if(s.code.equals(code)) {
    				return s;
    			}
    		}
    		return UNKNOWN;
    	}
    }
    
    Long loadedRevision = -1L;
    
    public Party() {
    	setStatusCode(Status.UNKNOWN.code);		//dummy
    	setRevision(loadedRevision);			//dummy
    }

    @PostLoad
    @PostUpdate
    @PostPersist
    public void resetLoadedRevision() {
    	loadedRevision = getRevision();
    }
    
    public static List<Party> getAll() {
    	Extractor extractor = new Extractor(Party.class);
    	extractor.addOrder(Order.asc(new Property(ID)));
    	return BasicService.getService().findByExtractor(extractor);
    }
    
    public static Party getOwnerByCode(String systemName, String code) {
    	return getOwnerByCode(CodeSystem.getCodeSystemByName(systemName), code);
    }
    
    public static Party getOwnerByCode(String systemName, String code, Date date) {
    	return getOwnerByCode(CodeSystem.getCodeSystemByName(systemName), code, date);
    }
    
    public static Party getOwnerByCode(CodeSystem system, String code) {
    	return getOwnerByCode(system, code, new Date());
    }
    
    public static Party getOwnerByCode(CodeSystem system, String code, Date date) {
    	List<PartyCode> targets = Code.getTargetHoldersByCode(PartyCode.class, PartyCode.CI, system, code, date);
    	for(PartyCode target : targets) {
    		String code2 = target.getTarget().getCode(system, date);
    		if(code2.equals(code)) {
    			return target.getTarget();
    		}
    	}
    	return null;
    }
    
	public long getUpdateCount() {
		Extractor ex = new Extractor(PartyCode.class);
		ex.add(Condition.eq(new Property(PartyCode.TARGET), this));
		ex.addExtractValue(new ExtractValue("rev", new Max(new Property(PartyCode.CI + Code.REVISION))));
		ex.setReturnType(Long.class);
		long revMax = (Long)BasicService.getService().findByExtractor(ex).get(0);
		return Math.max(revMax, getRevision());
	}

	public String getCode(String systemName) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName));
    }
    
    public String getCode(String systemName, Date date) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName), date);
    }
    
    public String getCode(CodeSystem system) {
    	return getCode(system, new Date());
    }
    
	public String getCode(CodeSystem system, Date date) {
		return Code.getCode(PartyCode.class, this, PartyCode.TARGET, PartyCode.CI, system, date);
    }
	
	public Status getStatus() {
		return Status.getStstusByCode(getStatusCode());
	}
	
	@PrePersist
	@PreUpdate
	public void refreshStatus() {
		if(getStatus() == Status.UNKNOWN) {
			setStatusCode(Status.TEST.code);
		}
	}
	
	@PrePersist
	@PreUpdate
	public void refreshRevision() {
		//XXX 強制アップデートにしてもよいかもね。
		if(getRevision().equals(loadedRevision)) {
			setRevision(PartyCode.getRevisionInTransaction());
		}
	}
}
