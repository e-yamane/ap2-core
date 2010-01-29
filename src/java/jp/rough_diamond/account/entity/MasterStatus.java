/**
 * 
 */
package jp.rough_diamond.account.entity;

public enum MasterStatus {
	/**
	 * 未定（初期値用）
	 */
	UNKNOWN("00"),
	/**
	 * テスト運用
	 */
	TEST("10"),
	/**
	 * 稼働中
	 */
	AVAILABLE("20"),
	/**
	 * 停止中
	 */
	STOP("30"),
	/**
	 * 削除
	 */
	DELETED("40");
	public final String code;
	private MasterStatus(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static MasterStatus getStstusByCode(String code) {
		if(code == null) {
			return UNKNOWN;
		}
		for(MasterStatus s : values()) {
			if(s.code.equals(code)) {
				return s;
			}
		}
		return UNKNOWN;
	}
}