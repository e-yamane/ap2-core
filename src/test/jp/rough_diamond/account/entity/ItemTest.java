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
		assertEquals("�ԋp��������Ă��܂��B", 5, list.size());
		assertEquals("ID������Ă��܂��B", 2L, list.get(0).getId().longValue());
		assertEquals("ID������Ă��܂��B", 3L, list.get(1).getId().longValue());
		assertEquals("�q�������܂��B", 0, list.get(1).getChildren().size());
	}

	public void testGetRoutes() throws Exception {
		Item item = BasicService.getService().findByPK(Item.class, 4L);
		List<Item> list = item.getRoutes();
		assertEquals("�ԋp��������Ă��܂��B", 2, list.size());
		assertEquals("ID������Ă��܂��B", 1L, list.get(0).getId().longValue());
		assertEquals("ID������Ă��܂��B", 2L, list.get(1).getId().longValue());
	}
	
	public void testGetRootItems() throws Exception {
		List<Item> list = Item.getRootItems();
		assertEquals("�ԋp��������Ă��܂��B", 1, list.size());
		assertEquals("ID������Ă��܂��B", 1L, list.get(0).getId().longValue());
	}
	
	//��Ղ̃e�X�g
	public void testIn() throws Exception {
		Extractor e = new Extractor(Item.class);
		e.add(Condition.in(new Property(Item.ID), 1L, 2L, 3L));
		e.addOrder(Order.asc(new Property(Item.ID)));
		List<Item> list = BasicService.getService().findByExtractor(e);
		assertEquals("�ԋp��������Ă��܂��B", 3, list.size());
		assertEquals("ID������Ă��܂��B", 1L, list.get(0).getId().longValue());
		assertEquals("ID������Ă��܂��B", 2L, list.get(1).getId().longValue());
		assertEquals("ID������Ă��܂��B", 3L, list.get(2).getId().longValue());
	}

	//��Ղ̃e�X�g
	public void testNotIn() throws Exception {
		Extractor e = new Extractor(Item.class);
		e.add(Condition.notIn(new Property(Item.ID), 1L, 2L, 3L));
		List<Item> all = BasicService.getService().findAll(Item.class);
		List<Item> list = BasicService.getService().findByExtractor(e);
		assertEquals("�ԋp��������Ă��܂��B", 3, all.size() - list.size());
	}
	
	//Oracle10g�ł̐��K�\���擾�̃e�X�g
	public void testRegExp() throws Exception {
		Extractor e = new Extractor(Item.class);
		e.add(Condition.regex(new Property(Item.NAME), "^N"));
		List<Item> list = BasicService.getService().findByExtractor(e);
		assertEquals("�ԋp��������Ă��܂��B", 1, list.size());
		assertEquals("ID������Ă��܂��B", "NDS", list.get(0).getName());
	}

	public void testItemInsert() throws Exception {
		BasicService service = BasicService.getService();
		Item newItem;

		// �V�K�o�^
		newItem = new Item();
		newItem.setName("PC�G���W��");
		newItem.setParent(null);
		
		Item parent = service.findByPK(Item.class, 1L);
		newItem.setParent(parent);
		try {
			service.insert(newItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}
		

		// �e�Ȃ��œo�^�\
		newItem = new Item();
		newItem.setName("�t�@�C�i���t�@���^�W�[�U");
		newItem.setParent(null);
		try {
			service.insert(newItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}		

		
		// �e���Ⴆ�Γo�^�\
		newItem = new Item();
		newItem.setName("�t�@�C�i���t�@���^�W�[�U");

		parent = service.findByPK(Item.class, 10L);
		newItem.setParent(parent);
		try {
			service.insert(newItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}	
	}

//XXX ���j�[�N����	
//	public void testDuppulicateInsert() throws Exception {
//		//���O�̏d���`�F�b�N
//		BasicService service = BasicService.getService();
//		Item parent = service.findByPK(Item.class, 8L);
//		
//		Item newItem = new Item();
//		newItem.setName("�t�@�C�i���t�@���^�W�[�U");
//		newItem.setParent(parent);
//		try {
//			service.insert(newItem);
//			fail("��O���������Ă��܂���B");
//		} catch(MessagesIncludingException e) {
//			e.printStackTrace();
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("���̑���O���������Ă��܂��B");
//		}
//	}

	public void testItemUpdate() throws Exception {
		BasicService service = BasicService.getService();
		Item editItem = service.findByPK(Item.class, 9L);

		// ���O�ύX
		editItem.setName("�t�@�C�i���t�@���^�W�[�T");
		try {
			service.update(editItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}
		
		// �e�ύX
		editItem = service.findByPK(Item.class, 9L);
		Item parent = service.findByPK(Item.class, 10L);
		editItem.setParent(parent);
		try {
			service.update(editItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}
	
		// �����ύX
		editItem = service.findByPK(Item.class, 9L);
		editItem.setName("�t�@�C�i���t�@���^�W�[�U");
		parent = service.findByPK(Item.class, 8L);
		editItem.setParent(parent);
		try {
			service.update(editItem);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}
	}
	
	public void testGetChildIds() throws Exception {
		BasicService service = BasicService.getService();
		Item i = service.findByPK(Item.class, 2L);
		Set<Long> ids = i.getChildIds();
		assertEquals("�ԋp��������Ă��܂��B", 2, ids.size());
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(4L));
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(5L));

		Item i2 = service.findByPK(Item.class, 1L);
		Set<Long> ids2 = i2.getChildIds();
		assertEquals("�ԋp��������Ă��܂��B", 9, ids2.size());
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(2L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(3L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(4L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(5L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(6L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(7L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(8L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(9L));
		assertTrue("ID���܂܂�Ă��܂���B", ids2.contains(10L));
	}

	public void testGetChildIdsWhenRecesive() throws Exception {
		BasicService service = BasicService.getService();
		Item i1 = new Item();
		i1.setName("�ċA���ڂP");
		Item i2 = new Item();
		i2.setName("�ċA���ڂQ");
		service.insert(i1, i2);
		i1.setParent(i2);
		service.update(i1);
		i2.setParent(i1);
		service.update(i2);
		Set<Long> ids = i1.getChildIds();
		assertEquals("�ԋp��������Ă��܂��B", 2, ids.size());
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(i1.getId()));
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(i2.getId()));
	}
}
