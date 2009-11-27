package jp.rough_diamond.account.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.commons.resource.Messages;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.WhenVerifier;
import junit.framework.TestCase;

@SuppressWarnings("deprecation")
public class ActualTransactionTest extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void testVerify() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = sdf.parse("20080421");
		BasicService service = BasicService.getService();
		Account from = service.findByPK(Account.class, 3L);
		Account to = service.findByPK(Account.class, 5L);
		ActualTransaction at = makeTransaction(from, to, 70, d);
		Messages msgs = service.validate(at, WhenVerifier.INSERT);
		System.out.println(msgs);
		assertFalse("エラーが発生しています。", msgs.hasError());
		at = makeTransaction(from, to, 71, d);
		msgs = service.validate(at, WhenVerifier.INSERT);
		System.out.println(msgs);
		assertTrue("エラーが発生していません。", msgs.hasError());
	}
	
	ActualTransaction makeTransaction(Account from, Account to, long quantity, Date d) {
		ActualTransaction ret = new ActualTransactionExt();
		Entry fromE = new Entry();
		fromE.setAccount(from);
		Entry toE = new Entry();
		toE.setAccount(to);
		fromE.setQuantity(quantity * -1);
		toE.setQuantity(quantity);
		ret.setEntries(Arrays.asList(fromE, toE));
		ret.setProcessDate(d);
		ret.setProcessId("xyz");
		return ret;
	}
	
	public static class ActualTransactionExt extends ActualTransaction {
		private static final long serialVersionUID = 1L;
	}
}
