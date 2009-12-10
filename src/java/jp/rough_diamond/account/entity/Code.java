/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */


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
 * コードのHibernateマッピングクラス
**/
public class Code extends jp.rough_diamond.account.entity.base.BaseCode {
    private static final long serialVersionUID = 1L;

	public static <T> List<T> getTargetHoldersByCode(Class<T> holderType, String prefix, CodeSystem system, String code, Date date) {
    	Extractor ex = new Extractor(holderType);
    	ex.add(Condition.eq(new Property(prefix + Code.CODESYSTEM), system));
    	ex.add(Condition.eq(new Property(prefix + Code.CODE), code));
    	ex.addOrder(Order.asc(new Property(PlaceCode.TARGET)));
    	return BasicService.getService().findByExtractor(ex);
    }
    
    @SuppressWarnings("unchecked")
	public static String getCode(Class holderType, Object target, String targetProperty, String prefix, CodeSystem system, Date targetDate) {
    	Extractor ex = new Extractor(holderType);
    	Code.addCodeGettingCondition(ex, prefix, system, targetDate);
    	ex.add(Condition.eq(new Property(targetProperty), target));
    	ex.addExtractValue(new ExtractValue("code", new Property(prefix + Code.CODE)));
    	ex.setReturnType(String.class);
    	ex.setLimit(1);
		
    	List<String> ret = BasicService.getService().findByExtractor(ex);
    	return (ret.size() == 0) ? null : ret.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public static void addCodeGettingCondition(Extractor base, String prefix, CodeSystem system, Date targetDate) {
    	base.addOrder(Order.desc(new Property(prefix + Code.REVISION)));
    	base.add(Condition.eq(new Property(prefix + Code.CODESYSTEM), system));

    	Property valid = new Property(prefix + Code.VALID_DATE);
    	CombineCondition validCon = Condition.or();
    	validCon.add(Condition.isNull(valid));
    	validCon.add(Condition.le(valid, targetDate));
    	base.add(validCon);
    	
    	Property invalid = new Property(prefix + Code.INVALID_DATE);
    	CombineCondition invalidCon = Condition.or();
    	invalidCon.add(Condition.isNull(invalid));
    	invalidCon.add(Condition.gt(invalid, targetDate));
    	base.add(invalidCon);
    }
}
