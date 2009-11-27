package jp.rough_diamond.account.entity;

import jp.rough_diamond.commons.testing.DBInitializer;
import jp.rough_diamond.framework.service.ServiceLocator;

public class TransactionLoader extends DBInitializer {
	/**
	 * �C���X�^���X
	 */
	public final static DBInitializer INSTANCE = ServiceLocator.getService(TransactionLoader.class);

	/**
	 * ��������
	 * @throws Exception
	 */
    public static void init() throws Exception {
    	MasterLoader.init();
    	INSTANCE.load();
    }

    /**
     * ���[�h����t�@�C�����Q
     */
    final static String[] NAMES = new String[]{
        "jp/rough_diamond/account/entity/TRANSACTION.xls",
        "jp/rough_diamond/account/entity/PLAN_TRANSACTION.xls",
        "jp/rough_diamond/account/entity/ACTUAL_TRANSACTION.xls",
        "jp/rough_diamond/account/entity/ENTRY.xls",
        "jp/rough_diamond/account/entity/TRANSACTION_MAPPER.xls",
    };

    public static String[] getNames() {
    	return NAMES;
    }
    
    @Override
	protected String[] getResourceNames() {
		return getNames();
	}
}
