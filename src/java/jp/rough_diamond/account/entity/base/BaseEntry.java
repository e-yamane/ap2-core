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
        if(isThisObjectAnUpdateObject()) {
            update();
        } else {
            insert();
        }
    }

    /**
     * このオブジェクトを永続化する方法を返却する。
     * 永続化処理実行時、本メソッドがtrueを返却された場合は更新(UPDATE)、falseの場合は登録(INSERT)して振る舞う
     * @return trueの場合は更新、falseの場合は登録として振る舞う
    **/
    protected boolean isThisObjectAnUpdateObject() {
        return isLoaded;
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
     * オブジェクトの永続可能性を検証する
     * @return 検証結果。msgs.hasError()==falseが成立する場合は検証成功とみなす
    */
    public jp.rough_diamond.commons.resource.Messages validateObject() {
        if(isThisObjectAnUpdateObject()) {
            return validateObject(jp.rough_diamond.commons.service.WhenVerifier.UPDATE);
        } else {
            return validateObject(jp.rough_diamond.commons.service.WhenVerifier.INSERT);
        }
    }

    /**
     * オブジェクトの永続可能性を検証する
     * @return 検証結果。msgs.hasError()==falseが成立する場合は検証成功とみなす
    */
    protected jp.rough_diamond.commons.resource.Messages validateObject(jp.rough_diamond.commons.service.WhenVerifier when) {
        return jp.rough_diamond.commons.service.BasicService.getService().validate(this, when);
    }

    /**
     * 移動量
    **/ 
    private jp.rough_diamond.commons.entity.Quantity quantity =  new jp.rough_diamond.commons.entity.Quantity();

    public final static String QUANTITY = "quantity.";

    /**
     * 移動量を取得する
     * @hibernate.component
     *    prefix="QUANTITY_"
     * @return 移動量
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Entry.quantity")
    @jp.rough_diamond.commons.service.annotation.NestedComponent(property="Entry.quantity")
    public jp.rough_diamond.commons.entity.Quantity getQuantity() {
        return quantity;
    }

    /**
     * 移動量を設定する
     * @param quantity  移動量
    **/
    public void setQuantity(jp.rough_diamond.commons.entity.Quantity quantity) {
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
        if(jp.rough_diamond.commons.service.BasicService.isProxy(this.account)) {
            this.account = jp.rough_diamond.commons.service.BasicService.getService().replaceProxy(this.account);
        }
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
        if(jp.rough_diamond.commons.service.BasicService.isProxy(this.transaction)) {
            this.transaction = jp.rough_diamond.commons.service.BasicService.getService().replaceProxy(this.transaction);
        }
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

//ForeignProperties.vm finish.
}
