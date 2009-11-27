/**
 * 
 */
package jp.rough_diamond.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.rough_diamond.NumberingLoader;
import jp.rough_diamond.account.entity.MasterLoader;
import jp.rough_diamond.account.entity.TransactionLoader;
import jp.rough_diamond.commons.testing.DBInitializer;
import jp.rough_diamond.framework.service.ServiceLocator;

/**
 * @author e-yamane
 *
 */
public class Resetter extends DBInitializer {

	private final static String[] NAMES;
	
	static {
		List<String> tmp = new ArrayList<String>();
		tmp.addAll(Arrays.asList(MasterLoader.getNames()));
		tmp.addAll(Arrays.asList(TransactionLoader.getNames()));
		tmp.addAll(Arrays.asList(NumberingLoader.getNames()));
		NAMES = tmp.toArray(new String[tmp.size()]);
    };

    private static boolean isFirst = true;
    public static void reset() throws Exception {
        if(isFirst) {
            Resetter resetter = ServiceLocator.getService(Resetter.class);
            resetter.delete();
            isFirst = false;
        } 
    }
    
    @Override
    protected String[] getResourceNames() {
        return NAMES;
    }

    public static void main(String[] args) throws Exception {
        Resetter resetter = ServiceLocator.getService(Resetter.class);
        resetter.cleanInsert();
        System.out.println("-------------- Finish --------------");
    }
}
