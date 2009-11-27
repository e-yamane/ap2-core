package jp.rough_diamond.account.entity;

import jp.rough_diamond.commons.resource.Message;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.service.annotation.Verifier;

/**
 * 実績トランザクションのHibernateマッピングクラス
**/
@Deprecated
public class ActualTransaction extends jp.rough_diamond.account.entity.base.BaseActualTransaction {
    private static final long serialVersionUID = 1L;

    public ActualTransaction() {
    	super();
    	setActual(true);
    }
    
    @Verifier
    public Messages checkBalance() {
    	Messages m = new Messages();
    	for(Entry e : getEntries()) {
    		if(e.getAccount().getPlace().isVirtual()) {
    			continue;
    		}
    		long balance = e.getAccount().getBalance(getProcessDate());
    		if(balance + e.getQuantity() < 0) {
    			m.add("quantity", new Message(
    					"errors.blance.under.zero",
    					e.getAccount().getPlace().getName(),
    					e.getAccount().getItem().getName()));
    		}
    	}
    	return m;
    }

}
