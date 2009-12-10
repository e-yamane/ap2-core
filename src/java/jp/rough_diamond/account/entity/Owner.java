package jp.rough_diamond.account.entity;

import java.util.Date;
import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
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
    	List<OwnerCode> targets = Code.getTargetHoldersByCode(OwnerCode.class, OwnerCode.CI, system, code, date);
    	for(OwnerCode target : targets) {
    		String code2 = target.getTarget().getCode(system, date);
    		if(code2.equals(code)) {
    			return target.getTarget();
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
		return Code.getCode(OwnerCode.class, this, OwnerCode.TARGET, OwnerCode.CI, system, date);
    }
}
