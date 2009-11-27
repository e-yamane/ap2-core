package jp.rough_diamond.account.entity;

import java.util.List;

import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.service.BasicService;

/**
 * 予定トランザクションのHibernateマッピングクラス
**/
@Deprecated
public class PlanTransaction extends jp.rough_diamond.account.entity.base.BasePlanTransaction {
    private static final long serialVersionUID = 1L;

    public PlanTransaction() {
    	super();
    	setActual(false);
    }
    
    public static List<PlanTransaction> getAll() {
    	Extractor extractor = new Extractor(PlanTransaction.class);
    	// 同一の場合はID順にソート
    	extractor.addOrder(Order.desc(new Property(PlanTransaction.PROCESS_DATE)));
    	extractor.addOrder(Order.desc(new Property(PlanTransaction.PROCESS_ID)));
    	return BasicService.getService().findByExtractor(extractor);
    }
}
