/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * トランザクション変遷マッパーのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="TRANSACTION_MAPPER"
 *    realClass="jp.rough_diamond.account.entity.TransactionMapper"
**/
@jp.rough_diamond.commons.service.annotation.Unique(
    entity="TransactionMapper",
    groups= {
          @jp.rough_diamond.commons.service.annotation.Check(properties={
              "before"
            , "after"
        })
    }
)
public abstract class BaseTransactionMapper  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseTransactionMapper() {
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
        if(o instanceof BaseTransactionMapper) {
            if(hashCode() == o.hashCode()) {
                BaseTransactionMapper obj = (BaseTransactionMapper)o;
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

//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Transaction before;
    public final static String BEFORE = "before";

    /**
     * Get the associated Transaction object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "BEFORE_TRANSACTION_ID"
     *
     * @return the associated Transaction object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="TransactionMapper.beforeTransactionId")
    public jp.rough_diamond.account.entity.Transaction getBefore() {
        if(jp.rough_diamond.commons.service.BasicService.isProxy(this.before)) {
            this.before = jp.rough_diamond.commons.service.BasicService.getService().replaceProxy(this.before);
        }
        return this.before;
    }

    /**
     * Declares an association between this object and a Transaction object
     *
     * @param v Transaction
     */
    public void setBefore(jp.rough_diamond.account.entity.Transaction v) {
        this.before = v;
    }

    private jp.rough_diamond.account.entity.Transaction after;
    public final static String AFTER = "after";

    /**
     * Get the associated Transaction object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "AFTER_TRANSACTION_ID"
     *
     * @return the associated Transaction object
     */
    public jp.rough_diamond.account.entity.Transaction getAfter() {
        if(jp.rough_diamond.commons.service.BasicService.isProxy(this.after)) {
            this.after = jp.rough_diamond.commons.service.BasicService.getService().replaceProxy(this.after);
        }
        return this.after;
    }

    /**
     * Declares an association between this object and a Transaction object
     *
     * @param v Transaction
     */
    public void setAfter(jp.rough_diamond.account.entity.Transaction v) {
        this.after = v;
    }

//ForeignProperties.vm finish.


}
