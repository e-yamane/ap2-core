/**
 * 
 */
package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

/**
 * @author user1
 *
 */
public class PartyTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		NumberingLoader.init();
	}
	
    public void testGetAll() {
		List<Party> list = Party.getAll();
		assertEquals("返却数が誤っています。", 5, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 2L, list.get(1).getId().longValue());
		assertEquals("IDが誤っています。", 3L, list.get(2).getId().longValue());
		assertEquals("IDが誤っています。", 4L, list.get(3).getId().longValue());
		assertEquals("IDが誤っています。", 5L, list.get(4).getId().longValue());
    }

	public void testOwnerInsert() {
		BasicService service = BasicService.getService();
		
    	try {
    		// 1.新規登録
    		Party newOwner = new Party();
        	newOwner.setName("地頭");
			service.insert(newOwner);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}

//XXX ユニーク解除		
//    	try {
//    		// 2.同一名称は登録不可
//    		Owner newOwner = new Owner();
//        	newOwner.setName("本田");
//			service.insert(newOwner);
//			fail("例外が発生していません。");
//		} catch(MessagesIncludingException e) {
//			e.printStackTrace();
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("その他例外が発生しています。");
//		}
	}
	
	public void testOwnerUpdate() {
		BasicService service = BasicService.getService();
		
    	try {
    		// 1.名称更新
    		Party newOwner = service.findByPK(Party.class, 1L);
        	newOwner.setName("本田　哉樹");
			service.update(newOwner);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("例外が発生しました。");
		} catch(Exception e) {
			e.printStackTrace();
			fail("その他例外が発生しています。");
		}

//XXX ユニーク解除		
//    	try {
//    		// 2.同一名称は登録不可
//    		Owner newOwner = service.findByPK(Owner.class, 1L);
//        	newOwner.setName("江並");
//			service.update(newOwner);
//			fail("例外が発生していません。");
//		} catch(MessagesIncludingException e) {
//			e.printStackTrace();
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("その他例外が発生しています。");
//		}
	}
	
	public void testGetCode() throws Exception {
		CodeSystem cs = BasicService.getService().findByPK(CodeSystem.class, 2L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
		
		Party o = BasicService.getService().findByPK(Party.class, 5L);
		String code = o.getCode(cs, sdf.parse("2009/12/01"));
		assertEquals("返却コードが誤っています。", "code_x", code);
		
		o = BasicService.getService().findByPK(Party.class, 4L);
		Calendar cal = Calendar.getInstance();

		cal.setTime(sdf.parse("2009/12/01"));
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/2
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));

		//12/3
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/4
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/5
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/6
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/7
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/8
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/9
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/10
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/11
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/12
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/13
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/14
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/15
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/16
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/17
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/18
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/19
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/20
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", o.getCode(cs, cal.getTime()));
		
		//12/21
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", o.getCode(cs, cal.getTime()));
		
		//12/22
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", o.getCode(cs, cal.getTime()));
		
		//12/23
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", o.getCode(cs, cal.getTime()));
		
		//12/24
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_4", o.getCode(cs, cal.getTime()));
		
		//12/25
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/26
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/27
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/28
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/29
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/30
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_3", o.getCode(cs, cal.getTime()));
		
		//12/31
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_2", o.getCode(cs, cal.getTime()));
		
		//1/1
		cal.add(Calendar.DATE, 1);
		System.out.println(cal.getTime());
		assertEquals("返却コードが誤っています。", "code_2", o.getCode(cs, cal.getTime()));
	}
	
	public void testGetOwnerByCode() throws Exception {
		CodeSystem cs = BasicService.getService().findByPK(CodeSystem.class, 2L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
		
		Party o = Party.getOwnerByCode(cs, "code_x", sdf.parse("2009/12/01"));
		assertEquals("所有者IDが誤っています。", 5L, o.getId().longValue());

		Calendar cal = Calendar.getInstance();

		//12/1
		cal.setTime(sdf.parse("2009/12/01"));
		System.out.println(cal.getTime());
		Party o1 = Party.getOwnerByCode(cs, "code_1", cal.getTime());
		Party o2 = Party.getOwnerByCode(cs, "code_2", cal.getTime());
		Party o3 = Party.getOwnerByCode(cs, "code_3", cal.getTime());
		Party o4 = Party.getOwnerByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", o1);
		assertNull("返却値が誤っています。", o2);
		assertEquals("返却値が誤っています。", 4L, o3.getId().longValue());
		assertNull("返却値が誤っています。", o4);

		cal.setTime(sdf.parse("2009/12/19"));
		System.out.println(cal.getTime());
		o1 = Party.getOwnerByCode(cs, "code_1", cal.getTime());
		o2 = Party.getOwnerByCode(cs, "code_2", cal.getTime());
		o3 = Party.getOwnerByCode(cs, "code_3", cal.getTime());
		o4 = Party.getOwnerByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", o1);
		assertNull("返却値が誤っています。", o2);
		assertEquals("返却値が誤っています。", 4L, o3.getId().longValue());
		assertNull("返却値が誤っています。", o4);

		cal.setTime(sdf.parse("2009/12/20"));
		System.out.println(cal.getTime());
		o1 = Party.getOwnerByCode(cs, "code_1", cal.getTime());
		o2 = Party.getOwnerByCode(cs, "code_2", cal.getTime());
		o3 = Party.getOwnerByCode(cs, "code_3", cal.getTime());
		o4 = Party.getOwnerByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", o1);
		assertNull("返却値が誤っています。", o2);
		assertNull("返却値が誤っています。", o3);
		assertEquals("返却値が誤っています。", 4L, o4.getId().longValue());

		cal.setTime(sdf.parse("2009/12/24"));
		System.out.println(cal.getTime());
		o1 = Party.getOwnerByCode(cs, "code_1", cal.getTime());
		o2 = Party.getOwnerByCode(cs, "code_2", cal.getTime());
		o3 = Party.getOwnerByCode(cs, "code_3", cal.getTime());
		o4 = Party.getOwnerByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", o1);
		assertNull("返却値が誤っています。", o2);
		assertNull("返却値が誤っています。", o3);
		assertEquals("返却値が誤っています。", 4L, o4.getId().longValue());

		cal.setTime(sdf.parse("2009/12/25"));
		System.out.println(cal.getTime());
		o1 = Party.getOwnerByCode(cs, "code_1", cal.getTime());
		o2 = Party.getOwnerByCode(cs, "code_2", cal.getTime());
		o3 = Party.getOwnerByCode(cs, "code_3", cal.getTime());
		o4 = Party.getOwnerByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", o1);
		assertNull("返却値が誤っています。", o2);
		assertEquals("返却値が誤っています。", 4L, o3.getId().longValue());
		assertNull("返却値が誤っています。", o4);

		cal.setTime(sdf.parse("2009/12/30"));
		System.out.println(cal.getTime());
		o1 = Party.getOwnerByCode(cs, "code_1", cal.getTime());
		o2 = Party.getOwnerByCode(cs, "code_2", cal.getTime());
		o3 = Party.getOwnerByCode(cs, "code_3", cal.getTime());
		o4 = Party.getOwnerByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", o1);
		assertNull("返却値が誤っています。", o2);
		assertEquals("返却値が誤っています。", 4L, o3.getId().longValue());
		assertNull("返却値が誤っています。", o4);

		cal.setTime(sdf.parse("2009/12/31"));
		System.out.println(cal.getTime());
		o1 = Party.getOwnerByCode(cs, "code_1", cal.getTime());
		o2 = Party.getOwnerByCode(cs, "code_2", cal.getTime());
		o3 = Party.getOwnerByCode(cs, "code_3", cal.getTime());
		o4 = Party.getOwnerByCode(cs, "code_4", cal.getTime());
		assertNull("返却値が誤っています。", o1);
		assertEquals("返却値が誤っています。", 4L, o2.getId().longValue());
		assertNull("返却値が誤っています。", o3);
		assertNull("返却値が誤っています。", o4);
	}
}
