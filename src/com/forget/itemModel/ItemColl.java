package com.forget.itemModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/*
 * 单子 集合
 */
public class ItemColl implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * keyword 集合
	 */
	private Map<String,ItemModel> keyItemColl = new HashMap<String,ItemModel>();
	/*
	 * 全局计数器
	 */
	private double totalOffsetCount = 0;
	
	private Set<String> stopWords;
	
	public Map<String, ItemModel> getKeyItemColl() {
		return keyItemColl;
	}

	public void setKeyItemColl(Map<String, ItemModel> keyItemColl) {
		this.keyItemColl = keyItemColl;
	}

	public double getTotalOffsetCount() {
		return totalOffsetCount;
	}

	public void setTotalOffsetCount(double totalOffsetCount) {
		this.totalOffsetCount = totalOffsetCount;
	}
	
	public Set<String> getStopWords() {
		return stopWords;
	}

	public void setStopWords(Set<String> stopWords) {
		this.stopWords = stopWords;
	}

	public static void readEmotion(Set<String> set,InputStreamReader read){
		try {
			//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String line=null;
			while((line=bufferedReader.readLine()) != null){
				set.add(line.trim());
			}
			read.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
