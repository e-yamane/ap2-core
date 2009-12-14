/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */


package jp.rough_diamond.account.entity;

import jp.rough_diamond.commons.service.NumberingService;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.service.annotation.PreUpdate;

/**
 * 所有者コードのHibernateマッピングクラス
**/
public class PartyCode extends jp.rough_diamond.account.entity.base.BasePartyCode {
    private static final long serialVersionUID = 1L;
    
    public final static String REVISION_COUNTER_KEY = "partyRevision";

	@PrePersist
	@PreUpdate
	public void countUpRevision() {
		getCodeInfo().setRevision(NumberingService.getService().getNumber(PartyCode.REVISION_COUNTER_KEY));
	}
}
