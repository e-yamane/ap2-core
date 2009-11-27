package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class AccountTest extends DataLoadingTestCase {
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	//本当はフレームワークでするべき
	public void testUniqueCheck() throws Exception {
		Account account = Account.getService().getAccount(1L, 4L, 1L);
		account.setId(null);
		try {
			account.save();
			fail("例外が発生していません。");
		} catch(Exception e) {
			e.printStackTrace();
		}
		//ID3のデータを更新
		account.setId(3L);
		try {
			BasicService.getService().update(account);
			fail("例外が発生していません。");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testGetAccount() throws Exception {
		Account account = Account.getService().getAccount(1L, 4L, 1L);
		assertEquals("IDが誤っています。", 2L, account.getId().longValue());
		account = Account.getService().getAccount(Long.MAX_VALUE, 4L, 1L);
		assertNull("変なデータが返却されています。", account);
	}

	public void testGetBlance() throws Exception {
		Account account = Account.getService().getAccount(4L, 4L, 1L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080410");
		assertEquals("残量が誤っています。", 0L, account.getBalance(d).longValue());
		d = sdf.parse("20080411");
		assertEquals("残量が誤っています。", 100L, account.getBalance(d).longValue());
		d = sdf.parse("20080420");
		assertEquals("残量が誤っています。", 100L, account.getBalance(d).longValue());
		d = sdf.parse("20080421");
		assertEquals("残量が誤っています。", 70L, account.getBalance(d).longValue());

		Account account2 = Account.getService().getAccount(4L, 5L, 3L);
		assertEquals("残量が誤っています。", 1000L, account2.getBalance().longValue());
	}
	
	public void testGetTrend() throws Exception {
		Account account = Account.getService().getAccount(4L, 4L, 1L);
		List<Entry> list = account.getTrend();
		assertEquals("件数が誤っています。", 4, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 3L, list.get(1).getId().longValue());
		assertEquals("IDが誤っています。", 5L, list.get(2).getId().longValue());
		assertEquals("IDが誤っています。", 7L, list.get(3).getId().longValue());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		list = account.getTrend(null, sdf.parse("20080411"));
		assertEquals("件数が誤っています。", 1, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());

		list = account.getTrend(sdf.parse("20080411"), null);
		assertEquals("件数が誤っています。", 3, list.size());
		assertEquals("IDが誤っています。", 3L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 5L, list.get(1).getId().longValue());
		assertEquals("IDが誤っています。", 7L, list.get(2).getId().longValue());

		
		Account account2 = Account.getService().getAccount(4L, 5L, 3L);
		List<Entry> list2 = account2.getTrend();
		assertEquals("件数が誤まっています。", 1, list2.size());
		assertEquals("IDが誤まっています。",  12L, list2.get(0).getId().longValue());
	}
	
	public void testGrossItemBalance() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 4L);
		Owner o = p.getOwner();
		Item i = service.findByPK(Item.class, 2L);
		Account a = new Account();
		a.setPlace(p);
		a.setItem(i);
		a.setOwner(o);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080720");
		assertEquals("残量が誤っています。", 20L, a.getBalance(d, true, false).longValue());
	}
	
	public void testGrossItemTrend() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 4L);
		Owner o = p.getOwner();
		Item i = service.findByPK(Item.class, 2L);
		Account a = new Account();
		a.setPlace(p);
		a.setItem(i);
		a.setOwner(o);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080720");
		List<Entry> entries = a.getTrend(null, d, true, false);
		assertEquals("件数が誤っています。", 4, entries.size());
	}
	
	public void testGrossPlaceBalance() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 2L);
		Owner o = p.getOwner();
		Item i = service.findByPK(Item.class, 4L);
		Account a = new Account();
		a.setPlace(p);
		a.setItem(i);
		a.setOwner(o);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080720");
		assertEquals("残量が誤っています。", 20L, a.getBalance(d, false, true).longValue());
	}
	
	public void testGrossPlaceTrend() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 2L);
		Owner o = p.getOwner();
		Item i = service.findByPK(Item.class, 4L);
		Account a = new Account();
		a.setPlace(p);
		a.setItem(i);
		a.setOwner(o);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080720");
		List<Entry> entries = a.getTrend(null, d, false, true);
		assertEquals("件数が誤っています。", 4, entries.size());
	}

	public void testGrossPlaceAndItemBalance() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 2L);
		Owner o = p.getOwner();
		Item i = service.findByPK(Item.class, 2L);
		Account a = new Account();
		a.setPlace(p);
		a.setItem(i);
		a.setOwner(o);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080720");
		assertEquals("残量が誤っています。", 20L, a.getBalance(d, true, true).longValue());
	}
	
	public void testGrossPlaceAndItemTrend() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 2L);
		Owner o = p.getOwner();
		Item i = service.findByPK(Item.class, 2L);
		Account a = new Account();
		a.setPlace(p);
		a.setItem(i);
		a.setOwner(o);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080720");
		List<Entry> entries = a.getTrend(null, d, true, true);
		assertEquals("件数が誤っています。", 4, entries.size());
	}
	
	public static void main(String[] args) throws Exception {
		new AccountTest().setUp();
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 2L);
		Owner o = p.getOwner();
		Item i = service.findByPK(Item.class, 2L);
		Account a = new Account();
		a.setPlace(p);
		a.setItem(i);
		a.setOwner(o);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080720");
		long start = System.currentTimeMillis();
		for(int j = 0 ; j < 100 ; j++) {
			a.getTrend(null, d, true, true);
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
