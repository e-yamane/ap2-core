package jp.rough_diamond.account.entity;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class EntryTest extends DataLoadingTestCase {
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testGetBalance() throws Exception {
		Entry e = BasicService.getService().findByPK(Entry.class, 7L);
		assertEquals("écêîÇ™åÎÇ¡ÇƒÇ¢Ç‹Ç∑ÅB", 20L, e.getBalance().longValue());
	}
}
