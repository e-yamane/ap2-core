package jp.rough_diamond.account.entity;

import java.util.Date;
import java.util.List;

import jp.rough_diamond.commons.entity.UpdateTimestamp;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Max;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.service.annotation.PreUpdate;
import jp.rough_diamond.commons.util.DateManager;

/**
 * 所有者のHibernateマッピングクラス
**/
public class Party extends jp.rough_diamond.account.entity.base.BaseParty {
    private static final long serialVersionUID = 1L;
    public final static Long DUMMY_REVISION = Long.MIN_VALUE;
    
    public Party() {
    	setStatusCode(MasterStatus.UNKNOWN.code);		//dummy
    	setRevision(DUMMY_REVISION);					//dummy
    }

	public static <T extends Party> T getPartyByPartyCode(String partyCode) {
		Extractor ex = new Extractor(Party.class);
		ex.add(Condition.eq(new Property(Party.PARTY_CODE), partyCode));
		List<T> parties = BasicService.getService().findByExtractor(ex);
		return (parties.size() == 0) ? null : (T)parties.get(0);
	}

	public static Long getMaxRevision() {
    	Extractor ex1 = new Extractor(Party.class);
    	ex1.addExtractValue(new ExtractValue("max", new Max(new Property(Party.REVISION))));
    	ex1.setReturnType(Long.class);
    	Long ret = (Long)BasicService.getService().findByExtractor(ex1).get(0);
    	Extractor ex2 = new Extractor(PartyCode.class);
    	ex2.addExtractValue(new ExtractValue("max", new Max(new Property(PartyCode.CI + Code.REVISION))));
    	ex2.setReturnType(Long.class);
    	return Math.max(ret, (Long)BasicService.getService().findByExtractor(ex2).get(0));
    }
    
    public static List<Party> getAll() {
    	Extractor extractor = new Extractor(Party.class);
    	extractor.addOrder(Order.asc(new Property(ID)));
    	return BasicService.getService().findByExtractor(extractor);
    }
    
    public static <T extends Party> T getPartyByCode(String systemName, String code) {
    	return getPartyByCode(CodeSystem.getCodeSystemByName(systemName), code);
    }
    
    public static <T extends Party> T getPartyByCode(String systemName, String code, Date date) {
    	return getPartyByCode(CodeSystem.getCodeSystemByName(systemName), code, date);
    }
    
    public static <T extends Party> T getPartyByCode(CodeSystem system, String code) {
    	return getPartyByCode(system, code, DateManager.DM.newDate());
    }
    
    @SuppressWarnings("unchecked")
	public static <T extends Party> T getPartyByCode(CodeSystem system, String code, Date date) {
    	List<PartyCode> targets = Code.getTargetHoldersByCode(PartyCode.class, PartyCode.CI, system, code, date);
    	for(PartyCode target : targets) {
    		String code2 = target.getTarget().getCode(system, date);
    		if(code2.equals(code)) {
    			return (T)target.getTarget();
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

	public List<PartyCode> getPartyCodes() {
		Extractor ex = new Extractor(PartyCode.class);
		ex.add(Condition.eq(new Property(PartyCode.TARGET), this));
		ex.addOrder(Order.desc(new Property(PartyCode.TS + UpdateTimestamp.LAST_MODIFIED_DATE)));
		ex.addOrder(Order.desc(new Property(PartyCode.ID)));
		return BasicService.getService().findByExtractor(ex);
	}
	
	public String getCode(String systemName) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName));
    }
    
    public String getCode(String systemName, Date date) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName), date);
    }
    
    public String getCode(CodeSystem system) {
    	return getCode(system, DateManager.DM.newDate());
    }
    
	public String getCode(CodeSystem system, Date date) {
		return Code.getCode(PartyCode.class, this, PartyCode.TARGET, PartyCode.CI, system, date);
    }
	
	public MasterStatus getStatus() {
		return MasterStatus.getStstusByCode(getStatusCode());
	}
	
	@PrePersist
	@PreUpdate
	public void refreshStatus() {
		if(getStatus() == MasterStatus.UNKNOWN) {
			setStatusCode(MasterStatus.TEST.code);
		}
	}
}
