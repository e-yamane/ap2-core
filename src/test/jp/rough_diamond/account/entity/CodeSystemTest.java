package jp.rough_diamond.account.entity;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class CodeSystemTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		NumberingLoader.init();
	}

	public void testGetCodeSystemByName() throws Exception {
		CodeSystem cs = CodeSystem.getCodeSystemByName("帝国データバンク");
		assertEquals("返却値が誤っています。", 1L, cs.getId().longValue());
		
		cs = CodeSystem.getCodeSystemByName("存在しないコード体系");
		assertNull("なんか返却されています。", cs);
	}
}
