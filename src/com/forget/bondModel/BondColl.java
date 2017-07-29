package com.forget.bondModel;


import java.util.HashMap;
import java.util.Map;

/*
 * 数据集合
 */
public class BondColl implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	/*
	 * 首字对象
	 */
	private Map<String,BondModel> keybondColl = new HashMap<String,BondModel>();
	
	private double totalOffsetCount = 0;

	
	
	public Map<String, BondModel> getKeybondColl() {
		return keybondColl;
	}

	public void setKeybondColl(Map<String, BondModel> keybondColl) {
		this.keybondColl = keybondColl;
	}

	public double getTotalOffsetCount() {
		return totalOffsetCount;
	}

	public void setTotalOffsetCount(double totalOffsetCount) {
		this.totalOffsetCount = totalOffsetCount;
	}
	
	
	

	
}
