/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */


package jp.rough_diamond.account.entity;

import java.util.Date;

import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.service.annotation.PreUpdate;

/**
 * 更新日時情報のHibernateマッピングクラス
**/
public class UpdateTimestamp extends jp.rough_diamond.account.entity.base.BaseUpdateTimestamp {
    private static final long serialVersionUID = 1L;
    public UpdateTimestamp() {
    	setRegistererDate(new Date());		//dummy
    	setLastModifiedDate(new Date());	//dummy
    }
    
    @PrePersist
    public void refreshRegistererDate() {
    	setRegistererDate(new Date());
    }
    
    @PrePersist
    @PreUpdate
    public void refreshLastModifiedDate() {
    	setRegistererDate(new Date());
    }
}
