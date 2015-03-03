package com.mapbar.decisionTree.data;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mapbar.decisionTree.object.AttributeVector;
import com.mapbar.decisionTree.object.AttributionName;
import com.mapbar.decisionTree.util.WriteFile;

public class SaveArff {
	/**
	 * 将属性向量保存为weka能处理的.arff文件
	 * 
	 * @author lpan
	 * 
	 */
	private static final Logger LOG = Logger.getLogger(SaveArff.class);

	public static void save(ArrayList<AttributeVector> vectors, String filename) {
		if (vectors == null || vectors.size() == 0) {
			LOG.info("attribute vector is null. ");
			return;
		}
		String head = getHead();
		WriteFile.writeFile(head, filename);
		ArrayList<String> lines = new ArrayList<String>();
		for (int i = 0; i < vectors.size(); i++) {
			lines.add(vectors.get(i).toString());
		}
		WriteFile.writeFile(lines, filename);
	}

	public static String getHead() {
		StringBuffer head = new StringBuffer();
		head.append("@relation duplicate\n\n");
		AttributionName an = new AttributionName();
		head.append("@attribute " + AttributionName.nameED + " "
				+ an.ValueType.get(AttributionName.nameED) + "\n");
		head.append("@attribute " + AttributionName.nameLCS + " "
				+ an.ValueType.get(AttributionName.nameLCS) + "\n");
		head.append("@attribute " + AttributionName.addressED + " "
				+ an.ValueType.get(AttributionName.addressED) + "\n");
		head.append("@attribute " + AttributionName.addressLCS + " "
				+ an.ValueType.get(AttributionName.addressLCS) + "\n");
		head.append("@attribute " + AttributionName.brand + " "
				+ an.ValueType.get(AttributionName.brand) + "\n");
		head.append("@attribute " + AttributionName.distance + " "
				+ an.ValueType.get(AttributionName.distance) + "\n");
		// head.append("@attribute " + AttributionName.typeSim + " " +
		// an.ValueType.get(AttributionName.typeSim) + "\n");
		head.append("@attribute " + AttributionName.typeCrossDegree + " "
				+ an.ValueType.get(AttributionName.typeCrossDegree) + "\n");
		head.append("@attribute " + AttributionName.typeIndex + " "
				+ an.ValueType.get(AttributionName.typeIndex) + "\n");
		head.append("@attribute " + AttributionName.phone + " "
				+ an.ValueType.get(AttributionName.phone) + "\n");
		head.append("@attribute " + AttributionName.isDuplicated + " "
				+ an.ValueType.get(AttributionName.isDuplicated) + "\n");
		head.append("\n\n@data\n");
		return head.toString();
	}
	public static void main(String[] args) {
		String head = SaveArff.getHead();
		System.out.println(head);
	}
}
