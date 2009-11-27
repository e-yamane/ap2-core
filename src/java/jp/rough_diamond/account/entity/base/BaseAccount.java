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
 * �����Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="ACCOUNT"
 *    realClass="jp.rough_diamond.account.entity.Account"
**/
@jp.rough_diamond.commons.service.annotation.Unique(
    entity="Account",
    groups= {
          @jp.rough_diamond.commons.service.annotation.Check(properties={
              "place"
            , "item"
            , "owner"
        })
    }
)
public abstract class BaseAccount  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BaseAccount() {
    }

    /**
     * OID
    **/ 
    private Long id;
    public final static String ID = "id";
    /**
     * OID���擾����
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
     * OID��ݒ肷��
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
        if(o instanceof BaseAccount) {
            if(hashCode() == o.hashCode()) {
                BaseAccount obj = (BaseAccount)o;
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
     * �I�u�W�F�N�g���i��������
     * �i�������[���͈ȉ��̒ʂ�ł��B
     * <ul>
     *   <li>new��������̃I�u�W�F�N�g�̏ꍇ��insert</li>
     *   <li>load���ꂽ�I�u�W�F�N�g�̏ꍇ��update</li>
     *   <li>load���ꂽ�I�u�W�F�N�g�ł���L�[�������ւ����ꍇ��insert</li>
     *   <li>insert�����I�u�W�F�N�g���ēxsave�����ꍇ��update</li>
     *   <li>setLoadingFlag���\�b�h���Ăяo�����ꍇ�͋����I��update�i�񐄏��j</li>
     * </ul>
     * @throws VersionUnmuchException   �y�ϓI���b�L���O�G���[
     * @throws MessagesIncludingException ���ؗ�O
    **/
    public void save() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        if(isLoaded) {
            update();
        } else {
            insert();
        }
    }

    /**
     * �I�u�W�F�N�g���i��������
     * @throws MessagesIncludingException ���ؗ�O
    **/
    protected void insert() throws jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().insert(this);
    }

    /**
     * �i�����I�u�W�F�N�g���X�V����
     * @throws MessagesIncludingException ���ؗ�O
     * @throws VersionUnmuchException   �y�ϓI���b�L���O�G���[
    **/
    protected void update() throws jp.rough_diamond.framework.transaction.VersionUnmuchException, jp.rough_diamond.commons.resource.MessagesIncludingException {
        jp.rough_diamond.commons.service.BasicService.getService().update(this);
    }
    /**
     * ����쐬��
    **/ 
    private Date registerDate;
    public final static String REGISTER_DATE = "registerDate";

    /**
     * ����쐬�����擾����
     * @hibernate.property
     *    column="REGISTER_DATE"
     *    not-null="true"
     * @return ����쐬��
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Account.registerDate")
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * ����쐬����ݒ肷��
     * @param registerDate  ����쐬��
    **/
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Place place;
    public final static String PLACE = "place";

    /**
     * Get the associated Place object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "PLACE_ID"
     *
     * @return the associated Place object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Account.placeId")
    public jp.rough_diamond.account.entity.Place getPlace() {
        return this.place;
    }

    /**
     * Declares an association between this object and a Place object
     *
     * @param v Place
     */
    public void setPlace(jp.rough_diamond.account.entity.Place v) {
        this.place = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadPlace() {
        jp.rough_diamond.account.entity.Place place = getPlace();
        if(place != null) {
            Long pk = place.getId();
            setPlace(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Place.class, pk)
            );
        }
    }

    private jp.rough_diamond.account.entity.Item item;
    public final static String ITEM = "item";

    /**
     * Get the associated Item object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "ITEM_ID"
     *
     * @return the associated Item object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Account.itemId")
    public jp.rough_diamond.account.entity.Item getItem() {
        return this.item;
    }

    /**
     * Declares an association between this object and a Item object
     *
     * @param v Item
     */
    public void setItem(jp.rough_diamond.account.entity.Item v) {
        this.item = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadItem() {
        jp.rough_diamond.account.entity.Item item = getItem();
        if(item != null) {
            Long pk = item.getId();
            setItem(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Item.class, pk)
            );
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
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Account.ownerId")
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
