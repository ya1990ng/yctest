package com.forget.bondModel;


import com.forget.itemModel.ItemColl;
import com.forget.itemModel.ItemModel;

/*
 * 键  对象
 */
public class BondModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemModel headItemModel = new ItemModel();
	private ItemColl tailItemColl = new ItemColl();
	
	
	
	public ItemModel getHeadItemModel() {
		return headItemModel;
	}
	public void setHeadItemModel(ItemModel headItemModel) {
		this.headItemModel = headItemModel;
	}
	public ItemColl getTailItemColl() {
		return tailItemColl;
	}
	public void setTailItemColl(ItemColl tailItemColl) {
		this.tailItemColl = tailItemColl;
	}
	
}
