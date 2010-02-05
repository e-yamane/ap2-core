package jp.rough_diamond.account.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.ExtractValue;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Max;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PreUpdate;

/**
 * 場所のHibernateマッピングクラス
**/
public class Place extends jp.rough_diamond.account.entity.base.BasePlace {
    private static final long serialVersionUID = 1L;
    public final static Long DUMMY_REVISION = Long.MIN_VALUE;

    public Place() {
    	setStatusCode(MasterStatus.UNKNOWN.code);		//dummy
    	setRevision(DUMMY_REVISION);					//dummy
    }
    
    public static Place getPlaceByPlaceCode(String placeCode) {
		Extractor ex = new Extractor(Place.class);
		ex.add(Condition.eq(new Property(Place.PLACE_CODE), placeCode));
		List<Place> places = BasicService.getService().findByExtractor(ex);
		return (places.size() == 0) ? null : places.get(0);
    }
    
	public static Long getMaxRevision() {
    	Extractor ex1 = new Extractor(Place.class);
    	ex1.addExtractValue(new ExtractValue("max", new Max(new Property(Place.REVISION))));
    	ex1.setReturnType(Long.class);
    	Long ret = (Long)BasicService.getService().findByExtractor(ex1).get(0);
    	Extractor ex2 = new Extractor(PlaceCode.class);
    	ex2.addExtractValue(new ExtractValue("max", new Max(new Property(PlaceCode.CI + Code.REVISION))));
    	ex2.setReturnType(Long.class);
    	return Math.max(ret, (Long)BasicService.getService().findByExtractor(ex2).get(0));
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
    
	public MasterStatus getStatus() {
		return MasterStatus.getStstusByCode(getStatusCode());
	}

	@PreUpdate
	public void refreshStatus() {
		if(getStatus() == MasterStatus.UNKNOWN) {
			setStatusCode(MasterStatus.TEST.code);
		}
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

	public long getUpdateCount() {
		Extractor ex = new Extractor(PlaceCode.class);
		ex.add(Condition.eq(new Property(PlaceCode.TARGET), this));
		ex.addExtractValue(new ExtractValue("rev", new Max(new Property(PlaceCode.CI + Code.REVISION))));
		ex.setReturnType(Long.class);
		long revMax = (Long)BasicService.getService().findByExtractor(ex).get(0);
		return Math.max(revMax, getRevision());
	}

	public List<PlaceCode> getPlaceCodes() {
		Extractor ex = new Extractor(PlaceCode.class);
		ex.add(Condition.eq(new Property(PlaceCode.TARGET), this));
		ex.addOrder(Order.desc(new Property(PlaceCode.TS + UpdateTimestamp.LAST_MODIFIED_DATE)));
		ex.addOrder(Order.desc(new Property(PlaceCode.ID)));
		return BasicService.getService().findByExtractor(ex);
	}
}
