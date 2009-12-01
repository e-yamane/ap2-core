/**
 * 
 */
package jp.rough_diamond.account.entity;

import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

/**
 * @author user1
 *
 */
public class OwnerTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		NumberingLoader.init();
	}
	
    public void testGetAll() {
		List<Owner> list = Owner.getAll();
		assertEquals("返却数が誤っています。", 3, list.size());
		assertEquals("IDが誤っています。", 1L, list.get(0).getId().longValue());
		assertEquals("IDが誤っています。", 2L, list.get(1).getId().longValue());
		assertEquals("IDが誤っています。", 3L, list.get(2).getId().longValue());
    }

	public void testOwnerInsert() {
		BasicService service = BasicService.getService();
		
    	try {
    		// 1.新規登録
    		Owner newOwner = new Owner();
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
    		Owner newOwner = service.findByPK(Owner.class, 1L);
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
}
