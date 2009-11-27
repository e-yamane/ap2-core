package jp.rough_diamond.account.entity;

/**
 * �G���g���[��Hibernate�}�b�s���O�N���X
**/
public class Entry extends jp.rough_diamond.account.entity.base.BaseEntry {
    private static final long serialVersionUID = 1L;

    /**
     * �����_�̃o�����X��ԋp����
     * @return
     */
    public Long getBalance() {
    	return getBalance(false, false);
    }

    /**
     * �����_�̃o�����X��ԋp����
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
