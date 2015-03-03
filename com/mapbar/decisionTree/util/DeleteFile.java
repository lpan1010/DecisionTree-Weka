package com.mapbar.decisionTree.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/***
 * 
 * @author liupa
 * 
 */
public class DeleteFile {
	public static final Log LOG = LogFactory.getLog(DeleteFile.class);
	public static int delete(String filename){
		File file = new File(filename);
		if(!file.exists()){
			LOG.debug("�ļ�������\t" + filename);
			return -1;
		}
		else{
			if(!file.delete()){
				LOG.debug("�ļ�ɾ��ʧ��\t" + filename);
				return 0;
			}
			else{
				return 1;
			}
		}
	}
	public static void main(String[] args){
		DeleteFile.delete("./res/fail_list.txt");
	}
}
