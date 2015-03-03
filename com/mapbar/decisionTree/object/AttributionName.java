package com.mapbar.decisionTree.object;

import java.util.HashMap;
import java.util.Map;

public class AttributionName {
	public static final String nameED = "nameED";
	public static final String nameLCS = "nameLCS";
	public static final String addressED = "addressED";
	public static final String addressLCS = "addressLCS";
	public static final String brand = "brand";
	public static final String distance = "distance";
//	public static final String typeSim = "typeSim";
	public static final String typeCrossDegree = "typeCrossDegree";
	public static final String typeIndex = "typeIndex";
	public static final String phone = "phone";
	public static final String isDuplicated = "isDuplicated";
	
	public Map<String, String> ValueType = new HashMap<String, String>();
	
	public AttributionName(){
		init();
	}
	
	public void init(){
		ValueType.put(nameED, "numeric");
		ValueType.put(nameLCS, "numeric");
		ValueType.put(addressED, "numeric");
		ValueType.put(addressLCS, "numeric");
		ValueType.put(brand, "{0, 1, 2}");
		ValueType.put(distance, "numeric");
//		ValueType.put(typeSim, "{0, 1, 2}");
		ValueType.put(typeCrossDegree, "numeric");
		ValueType.put(typeIndex, "{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}");
		ValueType.put(phone, "{0, 1, 2}");
		ValueType.put(isDuplicated, "{0.0, 1.0}");
	}
}
