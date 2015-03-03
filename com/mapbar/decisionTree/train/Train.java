package com.mapbar.decisionTree.train;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.mapbar.decisionTree.data.GenerateArff;
import com.mapbar.decisionTree.evaluation.CrossValidation;
import com.mapbar.decisionTree.evaluation.DedicatedTestSet;
import com.mapbar.similarity.util.WriteFile;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class Train {
	
	private static final Logger LOG = Logger.getLogger(Train.class);
	
	public static String train_arff_path = "";
	public static String train_positive_path = "";
	public static String train_negative_path = "";
	public static String test_arff_path = "";
	public static String test_positive_path = "";
	public static String test_negative_path = "";
	public static String model_path = "";
	public static String evalutation_method = "";
	public static String ARFF_exist = "";
	public static String evaluation_method = "";
	public static String parse_conf_path = "";
	public static String tree_map_path = "";
	
	public void init(){
		ResourceBundle rb = ResourceBundle.getBundle("DecisionTree");
		train_arff_path = rb.getString("train_arff_path");
		ARFF_exist = rb.getString("ARFF_exist");
		evaluation_method = rb.getString("evaluation_method");
		model_path = rb.getString("model_path");
		tree_map_path = rb.getString("tree_map_path");
		if(ARFF_exist.equalsIgnoreCase("true")){
			if(evaluation_method.equalsIgnoreCase("DEDICATED-TEST-SET")){
				test_arff_path = rb.getString("test_arff_path");
			}
		}
		else if(ARFF_exist.equalsIgnoreCase("false")){
			parse_conf_path = rb.getString("parse_conf_path");
			train_positive_path = rb.getString("train_positive_path");
			train_negative_path = rb.getString("train_negative_path");
			if(evaluation_method.equalsIgnoreCase("DEDICATED-TEST-SET")){
				test_positive_path = rb.getString("test_positive_path");
				test_negative_path = rb.getString("test_negative_path");
			}
		}
	}
	
	public Train(){
		init();
	}
	
	public void trainHandler(){
		DecisionTree dt = new DecisionTree();
		if(evaluation_method.equalsIgnoreCase("DEDICATED-TEST-SET")){
			if(ARFF_exist.equalsIgnoreCase("false")){
				GenerateArff ga = new GenerateArff();
				ga.genArff_TrainTest(train_positive_path, train_negative_path, train_arff_path, test_positive_path, test_negative_path, test_arff_path);
			}
			try {
				//加载训练数据（arff文件）
				Instances train = DataSource.read(train_arff_path);
				//设置最后一列为分类属性
				if (train.classIndex() == -1){
					train.setClassIndex(train.numAttributes() - 1);
				}
				//加载测试数据(arff文件)
				Instances test = DataSource.read(test_arff_path);
				//设置最后一列为分类属性
				if (train.classIndex() == -1){
					train.setClassIndex(train.numAttributes() - 1);
				}
				//decision tree
				J48 classifier = new J48();
				classifier.setSaveInstanceData(true);
				classifier = dt.genDecisionTree(train);
				
				
				
				//evaluation
				DedicatedTestSet dts = new DedicatedTestSet();
				dts.start(train, test, classifier);
				//serialization
				SerializationHelper.write(model_path, classifier);
				/**save the tree map*/
				String treeMap = classifier.toString();
//				String prefixtree = classifier.prefix();
				WriteFile.writeFile(addInstancesRateOnleaf(treeMap), tree_map_path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(evaluation_method.equalsIgnoreCase("CROSS-VALIDATION")){
			if(ARFF_exist.equalsIgnoreCase("false")){
				GenerateArff ga = new GenerateArff();
				ga.genArff_CrossValidation(train_positive_path, train_negative_path, train_arff_path);
			}
			Instances train;
			try {
				//加载训练数据（arff文件）
				train = DataSource.read(train_arff_path);
				//设置最后一列为分类属性
				if (train.classIndex() == -1){
					train.setClassIndex(train.numAttributes() - 1);
				}
				J48 classifier = new J48();
				classifier.setSaveInstanceData(true);
				classifier = dt.genDecisionTree(train);
				//evaluation
				CrossValidation cv = new CrossValidation();
				cv.start(train, classifier, 10, 1);
				//serialization
				SerializationHelper.write(model_path, classifier);
				/**save the tree map*/
				String treeMap = classifier.toString();
//				String prefixtree = classifier.prefix();
				WriteFile.writeFile(addInstancesRateOnleaf(treeMap), tree_map_path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			LOG.info("the evaluation_method is invalid.");
			return;
		}
	}
	
	public static String addInstancesRateOnleaf(String treeMap){
		StringBuffer result = new StringBuffer();
		String[] lines = treeMap.split("\n");
		for(String line:lines){
			int beginIndex = line.indexOf("(");
			int endIndex = line.indexOf(")");
			if(beginIndex == endIndex){
				result.append(line + "\n");
			}
			else{
				String target = line.substring(beginIndex + 1, endIndex);
				if(target.contains("/")){
					String[] scores = target.split("/");
					if(scores.length == 2){
						double rate = Double.parseDouble(scores[1])/Double.parseDouble(scores[0]);
						result.append(line + "\t" + rate + "\t" + (1-rate) + "\n");
					}
					else{
						result.append(line + "\n");
					}
				}
				else{
					result.append(line + "\n");
				}
			}
		}
		return result.toString();
	}
	
	public static void main(String[] args){
		Train train = new Train();
		train.trainHandler();
	}

}
