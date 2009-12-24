/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * パーティのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="PARTY"
 *    realClass="jp.rough_diamond.account.entity.Party"
**/
@jp.rough_diamond.commons.service.annotation.Unique(
    entity="Party",
    groups= {
          @jp.rough_diamond.commons.service.annotation.Check(properties={
              "partyCode"
        })
    }
)
public abstract class BaseParty  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseParty() {
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
        if(o instanceof BaseParty) {
            if(hashCode() == o.hashCode()) {
                BaseParty obj = (BaseParty)o;
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
     * 内部管理用パーティコード
    **/ 
    private String partyCode;
    public final static String PARTY_CODE = "partyCode";

    /**
     * 内部管理用パーティコードを取得する
     * @hibernate.property
     *    column="PARTY_CODE"
     *    not-null="true"
     *    length="256"
     * @return 内部管理用パーティコード
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="Party.partyCode")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Party.partyCode")
    public String getPartyCode() {
        return partyCode;
    }

    /**
     * 内部管理用パーティコードを設定する
     * @param partyCode  内部管理用パーティコード
    **/
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }
    /**
     * パーティ名
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * パーティ名を取得する
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="256"
     * @return パーティ名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="Party.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Party.name")
    public String getName() {
        return name;
    }

    /**
     * パーティ名を設定する
     * @param name  パーティ名
    **/
    public void setName(String name) {
        this.name = name;
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
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Party.revision")
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
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=2, property="Party.statusCode")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Party.statusCode")
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
    private jp.rough_diamond.account.entity.UpdateTimestamp timeStamp =  new jp.rough_diamond.account.entity.UpdateTimestamp();

    public final static String TS = "timeStamp.";

    /**
     * 更新日時情報を取得する
     * @hibernate.component
     *    prefix="TS_"
     * @return 更新日時情報
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Party.timeStamp")
    @jp.rough_diamond.commons.service.annotation.NestedComponent(property="Party.timeStamp")
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

    
//ForeignProperties.vm finish.
}
