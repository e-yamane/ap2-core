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
    
    public String getCode(String systemName) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName));
    }
    
    public String getCode(String systemName, Date date) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName), date);
    }
    
    public String getCode(CodeSystem system) {
    	return getCode(system, new Date());
    }
    
    @SuppressWarnings("unchecked")
	public String getCode(CodeSystem system, Date date) {
    	Extractor ex = new Extractor(OwnerCode.class);
    	ex.addExtractValue(new ExtractValue("code", new Property(OwnerCode.CI + Code.CODE)));
    	ex.setReturnType(String.class);
    	ex.setLimit(1);
    	ex.addOrder(Order.desc(new Property(OwnerCode.CI + Code.REVISION)));
    	ex.add(Condition.eq(new Property(OwnerCode.CI + Code.CODESYSTEM), system));
    	ex.add(Condition.eq(new Property(OwnerCode.TARGET), this));
    	
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

    	List<String> ret = BasicService.getService().findByExtractor(ex);
    	return (ret.size() == 0) ? null : ret.get(0);
    }
}
