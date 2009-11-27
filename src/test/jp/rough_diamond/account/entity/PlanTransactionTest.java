package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

@SuppressWarnings("deprecation")
public class PlanTransactionTest extends DataLoadingTestCase {
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testGetAll() throws Exception {
		List<PlanTransaction> list = PlanTransaction.getAll();

		assertEquals("件数が誤まっています。", 7, list.size());

		assertEquals("IDが誤まっています。", 10L, list.get(0).getId().longValue());
		assertEquals("IDが誤まっています。", 9L,  list.get(1).getId().longValue());
		assertEquals("IDが誤まっています。", 8L,  list.get(2).getId().longValue());
		assertEquals("IDが誤まっています。", 7L,  list.get(3).getId().longValue());
		assertEquals("IDが誤まっています。", 5L,  list.get(4).getId().longValue());
		assertEquals("IDが誤まっています。", 4L,  list.get(5).getId().longValue());
		assertEquals("IDが誤まっています。", 3L,  list.get(6).getId().longValue());
	}
	
	public void testInsert() throws Exception {
		PlanTransaction pt = new PlanTransaction();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		pt.setProcessDate(sdf.parse("20080827101112"));
		pt.setProcessId("TEST-1");
		pt.setRegisterDate(sdf.parse("20070827101112"));
		Entry e = new Entry();
		BasicService service = BasicService.getService();
		e.setAccount(service.findByPK(Account.class, 1L));
		e.setQuantity(10L);
		pt.setEntries(Arrays.asList(new Entry[]{e}));
		service.insert(pt);
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE), pt));
		List<TransactionMapper> list = service.findByExtractor(ex);
		assertEquals("TransactionMapperが生成されていません。", 1, list.size());
		assertNull("Afterがnullになっていません。", list.get(0).getAfter());
	}
	
	public void testInsertAndChain() throws Exception {
		PlanTransaction pt = new PlanTransaction();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		pt.setProcessDate(sdf.parse("20080827101112"));
		pt.setProcessId("TEST-1");
		pt.setRegisterDate(sdf.parse("20070827101112"));
		Entry e = new Entry();
		BasicService service = BasicService.getService();
		e.setAccount(service.findByPK(Account.class, 1L));
		e.setQuantity(10L);
		pt.setEntries(Arrays.asList(new Entry[]{e}));

		TransactionMapper tm = service.findByPK(TransactionMapper.class, 10L);
		tm.setAfter(pt);
		
		service.update(tm);
		
		Transaction t = service.findByPK(Transaction.class, pt.getId());
		assertNotNull("正しく保存されています。", t);
		
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE), pt));
		List<TransactionMapper> list = service.findByExtractor(ex);
		assertEquals("TransactionMapperが生成されていません。", 1, list.size());
		assertNull("Afterがnullになっていません。", list.get(0).getAfter());
	}
	
	public void testIsNewestTransaction() throws Exception {
		PlanTransaction pt = BasicService.getService().findByPK(PlanTransaction.class, 3L);
		assertTrue("最新じゃないと言われています。", pt.isNewestTransaction());
		pt = BasicService.getService().findByPK(PlanTransaction.class, 5L);
		assertFalse("最新だと言われています。", pt.isNewestTransaction());
	}

	public void testGetNewestTransactionExtractorBase() {
		Extractor ex = Transaction.getNewestTransactionExtractorBase(ActualTransaction.class);
		assertEquals("Targetが誤っています。", ActualTransaction.class, ex.target);

		Extractor ex2 = Transaction.getNewestTransactionExtractorBase(Transaction.class);
		assertEquals("Targetが誤っています。", Transaction.class, ex2.target);
		List<Transaction> list = BasicService.getService().findByExtractor(ex2);
		assertEquals("最新データのみ取得できていません。", 9, list.size());
	}
}
