/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * �\��g�����U�N�V������Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.joined-subclass-gg
 *    realClass="jp.rough_diamond.account.entity.PlanTransaction"
 *    table="PLAN_TRANSACTION"
 * @hibernate.joined-subclass-key
 *    column="ID"
**/
public abstract class BasePlanTransaction extends jp.rough_diamond.account.entity.Transaction implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BasePlanTransaction() {
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
        if(o instanceof BasePlanTransaction) {
            if(hashCode() == o.hashCode()) {
                BasePlanTransaction obj = (BasePlanTransaction)o;
                if(getId() == null) {
                    return super.equals(o);
                }
                return getId().equals(obj.getId());
            }
        }
        return false;
    }
    /**
     * ����ԍ�
    **/ 
    private String processId;
    public final static String PROCESS_ID = "processId";

    /**
     * ����ԍ����擾����
     * @hibernate.property
     *    column="PROCESS_ID"
     *    not-null="false"
     *    length="256"
     * @return ����ԍ�
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=256, property="PlanTransaction.processId")
    public String getProcessId() {
        return processId;
    }

    /**
     * ����ԍ���ݒ肷��
     * @param processId  ����ԍ�
    **/
    public void setProcessId(String processId) {
        this.processId = processId;
    }
//ForeignProperties.vm start.

    
//ForeignProperties.vm finish.
}
