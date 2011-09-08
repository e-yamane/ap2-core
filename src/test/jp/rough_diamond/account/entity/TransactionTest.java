package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Count;
import jp.rough_diamond.commons.extractor.DateToString;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;
import jp.rough_diamond.framework.service.Service;
import jp.rough_diamond.framework.service.ServiceLocator;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

public class TransactionTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testSaveWhenUpdate() throws Exception {
		Extractor tCountEx = new Extractor(Transaction.class);
		long tCount = BasicService.getService().getCountByExtractor(tCountEx);
		Extractor eCountEx = new Extractor(Entry.class);
		long eCount = BasicService.getService().getCountByExtractor(eCountEx);
		Transaction t = doInsert();
		assertEquals("Transactionの件数が誤っています。", tCount + 1, BasicService.getService().getCountByExtractor(tCountEx));
		assertEquals("Entryの件数が誤っています。", eCount + 2, BasicService.getService().getCountByExtractor(eCountEx));
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE + "." + Transaction.ID), 10L));
		ex.add(Condition.eq(new Property(TransactionMapper.AFTER), t));
		assertEquals("TransactionMapperが登録されていません", 1, BasicService.getService().getCountByExtractor(ex));
	}
	
	public void testSaveWhenUpdateInTransaction() throws Exception {
		Extractor tCountEx = new Extractor(Transaction.class);
		long tCount = BasicService.getService().getCountByExtractor(tCountEx);
		Extractor eCountEx = new Extractor(Entry.class);
		long eCount = BasicService.getService().getCountByExtractor(eCountEx);
		Transaction t = ServiceLocator.getService(TransactionTestService.class).doIt();
		assertEquals("Transactionの件数が誤っています。", tCount + 1, BasicService.getService().getCountByExtractor(tCountEx));
		assertEquals("Entryの件数が誤っています。", eCount + 2, BasicService.getService().getCountByExtractor(eCountEx));
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE + "." + Transaction.ID), 10L));
		ex.add(Condition.eq(new Property(TransactionMapper.AFTER), t));
		assertEquals("TransactionMapperが登録されていません", 1, BasicService.getService().getCountByExtractor(ex));
	}
	
	static Transaction doInsert() throws VersionUnmuchException, MessagesIncludingException {
		Transaction t1 = BasicService.getService().findByPK(Transaction.class, 1L);
		System.out.println(t1.getRegisterDate());
		Transaction t = BasicService.getService().findByPK(Transaction.class, 10L);
		t.getFromEntries().get(0).setQuantityValue(-20L);
		t.getToEntries().get(0).setQuantityValue(20L);
		t.save();
		return t;
	}
	
	public void test読み込み直後に何もせずに保存すてもEntryとの関係は保たれていること() throws Exception {
		Transaction t1 = BasicService.getService().findByPK(Transaction.class, 1L);
		assertEquals("Entryの個数が誤っています。", 2, t1.getEntries().size());
		t1 = BasicService.getService().findByPK(Transaction.class, 1L);
		t1.save();
		t1 = BasicService.getService().findByPK(Transaction.class, t1.getId());
		assertEquals("Entryの個数が誤っています。", 2, t1.getEntries().size());
	}
	
	public static class TransactionTestService implements Service {
		public Transaction doIt() throws VersionUnmuchException, MessagesIncludingException {
			return doInsert();
		}
	}

	public void testInsert() throws Exception {
		Transaction pt = new Transaction();
		pt.setActual(false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		pt.setProcessDate(sdf.parse("20080827101112"));
		pt.setRegisterDate(sdf.parse("20070827101112"));
		Entry e = new Entry();
		BasicService service = BasicService.getService();
		e.setAccount(service.findByPK(Account.class, 1L));
		e.setQuantityValue(10L);
		pt.setEntries(Arrays.asList(new Entry[]{e}));
		service.insert(pt);
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE), pt));
		List<TransactionMapper> list = service.findByExtractor(ex);
		assertEquals("TransactionMapperが生成されていません。", 1, list.size());
		assertNull("Afterがnullになっていません。", list.get(0).getAfter());
	}

	public void testInsertAndChain() throws Exception {
		Transaction pt = new Transaction();
		pt.setActual(false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		pt.setProcessDate(sdf.parse("20080827101112"));
		pt.setRegisterDate(sdf.parse("20070827101112"));
		Entry e = new Entry();
		BasicService service = BasicService.getService();
		e.setAccount(service.findByPK(Account.class, 1L));
		e.setQuantityValue(10L);
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
		Transaction pt = BasicService.getService().findByPK(Transaction.class, 3L);
		assertTrue("最新じゃないと言われています。", pt.isNewestTransaction());
		pt = BasicService.getService().findByPK(Transaction.class, 5L);
		assertFalse("最新だと言われています。", pt.isNewestTransaction());
	}

	public void testGetNewestTransactionExtractorBase() {
		Extractor ex2 = Transaction.getNewestTransactionExtractorBase(Transaction.class);
		assertEquals("Targetが誤っています。", Transaction.class, ex2.target);
		List<Transaction> list = BasicService.getService().findByExtractor(ex2);
		assertEquals("最新データのみ取得できていません。", 9, list.size());
	}

	public void testトランザクション内で読み込んだTransactionと違うTransactionを永続化できる事() throws Exception {
		Transaction t = BasicService.getService().findByPK(Transaction.class, 1L);
		t.setActual(false);
		ServiceLocator.getService(トランザクション内で読み込んだTransactionと違うTransactionを永続化するService.class).testIt(t);
//		t.save();
		t = BasicService.getService().findByPK(Transaction.class, t.getId());
		assertFalse("予実フラグが変化していません", t.isActual());
	}
	
	public static class トランザクション内で読み込んだTransactionと違うTransactionを永続化するService implements Service {
		public void testIt(Transaction t) throws VersionUnmuchException, MessagesIncludingException {
			@SuppressWarnings("unused")
			Transaction t2 = BasicService.getService().findByPK(Transaction.class, 1L);
			t.save();
		}
	}
	
	//これはFrameworkで検証できなかったためテストとしてこっとに加えてみた
	public void test条件に日付を加えて集計関数を使用した結果の件数を取得してみる() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080501");
		Extractor ex = new Extractor(Transaction.class);
		ex.add(Condition.gt(new Property(Transaction.REGISTER_DATE), d));
		ex.addExtractValue(new ExtractValue("date", new Property(Transaction.REGISTER_DATE)));
		ex.addExtractValue(new ExtractValue("count", new Count()));
		assertEquals("返却数が誤っています。", 4, BasicService.getService().getCountByExtractor(ex));
	}

	public void testDateToString() throws Exception {
		Extractor ex = new Extractor(Transaction.class);
		ex.add(Condition.eq(new Property(Transaction.ID), 1L));
		ex.addExtractValue(new ExtractValue("registerDate", new DateToString(new Property(Transaction.REGISTER_DATE), "yyyy/MM/dd HH:mm:ss.SSS")));
		ex.setReturnType(String.class);
		List<String> list = BasicService.getService().findByExtractor(ex);
		assertEquals("返却数が誤っています。", 1, list.size());
		assertEquals("返却値が誤っています。", "2008/04/10 01:02:03.456", list.get(0));

		ex = new Extractor(Transaction.class);
		ex.add(Condition.eq(new Property(Transaction.ID), 1L));
		ex.addExtractValue(new ExtractValue("registerDate", new DateToString(new Property(Transaction.REGISTER_DATE), "yyyy/M/d H:m:s.S")));
		ex.setReturnType(String.class);
		list = BasicService.getService().findByExtractor(ex);
		assertEquals("返却数が誤っています。", 1, list.size());
		assertEquals("返却値が誤っています。", "2008/4/10 1:2:3.456", list.get(0));
	}
	
//TODO ActualTransactionの遺品。性能劣化が多分著しいので一端コメントアウト
//	public void testVerify() throws Exception {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Date d = sdf.parse("20080421");
//		BasicService service = BasicService.getService();
//		Account from = service.findByPK(Account.class, 3L);
//		Account to = service.findByPK(Account.class, 5L);
//		Transaction at = makeTransaction(from, to, 70, d);
//		Messages msgs = service.validate(at, WhenVerifier.INSERT);
//		System.out.println(msgs);
//		assertFalse("エラーが発生しています。", msgs.hasError());
//		at = makeTransaction(from, to, 71, d);
//		msgs = service.validate(at, WhenVerifier.INSERT);
//		System.out.println(msgs);
//		assertTrue("エラーが発生していません。", msgs.hasError());
//	}
//
//	Transaction makeTransaction(Account from, Account to, long quantity, Date d) {
//		Transaction ret = new Transaction();
//		ret.setActual(true);
//		Entry fromE = new Entry();
//		fromE.setAccount(from);
//		Entry toE = new Entry();
//		toE.setAccount(to);
//		fromE.setQuantity(quantity * -1);
//		toE.setQuantity(quantity);
//		ret.setEntries(Arrays.asList(fromE, toE));
//		ret.setProcessDate(d);
//		return ret;
//	}
}
