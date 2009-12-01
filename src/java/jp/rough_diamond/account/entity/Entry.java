package jp.rough_diamond.account.entity;

/**
 * エントリーのHibernateマッピングクラス
**/
public class Entry extends jp.rough_diamond.account.entity.base.BaseEntry {
    private static final long serialVersionUID = 1L;

    /**
     * 現時点のバランスを返却する
     * @return
     */
    public Long getBalance() {
    	return getBalance(false, false);
    }

    /**
     * 現時点のバランスを返却する
     * @return
     */
    public Long getBalance(boolean isGrossItem, boolean isGrossPlace) {
    	Account a = getAccount();
    	Transaction t = getTransaction();
    	if(a == null || t == null) {
    		return getQuantity();
    	}
    	return a.getBalance(t.getProcessDate(), isGrossItem, isGrossPlace) + getQuantity();
    }
}
