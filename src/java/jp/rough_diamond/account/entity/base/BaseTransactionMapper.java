/*
 * Copyright (c) 2008, 2009
 *  Rough Diamond Co., Ltd.              -- http://www.rough-diamond.co.jp/
 *  Information Systems Institute, Ltd.  -- http://www.isken.co.jp/
 *  All rights reserved.
 */

package jp.rough_diamond.account.entity.base;

import  java.io.Serializable;



/**
 * �g�����U�N�V�����ϑJ�}�b�p�[��Hibernate�}�b�s���O�x�[�X�N���X
 * @hibernate.class
 *    table="TRANSACTION_MAPPER"
 *    realClass="jp.rough_diamond.account.entity.TransactionMapper"
**/
@jp.rough_diamond.commons.service.annotation.Unique(
    entity="TransactionMapper",
    groups= {
          @jp.rough_diamond.commons.service.annotation.Check(properties={
              "before"
            , "after"
        })
    }
)
public abstract class BaseTransactionMapper  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * �f�t�H���g�R���X�g���N�^
    **/
    public BaseTransactionMapper() {
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
        if(o instanceof BaseTransactionMapper) {
            if(hashCode() == o.hashCode()) {
                BaseTransactionMapper obj = (BaseTransactionMapper)o;
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
//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.Transaction before;
    public final static String BEFORE = "before";

    /**
     * Get the associated Transaction object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "BEFORE_TRANSACTION_ID"
     *
     * @return the associated Transaction object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="TransactionMapper.beforeTransactionId")
    public jp.rough_diamond.account.entity.Transaction getBefore() {
        return this.before;
    }

    /**
     * Declares an association between this object and a Transaction object
     *
     * @param v Transaction
     */
    public void setBefore(jp.rough_diamond.account.entity.Transaction v) {
        this.before = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadBefore() {
        jp.rough_diamond.account.entity.Transaction before = getBefore();
        if(before != null) {
            Long pk = before.getId();
            setBefore(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Transaction.class, pk)
            );
        }
    }

    private jp.rough_diamond.account.entity.Transaction after;
    public final static String AFTER = "after";

    /**
     * Get the associated Transaction object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "AFTER_TRANSACTION_ID"
     *
     * @return the associated Transaction object
     */
    public jp.rough_diamond.account.entity.Transaction getAfter() {
        return this.after;
    }

    /**
     * Declares an association between this object and a Transaction object
     *
     * @param v Transaction
     */
    public void setAfter(jp.rough_diamond.account.entity.Transaction v) {
        this.after = v;
    }

    @jp.rough_diamond.commons.service.annotation.PostLoad
    public void loadAfter() {
        jp.rough_diamond.account.entity.Transaction after = getAfter();
        if(after != null) {
            Long pk = after.getId();
            setAfter(
                    jp.rough_diamond.commons.service.BasicService.getService().findByPK(jp.rough_diamond.account.entity.Transaction.class, pk)
            );
        }
    }

//ForeignProperties.vm finish.
}
