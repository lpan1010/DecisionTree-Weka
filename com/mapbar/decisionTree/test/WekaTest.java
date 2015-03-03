package com.mapbar.decisionTree.test;

import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class WekaTest {
	
	public static void main(String[] args){
		try {
			J48 classifier = new J48(); 
			String defaultOptions[] = classifier.getOptions();
			String[] options = Utils.splitOptions("-R 1 -B -S");
			System.out.println("j48 default options: ");
			for(int i = 0; i < defaultOptions.length; i++){
				System.out.println(defaultOptions[i]);
			}
			System.out.println("set options: ");
			for(int i = 0; i < options.length; i++){
				System.out.println(options[i]);
			}
			//加载数据
			Instances train = DataSource.read("/home/lpan/workspace/resource/panchong/testData/地产/duplicate_train.arff");
			//设置最后一列为分类属性
			if (train.classIndex() == -1){
				train.setClassIndex(train.numAttributes() - 1);
			}
			//随机化
			train.randomize(new Random());
			//构造决策树
			classifier.buildClassifier(train);
			//加载测试数据
			Instances test = DataSource.read("/home/lpan/workspace/resource/panchong/testData/地产/duplicate_test.arff");
			if (test.classIndex() == -1){
				test.setClassIndex(test.numAttributes() - 1);
			}
			//评价
			
			//训练集+验证集
			Evaluation eval = new Evaluation(train);
			eval.evaluateModel(classifier, test);
			System.out.println("correct:"+eval.correct() + "\tincorrect:" + eval.incorrect());
			System.out.println(eval.toSummaryString("\nResults\n\n", false));
			System.out.println(eval.toMatrixString());
			System.out.println(eval.toClassDetailsString());
			System.out.println("----------------------------------------------------------");
			//交叉验证
			Evaluation eval2 = new Evaluation(train);
			eval2.crossValidateModel(classifier, train, 10, new Random(1));
			System.out.println(eval2.toSummaryString("\nResults\n\n", false));
			System.out.println("----------------------------------------------------------");
			System.out.println(eval2.toMatrixString());
			System.out.println("----------------------------------------------------------");
			System.out.println(eval2.toClassDetailsString());
			System.out.println("----------------------------------------------------------");
//			System.out.println(eval2.toCumulativeMarginDistributionString());
//			System.out.println("----------------------------------------------------------");
			//可视化
			TreeVisualizer tv = new TreeVisualizer(
					null, classifier.graph(), new PlaceNode2());
			JFrame jf = new JFrame("Weka Classifier Tree Visualizer: J48");
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setSize(2000, 1000);
			jf.getContentPane().setLayout(new BorderLayout());
			jf.getContentPane().add(tv, BorderLayout.CENTER);
			jf.setVisible(true);
			jf.setResizable(true);
			tv.fitToScreen();
			//序列化
			SerializationHelper.write("/home/lpan/workspace/resource/panchong/testData/地产/j48.model", classifier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
