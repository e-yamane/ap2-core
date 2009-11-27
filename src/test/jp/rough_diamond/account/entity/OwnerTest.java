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
		assertEquals("�ԋp��������Ă��܂��B", 3, list.size());
		assertEquals("ID������Ă��܂��B", 1L, list.get(0).getId().longValue());
		assertEquals("ID������Ă��܂��B", 2L, list.get(1).getId().longValue());
		assertEquals("ID������Ă��܂��B", 3L, list.get(2).getId().longValue());
    }

	public void testOwnerInsert() {
		BasicService service = BasicService.getService();
		
    	try {
    		// 1.�V�K�o�^
    		Owner newOwner = new Owner();
        	newOwner.setName("�n��");
			service.insert(newOwner);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}

//XXX ���j�[�N����		
//    	try {
//    		// 2.���ꖼ�͓̂o�^�s��
//    		Owner newOwner = new Owner();
//        	newOwner.setName("�{�c");
//			service.insert(newOwner);
//			fail("��O���������Ă��܂���B");
//		} catch(MessagesIncludingException e) {
//			e.printStackTrace();
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("���̑���O���������Ă��܂��B");
//		}
	}
	
	public void testOwnerUpdate() {
		BasicService service = BasicService.getService();
		
    	try {
    		// 1.���̍X�V
    		Owner newOwner = service.findByPK(Owner.class, 1L);
        	newOwner.setName("�{�c�@�Ǝ�");
			service.update(newOwner);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}

//XXX ���j�[�N����		
//    	try {
//    		// 2.���ꖼ�͓̂o�^�s��
//    		Owner newOwner = service.findByPK(Owner.class, 1L);
//        	newOwner.setName("�]��");
//			service.update(newOwner);
//			fail("��O���������Ă��܂���B");
//		} catch(MessagesIncludingException e) {
//			e.printStackTrace();
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("���̑���O���������Ă��܂��B");
//		}
	}
}
