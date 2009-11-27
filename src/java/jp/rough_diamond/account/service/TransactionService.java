package jp.rough_diamond.account.service;

import jp.rough_diamond.account.entity.Transaction;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.framework.service.Service;

public interface TransactionService extends Service {
	/**
	 * �\��ɑ΂�����т�ǉ�����
	 * ���Ƀ}�b�s���O����Ă��Ă��悢�B
	 * @param plan
	 * @param actual
	 */
	public void addAfterTransaction(Transaction before, Transaction after) throws MessagesIncludingException;
}
