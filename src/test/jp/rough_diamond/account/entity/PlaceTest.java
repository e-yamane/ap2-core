package jp.rough_diamond.account.entity;

import java.util.List;
import java.util.Set;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class PlaceTest extends DataLoadingTestCase {
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		NumberingLoader.init();
	}

	public void testGetByPK() throws Exception {
		Place place = BasicService.getService().findByPK(Place.class, 1L);
		System.out.println(place.getClass().getName());
	}
	
	public void testGetChildren() throws Exception {
		Place place = BasicService.getService().findByPK(Place.class, 1L);
		List<Place> list = place.getChildren();
		assertEquals("返却数が誤っています。", 2, list.size());
		assertEquals("IDが誤っています。", 2L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 3L, list.get(1).getId().longValue());
		assertEquals("子供がいます。", 0, list.get(1).getChildren().size());
	}

	public void testGetRoutes() throws Exception {
		Place place = BasicService.getService().findByPK(Place.class, 4L);
		List<Place> list = place.getRoutes();
		assertEquals("返却数が誤っています。", 2, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 2L, list.get(1).getId().longValue());
	}
	
	public void testGetRootPlaces() throws Exception {
		List<Place> list = Place.getRootPlaces();
//		assertEquals("返却数が誤っています。", 3, list.size());
		assertEquals("返却数が誤っています。", 4, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 5L, list.get(1).getId().longValue());
		assertEquals("IDが誤っています。", 6L, list.get(2).getId().longValue());
	}

	public void testMultilevelUniqueCheckWhenUpdate() throws Exception {
		//ユニーク属性を変化させずにアップデートできること
		BasicService service = BasicService.getService();
		Place target = service.findByPK(Place.class, 2L);
		target.setVirtual(true);
		try {
			service.update(target);
		} catch(Exception e) {
			e.printStackTrace();
			fail("例外が発生しています。");
		}
//XXX ユニーク解除	
//		//名前の重複チェック
//		target.setName("兵庫");
//		target.setVirtual(false);
//		int i = 0;	//FindBug対応
//		try {
//			service.update(target);
//			fail("例外が発生していません。");
//		} catch(MessagesIncludingException e) {
//			i++;
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("例外が発生しています。");
//		}
	}

	public void testPlaceInsert() throws Exception {
		// 1.親設定なし
		BasicService service = BasicService.getService();
		// placeの新規登録チェック
		Place newPlace = new Place();
		newPlace.setName("大阪");
		newPlace.setVirtual(false);
		
		// 所有者設定
		Owner newOwner = service.findByPK(Owner.class, 2L);
		newPlace.setOwner(newOwner);
		try {
			service.insert(newPlace);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}
		
		// 2.親設定あり
		Place kansai = service.findByPK(Place.class, 1L);
		newPlace = new Place();
		newPlace.setName("大阪");
		newPlace.setVirtual(false);
		newPlace.setParent(kansai);
		
		// 所有者設定
		newOwner = service.findByPK(Owner.class, 2L);
		newPlace.setOwner(newOwner);
		
		try {
			service.insert(newPlace);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}
		
		// 3.仮想設定のみ変更(ユニーク項目が一緒なので登録不可)
		newPlace = new Place();
		newPlace.setName("大阪");
		newPlace.setVirtual(true);
		newPlace.setParent(kansai);
		
		// 所有者設定
		newPlace.setOwner(newOwner);
		
		try {
			service.insert(newPlace);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}
	}

	public void testGetChildIds() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 1L);
		Set<Long> ids = p.getChildIds();
		assertEquals("返却数が誤っています。", 3, ids.size());
		assertTrue("IDが含まれていません。", ids.contains(2L));
		assertTrue("IDが含まれていません。", ids.contains(3L));
		assertTrue("IDが含まれていません。", ids.contains(4L));
	}

	public void testGetChildIdsWhenRecesive() throws Exception {
		BasicService service = BasicService.getService();
		Owner o = service.findByPK(Owner.class, 1L);
		Place p1 = new Place();
		p1.setOwner(o);
		p1.setName("再帰項目１");
		Place p2 = new Place();
		p2.setName("再帰項目２");
		p2.setOwner(o);
		service.insert(p1, p2);
		p1.setParent(p2);
		service.update(p1);
		p2.setParent(p1);
		service.update(p2);
		Set<Long> ids = p1.getChildIds();
		assertEquals("返却数が誤っています。", 2, ids.size());
		assertTrue("IDが含まれていません。", ids.contains(p1.getId()));
		assertTrue("IDが含まれていません。", ids.contains(p2.getId()));
	}
}
