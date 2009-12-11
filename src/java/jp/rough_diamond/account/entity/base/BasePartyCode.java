/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * パーティコードのHibernateマッピングベースクラス
 * @hibernate.class
 *    table="PARTY_CODE"
 *    realClass="jp.rough_diamond.account.entity.PartyCode"
**/
public abstract class BasePartyCode  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BasePartyCode() {
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
        if(o instanceof BasePartyCode) {
            if(hashCode() == o.hashCode()) {
                BasePartyCode obj = (BasePartyCode)o;
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
     * パーティコード情報
    **/ 
    private jp.rough_diamond.account.entity.Code codeInfo =  new jp.rough_diamond.account.entity.Code();

    public final static String CI = "codeInfo.";

    /**
     * パーティコード情報を取得する
     * @hibernate.component
     *    prefix="CI_"
     * @return パーティコード情報
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="PartyCode.codeInfo")
    @jp.rough_diamond.commons.service.annotation.NestedComponent(property="PartyCode.codeInfo")
    public jp.rough_diamond.account.entity.Code getCodeInfo() {
        return codeInfo;
    }

    /**
     * パーティコード情報を設定する
     * @param codeInfo  パーティコード情報
    **/
    public void setCodeInfo(jp.rough_diamond.account.entity.Code codeInfo) {
        this.codeInfo = codeInfo;
    }

//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Party target;
    public final static String TARGET = "target";

    /**
     * Get the associated Party object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "PARTY_ID"
     *
     * @return the associated Party object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="PartyCode.partyId")
    public jp.rough_diamond.account.entity.Party getTarget() {
        return this.target;
    }

    /**
     * Declares an association between this object and a Party object
     *
     * @param v Party
     */
    public void setTarget(jp.rough_diamond.account.entity.Party v) {
        this.target = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadTarget() {
        jp.rough_diamond.account.entity.Party target = getTarget();
        if(target != null) {
            Long pk = target.getId();
            setTarget(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Party.class, pk)
            );
        }
    }

//ForeignProperties.vm finish.
}
