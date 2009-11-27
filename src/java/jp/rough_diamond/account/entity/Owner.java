package jp.rough_diamond.account.entity;

import java.util.List;

import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;

/**
 * 所有者のHibernateマッピングクラス
**/
public class Owner extends jp.rough_diamond.account.entity.base.BaseOwner {
    private static final long serialVersionUID = 1L;

    public static List<Owner> getAll() {
    	Extractor extractor = new Extractor(Owner.class);
    	extractor.addOrder(Order.asc(new Property(ID)));
    	return BasicService.getService().findByExtractor(extractor);
    }
}
