package com.mapbar.decisionTree.evaluation;


import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class DedicatedTestSet {
	
	public void start(Instances train, Instances test, Classifier classifier){
		
		try {
			Evaluation eval = new Evaluation(train);
			eval.evaluateModel(classifier, test);
			System.out.println("------------------------------Summary  String---------------------------------");
			System.out.println(eval.toSummaryString("\nResults\n\n", false));
			System.out.println("------------------------------Matrix  String----------------------------------");
			System.out.println(eval.toMatrixString());
			System.out.println("------------------------------class detail String-----------------------------");
			System.out.println(eval.toClassDetailsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
