package jp.rough_diamond.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.rough_diamond.account.entity.Account;
import jp.rough_diamond.account.entity.Entry;
import jp.rough_diamond.account.entity.Item;
import jp.rough_diamond.account.entity.Owner;
import jp.rough_diamond.account.entity.Place;
import jp.rough_diamond.account.entity.Transaction;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.extractor.Sum;
import jp.rough_diamond.commons.service.BasicService;

public class DefaultAccountService implements AccountService {

	public Account getAccount(Long placeId, Long itemId, Long ownerId) {
		BasicService service = BasicService.getService();
		return getAccount(
				service.findByPK(Place.class, placeId),
				service.findByPK(Item.class, itemId),
				service.findByPK(Owner.class, ownerId));
	}

	public Account getAccount(Place place, Item item, Owner owner) {
		//　いずれかが未設定の場合は処理中断
		if(place == null || item == null || owner ==null) {
			return null;
		}
		// 勘定の検索条件設定
		Extractor extractor = new Extractor(Account.class);
		extractor.add(Condition.eq(new Property(Account.PLACE),  place));
		extractor.add(Condition.eq(new Property(Account.ITEM), item));
		extractor.add(Condition.eq(new Property(Account.OWNER), owner));
		// 勘定の検索
		List<Account> list = BasicService.getService().findByExtractor(extractor);

		if(list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public Account getAccount(Place place, Long itemId, Long ownerId) {
		BasicService service = BasicService.getService();
		return getAccount(
				place,
				service.findByPK(Item.class, itemId),
				service.findByPK(Owner.class, ownerId));

	}

	public Account getAccount(Place place, Item item, Long ownerId) {
		BasicService service = BasicService.getService();
		return getAccount(
				place,
				item,
				service.findByPK(Owner.class, ownerId));
	}

	public Account getAccount(Place place, Long itemId, Owner owner) {
		BasicService service = BasicService.getService();
		return getAccount(
				place,
				service.findByPK(Item.class, itemId),
				owner);
	}

	public Account getAccount(Long placeId, Item item, Long ownerId) {
		BasicService service = BasicService.getService();
		return getAccount(
				service.findByPK(Place.class, placeId),
				item,
				service.findByPK(Owner.class, ownerId));
	}

	public Account getAccount(Long placeId, Item item, Owner owner) {
		BasicService service = BasicService.getService();
		return getAccount(
				service.findByPK(Place.class, placeId),
				item,
				owner);
	}

	public Account getAccount(Long placeId, Long itemId, Owner owner) {
		BasicService service = BasicService.getService();
		return getAccount(
				service.findByPK(Place.class, placeId),
				service.findByPK(Item.class, itemId),
				owner);
	}
	
	public Long getBalance(Account account,
			Date date, boolean isGrossItem, boolean isGrossPlace) {
		//TODO 計算結果をキャッシュした場合の処理は未実装
/*
		Extractor e = new Extractor(Entry.class);
		e.add(Condition.eq(Entry.ACCOUNT, this));
		e.add(Condition.le(
				Entry.TRANSACTION + "." + Transaction.PROCESS_DATE, date));
		e.addExtractValue(new ExtractValue(
				"quantity", Entry.class, null, "quantity"));
		List<Map<String, Long>> mapList = BasicService.getService().findByExtractor(e);
		long ret = 0L;
		for(Map<String, Long> map : mapList) {
			ret += map.get("quantity");
		}
		return ret;
*/
		Extractor e = getTrendExtractor(account, null, date, isGrossItem, isGrossPlace);
		if(e == null) {
			return 0L;
		}
		e.addExtractValue(new ExtractValue("quantity", new Sum(new Property(Entry.QUANTITY))));
		List<Map<String, Long>> list = BasicService.getService().findByExtractor(e);
		if(list.size() == 0) {
			return 0L;
		} else {
			return list.get(0).get("quantity");
		}
	}
	
	// TODO: AccountTestでのテストをAccountServiceTestに移動？
	public List<Entry> getTrend(Account account,
			Date from, Date to, boolean isGrossItem, boolean isGrossPlace) {
		Extractor e = getTrendExtractor(account, from, to, isGrossItem, isGrossPlace);
		if(e == null) {
			return new ArrayList<Entry>();
		} else {
			e.addOrder(Order.asc(new Property(Entry.TRANSACTION + "." + Transaction.PROCESS_DATE)));
			e.addOrder(Order.asc(new Property(Entry.ID)));
			return BasicService.getService().findByExtractor(e);
		}
	}
	
	protected Extractor getTrendExtractor(Account account,
			Date from, Date to, boolean isGrossItem, boolean isGrossPlace) {
		Set<Long> targetAccountIds = getTargetAccountIds(
				account, isGrossItem, isGrossPlace);
		if(targetAccountIds.isEmpty()) {
			return null;
		}
		Extractor e = new Extractor(Entry.class);
		e.add(Condition.eq(new Property(Entry.ACCOUNT + "." + Account.OWNER), account.getOwner()));
		e.add(Condition.in(new Property(Entry.ACCOUNT + "." + Account.ID), targetAccountIds));
		if(from != null) {
			e.add(Condition.ge(new Property(Entry.TRANSACTION + "." +
					Transaction.PROCESS_DATE), from));
		}
		if(to != null) {
			e.add(Condition.lt(new Property(Entry.TRANSACTION + "." +
					Transaction.PROCESS_DATE), to));
		}
		Transaction.addNewestTransactionCondition(e, Entry.class, Entry.TRANSACTION, null);
		return e;
		
	}
	
	protected Set<Long> getTargetAccountIds(
			Account account, boolean isGrossItem, boolean isGrossPlace) {
		Set<Long> ret = new HashSet<Long>();
//	  ret.addAll(getAccountIdsByChildrenItems(isGrossItem));
//	  ret.retainAll(getAccountIdsByChildrenPlaces(isGrossPlace));
		GetAccountIdsByChildrenItemsRunnable r1
				= new GetAccountIdsByChildrenItemsRunnable(account, isGrossItem);
		GetAccountIdsByChildrenPlacesRunnable r2
				= new GetAccountIdsByChildrenPlacesRunnable(account, isGrossPlace);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
		try {
			t2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
		ret.addAll(r1.ids);
		ret.retainAll(r2.ids);
		if(account.getId() != null) {
			ret.add(account.getId());
		}
		return ret;
	}
	
	private class GetAccountIdsByChildrenItemsRunnable implements Runnable {
		private boolean isGrossItem;
		private Account account;
		private Set<Long> ids;
		GetAccountIdsByChildrenItemsRunnable(
				Account account, boolean isGrossItem) {
			this.account = account;
			this.isGrossItem = isGrossItem;
		}

		@Override
		public void run() {
			ids = getAccountIdsByChildrenItems(account, isGrossItem);
		}
	}
	
	protected Set<Long> getAccountIdsByChildrenItems(
			Account account, boolean isGrossItem) {
		Set<Long> itemIds;
		if(isGrossItem) {
			if(account.getItem() == null) {
				itemIds = Item.getAllIds();
			} else {
				itemIds = account.getItem().getChildIds();
				itemIds.add(account.getItem().getId());
			}
		} else {
			itemIds = new HashSet<Long>();
			itemIds.add(account.getItem().getId());
		}
		if(itemIds.size() == 0) {
			return new HashSet<Long>();
		}
		Extractor e = new Extractor(Account.class);
		e.add(Condition.in(new Property(Account.ITEM + "." + Item.ID), itemIds));
		e.addExtractValue(new ExtractValue("id", new Property(Account.class, null, Account.ID)));
		List<Map<String, Long>> list = BasicService.getService().findByExtractor(e);
		Set<Long> ret = new HashSet<Long>();
		for(Map<String, Long> map : list) {
			ret.add(map.get("id"));
		}
		return ret;
	}

	private class GetAccountIdsByChildrenPlacesRunnable implements Runnable {
		private boolean isGrossPlace;
		private Account account;
		private Set<Long> ids;
		GetAccountIdsByChildrenPlacesRunnable(
				Account account, boolean isGrossPlace) {
			this.account = account;
			this.isGrossPlace = isGrossPlace;
		}

		@Override
		public void run() {
			ids = getAccountIdsByChildrenPlaces(account, isGrossPlace);
		}
	}
	
	protected Set<Long> getAccountIdsByChildrenPlaces(
			Account account, boolean isGrossPlace) {
		Set<Long> placeIds;
		if(isGrossPlace) {
			if(account.getPlace() == null) {
				placeIds = Place.getAllIds();
			} else {
				placeIds = account.getPlace().getChildIds();
				placeIds.add(account.getPlace().getId());
			}
		} else {
			placeIds = new HashSet<Long>();
			if(account.getPlace() != null) {
				placeIds.add(account.getPlace().getId());
			}
		}
		if(placeIds.size() == 0) {
			return new HashSet<Long>();
		}
		Extractor e = new Extractor(Account.class);
		e.add(Condition.in(new Property(Account.PLACE + "." + Place.ID), placeIds));
		e.addExtractValue(new ExtractValue("id", new Property(Account.class, null, Account.ID)));
		List<Map<String, Long>> list = BasicService.getService().findByExtractor(e);
		Set<Long> ret = new HashSet<Long>();
		for(Map<String, Long> map : list) {
			ret.add(map.get("id"));
		}
		return ret;
	}
}
