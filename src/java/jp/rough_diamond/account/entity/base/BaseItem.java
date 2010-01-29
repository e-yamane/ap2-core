/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * 品目のHibernateマッピングベースクラス
 * @hibernate.class
 *    table="ITEM"
 *    realClass="jp.rough_diamond.account.entity.Item"
**/
public abstract class BaseItem  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseItem() {
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
        if(o instanceof BaseItem) {
            if(hashCode() == o.hashCode()) {
                BaseItem obj = (BaseItem)o;
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
     * 品目名
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * 品目名を取得する
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="256"
     * @return 品目名
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="Item.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Item.name")
    public String getName() {
        return name;
    }

    /**
     * 品目名を設定する
     * @param name  品目名
    **/
    public void setName(String name) {
        this.name = name;
    }
//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Item parent;
    public final static String PARENT = "parent";

    /**
     * Get the associated Item object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "PARENT_ID"
     *
     * @return the associated Item object
     */
    public jp.rough_diamond.account.entity.Item getParent() {
        return this.parent;
    }

    /**
     * Declares an association between this object and a Item object
     *
     * @param v Item
     */
    public void setParent(jp.rough_diamond.account.entity.Item v) {
        this.parent = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadParents() {
        jp.rough_diamond.account.entity.Item parent = getParent();
        if(parent != null) {
            jp.rough_diamond.account.entity.Item tmp = parent.getParent();
            if(tmp != null) {
                Long pk = tmp.getId();
                parent.setParent(
                        jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Item.class, pk));
            }
        }
    }

//ForeignProperties.vm finish.
}
