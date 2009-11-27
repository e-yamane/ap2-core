/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * �ꏊ��Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="PLACE"
 *    realClass="jp.rough_diamond.account.entity.Place"
**/
public abstract class BasePlace  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BasePlace() {
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
     * �ꏊ��
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * �ꏊ�����擾����
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="256"
     * @return �ꏊ��
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="Place.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.name")
    public String getName() {
        return name;
    }

    /**
     * �ꏊ����ݒ肷��
     * @param name  �ꏊ��
    **/
    public void setName(String name) {
        this.name = name;
    }
    /**
     * �_���I�ȏꏊ
    **/ 
    private Boolean virtual = Boolean.FALSE;
    public final static String VIRTUAL = "virtualInDB";

    /**
     * �_���I�ȏꏊ���擾����
     * @hibernate.property
     *    column="VIRTUAL"
     *    not-null="true"
     * @return �_���I�ȏꏊ
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=1, property="Place.virtual")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Place.virtual")
    public String getVirtualInDB() {
        return (virtual) ? "Y" : "N";
    }

    /**
     * �_���I�ȏꏊ���擾����
     * @return �_���I�ȏꏊ
    **/
    public Boolean isVirtual() {
        return virtual;
    }

    /**
     * �_���I�ȏꏊ���擾����
     * @return �_���I�ȏꏊ
    **/
    public Boolean getVirtual() {
        return virtual;
    }


    /**
     * �_���I�ȏꏊ��ݒ肷��
     * @param virtual  �_���I�ȏꏊ
    **/
    public void setVirtualInDB(String virtual) {
        this.virtual = ("Y".equalsIgnoreCase(virtual));
    }

    /**
     * �_���I�ȏꏊ��ݒ肷��
     * @param virtual  �_���I�ȏꏊ
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
