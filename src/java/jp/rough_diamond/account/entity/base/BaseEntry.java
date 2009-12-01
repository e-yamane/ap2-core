/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * エントリーのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="ENTRY"
 *    realClass="jp.rough_diamond.account.entity.Entry"
**/
public abstract class BaseEntry  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseEntry() {
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
        if(o instanceof BaseEntry) {
            if(hashCode() == o.hashCode()) {
                BaseEntry obj = (BaseEntry)o;
                if(getId() == null) {
                    return super.equals(o);
                }
                return getId().equals(obj.getId());
            }
        }
        return false;
    }

    protected boolean isLoaded;
    @jp.rough_diamond.commons.service.annotation.PostLoad
    @jp.rough_diamond.commons.service.annotation.PostPersist
    public void setLoadingFlag() {
        isLoaded = true;
    }

    /**
     * オブジェクトを永続化する
     * 永続化ルールは以下の通りです。
     * <ul>
     *   <li>newした直後のオブジェクトの場合はinsert</li>
     *   <li>loadされたオブジェクトの場合はupdate</li>
     *   <li>loadされたオブジェクトでも主キーを差し替えた場合はinsert</li>
     *   <li>insertしたオブジェクトを再度saveした場合はupdate</li>
     *   <li>setLoadingFlagメソッドを呼び出した場合は強制的にupdate（非推奨）</li>
     * </ul>
     * @throws VersionUnmuchException   楽観的ロッキングエラー
     * @throws MessagesIncludingException 検証例外
    **/
    public void save() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        if(isLoaded) {
            update();
        } else {
            insert();
        }
    }

    /**
     * オブジェクトを永続化する
     * @throws MessagesIncludingException 検証例外
    **/
    protected void insert() throws jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().insert(this);
    }

    /**
     * 永続化オブジェクトを更新する
     * @throws MessagesIncludingException 検証例外
     * @throws VersionUnmuchException   楽観的ロッキングエラー
    **/
    protected void update() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().update(this);
    }
    /**
     * 移動量
    **/ 
    private Long quantity;
    public final static String QUANTITY = "quantity";

    /**
     * 移動量を取得する
     * @hibernate.property
     *    column="QUANTITY"
     *    not-null="true"
     * @return 移動量
    **/
    public Long getQuantity() {
        return quantity;
    }

    /**
     * 移動量を設定する
     * @param quantity  移動量
    **/
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Account account;
    public final static String ACCOUNT = "account";

    /**
     * Get the associated Account object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "ACCOUNT_ID"
     *
     * @return the associated Account object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Entry.accountId")
    public jp.rough_diamond.account.entity.Account getAccount() {
        return this.account;
    }

    /**
     * Declares an association between this object and a Account object
     *
     * @param v Account
     */
    public void setAccount(jp.rough_diamond.account.entity.Account v) {
        this.account = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadAccount() {
        jp.rough_diamond.account.entity.Account account = getAccount();
        if(account != null) {
            Long pk = account.getId();
            setAccount(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Account.class, pk)
            );
        }
    }

    private jp.rough_diamond.account.entity.Transaction transaction;
    public final static String TRANSACTION = "transaction";

    /**
     * Get the associated Transaction object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "TRANSACTION_ID"
     *
     * @return the associated Transaction object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Entry.transactionId")
    public jp.rough_diamond.account.entity.Transaction getTransaction() {
        return this.transaction;
    }

    /**
     * Declares an association between this object and a Transaction object
     *
     * @param v Transaction
     */
    public void setTransaction(jp.rough_diamond.account.entity.Transaction v) {
        this.transaction = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadTransaction() {
        jp.rough_diamond.account.entity.Transaction transaction = getTransaction();
        if(transaction != null) {
            Long pk = transaction.getId();
            setTransaction(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Transaction.class, pk)
            );
        }
    }

//ForeignProperties.vm finish.
}
