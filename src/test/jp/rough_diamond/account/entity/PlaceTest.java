package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public void testGetCode() throws Exception {
		CodeSystem cs = BasicService.getService().findByPK(CodeSystem.class, 2L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
		
		Place p = BasicService.getService().findByPK(Place.class, 2L);
		String code = p.getCode(cs, sdf.parse("2009/12/01"));
		assertEquals("返却コードが誤っています。", "code_x", code);
		
		p = BasicService.getService().findByPK(Place.class, 1L);
		Calendar cal = Calendar.getInstance();

		cal.setTime(sdf.parse("2009/12/01"));
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/2
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));

		//12/3
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/4
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/5
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/6
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/7
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/8
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/9
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/10
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/11
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/12
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/13
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/14
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/15
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/16
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/17
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/18
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/19
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/20
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/21
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/22
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/23
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/24
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", p.getCode(cs, cal.getTime()));
		
		//12/25
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/26
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/27
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/28
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/29
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/30
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", p.getCode(cs, cal.getTime()));
		
		//12/31
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_2", p.getCode(cs, cal.getTime()));
		
		//1/1
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_2", p.getCode(cs, cal.getTime()));
	}
	
	public void testGetPlaceByCode() throws Exception {
		CodeSystem cs = BasicService.getService().findByPK(CodeSystem.class, 2L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
		
		Place p = Place.getPlaceByCode(cs, "code_x", sdf.parse("2009/12/01"));
		assertEquals("所有者IDが誤っています。", 2L, p.getId().longValue());

		Calendar cal = Calendar.getInstance();

		//12/1
		cal.setTime(sdf.parse("2009/12/01"));
		System.out.println(cal.getTime());
		Place p1 = Place.getPlaceByCode(cs, "code_1", cal.getTime());
		Place p2 = Place.getPlaceByCode(cs, "code_2", cal.getTime());
		Place p3 = Place.getPlaceByCode(cs, "code_3", cal.getTime());
		Place p4 = Place.getPlaceByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", p1);
		assertNull("返却値が誤っています。", p2);
		assertEquals("返却値が誤っています。", 1L, p3.getId().longValue());
		assertNull("返却値が誤っています。", p4);

		cal.setTime(sdf.parse("2009/12/19"));
		System.out.println(cal.getTime());
		p1 = Place.getPlaceByCode(cs, "code_1", cal.getTime());
		p2 = Place.getPlaceByCode(cs, "code_2", cal.getTime());
		p3 = Place.getPlaceByCode(cs, "code_3", cal.getTime());
		p4 = Place.getPlaceByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", p1);
		assertNull("返却値が誤っています。", p2);
		assertNull("返却値が誤っています。", p3);
		assertEquals("返却値が誤っています。", 1L, p4.getId().longValue());

		cal.setTime(sdf.parse("2009/12/20"));
		System.out.println(cal.getTime());
		p1 = Place.getPlaceByCode(cs, "code_1", cal.getTime());
		p2 = Place.getPlaceByCode(cs, "code_2", cal.getTime());
		p3 = Place.getPlaceByCode(cs, "code_3", cal.getTime());
		p4 = Place.getPlaceByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", p1);
		assertNull("返却値が誤っています。", p2);
		assertNull("返却値が誤っています。", p3);
		assertEquals("返却値が誤っています。", 1L, p4.getId().longValue());

		cal.setTime(sdf.parse("2009/12/24"));
		System.out.println(cal.getTime());
		p1 = Place.getPlaceByCode(cs, "code_1", cal.getTime());
		p2 = Place.getPlaceByCode(cs, "code_2", cal.getTime());
		p3 = Place.getPlaceByCode(cs, "code_3", cal.getTime());
		p4 = Place.getPlaceByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", p1);
		assertNull("返却値が誤っています。", p2);
		assertNull("返却値が誤っています。", p3);
		assertEquals("返却値が誤っています。", 1L, p4.getId().longValue());

		cal.setTime(sdf.parse("2009/12/25"));
		System.out.println(cal.getTime());
		p1 = Place.getPlaceByCode(cs, "code_1", cal.getTime());
		p2 = Place.getPlaceByCode(cs, "code_2", cal.getTime());
		p3 = Place.getPlaceByCode(cs, "code_3", cal.getTime());
		p4 = Place.getPlaceByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", p1);
		assertNull("返却値が誤っています。", p2);
		assertEquals("返却値が誤っています。", 1L, p3.getId().longValue());
		assertNull("返却値が誤っています。", p4);

		cal.setTime(sdf.parse("2009/12/30"));
		System.out.println(cal.getTime());
		p1 = Place.getPlaceByCode(cs, "code_1", cal.getTime());
		p2 = Place.getPlaceByCode(cs, "code_2", cal.getTime());
		p3 = Place.getPlaceByCode(cs, "code_3", cal.getTime());
		p4 = Place.getPlaceByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", p1);
		assertNull("返却値が誤っています。", p2);
		assertEquals("返却値が誤っています。", 1L, p3.getId().longValue());
		assertNull("返却値が誤っています。", p4);

		cal.setTime(sdf.parse("2009/12/31"));
		System.out.println(cal.getTime());
		p1 = Place.getPlaceByCode(cs, "code_1", cal.getTime());
		p2 = Place.getPlaceByCode(cs, "code_2", cal.getTime());
		p3 = Place.getPlaceByCode(cs, "code_3", cal.getTime());
		p4 = Place.getPlaceByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", p1);
		assertEquals("返却値が誤っています。", 1L, p2.getId().longValue());
		assertNull("返却値が誤っています。", p3);
		assertNull("返却値が誤っています。", p4);
	}
}
