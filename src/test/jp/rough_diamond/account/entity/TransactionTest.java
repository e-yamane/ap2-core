package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Count;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;
import jp.rough_diamond.framework.service.Service;
import jp.rough_diamond.framework.service.ServiceLocator;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

public class TransactionTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testSaveWhenUpdate() throws Exception {
		Extractor tCountEx = new Extractor(Transaction.class);
		long tCount = BasicService.getService().getCountByExtractor(tCountEx);
		Extractor eCountEx = new Extractor(Entry.class);
		long eCount = BasicService.getService().getCountByExtractor(eCountEx);
		Transaction t = doInsert();
		assertEquals("Transaction�̌���������Ă��܂��B", tCount + 1, BasicService.getService().getCountByExtractor(tCountEx));
		assertEquals("Entry�̌���������Ă��܂��B", eCount + 2, BasicService.getService().getCountByExtractor(eCountEx));
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE + "." + Transaction.ID), 10L));
		ex.add(Condition.eq(new Property(TransactionMapper.AFTER), t));
		assertEquals("TransactionMapper���o�^����Ă��܂���", 1, BasicService.getService().getCountByExtractor(ex));
	}
	
	public void testSaveWhenUpdateInTransaction() throws Exception {
		Extractor tCountEx = new Extractor(Transaction.class);
		long tCount = BasicService.getService().getCountByExtractor(tCountEx);
		Extractor eCountEx = new Extractor(Entry.class);
		long eCount = BasicService.getService().getCountByExtractor(eCountEx);
		Transaction t = ServiceLocator.getService(TransactionTestService.class).doIt();
		assertEquals("Transaction�̌���������Ă��܂��B", tCount + 1, BasicService.getService().getCountByExtractor(tCountEx));
		assertEquals("Entry�̌���������Ă��܂��B", eCount + 2, BasicService.getService().getCountByExtractor(eCountEx));
		Extractor ex = new Extractor(TransactionMapper.class);
		ex.add(Condition.eq(new Property(TransactionMapper.BEFORE + "." + Transaction.ID), 10L));
		ex.add(Condition.eq(new Property(TransactionMapper.AFTER), t));
		assertEquals("TransactionMapper���o�^����Ă��܂���", 1, BasicService.getService().getCountByExtractor(ex));
	}
	
	static Transaction doInsert() throws VersionUnmuchException, MessagesIncludingException {
		Transaction t1 = BasicService.getService().findByPK(Transaction.class, 1L);
		System.out.println(t1.getRegisterDate());
		Transaction t = BasicService.getService().findByPK(Transaction.class, 10L);
		t.getFromEntries().get(0).setQuantity(-20L);
		t.getToEntries().get(0).setQuantity(20L);
		t.save();
		return t;
	}
	
	public static class TransactionTestService implements Service {
		public Transaction doIt() throws VersionUnmuchException, MessagesIncludingException {
			return doInsert();
		}
	}

	//�����Framework�Ō��؂ł��Ȃ��������߃e�X�g�Ƃ��Ă����Ƃɉ����Ă݂�
	public void test�����ɓ��t�������ďW�v�֐����g�p�������ʂ̌������擾���Ă݂�() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080501");
		Extractor ex = new Extractor(Transaction.class);
		ex.add(Condition.gt(new Property(Transaction.REGISTER_DATE), d));
		ex.addExtractValue(new ExtractValue("date", new Property(Transaction.REGISTER_DATE)));
		ex.addExtractValue(new ExtractValue("count", new Count()));
		assertEquals("�ԋp��������Ă��܂��B", 4, BasicService.getService().getCountByExtractor(ex));
	}
}
