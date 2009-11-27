package jp.rough_diamond.account.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PostLoad;
import jp.rough_diamond.framework.transaction.TransactionManager;

/**
 * 品目のHibernateマッピングクラス
**/
public class Item extends jp.rough_diamond.account.entity.base.BaseItem {
    private static final long serialVersionUID = 1L;

    public static List<Item> getRootItems() {
    	Extractor extractor = new Extractor(Item.class);
    	extractor.add(Condition.isNull(new Property(PARENT)));
    	extractor.addOrder(Order.asc(new Property(ID)));
    	return BasicService.getService().findByExtractor(extractor);
    }
    
    public List<Item> getRoutes() {
    	List<Item> list = new ArrayList<Item>();
    	Item parent = getParent();
    	while(parent != null) {
    		list.add(parent);
    		parent = parent.getParent();
    	}
    	Collections.reverse(list);
    	return list;
    }
    
    public static Set<Long> getAllIds() {
    	Extractor e = new Extractor(Item.class);
    	e.addExtractValue(new ExtractValue("id", new Property(Item.class, null, Item.ID)));
    	List<Map<String, Long>> list = BasicService.getService().findByExtractor(e);
    	Set<Long> ret = new HashSet<Long>();
    	for(Map<String, Long> map : list) {
    		ret.add(map.get("id"));
    	}
    	return ret;
    }
    
    public List<Item> getChildren() {
    	Extractor extractor = new Extractor(Item.class);
    	extractor.add(Condition.eq(new Property(PARENT), this));
    	extractor.addOrder(Order.asc(new Property(ID)));
    	return BasicService.getService().findByExtractor(extractor);
    }

    public Set<Long> getChildIds() {
    	Set<Long> set = new HashSet<Long>();
    	findChildIds(set, Arrays.asList(new Long[]{getId()}));
    	return set;
    }

    private static void findChildIds(Set<Long> set, Collection<Long> ids) {
    	Extractor e = new Extractor(Item.class);
    	e.add(Condition.in(new Property(PARENT + "." + ID), ids));
    	e.addExtractValue(new ExtractValue("id", new Property(Item.class, null, ID)));
    	Set<Long> tmp = new HashSet<Long>();
    	List<Map<String, Long>> list = BasicService.getService().findByExtractor(e);
    	for(Map<String, Long> map : list) {
    		tmp.add(map.get("id"));
    	}
    	removeCycricIds(set, tmp);
    	set.addAll(tmp);
    	if(tmp.size() != 0) {
    		findChildIds(set, tmp);
    	}
    }
    
    private static void removeCycricIds(Set<Long> baseIds, Set<Long> newIds) {
    	Iterator<Long> ite = newIds.iterator();
    	while(ite.hasNext()) {
    		Long id = ite.next();
    		if(baseIds.contains(id)) {
    			ite.remove();
    		}
    	}
    }

    private final static String SKIP_LOAD_PARENT_KEY = "jp.rough_diamond.account.entity.Item.loadParents.skip";
    public static void skipLoadParents() {
    	Map<Object, Object> map = TransactionManager.getTransactionContext();
    	if(map != null) {
    		map.put(SKIP_LOAD_PARENT_KEY, Boolean.TRUE);
    	}
    }
    
    @PostLoad
    @Override
    public void loadParents() {
    	if(!TransactionManager.getTransactionContext().containsKey(SKIP_LOAD_PARENT_KEY)) {
    		super.loadParents();
    	}
    }    
}
