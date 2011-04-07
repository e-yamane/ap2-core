package jp.rough_diamond.account.entity;

import java.util.Date;
import java.util.List;

import jp.rough_diamond.account.service.AccountService;
import jp.rough_diamond.commons.entity.Quantity;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.commons.util.DateManager;
import jp.rough_diamond.framework.service.ServiceLocator;

/**
 * 勘定のHibernateマッピングクラス
**/
public class Account extends jp.rough_diamond.account.entity.base.BaseAccount {
    private static final long serialVersionUID = 1L;

    public Account() {
        // dummy
        setRegisterDate(DateManager.DM.newDate());
    }
    
    @PrePersist
    public void refreshRegistDate() {
        setRegisterDate(DateManager.DM.newDate());
    }
    
    public static AccountService getService() {
        return ServiceLocator.getService(AccountService.class);
    }
    
    public Quantity getBalance() {
        return getBalance(DateManager.DM.newDate());
    }
    
    public Quantity getBalance(Date date) {
        return getBalance(date, false, false);
    }
    
    public Quantity getBalance(Date date, boolean isGrossItem, boolean isGrossPlace) {
        return getService().getBalance(this, date, isGrossItem, isGrossPlace);
    }
    
    public List<Entry> getTrend() {
        return getTrend(null, null);
    }
    
    public List<Entry> getTrend(Date from, Date to) {
        return getTrend(from, to, false, false);
    }

    public List<Entry> getTrend(Date from, Date to, boolean isGrossItem, boolean isGrossPlace) {
        return getService().getTrend(this, from, to, isGrossItem, isGrossPlace);
    }
}
