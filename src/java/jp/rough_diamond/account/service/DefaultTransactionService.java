package jp.rough_diamond.account.service;

import java.util.List;

import jp.rough_diamond.account.entity.TransactionMapper;
import jp.rough_diamond.account.entity.Transaction;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

public class DefaultTransactionService implements TransactionService {

	public void addAfterTransaction(Transaction before, Transaction after) throws MessagesIncludingException {
		BasicService service = BasicService.getService();
		TransactionMapper mapper;
		Extractor e = new Extractor(TransactionMapper.class);
		e.add(Condition.eq(new Property(TransactionMapper.BEFORE), before));
		List<TransactionMapper> list = service.findByExtractor(e, true);
		if(list.size() == 0) {
			mapper = new TransactionMapper();
			mapper.setBefore(before);
			mapper.setAfter(after);
			BasicService.getService().insert(mapper);
		} else {
			mapper = list.get(0);
			mapper.setAfter(after);
			try {
				service.update(mapper);
			} catch (VersionUnmuchException e1) {
				//ç°ÇÃÇ∆Ç±ÇÎÇ†ÇËÇ¶Ç»Ç¢ó·äO
				throw new RuntimeException(e1);
			}
		}
	}
}
