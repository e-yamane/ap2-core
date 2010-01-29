/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * 場所コードのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="PLACE_CODE"
 *    realClass="jp.rough_diamond.account.entity.PlaceCode"
**/
public abstract class BasePlaceCode  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BasePlaceCode() {
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
        if(o instanceof BasePlaceCode) {
            if(hashCode() == o.hashCode()) {
                BasePlaceCode obj = (BasePlaceCode)o;
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
     * 場所コード情報
    **/ 
    private jp.rough_diamond.account.entity.Code codeInfo =  new jp.rough_diamond.account.entity.Code();

    public final static String CI = "codeInfo.";

    /**
     * 場所コード情報を取得する
     * @hibernate.component
     *    prefix="CI_"
     * @return 場所コード情報
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="PlaceCode.codeInfo")
    @jp.rough_diamond.commons.service.annotation.NestedComponent(property="PlaceCode.codeInfo")
    public jp.rough_diamond.account.entity.Code getCodeInfo() {
        return codeInfo;
    }

    /**
     * 場所コード情報を設定する
     * @param codeInfo  場所コード情報
    **/
    public void setCodeInfo(jp.rough_diamond.account.entity.Code codeInfo) {
        this.codeInfo = codeInfo;
    }

    /**
     * 更新日時情報
    **/ 
    private jp.rough_diamond.account.entity.UpdateTimestamp timeStamp =  new jp.rough_diamond.account.entity.UpdateTimestamp();

    public final static String TS = "timeStamp.";

    /**
     * 更新日時情報を取得する
     * @hibernate.component
     *    prefix="TS_"
     * @return 更新日時情報
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="PlaceCode.timeStamp")
    @jp.rough_diamond.commons.service.annotation.NestedComponent(property="PlaceCode.timeStamp")
    public jp.rough_diamond.account.entity.UpdateTimestamp getTimeStamp() {
        return timeStamp;
    }

    /**
     * 更新日時情報を設定する
     * @param timeStamp  更新日時情報
    **/
    public void setTimeStamp(jp.rough_diamond.account.entity.UpdateTimestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Place target;
    public final static String TARGET = "target";

    /**
     * Get the associated Place object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "PLACE_ID"
     *
     * @return the associated Place object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="PlaceCode.placeId")
    public jp.rough_diamond.account.entity.Place getTarget() {
        return this.target;
    }

    /**
     * Declares an association between this object and a Place object
     *
     * @param v Place
     */
    public void setTarget(jp.rough_diamond.account.entity.Place v) {
        this.target = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadTarget() {
        jp.rough_diamond.account.entity.Place target = getTarget();
        if(target != null) {
            Long pk = target.getId();
            setTarget(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Place.class, pk)
            );
        }
    }

//ForeignProperties.vm finish.
}
