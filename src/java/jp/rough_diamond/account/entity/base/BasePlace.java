/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * 場所のHibernateマッピングベースクラス
 * @hibernate.class
 *    table="PLACE"
 *    realClass="jp.rough_diamond.account.entity.Place"
**/
@jp.rough_diamond.commons.service.annotation.Unique(
    entity="Place",
    groups= {
          @jp.rough_diamond.commons.service.annotation.Check(properties={
              "placeCode"
        })
    }
)
public abstract class BasePlace  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BasePlace() {
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
        if(o instanceof BasePlace) {
            if(hashCode() == o.hashCode()) {
                BasePlace obj = (BasePlace)o;
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
     * 内部管理用場所コード
    **/ 
    private String placeCode;
    public final static String PLACE_CODE = "placeCode";

    /**
     * 内部管理用場所コードを取得する
     * @hibernate.property
     *    column="PLACE_CODE"
     *    not-null="true"
     *    length="255"
     * @return 内部管理用場所コード
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=255, property="Place.placeCode")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.placeCode")
    public String getPlaceCode() {
        return placeCode;
    }

    /**
     * 内部管理用場所コードを設定する
     * @param placeCode  内部管理用場所コード
    **/
    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }
    /**
     * 場所名
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * 場所名を取得する
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="255"
     * @return 場所名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=255, property="Place.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.name")
    public String getName() {
        return name;
    }

    /**
     * 場所名を設定する
     * @param name  場所名
    **/
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 論理的な場所
    **/ 
    private Boolean virtual = Boolean.FALSE;
    public final static String VIRTUAL = "virtualInDB";

    /**
     * 論理的な場所を取得する
     * @hibernate.property
     *    column="VIRTUAL"
     *    not-null="true"
     * @return 論理的な場所
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=1, property="Place.virtual")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.virtual")
    public String getVirtualInDB() {
        return (virtual) ? "Y" : "N";
    }

    /**
     * 論理的な場所を取得する
     * @return 論理的な場所
    **/
    public Boolean isVirtual() {
        return virtual;
    }

    /**
     * 論理的な場所を取得する
     * @return 論理的な場所
    **/
    public Boolean getVirtual() {
        return virtual;
    }


    /**
     * 論理的な場所を設定する
     * @param virtual  論理的な場所
    **/
    public void setVirtualInDB(String virtual) {
        this.virtual = ("Y".equalsIgnoreCase(virtual));
    }

    /**
     * 論理的な場所を設定する
     * @param virtual  論理的な場所
    **/
    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }
    /**
     * リビジョン
    **/ 
    private Long revision;
    public final static String REVISION = "revision";

    /**
     * リビジョンを取得する
     * @hibernate.property
     *    column="REVISION"
     *    not-null="true"
     * @return リビジョン
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.revision")
    public Long getRevision() {
        return revision;
    }

    /**
     * リビジョンを設定する
     * @param revision  リビジョン
    **/
    public void setRevision(Long revision) {
        this.revision = revision;
    }
    /**
     * ステータスコード
    **/ 
    private String statusCode;
    public final static String STATUS_CODE = "statusCode";

    /**
     * ステータスコードを取得する
     * @hibernate.property
     *    column="STATUS_CODE"
     *    not-null="true"
     *    length="2"
     * @return ステータスコード
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=2, property="Place.statusCode")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * ステータスコードを設定する
     * @param statusCode  ステータスコード
    **/
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * 更新日時情報
    **/ 
    private jp.rough_diamond.commons.entity.UpdateTimestamp timeStamp =  new jp.rough_diamond.commons.entity.UpdateTimestamp();

    public final static String TS = "timeStamp.";

    /**
     * 更新日時情報を取得する
     * @hibernate.component
     *    prefix="TS_"
     * @return 更新日時情報
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.timeStamp")
    @jp.rough_diamond.commons.service.annotation.NestedComponent(property="Place.timeStamp")
    public jp.rough_diamond.commons.entity.UpdateTimestamp getTimeStamp() {
        return timeStamp;
    }

    /**
     * 更新日時情報を設定する
     * @param timeStamp  更新日時情報
    **/
    public void setTimeStamp(jp.rough_diamond.commons.entity.UpdateTimestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Place parent;
    public final static String PARENT = "parent";

    /**
     * Get the associated Place object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "PARENT_ID"
     *
     * @return the associated Place object
     */
    public jp.rough_diamond.account.entity.Place getParent() {
        if(jp.rough_diamond.commons.service.BasicService.isProxy(this.parent)) {
            this.parent = jp.rough_diamond.commons.service.BasicService.getService().replaceProxy(this.parent);
        }
        return this.parent;
    }

    /**
     * Declares an association between this object and a Place object
     *
     * @param v Place
     */
    public void setParent(jp.rough_diamond.account.entity.Place v) {
        this.parent = v;
    }

    private jp.rough_diamond.account.entity.Party owner;
    public final static String OWNER = "owner";

    /**
     * Get the associated Party object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "OWNER_ID"
     *
     * @return the associated Party object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.ownerId")
    public jp.rough_diamond.account.entity.Party getOwner() {
        if(jp.rough_diamond.commons.service.BasicService.isProxy(this.owner)) {
            this.owner = jp.rough_diamond.commons.service.BasicService.getService().replaceProxy(this.owner);
        }
        return this.owner;
    }

    /**
     * Declares an association between this object and a Party object
     *
     * @param v Party
     */
    public void setOwner(jp.rough_diamond.account.entity.Party v) {
        this.owner = v;
    }

//ForeignProperties.vm finish.
}
