package jp.rough_diamond.account.entity;

import java.util.Date;
import java.util.List;

import jp.rough_diamond.commons.extractor.CombineCondition;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;

/**
 * 所有者のHibernateマッピングクラス
**/
public class Owner extends jp.rough_diamond.account.entity.base.BaseOwner {
    private static final long serialVersionUID = 1L;

    public static List<Owner> getAll() {
    	Extractor extractor = new Extractor(Owner.class);
    	extractor.addOrder(Order.asc(new Property(ID)));
    	return BasicService.getService().findByExtractor(extractor);
    }
    
    public static Owner getOwnerByCode(String systemName, String code) {
    	return getOwnerByCode(CodeSystem.getCodeSystemByName(systemName), code);
    }
    
    public static Owner getOwnerByCode(String systemName, String code, Date date) {
    	return getOwnerByCode(CodeSystem.getCodeSystemByName(systemName), code, date);
    }
    
    public static Owner getOwnerByCode(CodeSystem system, String code) {
    	return getOwnerByCode(system, code, new Date());
    }
    
    public static Owner getOwnerByCode(CodeSystem system, String code, Date date) {
    	Extractor ex = new Extractor(OwnerCode.class);
    	ex.add(Condition.eq(new Property(OwnerCode.CI + Code.CODESYSTEM), system));
    	ex.add(Condition.eq(new Property(OwnerCode.CI + Code.CODE), code));
    	ex.addOrder(Order.asc(new Property(OwnerCode.TARGET)));
    	List<OwnerCode> list = BasicService.getService().findByExtractor(ex);
    	for(OwnerCode oc : list) {
    		String code2 = oc.getTarget().getCode(system, date);
    		if(code2.equals(code)) {
    			return oc.getTarget();
    		}
    	}
    	return null;
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
		Extractor ex = makeExtractor(system, date);
    	ex.add(Condition.eq(new Property(OwnerCode.TARGET), this));
    	ex.addExtractValue(new ExtractValue("code", new Property(OwnerCode.CI + Code.CODE)));
    	ex.setReturnType(String.class);
    	ex.setLimit(1);
		
    	List<String> ret = BasicService.getService().findByExtractor(ex);
    	return (ret.size() == 0) ? null : ret.get(0);
    }
    
    @SuppressWarnings("unchecked")
    static Extractor makeExtractor(CodeSystem system, Date date) {
    	Extractor ex = new Extractor(OwnerCode.class);
    	ex.addOrder(Order.desc(new Property(OwnerCode.CI + Code.REVISION)));
    	ex.add(Condition.eq(new Property(OwnerCode.CI + Code.CODESYSTEM), system));

    	Property valid = new Property(OwnerCode.CI + Code.VALID_DATE);
    	CombineCondition validCon = Condition.or();
    	validCon.add(Condition.isNull(valid));
    	validCon.add(Condition.le(valid, date));
    	ex.add(validCon);
    	
    	Property invalid = new Property(OwnerCode.CI + Code.INVALID_DATE);
    	CombineCondition invalidCon = Condition.or();
    	invalidCon.add(Condition.isNull(invalid));
    	invalidCon.add(Condition.gt(invalid, date));
    	ex.add(invalidCon);

    	return ex;
    }
}
