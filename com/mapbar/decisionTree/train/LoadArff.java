package com.mapbar.decisionTree.train;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import weka.core.Instances;

/**
 * 加载arff文件
 * @author lpan
 *
 */
public class LoadArff {
	private static final Logger LOG = Logger.getLogger(LoadArff.class);
	/**
	 * 
	 * @param path 文件路径
	 * @return 实例集合
	 */
	public Instances load(String path){
		Instances data = null;
		try {
			FileReader frData = new FileReader(path);
			data = new Instances(frData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 return data;  
	}
	
	
	public static void main(String[] args){
		LoadArff la = new LoadArff();
		ResourceBundle rb = ResourceBundle.getBundle("DecisionTree");
		String path = rb.getString("arff_path");
		Instances instances = la.load(path);
		for (int i = 0; i < instances.numInstances(); i++) {  
            // instance( i )是得到第i个样本  
            System.out.println(instances.instance(i));  
        } 
		LOG.info("instances: " + instances.numInstances());
	}
}
