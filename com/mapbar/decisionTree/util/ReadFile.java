package com.mapbar.decisionTree.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.mapbar.entity.POIEntity;
import com.mapbar.util.common.CSVParserUtil;
import com.mapbar.util.common.StringUtil;
import com.mapbar.util.io.FileIterator;
/***
 * 
 * @author liupa
 *
 */
public class ReadFile {
	private static final Logger LOG = Logger.getLogger(ReadFile.class);
	/***
	 * 读取一个文件中的所有行
	 * @param filename
	 * @param charset
	 * @return
	 */
	public static ArrayList<String> readFile(String filename, String charset){
		ArrayList<String> fields = new ArrayList<String>();
		File file = new File(filename);
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(file);
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(fstream,charset));
			String line;
			while((line = bufferedreader.readLine()) != null){
				fields.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fields;
	}
	
	/***
	 * 读取一堆文件，保存为POIEntity
	 * @param files
	 * @return
	 */
	public ArrayList<POIEntity> readPOIFile(ArrayList<File> files){
		ArrayList<POIEntity> entities = new ArrayList<POIEntity>();
		for (File file : files) {
			FileIterator it;
			try {
				it = new FileIterator(file, "UTF-8");
				while (it.hasNext()) {
					String line = it.next();
					if (StringUtil.isEmpty(line)) {
						continue;
					}
					try {
						String[] items = CSVParserUtil.split(line);
						if (items.length != 8) {
							continue;
						}
						entities.add(new POIEntity(items));
					} catch (Exception e) {
						LOG.error(line);
					}
				}
				it.close();
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return entities;
	}

}
