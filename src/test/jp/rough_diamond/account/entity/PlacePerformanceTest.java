package jp.rough_diamond.account.entity;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

public class PlacePerformanceTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		NumberingLoader.init();
	}

	public void testPerformance() {
		long start = System.currentTimeMillis();
		Place p = null;
		for(int i = 0 ; i < 500 ; i++) {
			p = BasicService.getService().findByPK(Place.class, 32L);
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC32", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC31", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC30", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC29", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC28", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC27", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC26", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC25", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC24", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC23", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC22", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC21", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC20", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC19", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC18", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC17", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC16", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC15", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC14", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC13", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC12", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC11", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC10", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC9", p.getPlaceCode());
		p = p.getParent();
		System.out.println("placeCodeにアクセスします");
		assertEquals("コードが誤っています。", "PLC8", p.getPlaceCode());
		p = p.getParent();
		assertNull(p);
	}
}
