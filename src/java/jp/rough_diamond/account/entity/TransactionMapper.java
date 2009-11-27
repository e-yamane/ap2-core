package jp.rough_diamond.account.entity;

import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.service.annotation.PreUpdate;

/**
 * 予実マッパーのHibernateマッピングクラス
**/
public class TransactionMapper extends jp.rough_diamond.account.entity.base.BaseTransactionMapper {
    private static final long serialVersionUID = 1L;

    @PrePersist
    @PreUpdate
    public void insertActualWhenNotPersistence() throws MessagesIncludingException {
    	Transaction after = getAfter();
    	if(after != null && after.getId() == null) {
    		BasicService.getService().insert(getAfter());
    	}
    }
}
