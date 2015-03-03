package com.mapbar.decisionTree.data;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mapbar.decisionTree.object.DataSample;

public class GenerateArff {
	
	private static final Logger LOG = Logger.getLogger(GenerateArff.class);
	/**
	 * 为训练/测试生成对应的Arff文件
	 */
	public void genArff_TrainTest(String positive_path, String negative_path, String train_arff_path, 
			String test_positive_path, String test_negative_path, String test_arff_path){
		
		GenerateAttributeVector gav = new GenerateAttributeVector();
		//train set
		ArrayList<DataSample> positiveSamples = LoadTrainData.GenerateDataSample(positive_path, 5, 1, 2);
		ArrayList<DataSample> negativeSamples = LoadTrainData.GenerateDataSample(negative_path, 5, 1, 2);
		LOG.info("read training data. positive: " + positiveSamples.size() + "\tnegative: " + negativeSamples.size());
		gav.start(positiveSamples, negativeSamples, train_arff_path);
		LOG.info("generate ARFF file for train finished, the path of ARFF file: " + train_arff_path);
		//test set
		ArrayList<DataSample> test_positiveSamples = LoadTrainData.GenerateDataSample(test_positive_path, 5, 1, 2);
		ArrayList<DataSample> test_negativeSamples = LoadTrainData.GenerateDataSample(test_negative_path, 5, 1, 2);
		LOG.info("read test data. positive: " + test_positiveSamples.size() + "\tnegative: " + test_negativeSamples.size());
		gav.start(test_positiveSamples, test_negativeSamples, test_arff_path);
		LOG.info("generate ARFF file for test finished, the path of ARFF file: " + test_arff_path);
	}
	/**
	 * 为交叉验证生成对应的Arff文件
	 */
	public void genArff_CrossValidation(String positive_path, String negative_path, String train_arff_path){
		GenerateAttributeVector gav = new GenerateAttributeVector();
		ArrayList<DataSample> positiveSamples = LoadTrainData.GenerateDataSample(positive_path, 5, 1, 2);
		ArrayList<DataSample> negativeSamples = LoadTrainData.GenerateDataSample(negative_path, 5, 1, 2);
		LOG.info("read training data. positive: " + positiveSamples.size() + "\tnegative: " + negativeSamples.size());
		gav.start(positiveSamples, negativeSamples, train_arff_path);
		LOG.info("generate ARFF file for cross-validation finished, the path of ARFF file: " + train_arff_path);
	}
}
