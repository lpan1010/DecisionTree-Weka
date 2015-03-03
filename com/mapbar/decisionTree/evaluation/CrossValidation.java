package com.mapbar.decisionTree.evaluation;

import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * 
 * @author lpan
 *
 *	cross-validation
 *	split the instances to several parts, one part as the test set, the others as the train set
 *  Before cross-validation is performed, the data gets randomized using the
 *  supplied random number generator (java.util.Random). It is recommended
 *  that this number generator is “seeded” with a specified seed value.
 */
public class CrossValidation {
	
	/**
	 * 
	 * @param train 
	 * @param classifier 
	 * @param part 
	 * @param random 
	 */
	public void start(Instances train, Classifier classifier, int part, int random){
		Evaluation eval;
		try {
			eval = new Evaluation(train);
			eval.crossValidateModel(classifier, train, part, new Random(random));
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
