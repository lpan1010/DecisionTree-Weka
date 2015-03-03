package com.mapbar.decisionTree.visualization;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import weka.classifiers.trees.J48;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class Visualizer {
	
	public void start(J48 classifier){
		//可视化
		TreeVisualizer tv;
		try {
			tv = new TreeVisualizer(null, classifier.graph(), new PlaceNode2());
			JFrame jf = new JFrame("Weka Classifier Tree Visualizer: J48");
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setSize(2000, 1000);
			jf.getContentPane().setLayout(new BorderLayout());
			jf.getContentPane().add(tv, BorderLayout.CENTER);
			jf.setVisible(true);
			tv.fitToScreen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
