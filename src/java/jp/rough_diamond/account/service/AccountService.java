package jp.rough_diamond.account.service;

import java.util.Date;
import java.util.List;

import jp.rough_diamond.account.entity.Account;
import jp.rough_diamond.account.entity.Entry;
import jp.rough_diamond.account.entity.Item;
import jp.rough_diamond.account.entity.Party;
import jp.rough_diamond.account.entity.Place;
import jp.rough_diamond.framework.service.Service;

public interface AccountService extends Service {

	public Account getAccount(Long placeId, Long itemId, Long ownerId);
	public Account getAccount(Place place, Item item, Party owner);

	public Account getAccount(Place place, Long itemId, Long ownerId);
	public Account getAccount(Place place, Item item, Long ownerId);
	public Account getAccount(Place place, Long itemId, Party owner);

	public Account getAccount(Long placeId, Item item, Long ownerId);
	public Account getAccount(Long placeId, Item item, Party owner);

	public Account getAccount(Long placeId, Long itemId, Party owner);
	
	public Long getBalance(Account account,
			Date date, boolean isGrossItem, boolean isGrossPlace);
	public List<Entry> getTrend(Account account,
			Date from, Date to, boolean isGrossItem, boolean isGrossPlace);

}
