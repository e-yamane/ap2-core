/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;
import  java.util.Date;


/**
 * トランザクションのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="TRANSACTION"
 *    realClass="jp.rough_diamond.account.entity.Transaction"
**/
public abstract class BaseTransaction  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseTransaction() {
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
        if(o instanceof BaseTransaction) {
            if(hashCode() == o.hashCode()) {
                BaseTransaction obj = (BaseTransaction)o;
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
     * 実績の取引の場合はY。予定はN
    **/ 
    private Boolean actual = Boolean.FALSE;
    public final static String ACTUAL = "actualInDB";

    /**
     * 実績の取引の場合はY。予定はNを取得する
     * @hibernate.property
     *    column="ACTUAL"
     *    not-null="true"
     * @return 実績の取引の場合はY。予定はN
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=1, property="Transaction.actual")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Transaction.actual")
    public String getActualInDB() {
        return (actual) ? "Y" : "N";
    }

    /**
     * 実績の取引の場合はY。予定はNを取得する
     * @return 実績の取引の場合はY。予定はN
    **/
    public Boolean isActual() {
        return actual;
    }

    /**
     * 実績の取引の場合はY。予定はNを取得する
     * @return 実績の取引の場合はY。予定はN
    **/
    public Boolean getActual() {
        return actual;
    }


    /**
     * 実績の取引の場合はY。予定はNを設定する
     * @param actual  実績の取引の場合はY。予定はN
    **/
    public void setActualInDB(String actual) {
        this.actual = ("Y".equalsIgnoreCase(actual));
    }

    /**
     * 実績の取引の場合はY。予定はNを設定する
     * @param actual  実績の取引の場合はY。予定はN
    **/
    public void setActual(Boolean actual) {
        this.actual = actual;
    }
    /**
     * 入力日
    **/ 
    private Date registerDate;
    public final static String REGISTER_DATE = "registerDate";

    /**
     * 入力日を取得する
     * @hibernate.property
     *    column="REGISTER_DATE"
     *    not-null="true"
     * @return 入力日
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Transaction.registerDate")
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * 入力日を設定する
     * @param registerDate  入力日
    **/
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    /**
     * 取引日
    **/ 
    private Date processDate;
    public final static String PROCESS_DATE = "processDate";

    /**
     * 取引日を取得する
     * @hibernate.property
     *    column="PROCESS_DATE"
     *    not-null="true"
     * @return 取引日
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Transaction.processDate")
    public Date getProcessDate() {
        return processDate;
    }

    /**
     * 取引日を設定する
     * @param processDate  取引日
    **/
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
//ForeignProperties.vm start.

    
//ForeignProperties.vm finish.
}
