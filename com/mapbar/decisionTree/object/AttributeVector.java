package com.mapbar.decisionTree.object;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;




public class AttributeVector {
	
	private static final Logger LOG = Logger.getLogger(AttributeVector.class);
	
	public Map<Object, Object> attributionValueMap = new HashMap<Object, Object>();
	
	public ParsedDataSample dataSample = new ParsedDataSample();
	
	public AttributeVector(){
		init();
	}
	
	public void init(){
		/**加入属性名称*/
		attributionValueMap.put(AttributionName.nameED, 1.0);
		attributionValueMap.put(AttributionName.nameLCS, 1.0);
		attributionValueMap.put(AttributionName.addressED, 1.0);
		attributionValueMap.put(AttributionName.addressLCS, 1.0);
		attributionValueMap.put(AttributionName.distance, 1.0);
		attributionValueMap.put(AttributionName.brand, 0.0);
//		attributionValueMap.put(AttributionName.typeSim, 0.0);
		attributionValueMap.put(AttributionName.typeCrossDegree, 0);
		attributionValueMap.put(AttributionName.typeIndex, -1.0);
		attributionValueMap.put(AttributionName.phone, 0.0);
		attributionValueMap.put(AttributionName.isDuplicated, 0.0);
	}
	
	public void put(String attributionName, Object attributionValue){
		if(attributionValueMap.containsKey(attributionName)){
			attributionValueMap.put(attributionName, attributionValue);
		}
		else{
			LOG.info("no attribution name " + attributionName + " in attributionValueMap, put fail.");
		}
	}
	
	public Object get(String attributionName){
		if(attributionValueMap.containsKey(attributionName)){
			return attributionValueMap.get(attributionName);
		}
		else{
			LOG.info("no attribution name " + attributionName + " in attributionValueMap, get fail.");
			return 0;
		}
	}
	
	public String toString(){
		String vector = "";
		vector = vector + attributionValueMap.get(AttributionName.nameED) + ",";
		vector = vector + attributionValueMap.get(AttributionName.nameLCS) + ",";
		vector = vector + attributionValueMap.get(AttributionName.addressED) + ",";
		vector = vector + attributionValueMap.get(AttributionName.addressLCS) + ",";
		vector = vector + attributionValueMap.get(AttributionName.brand) + ",";
		vector = vector + attributionValueMap.get(AttributionName.distance) + ",";
//		vector = vector + attributionValueMap.get(AttributionName.typeSim) + ",";
		vector = vector + attributionValueMap.get(AttributionName.typeCrossDegree) + ",";
		vector = vector + attributionValueMap.get(AttributionName.typeIndex) + ",";
		vector = vector + attributionValueMap.get(AttributionName.phone) + ",";
		vector = vector + attributionValueMap.get(AttributionName.isDuplicated);
		return vector;
	}
	
	public void printData(){
		LOG.info(dataSample.getParsedSourcePOI().toString());
		LOG.info(dataSample.getParsedTargetPOI().toString());
	}
	
	public void printScore(){
		for(Object attribution:attributionValueMap.keySet()){
			LOG.info(attribution + ":" + attributionValueMap.get(attribution));
		}
	}
}
