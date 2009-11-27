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
 * �g�����U�N�V������Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="TRANSACTION"
 *    realClass="jp.rough_diamond.account.entity.Transaction"
**/
public abstract class BaseTransaction  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BaseTransaction() {
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
     * ���т̎���̏ꍇ��Y�B�\���N
    **/ 
    private Boolean actual = Boolean.FALSE;
    public final static String ACTUAL = "actualInDB";

    /**
     * ���т̎���̏ꍇ��Y�B�\���N���擾����
     * @hibernate.property
     *    column="ACTUAL"
     *    not-null="true"
     * @return ���т̎���̏ꍇ��Y�B�\���N
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=1, property="Transaction.actual")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Transaction.actual")
    public String getActualInDB() {
        return (actual) ? "Y" : "N";
    }

    /**
     * ���т̎���̏ꍇ��Y�B�\���N���擾����
     * @return ���т̎���̏ꍇ��Y�B�\���N
    **/
    public Boolean isActual() {
        return actual;
    }

    /**
     * ���т̎���̏ꍇ��Y�B�\���N���擾����
     * @return ���т̎���̏ꍇ��Y�B�\���N
    **/
    public Boolean getActual() {
        return actual;
    }


    /**
     * ���т̎���̏ꍇ��Y�B�\���N��ݒ肷��
     * @param actual  ���т̎���̏ꍇ��Y�B�\���N
    **/
    public void setActualInDB(String actual) {
        this.actual = ("Y".equalsIgnoreCase(actual));
    }

    /**
     * ���т̎���̏ꍇ��Y�B�\���N��ݒ肷��
     * @param actual  ���т̎���̏ꍇ��Y�B�\���N
    **/
    public void setActual(Boolean actual) {
        this.actual = actual;
    }
    /**
     * ���͓�
    **/ 
    private Date registerDate;
    public final static String REGISTER_DATE = "registerDate";

    /**
     * ���͓����擾����
     * @hibernate.property
     *    column="REGISTER_DATE"
     *    not-null="true"
     * @return ���͓�
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Transaction.registerDate")
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * ���͓���ݒ肷��
     * @param registerDate  ���͓�
    **/
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    /**
     * �����
    **/ 
    private Date processDate;
    public final static String PROCESS_DATE = "processDate";

    /**
     * ��������擾����
     * @hibernate.property
     *    column="PROCESS_DATE"
     *    not-null="true"
     * @return �����
    **/
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Transaction.processDate")
    public Date getProcessDate() {
        return processDate;
    }

    /**
     * �������ݒ肷��
     * @param processDate  �����
    **/
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
//ForeignProperties.vm start.

    
//ForeignProperties.vm finish.
}
