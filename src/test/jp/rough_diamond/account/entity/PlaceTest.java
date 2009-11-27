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
		assertEquals("�ԋp��������Ă��܂��B", 2, list.size());
		assertEquals("ID������Ă��܂��B", 2L, list.get(0).getId().longValue());
		assertEquals("ID������Ă��܂��B", 3L, list.get(1).getId().longValue());
		assertEquals("�q�������܂��B", 0, list.get(1).getChildren().size());
	}

	public void testGetRoutes() throws Exception {
		Place place = BasicService.getService().findByPK(Place.class, 4L);
		List<Place> list = place.getRoutes();
		assertEquals("�ԋp��������Ă��܂��B", 2, list.size());
		assertEquals("ID������Ă��܂��B", 1L, list.get(0).getId().longValue());
		assertEquals("ID������Ă��܂��B", 2L, list.get(1).getId().longValue());
	}
	
	public void testGetRootPlaces() throws Exception {
		List<Place> list = Place.getRootPlaces();
//		assertEquals("�ԋp��������Ă��܂��B", 3, list.size());
		assertEquals("�ԋp��������Ă��܂��B", 4, list.size());
		assertEquals("ID������Ă��܂��B", 1L, list.get(0).getId().longValue());
		assertEquals("ID������Ă��܂��B", 5L, list.get(1).getId().longValue());
		assertEquals("ID������Ă��܂��B", 6L, list.get(2).getId().longValue());
	}

	public void testMultilevelUniqueCheckWhenUpdate() throws Exception {
		//���j�[�N������ω��������ɃA�b�v�f�[�g�ł��邱��
		BasicService service = BasicService.getService();
		Place target = service.findByPK(Place.class, 2L);
		target.setVirtual(true);
		try {
			service.update(target);
		} catch(Exception e) {
			e.printStackTrace();
			fail("��O���������Ă��܂��B");
		}
//XXX ���j�[�N����	
//		//���O�̏d���`�F�b�N
//		target.setName("����");
//		target.setVirtual(false);
//		int i = 0;	//FindBug�Ή�
//		try {
//			service.update(target);
//			fail("��O���������Ă��܂���B");
//		} catch(MessagesIncludingException e) {
//			i++;
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail("��O���������Ă��܂��B");
//		}
	}

	public void testPlaceInsert() throws Exception {
		// 1.�e�ݒ�Ȃ�
		BasicService service = BasicService.getService();
		// place�̐V�K�o�^�`�F�b�N
		Place newPlace = new Place();
		newPlace.setName("���");
		newPlace.setVirtual(false);
		
		// ���L�Ґݒ�
		Owner newOwner = service.findByPK(Owner.class, 2L);
		newPlace.setOwner(newOwner);
		try {
			service.insert(newPlace);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}
		
		// 2.�e�ݒ肠��
		Place kansai = service.findByPK(Place.class, 1L);
		newPlace = new Place();
		newPlace.setName("���");
		newPlace.setVirtual(false);
		newPlace.setParent(kansai);
		
		// ���L�Ґݒ�
		newOwner = service.findByPK(Owner.class, 2L);
		newPlace.setOwner(newOwner);
		
		try {
			service.insert(newPlace);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
			fail("��O���������܂����B");
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}
		
		// 3.���z�ݒ�̂ݕύX(���j�[�N���ڂ��ꏏ�Ȃ̂œo�^�s��)
		newPlace = new Place();
		newPlace.setName("���");
		newPlace.setVirtual(true);
		newPlace.setParent(kansai);
		
		// ���L�Ґݒ�
		newPlace.setOwner(newOwner);
		
		try {
			service.insert(newPlace);
		} catch(MessagesIncludingException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			fail("���̑���O���������Ă��܂��B");
		}
	}

	public void testGetChildIds() throws Exception {
		BasicService service = BasicService.getService();
		Place p = service.findByPK(Place.class, 1L);
		Set<Long> ids = p.getChildIds();
		assertEquals("�ԋp��������Ă��܂��B", 3, ids.size());
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(2L));
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(3L));
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(4L));
	}

	public void testGetChildIdsWhenRecesive() throws Exception {
		BasicService service = BasicService.getService();
		Owner o = service.findByPK(Owner.class, 1L);
		Place p1 = new Place();
		p1.setOwner(o);
		p1.setName("�ċA���ڂP");
		Place p2 = new Place();
		p2.setName("�ċA���ڂQ");
		p2.setOwner(o);
		service.insert(p1, p2);
		p1.setParent(p2);
		service.update(p1);
		p2.setParent(p1);
		service.update(p2);
		Set<Long> ids = p1.getChildIds();
		assertEquals("�ԋp��������Ă��܂��B", 2, ids.size());
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(p1.getId()));
		assertTrue("ID���܂܂�Ă��܂���B", ids.contains(p2.getId()));
	}
}
