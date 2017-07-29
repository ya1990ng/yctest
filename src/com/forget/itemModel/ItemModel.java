package com.forget.itemModel;

/* 单字
 * 数据模型
 */
public class ItemModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyItem;   //单字
	private Double validCount;  //边累积边遗忘   有效次数
	private Double lastUpdateTime;  //上一次入库时间
	
	public String getKeyItem() {
		return keyItem;
	}
	public void setKeyItem(String keyItem) {
		this.keyItem = keyItem;
	}
	public Double getValidCount() {
		return validCount;
	}
	public void setValidCount(Double validCount) {
		this.validCount = validCount;
	}
	public Double getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Double lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
}
