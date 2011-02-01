package jp.rough_diamond.account.entity;

import java.math.BigDecimal;

import jp.rough_diamond.commons.entity.Quantity;
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
    public Quantity getBalance() {
    	return getBalance(false, false);
    }

    /**
     * 現時点のバランスを返却する
     * @return
     */
    public Quantity getBalance(boolean isGrossItem, boolean isGrossPlace) {
    	Account a = getAccount();
    	Transaction t = getTransaction();
    	if(a == null || t == null) {
    		//TODO 単位は無視
    		return new Quantity(BigDecimal.ZERO, null);
    	}
    	//TODO 単位は無視
    	Quantity q = a.getBalance(t.getProcessDate(), isGrossItem, isGrossPlace);
    	return new Quantity(q.decimal().add(getQuantity().getAmount().decimal()), null);
    }

    
    @Deprecated
    public Long getQuantityValue() {
    	return getQuantity().getAmount().longValue();
    }
    
    @Deprecated
    public void setQuantityValue(Long quantity) {
    	getQuantity().setAmount(new ScalableNumber(quantity, 0));
    }
    
    public static Value getQuantityProperty() {
    	return getQuantityProperty(null);
    }
    
    public static Value getQuantityProperty(String alias) {
    	FreeFormat ff = new FreeFormat("? * power(10, ? * -1)",
    			new Property(Entry.class, alias, Entry.QUANTITY + Quantity.A + ScalableNumber.VALUE), 
    			new Property(Entry.class, alias, Entry.QUANTITY + Quantity.A + ScalableNumber.SCALE)
    	);
    	return ff;
    }
}
