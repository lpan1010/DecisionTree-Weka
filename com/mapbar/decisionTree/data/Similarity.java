package com.mapbar.decisionTree.data;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mapbar.POIParser.util.MathUtil;
import com.mapbar.decisionTree.object.AttributeVector;
import com.mapbar.decisionTree.object.AttributionName;
import com.mapbar.decisionTree.object.ParsedDataSample;
import com.mapbar.entity.POIEntityParsed;
import com.mapbar.similarity.FeatureCompute.AddressSimilarity;
import com.mapbar.similarity.FeatureCompute.BrandSimilarity;
import com.mapbar.similarity.FeatureCompute.DistanceSimilarity;
import com.mapbar.similarity.FeatureCompute.NameSimBasedWeights;
import com.mapbar.similarity.FeatureCompute.NameSimilarity;
import com.mapbar.similarity.FeatureCompute.PhoneSimilarity;
import com.mapbar.similarity.FeatureCompute.TypeSimilarity;

/**
 * 计算相似度
 * @author lpan
 *
 */
public class Similarity {
	private static final Logger LOG = Logger.getLogger(Similarity.class);
	
	public ArrayList<AttributeVector> similarity(ArrayList<ParsedDataSample> samples, double isDuplicated){
		if(samples == null || samples.size() == 0){
			LOG.info("samples is null.");
			return null;
		}
		if(isDuplicated != 0 && isDuplicated != 1){
			LOG.info("isDuplicated value is 0 or 1. ");
			return null;
		}
		ArrayList<AttributeVector> vectors = new ArrayList<AttributeVector> ();
		for(int i = 0; i < samples.size(); i++){
			AttributeVector vector = similarity(samples.get(i), isDuplicated);
			vectors.add(vector);
		}
		return vectors;
	}
	
	public AttributeVector similarity(ParsedDataSample pds, double isDuplicated){
		AttributeVector vector = new AttributeVector();
		POIEntityParsed parsedSourceEntity = pds.getParsedSourcePOI();
		POIEntityParsed parsedTargetEntity = pds.getParsedTargetPOI();
		if(parsedSourceEntity == null || parsedTargetEntity == null){
			LOG.info("parsed entity is null. ");
			return null;
		}
		/**计算名称相似度*/
		double nameLCS = NameSimilarity.getNameLCS(parsedSourceEntity, parsedTargetEntity);
		//带权重的名称编辑距离
		NameSimBasedWeights nameSim = new NameSimBasedWeights();
		double nameED = nameSim.getNameSimilarity(parsedSourceEntity, parsedTargetEntity);
//		double nameED = NameSimilarity.getNameED(parsedSourceEntity, parsedTargetEntity);
		/**计算经纬度相似度*/
		double distance = MathUtil.distance(parsedSourceEntity.coordinate, parsedTargetEntity.coordinate);
		double distanceSim = DistanceSimilarity.getDistanceSimilarity(distance);
		/**计算地址相似度*/
		double addressLCS = 0;
		double addressED = 0;
		addressLCS = AddressSimilarity.getAddressLCS(parsedSourceEntity, parsedTargetEntity);
		addressED = AddressSimilarity.getAddressED(parsedSourceEntity, parsedTargetEntity);
		/**计算品牌相似度*/
		int brandSim = 0;
		brandSim = BrandSimilarity.getBrandSimilarity(parsedSourceEntity.brand, parsedTargetEntity.brand);
		/**计算电话相似度*/
		int phoneSim = 0;
		phoneSim = PhoneSimilarity.getPhoneSimilarity(parsedSourceEntity, parsedTargetEntity);
		/**计算类型相似度*/
		TypeSimilarity ts = new TypeSimilarity();
//		int typeSim = 0;
//		typeSim = ts.getTypeSimilarity(parsedSourceEntity.type, parsedTargetEntity.type);
		/**计算类型交叉度*/
		double typeCrossDegree = 0;
		typeCrossDegree = ts.getCrossDegree(parsedSourceEntity.type, parsedTargetEntity.type);
		/**获取类型索引编号，编号从1开始计数，类型相同为类型对应的编号，最后两个编号分别表示类型不一样，类型为空**/
		int typeIndex = -1;
		typeIndex = ts.getTypeIndex(parsedSourceEntity.type, parsedTargetEntity.type);
		if(typeIndex == 0){
			LOG.info("type index = 0 " + parsedSourceEntity.toString() + parsedTargetEntity.toString());
		}
//		LOG.info("nameLCS:" + nameLCS + "\tnameED:" + nameED + "\taddressLCS:" + addressLCS + "\taddressED:" + addressED
//		+"\tbrand:" + brandSim + "\tphone:" + phoneSim + "\ttypeSim:" + typeSim  + "\ttypeIndex:" + typeIndex + "\tdistance:" + distanceSim);
//		System.out.println(nameLCS + "," + nameED + "," + addressLCS + "," + addressED
//		+"," + brandSim + "," + phoneSim + "," + typeSim + "," + distanceSim + "," + typeIndex + "," +  isDuplicated);
		
		vector.put(AttributionName.nameED, nameED);
		vector.put(AttributionName.nameLCS, nameLCS);
		vector.put(AttributionName.addressED, addressED);
		vector.put(AttributionName.addressLCS, addressLCS);
		vector.put(AttributionName.brand, brandSim);
		vector.put(AttributionName.distance, distanceSim);
//		vector.put(AttributionName.typeSim, typeSim);
		vector.put(AttributionName.typeCrossDegree, typeCrossDegree);
		vector.put(AttributionName.typeIndex, typeIndex);
		vector.put(AttributionName.phone, phoneSim);
		vector.put(AttributionName.isDuplicated, isDuplicated);
		vector.dataSample = pds;
		return vector;
	}
}
