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
 * コードのHibernateマッピングベースクラス
**/
public abstract class BaseCode  implements Serializable {
   private static final long serialVersionUID = 1L;
    /**
     * デフォルトコンストラクタ
    **/
    public BaseCode() {
    }
    /**
     * コード
    **/ 
    private String code;
    public final static String CODE = "code";

    /**
     * コードを取得する
     * @hibernate.property
     *    column="CODE"
     *    not-null="true"
     *    length="1024"
     * @return コード
    **/
    @jp.rough_diamond.commons.service.annotation.MaxLength(length=1024, property="Code.code")
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Code.code")
    public String getCode() {
        return code;
    }

    /**
     * コードを設定する
     * @param code  コード
    **/
    public void setCode(String code) {
        this.code = code;
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
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Code.revision")
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
     * 有効開始日時
    **/ 
    private Date validDate;
    public final static String VALID_DATE = "validDate";

    /**
     * 有効開始日時を取得する
     * @hibernate.property
     *    column="VALID_DATE"
     *    not-null="false"
     * @return 有効開始日時
    **/
    public Date getValidDate() {
        return validDate;
    }

    /**
     * 有効開始日時を設定する
     * @param validDate  有効開始日時
    **/
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    /**
     * 無効開始日時
    **/ 
    private Date invalidDate;
    public final static String INVALID_DATE = "invalidDate";

    /**
     * 無効開始日時を取得する
     * @hibernate.property
     *    column="INVALID_DATE"
     *    not-null="false"
     * @return 無効開始日時
    **/
    public Date getInvalidDate() {
        return invalidDate;
    }

    /**
     * 無効開始日時を設定する
     * @param invalidDate  無効開始日時
    **/
    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }
//ForeignProperties.vm start.

    
    private jp.rough_diamond.account.entity.CodeSystem codeSystem;
    public final static String CODESYSTEM = "codeSystem";

    /**
     * Get the associated CodeSystem object
     * @hibernate.many-to-one
     *   outer-join = "true"
     * @hibernate.column name = "CODE_SYSTEM_ID"
     *
     * @return the associated CodeSystem object
     */
    @jp.rough_diamond.commons.service.annotation.NotNull(property="Code.codeSystemId")
    public jp.rough_diamond.account.entity.CodeSystem getCodeSystem() {
        if(jp.rough_diamond.commons.service.BasicService.isProxy(this.codeSystem)) {
            this.codeSystem = jp.rough_diamond.commons.service.BasicService.getService().replaceProxy(this.codeSystem);
        }
        return this.codeSystem;
    }

    /**
     * Declares an association between this object and a CodeSystem object
     *
     * @param v CodeSystem
     */
    public void setCodeSystem(jp.rough_diamond.account.entity.CodeSystem v) {
        this.codeSystem = v;
    }

//ForeignProperties.vm finish.


}
