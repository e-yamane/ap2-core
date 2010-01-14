package jp.rough_diamond.account.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import jp.rough_diamond.commons.service.annotation.PostPersist;
import jp.rough_diamond.commons.service.annotation.PostUpdate;
import jp.rough_diamond.framework.transaction.TransactionManager;

/**
 * 場所のHibernateマッピングクラス
**/
public class Place extends jp.rough_diamond.account.entity.base.BasePlace {
    private static final long serialVersionUID = 1L;

    Long loadedRevision = -1L;

    public Place() {
    	setRevision(loadedRevision);	//dummy
    }
    
    public static Place getPlaceByPlaceCode(String placeCode) {
		Extractor ex = new Extractor(Place.class);
		ex.add(Condition.eq(new Property(Place.PLACE_CODE), placeCode));
		List<Place> places = BasicService.getService().findByExtractor(ex);
		return (places.size() == 0) ? null : places.get(0);
    }
    
    public List<Place> getRoutes() {
    	List<Place> list = new ArrayList<Place>();
    	Place parent = getParent();
    	while(parent != null) {
    		list.add(parent);
    		parent = parent.getParent();
    	}
    	Collections.reverse(list);
    	return list;
    }
    
    public static Set<Long> getAllIds() {
    	Extractor e = new Extractor(Place.class);
    	e.addExtractValue(new ExtractValue("id", new Property(Place.class, null, Place.ID)));
    	List<Map<String, Long>> list = BasicService.getService().findByExtractor(e);
    	Set<Long> ret = new HashSet<Long>();
    	for(Map<String, Long> map : list) {
    		ret.add(map.get("id"));
    	}
    	return ret;
    }
    
    public List<Place> getChildren() {
    	Extractor extractor = new Extractor(Place.class);
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
    	Extractor e = new Extractor(Place.class);
    	e.add(Condition.in(new Property(PARENT + "." + ID), ids));
    	e.addExtractValue(new ExtractValue("id", new Property(Place.class, null, ID)));
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
    
    private final static String SKIP_LOAD_PARENT_KEY = "jp.rough_diamond.account.entity.Plance.loadParents.skip";
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

    @PostLoad
    @PostUpdate
    @PostPersist
    public void resetLoadedRevision() {
    	loadedRevision = getRevision();
    }

    public static Place getPlaceByCode(String systemName, String code) {
    	return getPlaceByCode(CodeSystem.getCodeSystemByName(systemName), code);
    }
    
    public static Place getPlaceByCode(String systemName, String code, Date date) {
    	return getPlaceByCode(CodeSystem.getCodeSystemByName(systemName), code, date);
    }
    
    public static Place getPlaceByCode(CodeSystem system, String code) {
    	return getPlaceByCode(system, code, new Date());
    }
    
    public static Place getPlaceByCode(CodeSystem system, String code, Date date) {
    	List<PlaceCode> targets = Code.getTargetHoldersByCode(PlaceCode.class, PlaceCode.CI, system, code, date);
    	for(PlaceCode target : targets) {
    		String code2 = target.getTarget().getCode(system, date);
    		if(code2.equals(code)) {
    			return target.getTarget();
    		}
    	}
    	return null;
    }
    

    public String getCode(String systemName) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName));
    }
    
    public String getCode(String systemName, Date date) {
    	return getCode(CodeSystem.getCodeSystemByName(systemName), date);
    }
    
    public String getCode(CodeSystem system) {
    	return getCode(system, new Date());
    }
    
	public String getCode(CodeSystem system, Date date) {
		return Code.getCode(PlaceCode.class, this, PlaceCode.TARGET, PlaceCode.CI, system, date);
	}
}
