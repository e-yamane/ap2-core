/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * �i�ڂ�Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="ITEM"
 *    realClass="jp.rough_diamond.account.entity.Item"
**/
public abstract class BaseItem  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BaseItem() {
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
     * �i�ږ�
    **/ 
    private String name;
    public final static String NAME = "name";

    /**
     * �i�ږ����擾����
     * @hibernate.property
     *    column="NAME"
     *    not-null="true"
     *    length="256"
     * @return �i�ږ�
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="Item.name")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Item.name")
    public String getName() {
        return name;
    }

    /**
     * �i�ږ���ݒ肷��
     * @param name  �i�ږ�
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
