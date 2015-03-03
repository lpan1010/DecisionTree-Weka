package com.mapbar.decisionTree.test;

import java.util.ResourceBundle;

import com.mapbar.decisionTree.data.ParsePOI;
import com.mapbar.decisionTree.data.Similarity;
import com.mapbar.decisionTree.object.AttributeVector;
import com.mapbar.decisionTree.object.AttributionName;
import com.mapbar.decisionTree.object.DataSample;
import com.mapbar.decisionTree.object.ParsedDataSample;
import com.mapbar.entity.POIEntity;
import com.mapbar.util.common.CSVParserUtil;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;

public class DecisionTreeTest {
	
	
	public static void main(String[] args){
		ResourceBundle rb = ResourceBundle.getBundle("DecisionTree");
		String model_path = rb.getString("model_path");
		// deserialize model
		try {
			J48 classifier = (J48) SerializationHelper.read(model_path);
			classifier.toString();
			String source = "727255879,北京市,佛,雍和宫大街42-1,,0504,116.41065939,39.94306692";
			String target = "900035324,北京市,佛北京友朋座,雍和宫大街４２－１,,0504,116.410664392,39.943065919";
			String[] items1 = CSVParserUtil.split(source);
			String[] items2 = CSVParserUtil.split(target);
			POIEntity sourceEntity = new POIEntity(items1);
			POIEntity targetEntity = new POIEntity(items2);
			DataSample sample = new DataSample();
			sample.setSource(sourceEntity);
			sample.setTarget(targetEntity);
			ParsePOI parser = new ParsePOI();
			ParsedDataSample parsedDataSample = parser.parse(sample);
			Similarity s = new Similarity();
			AttributeVector vector = s.similarity(parsedDataSample, 0);
			
			Attribute nameED = new Attribute("nameED");
			Attribute nameLCS = new Attribute("nameLCS");
			Attribute addressED = new Attribute("addressED");
			Attribute addressLCS = new Attribute("addressLCS");
			FastVector brandLabels = new FastVector();
			brandLabels.addElement("0.0");
			brandLabels.addElement("1.0");
			brandLabels.addElement("2.0");
			Attribute brand = new Attribute("brand", brandLabels);
			
			Attribute distance = new Attribute("distance");
			
			FastVector typeLabels = new FastVector();
			typeLabels.addElement("0.0");	
			typeLabels.addElement("1.0");	
			typeLabels.addElement("2.0");	
			Attribute type = new Attribute("type", typeLabels);
			
			FastVector phoneLabels = new FastVector();
			phoneLabels.addElement("0.0");	
			phoneLabels.addElement("1.0");	
			phoneLabels.addElement("2.0");	
			Attribute phone = new Attribute("phone", phoneLabels);
			
			FastVector isDuplicatedLabels = new FastVector();
			isDuplicatedLabels.addElement("0.0");	
			isDuplicatedLabels.addElement("1.0");	
			Attribute isDuplicated = new Attribute("isDuplicated", isDuplicatedLabels);
			
			FastVector attributes = new FastVector();
			attributes.addElement(nameED);
			attributes.addElement(nameLCS);
			attributes.addElement(addressED);
			attributes.addElement(addressLCS);
			attributes.addElement(brand);
			attributes.addElement(distance);
			attributes.addElement(type);
			attributes.addElement(phone);
			attributes.addElement(isDuplicated);
			
			Instances dataset = new Instances("Test-dataset", attributes, 0);
			
			double[] values = new double[10];
			values[0] = Double.parseDouble(vector.get("nameED").toString());
			values[1] = Double.parseDouble(vector.get("nameLCS").toString());
			values[2] = Double.parseDouble(vector.get("addressED").toString());
			values[3] = Double.parseDouble(vector.get("addressLCS").toString());
			values[4] = Double.parseDouble(vector.get("brand").toString());
			values[5] = Double.parseDouble(vector.get("distance").toString());
			values[6] = Double.parseDouble(vector.get("type").toString());
			values[7] = Double.parseDouble(vector.get("typeIndex").toString());
			values[8] = Double.parseDouble(vector.get("phone").toString());
			values[9] = Double.parseDouble(vector.get("isDuplicated").toString());
			for(int i = 0; i < values.length; i++){
				System.out.println(values[i]);
			}
			System.out.println();
			Instance instance = new Instance(1.0, values);
			dataset.add(instance);
			
			if (dataset.classIndex() == -1){
				dataset.setClassIndex(dataset.numAttributes() - 1);
			}
			for(int i = 0; i < dataset.numInstances(); i++){
				double clsLabel = classifier.classifyInstance(dataset.instance(i));
				double[] dist = classifier.distributionForInstance(dataset.instance(i));
				System.out.println((i+1) + " - ");
				System.out.println(dataset.instance(i).toString(dataset.classIndex()) + " - ");
				System.out.println(dataset.classAttribute().value((int) clsLabel) + " - ");
				System.out.println(Utils.arrayToString(dist));
				System.out.println("label = " + clsLabel);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
