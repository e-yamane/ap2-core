/**
 * 
 */
package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.resource.Messages;
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

	public void testPartyInsert() throws Exception {
		//新規登録ができること
		Extractor ex = new Extractor(Party.class);
		long before = BasicService.getService().getCountByExtractor(ex);
		Party newOwner = new Party();
		newOwner.setPartyCode("PTY-000000006");
    	newOwner.setName("テスト用パーティ");
    	newOwner.save();
    	long after = BasicService.getService().getCountByExtractor(ex);
    	assertEquals("追加後の件数が誤っています。", before + 1, after);
	}
	
	public void testPartyInsertUniqueCheck() throws Exception {
		//新規登録ができること
		Extractor ex = new Extractor(Party.class);
		long before = BasicService.getService().getCountByExtractor(ex);
		Party newOwner = new Party();
		newOwner.setPartyCode("PTY-000000001");
    	newOwner.setName("テスト用パーティ");
    	try {
        	newOwner.save();
        	fail("例外が送出されていません");
    	} catch(MessagesIncludingException e) {
    		e.printStackTrace();
    		Messages msgs = e.getMessages();
    		List<String> props = new ArrayList<String>(msgs.getProperties());
    		assertEquals("エラー発生件数が誤っています。", 1, props.size());
    		assertEquals("エラーメッセージ数が誤っています。", 1, msgs.get(props.get(0)).size());
    		assertEquals("エラーメッセージキーが誤っています。", "errors.duplicate", msgs.get(props.get(0)).get(0).getKey());
    	}
    	long after = BasicService.getService().getCountByExtractor(ex);
    	assertEquals("データが登録されています", before, after);
	}
	
	public void testPartyInsertAndDefaultValue() throws Exception {
		Party newOwner = new Party();
		newOwner.setPartyCode("PTY-000000006");
    	newOwner.setName("テスト用パーティ");
    	newOwner.save();
    	newOwner = BasicService.getService().findByPK(Party.class, newOwner.getId());
    	assertEquals("ステータスが誤っています。", Party.Status.TEST, newOwner.getStatus());
    	assertTrue("リビジョンが更新されていません。", (-1L != newOwner.getRevision().longValue()));
    	assertEquals("ロード時リビジョンが更新されていません", newOwner.getRevision(), newOwner.loadedRevision);
	}
	
	public void testPartyInsertデフォルトでセットされる値を明示的に設定したらデフォルト値にならないこと() throws Exception {
		Party newOwner = new Party();
		newOwner.setPartyCode("PTY-000000006");
    	newOwner.setName("テスト用パーティ");
    	newOwner.setStatusCode(Party.Status.AVAILABLE.getCode());
    	newOwner.setRevision(9999L);
    	newOwner.save();
    	newOwner = BasicService.getService().findByPK(Party.class, newOwner.getId());
    	assertEquals("ステータスが誤っています。", Party.Status.AVAILABLE, newOwner.getStatus());
    	assertEquals("リビジョンが更新されていません。", 9999L, newOwner.getRevision().longValue());
    	assertEquals("ロード時リビジョンが更新されていません", newOwner.getRevision(), newOwner.loadedRevision);
	}
	
	public void testPartyUpdate() throws Exception {
		//更新ができること
		Extractor ex = new Extractor(Party.class);
		long before = BasicService.getService().getCountByExtractor(ex);
		Party theParty = BasicService.getService().findByPK(Party.class, 4L);
		theParty.setPartyCode("PTY-000000006");
    	theParty.setName("テスト用パーティ");
    	theParty.save();
    	long after = BasicService.getService().getCountByExtractor(ex);
    	assertEquals("追加後の件数が誤っています。", before, after);
    	theParty = BasicService.getService().findByPK(Party.class, 4L);
    	assertEquals("名前が誤っています。", "テスト用パーティ", theParty.getName());
	}
	
	public void testPartyUpdateUniqueCheck() throws Exception {
		//更新ができること
		Extractor ex = new Extractor(Party.class);
		long before = BasicService.getService().getCountByExtractor(ex);
		Party theParty = BasicService.getService().findByPK(Party.class, 4L);
		theParty.setPartyCode("PTY-000000001");
    	theParty.setName("テスト用パーティ");
    	theParty.save();
		theParty.setPartyCode("PTY-000000002");
    	try {
        	theParty.save();
        	fail("例外が送出されていません");
    	} catch(MessagesIncludingException e) {
    		e.printStackTrace();
    		Messages msgs = e.getMessages();
    		List<String> props = new ArrayList<String>(msgs.getProperties());
    		assertEquals("エラー発生件数が誤っています。", 1, props.size());
    		assertEquals("エラーメッセージ数が誤っています。", 1, msgs.get(props.get(0)).size());
    		assertEquals("エラーメッセージキーが誤っています。", "errors.duplicate", msgs.get(props.get(0)).get(0).getKey());
    	}
    	long after = BasicService.getService().getCountByExtractor(ex);
    	assertEquals("データが登録されています", before, after);
	}
	
	public void testPartyUpdateAndDefaultValue() throws Exception {
		Party theParty = BasicService.getService().findByPK(Party.class, 4L);
		theParty.setPartyCode("PTY-000000006");
    	theParty.setName("テスト用パーティ");
    	theParty.setStatusCode("??");
    	long oldRivision = theParty.loadedRevision;
    	theParty.save();
    	theParty = BasicService.getService().findByPK(Party.class, theParty.getId());
    	assertEquals("ステータスが誤っています。", Party.Status.TEST, theParty.getStatus());
    	assertTrue("リビジョンが更新されていません。", (oldRivision != theParty.getRevision().longValue()));
    	assertEquals("ロード時リビジョンが更新されていません", theParty.getRevision(), theParty.loadedRevision);
	}
	
	public void testPartyUpdateデフォルトでセットされる値を明示的に設定したらデフォルト値にならないこと() throws Exception {
		Party theParty = BasicService.getService().findByPK(Party.class, 4L);
		theParty.setPartyCode("PTY-000000006");
    	theParty.setName("テスト用パーティ");
    	theParty.setStatusCode(Party.Status.AVAILABLE.getCode());
    	theParty.setRevision(9999L);
    	theParty.save();
    	theParty = BasicService.getService().findByPK(Party.class, theParty.getId());
    	assertEquals("ステータスが誤っています。", Party.Status.AVAILABLE, theParty.getStatus());
    	assertEquals("リビジョンが更新されていません。", 9999L, theParty.getRevision().longValue());
    	assertEquals("ロード時リビジョンが更新されていません", theParty.getRevision(), theParty.loadedRevision);
	}

	public void testUpdateCount() {
		Party p = BasicService.getService().findByPK(Party.class, 1L);
		assertEquals("更新カウントが誤っています。", 3L, p.getUpdateCount());
		p = BasicService.getService().findByPK(Party.class, 2L);
		assertEquals("更新カウントが誤っています。", 4L, p.getUpdateCount());
		p = BasicService.getService().findByPK(Party.class, 3L);
		assertEquals("更新カウントが誤っています。", 5L, p.getUpdateCount());
		p = BasicService.getService().findByPK(Party.class, 4L);
		assertEquals("更新カウントが誤っています。", 11L, p.getUpdateCount());
		p = BasicService.getService().findByPK(Party.class, 5L);
		assertEquals("更新カウントが誤っています。", 12L, p.getUpdateCount());
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
