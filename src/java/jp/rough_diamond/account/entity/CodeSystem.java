/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */


package jp.rough_diamond.account.entity;

import java.util.List;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;

/**
 * コード体系のHibernateマッピングクラス
**/
public class CodeSystem extends jp.rough_diamond.account.entity.base.BaseCodeSystem {
    private static final long serialVersionUID = 1L;
    
    public static CodeSystem getCodeSystemByName(String name) {
    	Extractor ex = new Extractor(CodeSystem.class);
    	ex.add(Condition.eq(new Property(CodeSystem.NAME), name));
    	ex.setLimit(1);
    	List<CodeSystem> list = BasicService.getService().findByExtractor(ex);
    	return (list.size() == 0) ? null : list.get(0);
    }
}
