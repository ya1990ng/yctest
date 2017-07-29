package com.forget.keywords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.forget.bondModel.BondColl;
import com.forget.bondModel.BondModel;
import com.forget.dataSort.SortByValidValue;
import com.forget.forgetDAL.ForgetDAL;
import com.forget.itemModel.ItemColl;
import com.forget.itemModel.ItemColls;
import com.forget.itemModel.ItemModel;
import com.forget.utils.DateTest;
import com.forget.utils.SaveObject2Serialize;

/*
 * 词库生成
 */
public class KeywordColl { 

	public static Double rememberValue = 1.0/(1.0-ForgetDAL.rememberValue(7));
	
	public static JSONObject toInitKeywordColl(String serFilePath){
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			if(serFilePath != null && !serFilePath.trim().equals("")){
				System.setProperty("appPath", serFilePath);
				ItemColls itemColls = ItemColls.getItemColls();
				ItemColl itemColl = itemColls.getItemColl();
				if(itemColl != null){
					json.put("state", "success");
					json.put("msg", "");
				}else{
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	public static JSONObject saveKeywordColl(String serFilePath,String keywordCollFilePath){
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			if(serFilePath != null && !serFilePath.trim().equals("")){
				ItemColls itemColls = ItemColls.getItemColls();
				ItemColl itemColl = itemColls.getItemColl();
				SaveObject2Serialize.serialize(serFilePath, itemColl);
				if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
					Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
					Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
					WriteDate(sortMapByValue,keywordCollFilePath);
				}
				json.put("state", "success");
				json.put("msg", "ok");
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	
	public static JSONObject toKeywordColl(String srcFilePath,String keywordCollFilePath,String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			if(srcFilePath != null && !srcFilePath.trim().equals("") && serFilePath != null && !serFilePath.trim().equals("")){
				String str = srcFilePath;
				try {
					BondColl bondColl = new BondColl();
					List<String> list = readTxtFile(str,bondColl);
					ItemColl itemColl = new ItemColl();
					for (String string : list) {
						txtContext2BondColl(string,bondColl,itemColl,true);
					}
					itemColl = clearItemColl1(itemColl);
					SaveObject2Serialize.serialize(serFilePath, itemColl);
					if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
						Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
						Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
						WriteDate(sortMapByValue,keywordCollFilePath);
					}
					json.put("state", "success");
					json.put("msg", "ok");
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	public static JSONObject toUpdKeywordColl(String srcFilePath,String keywordCollFilePath,String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			if(srcFilePath != null && !srcFilePath.trim().equals("")){
				String str = srcFilePath;
				try {
					BondColl bondColl = new BondColl();
					List<String> list = readTxtFile(str,bondColl);
					ItemColls itemColls = ItemColls.getItemColls();
					ItemColl itemColl = itemColls.getItemColl();
					for (String string : list) {
						txtContext2BondColl(string,bondColl,itemColl,true);
					}
					itemColl = clearItemColl1(itemColl);
					if(serFilePath != null && !serFilePath.trim().equals("")){
						SaveObject2Serialize.serialize(serFilePath, itemColl);
					}
					if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
						Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
						Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
						WriteDate(sortMapByValue,keywordCollFilePath);
					}
					itemColls.setItemColl(itemColl);
					json.put("state", "success");
					json.put("msg", "ok");
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	public static JSONObject toUpdKeywordCollByStr(String content,String keywordCollFilePath,String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			if(content != null && !content.trim().equals("")){
				try {
					BondColl bondColl = new BondColl();
					txtContext2Bond(content,bondColl);
					ItemColls itemColls = ItemColls.getItemColls();
					ItemColl itemColl = itemColls.getItemColl();
					txtContext2BondColl(content,bondColl,itemColl,true);
					itemColl = clearItemColl1(itemColl);
					if(serFilePath != null && !serFilePath.trim().equals("")){
						SaveObject2Serialize.serialize(serFilePath, itemColl);
					}
					if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
						Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
						Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
						WriteDate(sortMapByValue,keywordCollFilePath);
					}
					itemColls.setItemColl(itemColl);
					json.put("state", "success");
					json.put("msg", "ok");
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		
		return json;
	}
	
	public static JSONObject toDelKeywordColl(String serFilePath,String keywordCollFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			ItemColls itemColls = ItemColls.getItemColls();
			ItemColl itemColl = itemColls.getItemColl();
			if(serFilePath != null && !serFilePath.trim().equals("")){
				SaveObject2Serialize.serialize(serFilePath, itemColl);
			}
			if(keywordCollFilePath != null && !keywordCollFilePath.trim().equals("")){
				Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
				Map<String, ItemModel> sortMapByValue = SortByValidValue.sortMapByValue(keyItemColl);
				WriteDate(sortMapByValue,keywordCollFilePath);
			}
			itemColl = new ItemColl();
			itemColls.setItemColl(itemColl);
			json.put("state", "success");
			json.put("msg", "ok");
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
		}
		return json;
	}
	
	public static JSONObject getKeywordColl(String serFilePath) {
		JSONObject json = new JSONObject();
		boolean compare_date = DateTest.compare_date();
		if(compare_date){
			if(serFilePath != null && !serFilePath.trim().equals("")){
				try {
					System.setProperty("appPath", serFilePath);
					ItemColls itemColls = ItemColls.getItemColls();
					ItemColl itemColl = itemColls.getItemColl();
					Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
					Map<String,Double> colls = new HashMap<String,Double>();
					if(keyItemColl != null && !keyItemColl.isEmpty()){
						ItemModel value = null;
						for(Map.Entry<String, ItemModel> entry : keyItemColl.entrySet()){
							value = entry.getValue();
							if(value != null){
								colls.put(entry.getKey(),value.getValidCount());
							}
						}
					}
					json.put("state", "success");
					json.put("msg", "ok");
					json.put("keywordColl", colls);
				} catch (Exception e) {
					json.put("state", "error");
					json.put("msg", "参数不合法.");
					json.put("keywordColl", null);
				}
			}else{
				json.put("state", "error");
				json.put("msg", "参数不合法.");
				json.put("keywordColl", null);
			}
		}else{
			json.put("state", "error");
			json.put("msg", "程序已逾期，请联系管理员.");
			json.put("keywordColl", null);
		}
		
		return json;
	}
	
	/*
	 * 单项
	 */
	public static ItemColl updateItemColl(String itemTail,ItemColl itemColl){
		
		Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
		
		if(!keyItemColl.containsKey(itemTail)){
			ItemModel itemModel = new ItemModel();
			itemModel.setKeyItem(itemTail);
			itemModel.setValidCount(Double.valueOf(1));
			itemModel.setLastUpdateTime(itemColl.getTotalOffsetCount());
			keyItemColl.put(itemTail, itemModel);
		}else{
			ItemModel itemModel = keyItemColl.get(itemTail);
			itemModel.setValidCount(itemModel.getValidCount()*ForgetDAL.rememberValue(itemColl.getTotalOffsetCount()-itemModel.getLastUpdateTime())+1);
			itemModel.setLastUpdateTime(itemColl.getTotalOffsetCount());
		}
		
		//清楚低于阀值的词
		
		if(itemColl.getTotalOffsetCount() >= Double.MAX_VALUE-10){
			itemColl = clearItemColl1(itemColl);
			
		}else{
			itemColl.setTotalOffsetCount(itemColl.getTotalOffsetCount()+1);
		}
		
		return itemColl;
	}
	
	/*
	 * 键
	 */
	public static void updateBondColl(String itemHead,String itemTail,BondColl bondColl){
		
		Map<String, BondModel> keyBondColl = bondColl.getKeybondColl();
		//首字第一次出现
		if(!keyBondColl.containsKey(itemHead)){
			BondModel bondModel = new BondModel();
			ItemModel headItemModel = bondModel.getHeadItemModel();
			headItemModel.setKeyItem(itemHead);
			headItemModel.setValidCount(Double.valueOf(0));
			headItemModel.setLastUpdateTime(bondColl.getTotalOffsetCount());
			keyBondColl.put(itemHead, bondModel);
		}
		//首字已出现
		BondModel bondModel = keyBondColl.get(itemHead);
		ItemModel headItemModel = bondModel.getHeadItemModel();
		headItemModel.setValidCount(1+headItemModel.getValidCount()*ForgetDAL.rememberValue(bondColl.getTotalOffsetCount()-headItemModel.getLastUpdateTime()));
		headItemModel.setLastUpdateTime(bondColl.getTotalOffsetCount());
	
		ItemColl tailItemColl = bondModel.getTailItemColl();
		
		tailItemColl.setTotalOffsetCount(bondColl.getTotalOffsetCount());
	
		updateItemColl(itemTail,tailItemColl);
		
		bondColl.setTotalOffsetCount(bondColl.getTotalOffsetCount()+1);
		if(bondColl.getTotalOffsetCount() >= Double.MAX_VALUE-10){
			bondColl = clearBondColl(bondColl);
			bondColl.setTotalOffsetCount(0);
		}
	}
	
	public static boolean isLetter(String str) {
		String regex = "^[a-zA-Z0-9.]+$";//其他需要，直接修改正则表达式就好
		return str.matches(regex);
	}
	
	public static void main(String[] args) {
		String line = ",shs.dd"; 
		System.out.println(line.replaceAll("[\\p{P}&&[^.]]", ""));
		txtContext2Bond("110.1cm的身高岂不是很low", null);
	}
	
	/*
	 * 句2键
	 */
	public static void txtContext2Bond(String txt,BondColl bondColl){
		if(txt != null && txt.length() > 0){
			String itemHead = txt.substring(0,1);
			String itemTail = "";
			String letter = "";
			int txtLen = txt.length();
			for (int i = 1; i < txtLen; i++) {
				itemTail = txt.substring(i,i+1);
				if(isLetter(itemTail)){
					letter += itemTail;
					if(i == txt.length()-1){
						itemTail = letter;
						updateBondColl(itemHead,itemTail,bondColl);
						itemHead = itemTail;
					}
					continue;
				}else{
					if(letter != null && !letter.trim().equals("")){
						updateBondColl(itemHead,letter,bondColl);
						letter = "";
					}
					updateBondColl(itemHead,itemTail,bondColl);
					itemHead = itemTail;
				}
			}
		}
	}
	
	/*
	 * 句2词库
	 */
	public static void txtContext2BondColl(String txt,BondColl bondColl,ItemColl itemColl,boolean isUpdateBondColl){
		
		if(isUpdateBondColl){
			txtContext2Bond(txt,bondColl);
		}
		StringBuffer buffer = new StringBuffer();
		if(txt.length() != 0){ 
			String keywordHead = txt.substring(0,1);
			buffer.append(keywordHead);
			String keywordTail = "";
			String letter = "";
			int txtLen = txt.length();
			for(int i=1;i<txtLen;i++){
				keywordTail = txt.substring(i,i+1);
				if(isLetter(keywordTail)){
					letter += keywordTail;
					if(i == txt.length()-1){
						keywordTail = letter;
						//true  相关    false 不相关
						boolean b = IsBondValid(keywordHead,keywordTail,bondColl);
						if(!b){
							String keyword = buffer.toString();
							if(keyword != null && !keyword.trim().equals("")){
								updateItemColl(keyword,itemColl);
								//清空StringBuffer
							}
							buffer.setLength(0);
							buffer.append(keywordTail);
						}else{
							buffer.append(keywordTail);
						}
						keywordHead = keywordTail;
					}
					continue;
				}else{
					if(letter != null && !letter.trim().equals("")){
						//true  相关    false 不相关
						boolean b = IsBondValid(keywordHead,letter,bondColl);
						if(!b){
							String keyword = buffer.toString();
							if(keyword != null && !keyword.trim().equals("")){
								updateItemColl(keyword,itemColl);
								//清空StringBuffer
							}
							buffer.setLength(0);
							buffer.append(letter);
						}else{
							buffer.append(letter);
						}
						letter = "";
					}
					//true  相关    false 不相关
					boolean b = IsBondValid(keywordHead,keywordTail,bondColl);
					if(!b){
						String keyword = buffer.toString();
						if(keyword != null && !keyword.trim().equals("")){
							updateItemColl(keyword,itemColl);
							//清空StringBuffer
						}
						buffer.setLength(0);
						buffer.append(keywordTail);
					}else{
						buffer.append(keywordTail);
					}
					keywordHead = keywordTail;
				}
				
			}
			
		}
	}
	
	/*
	 * 切片函数
	 */
	public static boolean IsBondValid(String itemHead,String itemTail,BondColl bondColl){
		
		Map<String, BondModel> keyBondColl = bondColl.getKeybondColl();
		double headItemCount = 0;
		double tailItemCount = 0;
		double keyBondCount = 0;
		double totalCount =  rememberValue;
		if(!keyBondColl.containsKey(itemHead) || !keyBondColl.containsKey(itemTail)){
			return false;
		}else{
			
			BondModel bondModel = keyBondColl.get(itemHead);
			ItemModel headItemModel = bondModel.getHeadItemModel();
			headItemCount = headItemModel.getValidCount();
			ItemColl tailItemColl = bondModel.getTailItemColl();
			Map<String, ItemModel> keyItemColl = tailItemColl.getKeyItemColl();
			ItemModel itemModel = keyItemColl.get(itemTail);
			keyBondCount = itemModel.getValidCount();
			BondModel model = keyBondColl.get(itemTail);
			ItemModel headItemModel2 = model.getHeadItemModel();
			tailItemCount = headItemModel2.getValidCount();
			
			if(headItemCount <= 0 || tailItemCount <= 0){
				return false;
			}
			
			return keyBondCount/headItemCount > tailItemCount/totalCount;
		}
	}
	
	/*
	 * 读取文件内容
	 */
	 public static List<String> readTxtFile(String filePath,BondColl bondColl){
        try {
        	List<String> list = new ArrayList<String>();
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.exists() && file.isFile()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer lineTxt1 = new StringBuffer();
                String line=null;
                while((line=bufferedReader.readLine()) != null){
                	lineTxt1.append(line.replaceAll("[\\p{P}&&[^.]]", ""));
                	if(lineTxt1.length() >= 4000000){
                		txtContext2Bond(lineTxt1.toString(),bondColl);
                		list.add(lineTxt1.toString());
                		lineTxt1.setLength(0);
                	}
                }
                if(lineTxt1.length() > 0){
                	txtContext2Bond(lineTxt1.toString(), bondColl);
                	list.add(lineTxt1.toString());
                }
                read.close();
                return list;
	        }else if(file.exists() && file.isDirectory()){
	        	File[] listFiles = file.listFiles();
				for (int i = 0; i < listFiles.length; i++) {
					if(listFiles[i].isFile()){
						InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(listFiles[i]),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    StringBuffer lineTxt1 = new StringBuffer();
	                    String line=null;
	                    while((line=bufferedReader.readLine()) != null){
	                    	lineTxt1.append(line.replaceAll("[\\p{P}&&[^.]]", ""));
	                    	if(lineTxt1.length() >= 4000000){
	                    		txtContext2Bond(lineTxt1.toString(),bondColl);
	                    		list.add(lineTxt1.toString());
	                    		lineTxt1.setLength(0);
	                    	}
	                    }
	                    if(lineTxt1.length() > 0){
	                    	txtContext2Bond(lineTxt1.toString(), bondColl);
	                    	list.add(lineTxt1.toString());
	                    }
	                    read.close();
					}
				}
				return list;
	        }else{
	            System.out.println("找不到指定的文件");
	            return null;
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	 
	 /*
	  * 写入txt 文本内
	  */
	 public static void WriteDate(Map<String, ItemModel> map,String filePath) {

			File file = new File(filePath);
			
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				ItemModel key = null;
				BufferedWriter output = new BufferedWriter(new FileWriter(file));
				for (Map.Entry<String, ItemModel> entry : map.entrySet()) {
					key = (ItemModel) entry.getValue();
					if(key.getKeyItem().length() > 1 && key.getValidCount() >= 1.0){
						key = (ItemModel) entry.getValue();
						output.write("【"+key.getKeyItem()+"】"+key.getValidCount()+"\r\n");
					}
				}
				output.close();
				System.out.println("ok.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
	 
	 /*
	  * 去掉低于阀值的词
	  */
	 public static ItemColl clearItemColl1(ItemColl itemColl){
		 
		 Map<String,ItemModel> itemModelMap = new HashMap<String,ItemModel>();
		 Map<String, ItemModel> keyItemColl = itemColl.getKeyItemColl();
		 double totalOffsetCount = itemColl.getTotalOffsetCount();
		 ItemModel value = null;
		 double valid = 0;
		 for (Map.Entry<String, ItemModel> entry : keyItemColl.entrySet()) {
				value = (ItemModel) entry.getValue();
				valid = value.getValidCount()*ForgetDAL.rememberValue(totalOffsetCount-value.getLastUpdateTime());
				value.setValidCount(valid);
				value.setLastUpdateTime(Double.valueOf(0));
				if(valid > 0.254){
					itemModelMap.put(entry.getKey(), value);
				}
		  }
		 itemColl.setKeyItemColl(itemModelMap);
		 itemColl.setTotalOffsetCount(0);
		 return itemColl;
	 }
	 
	 /*
	  * 清除低于阀值de键
	  */
	 public static BondColl clearBondColl(BondColl bondColl){
		 
		 Map<String,BondModel> bondModelMap = new HashMap<String,BondModel>();
		 Map<String, BondModel> keybondColl = bondColl.getKeybondColl();
		 double totalOffsetCount = bondColl.getTotalOffsetCount();
		 BondModel value = null;
		 double valid = 0;
		 for (Map.Entry<String, BondModel> entry : keybondColl.entrySet()) {
				value = (BondModel) entry.getValue();
				value.getHeadItemModel().setLastUpdateTime(Double.valueOf(0));
				valid = value.getHeadItemModel().getValidCount()*ForgetDAL.rememberValue(totalOffsetCount-value.getHeadItemModel().getLastUpdateTime());
				value.getHeadItemModel().setValidCount(valid);
				if(valid > 0.254){
					bondModelMap.put(entry.getKey(), entry.getValue());
				}
		  }
		 bondColl.setTotalOffsetCount(0);
		 return bondColl;
	 }
	 
	 /*
	  * 正则验证  
	  * 去除包含标点符号的结果
	  */
	 public static Map<String,ItemModel> keywordValid(Map<String,ItemModel> map){
		 
		 //正则验证  字符串中是否包含标点符号
		 Pattern pattern = Pattern.compile("\\p{P}");
		 
		 Iterator<Map.Entry<String,ItemModel>> it = map.entrySet().iterator();  
	        while(it.hasNext()){  
	            Map.Entry<String,ItemModel> entry=it.next();  
	            String key=entry.getKey();  
	            Matcher matcher = pattern.matcher(key);
	            if(key.length() <= 1 || matcher.find()){
	            	it.remove();
	            }
	        }  
	        
		 return map;
	 }
}
