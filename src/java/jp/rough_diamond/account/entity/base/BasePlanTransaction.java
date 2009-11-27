/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * 予定トランザクションのHibernateマッピングベースクラス
 * @hibernate.joined-subclass-gg
 *    realClass="jp.rough_diamond.account.entity.PlanTransaction"
 *    table="PLAN_TRANSACTION"
 * @hibernate.joined-subclass-key
 *    column="ID"
**/
public abstract class BasePlanTransaction extends jp.rough_diamond.account.entity.Transaction implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BasePlanTransaction() {
    }

    /**
     * OID
    **/ 
    private Long id;
    public final static String ID = "id";
    /**
     * OIDを取得する
     * @hibernate.id
     *    generator-class="assigned"
     *    column="ID"
     *    not-null="true"
     * @return OID
    **/
    public Long getId() {
        return id;
    }

    /**
     * OIDを設定する
     * @param id  OID
    **/
    public void setId(Long id) {
        this.id = id;
        isLoaded = false;
    }

    public int hashCode() {
        if(getId() == null) {
            return super.hashCode();
        } else {
            return getId().hashCode();
        }
    }
    
    public boolean equals(Object o) {
        if(o instanceof BasePlanTransaction) {
            if(hashCode() == o.hashCode()) {
                BasePlanTransaction obj = (BasePlanTransaction)o;
                if(getId() == null) {
                    return super.equals(o);
                }
                return getId().equals(obj.getId());
            }
        }
        return false;
    }
    /**
     * 取引番号
    **/ 
    private String processId;
    public final static String PROCESS_ID = "processId";

    /**
     * 取引番号を取得する
     * @hibernate.property
     *    column="PROCESS_ID"
     *    not-null="false"
     *    length="256"
     * @return 取引番号
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="PlanTransaction.processId")
    public String getProcessId() {
        return processId;
    }

    /**
     * 取引番号を設定する
     * @param processId  取引番号
    **/
    public void setProcessId(String processId) {
        this.processId = processId;
    }
//ForeignProperties.vm start.

    
//ForeignProperties.vm finish.
}
