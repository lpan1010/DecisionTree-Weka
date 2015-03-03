package com.mapbar.decisionTree.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author lpan1010
 *
 */
public class WriteFile {
	
	public static void writeFile(ArrayList<String> content, String filename){
		
		File outputFile = new File(filename);
		try {
			 OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream
					 (outputFile, true), "UTF-8"); 
			 String str;
			 for(int i = 0; i < content.size();i++){
				str=content.get(i);
				//如果字符串没有内容，则不写入文件
				if(str.length() < 1){
					continue;
				}
				else{
					osw.write(str+"\r\n");
					osw.flush();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
//	public static void writeFile(ArrayList<Integer> content, String filename){
//		
//	}

	public static Map.Entry[] getSortedHashtableByValueDown(Hashtable<String, Integer> wordcount) {
		Set set = wordcount.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);

		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				double key1 = Double.parseDouble(((Map.Entry) arg0).getValue()
						.toString());
				double key2 = Double.parseDouble(((Map.Entry) arg1).getValue()
						.toString());
				return ((Comparable) new Double (key2)).compareTo (new Double(key1));
			}
		});

		return entries;

	}

	public static void writeFile(int m, String filename) {
		// TODO Auto-generated method stub
		File outputFile = new File(filename);
		try {
			 OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream
					 (outputFile), "GBK"); 
				osw.write(String.valueOf(m));
				osw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void writeFile(String content, String filename){
		
		File outputFile = new File(filename);
		try {
			 OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream
					 (outputFile, true), "UTF-8"); 
			 if(content != null){
				 osw.write(content);
				 osw.flush();
				 osw.close();
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
