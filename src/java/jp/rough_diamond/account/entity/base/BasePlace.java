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
     *    length="20"
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
     * 場所名
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * 場所名を取得する
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="256"
     * @return 場所名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="Place.name")
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

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadParents() {
        jp.rough_diamond.account.entity.Place parent = getParent();
        if(parent != null) {
            jp.rough_diamond.account.entity.Place tmp = parent.getParent();
            if(tmp != null) {
                Long pk = tmp.getId();
                parent.setParent(
                        jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Place.class, pk));
            }
        }
    }

    private jp.rough_diamond.account.entity.Owner owner;
    public final static String OWNER = "owner";

    /**
     * Get the associated Owner object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "OWNER_ID"
     *
     * @return the associated Owner object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.ownerId")
    public jp.rough_diamond.account.entity.Owner getOwner() {
        return this.owner;
    }

    /**
     * Declares an association between this object and a Owner object
     *
     * @param v Owner
     */
    public void setOwner(jp.rough_diamond.account.entity.Owner v) {
        this.owner = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadOwner() {
        jp.rough_diamond.account.entity.Owner owner = getOwner();
        if(owner != null) {
            Long pk = owner.getId();
            setOwner(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Owner.class, pk)
            );
        }
    }

//ForeignProperties.vm finish.
}
