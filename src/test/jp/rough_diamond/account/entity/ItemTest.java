package jp.rough_diamond.account.entity;

import java.util.List;
import java.util.Set;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class ItemTest extends DataLoadingTestCase {
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		NumberingLoader.init();
	}

	public void testGetChildren() throws Exception {
		Item item = BasicService.getService().findByPK(Item.class, 1L);
		List<Item> list = item.getChildren();
		assertEquals("返却数が誤っています。", 5, list.size());
		assertEquals("IDが誤っています。", 2L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 3L, list.get(1).getId().longValue());
		assertEquals("子供がいます。", 0, list.get(1).getChildren().size());
	}

	public void testGetRoutes() throws Exception {
		Item item = BasicService.getService().findByPK(Item.class, 4L);
		List<Item> list = item.getRoutes();
		assertEquals("返却数が誤っています。", 2, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 2L, list.get(1).getId().longValue());
	}
	
	public void testGetRootItems() throws Exception {
		List<Item> list = Item.getRootItems();
		assertEquals("返却数が誤っています。", 1, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
	}
	
	//基盤のテスト
	public void testIn() throws Exception {
		Extractor e = new Extractor(Item.class);
		e.add(Condition.in(new Property(Item.ID), 1L, 2L, 3L));
		e.addOrder(Order.asc(new Property(Item.ID)));
		List<Item> list = BasicService.getService().findByExtractor(e);
		assertEquals("返却数が誤っています。", 3, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 2L, list.get(1).getId().longValue());
		assertEquals("IDが誤っています。", 3L, list.get(2).getId().longValue());
	}

	//基盤のテスト
	public void testNotIn() throws Exception {
		Extractor e = new Extractor(Item.class);
		e.add(Condition.notIn(new Property(Item.ID), 1L, 2L, 3L));
		List<Item> all = BasicService.getService().findAll(Item.class);
		List<Item> list = BasicService.getService().findByExtractor(e);
		assertEquals("返却数が誤っています。", 3, all.size() - list.size());
	}
	
	//Oracle10gでの正規表現取得のテスト
	public void testRegExp() throws Exception {
		Extractor e = new Extractor(Item.class);
		e.add(Condition.regex(new Property(Item.NAME), "^N"));
		List<Item> list = BasicService.getService().findByExtractor(e);
		assertEquals("返却数が誤っています。", 1, list.size());
		assertEquals("IDが誤っています。", "NDS", list.get(0).getName());
	}

	public void testItemInsert() throws Exception {
		BasicService service = BasicService.getService();
		Item newItem;

		// 新規登録
		newItem = new Item();
		newItem.setName("PCエンジン");
		newItem.setParent(null);
		
		Item parent = service.findByPK(Item.class, 1L);
		newItem.setParent(parent);
		try {
			service.insert(newItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}
		

		// 親なしで登録可能
		newItem = new Item();
		newItem.setName("ファイナルファンタジー６");
		newItem.setParent(null);
		try {
			service.insert(newItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}		

		
		// 親が違えば登録可能
		newItem = new Item();
		newItem.setName("ファイナルファンタジー６");

		parent = service.findByPK(Item.class, 10L);
		newItem.setParent(parent);
		try {
			service.insert(newItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}	
	}

//XXX ユニーク解除	
//	public void testDuppulicateInsert() throws Exception {
//		//名前の重複チェック
//		BasicService service = BasicService.getService();
//		Item parent = service.findByPK(Item.class, 8L);
//		
//		Item newItem = new Item();
//		newItem.setName("ファイナルファンタジー６");
//		newItem.setParent(parent);
//		try {
//			service.insert(newItem);
//			fail("例外が発生していません。");
//		} catch(MessagesIncludingException e) {
//			e.printStackTrace();
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("その他例外が発生しています。");
//		}
//	}

	public void testItemUpdate() throws Exception {
		BasicService service = BasicService.getService();
		Item editItem = service.findByPK(Item.class, 9L);

		// 名前変更
		editItem.setName("ファイナルファンタジー５");
		try {
			service.update(editItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}
		
		// 親変更
		editItem = service.findByPK(Item.class, 9L);
		Item parent = service.findByPK(Item.class, 10L);
		editItem.setParent(parent);
		try {
			service.update(editItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}
	
		// 両方変更
		editItem = service.findByPK(Item.class, 9L);
		editItem.setName("ファイナルファンタジー６");
		parent = service.findByPK(Item.class, 8L);
		editItem.setParent(parent);
		try {
			service.update(editItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}
	}
	
	public void testGetChildIds() throws Exception {
		BasicService service = BasicService.getService();
		Item i = service.findByPK(Item.class, 2L);
		Set<Long> ids = i.getChildIds();
		assertEquals("返却数が誤っています。", 2, ids.size());
		assertTrue("IDが含まれていません。", ids.contains(4L));
		assertTrue("IDが含まれていません。", ids.contains(5L));

		Item i2 = service.findByPK(Item.class, 1L);
		Set<Long> ids2 = i2.getChildIds();
		assertEquals("返却数が誤っています。", 9, ids2.size());
		assertTrue("IDが含まれていません。", ids2.contains(2L));
		assertTrue("IDが含まれていません。", ids2.contains(3L));
		assertTrue("IDが含まれていません。", ids2.contains(4L));
		assertTrue("IDが含まれていません。", ids2.contains(5L));
		assertTrue("IDが含まれていません。", ids2.contains(6L));
		assertTrue("IDが含まれていません。", ids2.contains(7L));
		assertTrue("IDが含まれていません。", ids2.contains(8L));
		assertTrue("IDが含まれていません。", ids2.contains(9L));
		assertTrue("IDが含まれていません。", ids2.contains(10L));
	}

	public void testGetChildIdsWhenRecesive() throws Exception {
		BasicService service = BasicService.getService();
		Item i1 = new Item();
		i1.setName("再帰項目１");
		Item i2 = new Item();
		i2.setName("再帰項目２");
		service.insert(i1, i2);
		i1.setParent(i2);
		service.update(i1);
		i2.setParent(i1);
		service.update(i2);
		Set<Long> ids = i1.getChildIds();
		assertEquals("返却数が誤っています。", 2, ids.size());
		assertTrue("IDが含まれていません。", ids.contains(i1.getId()));
		assertTrue("IDが含まれていません。", ids.contains(i2.getId()));
	}
}
