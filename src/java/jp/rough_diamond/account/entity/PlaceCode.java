/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */


package jp.rough_diamond.account.entity;

import java.util.Map;

import jp.rough_diamond.commons.service.NumberingService;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.service.annotation.PreUpdate;
import jp.rough_diamond.framework.transaction.TransactionManager;

/**
 * 場所コードのHibernateマッピングクラス
**/
public class PlaceCode extends jp.rough_diamond.account.entity.base.BasePlaceCode {
    private static final long serialVersionUID = 1L;

    public final static String REVISION_COUNTER_KEY = "placeRevision";
    private final static String RIVISION_IN_TRANSACTION_KEY = PlaceCode.class.getName() + "_RevisonInCurrentTransaction";

    /**
     * このトランザクションで払いだしたリビジョンを取得する
     * まだ払いだしていない場合は、新規に発行しそのリビジョンを返却する
     * @return
     */
    public static Long getRevisionInTransaction() {
    	Map<Object, Object> context = TransactionManager.getTransactionContext();
    	Long revision = (Long)context.get(RIVISION_IN_TRANSACTION_KEY);
    	if(revision == null) {
    		//XXX ユニークチェックするかは別途検討
    		revision = NumberingService.getService().getNumber(PlaceCode.REVISION_COUNTER_KEY);
    		context.put(RIVISION_IN_TRANSACTION_KEY, revision);
    	}
    	return revision;
    }
    
	@PrePersist
	@PreUpdate
	public void countUpRevision() {
//		getCodeInfo().setRevision(getRevisionInTransaction());
		//XXX 強制アップデートにしてもよいかもね。
		if(getCodeInfo().getRevision().equals(getCodeInfo().loadedRevision)) {
			getCodeInfo().setRevision(PlaceCode.getRevisionInTransaction());
		}
	}
}
