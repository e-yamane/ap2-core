package jp.rough_diamond.account.entity;

import jp.rough_diamond.account.Resetter;
import jp.rough_diamond.commons.testing.DBInitializer;
import jp.rough_diamond.framework.service.ServiceLocator;

public class MasterLoader extends DBInitializer {
	/**
	 * インスタンス
	 */
	public final static DBInitializer INSTANCE = ServiceLocator.getService(MasterLoader.class);

	/**
	 * 初期処理
	 * @throws Exception
	 */
    public static void init() throws Exception {
        Resetter.reset();
        INSTANCE.load();
    }

    /**
     * ロードするファイル名群
     */
    final static String[] NAMES = new String[]{
        "jp/rough_diamond/account/entity/OWNER.xls",    	
        "jp/rough_diamond/account/entity/ITEM.xls",
        "jp/rough_diamond/account/entity/PLACE.xls",
        "jp/rough_diamond/account/entity/ACCOUNT.xls",
    };

    public static String[] getNames() {
		return NAMES;
    }
    
    @Override
	protected String[] getResourceNames() {
    	return getNames();
	}
}
