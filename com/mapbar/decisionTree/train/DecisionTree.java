package com.mapbar.decisionTree.train;

import java.util.Random;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;

public class DecisionTree {
	
	public String[] Options(){
		String option = "-R 1 -S";
		try {
			return Utils.splitOptions(option);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public J48 genDecisionTree(Instances train){
		
		J48 classifier = new J48(); 
		try {
			//配置选项
			classifier.setOptions(Options());
			//随机化
			train.randomize(new Random());
			//生成决策树
			classifier.buildClassifier(train);
			//打印决策树
			System.out.println("------------------------------Decision  Tree----------------------------------");
			System.out.println(classifier.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classifier;
	}
}
