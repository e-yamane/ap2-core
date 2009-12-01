package jp.rough_diamond.account.service;

import jp.rough_diamond.account.entity.Transaction;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.framework.service.Service;

public interface TransactionService extends Service {
	/**
	 * 予定に対する実績を追加する
	 * 既にマッピングされていてもよい。
	 * @param plan
	 * @param actual
	 */
	public void addAfterTransaction(Transaction before, Transaction after) throws MessagesIncludingException;
}
