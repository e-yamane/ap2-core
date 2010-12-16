package jp.rough_diamond.account.entity;

import jp.rough_diamond.commons.entity.Amount;
import jp.rough_diamond.commons.entity.ScalableNumber;
import jp.rough_diamond.commons.extractor.FreeFormat;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.extractor.Value;

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

    @Deprecated
    public Long getQuantity() {
    	return getAmount().getQuantity().longValue();
    }
    
    @Deprecated
    public void setQuantity(Long quantity) {
    	getAmount().setQuantity(new ScalableNumber(quantity, 0));
    }
    
    public static Value getQuantityProperty() {
    	return getQuantityProperty(null);
    }
    
    public static Value getQuantityProperty(String alias) {
    	FreeFormat ff = new FreeFormat("? * power(10, ?)",
    			new Property(Entry.class, alias, Entry.AMOUNT + Amount.Q + ScalableNumber.VALUE), 
    			new Property(Entry.class, alias, Entry.AMOUNT + Amount.Q + ScalableNumber.SCALE)
    	);
    	return ff;
    }
}
