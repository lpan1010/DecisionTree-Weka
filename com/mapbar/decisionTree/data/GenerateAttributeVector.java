package com.mapbar.decisionTree.data;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mapbar.decisionTree.object.AttributeVector;
import com.mapbar.decisionTree.object.DataSample;
import com.mapbar.decisionTree.object.ParsedDataSample;
import com.mapbar.decisionTree.util.DeleteFile;
import com.mapbar.similarity.Loader.SimilarityResourceLoader;

/***
 * 生成属性向量
 * @输入：训练数据，训练数据包括一对一对的8个字段(x1,x2...x8)的POI，还要明确这一对POI是重的，还是不重的，即一个目标y值
 * @处理：一、需要读入训练数据，形成sourcePOI以及targetPOI
 * @处理：二、解析训练数据，形成sourcePOIParsed以及targetPOIparsed
 * @处理：三、计算两条解析后的POI的相似度，形成一个属性向量，如：nameSim, addressSim, phoneSim,...,这些特征可以后续再加
 * @处理：四、格式化结果，保存成weka能处理的格式
 * @author lpan
 */
public class GenerateAttributeVector {
	private static final Logger LOG = Logger.getLogger(GenerateAttributeVector.class);
	
	public void start(ArrayList<DataSample> positiveSamples, ArrayList<DataSample> negativeSamples, String arff_path){
		//1. 解析
		LOG.info("parse training data. ");
		ParsePOI parser = new ParsePOI();
		ArrayList<ParsedDataSample> positive = parser.parse(positiveSamples);
		ArrayList<ParsedDataSample> negative = parser.parse(negativeSamples);
		//2.计算POI的相似度，输出向量
		Similarity s = new Similarity();
		//加载大类数据,用于计算类型相似度
		LOG.info("similarity compute. ");
		SimilarityResourceLoader rl = new SimilarityResourceLoader();
		rl.load();
		ArrayList<AttributeVector> negativeVector = s.similarity(negative, 0);
		ArrayList<AttributeVector> positiveVector = s.similarity(positive, 1);
		//3. 两个数组合并成一个，并保存成weka能处理的.arff格式的文件
		if(positiveVector == null || negativeVector == null){
			LOG.info("vectors is null.");
			return ;
		}
		ArrayList<AttributeVector> vectors = positiveVector;
		for(int i = 0, size = negativeVector.size(); i < size; i++){
			vectors.add(negativeVector.get(i));
		}
		//4. 保存为arff格式的文件，如果这个文件存在了，删除这个已经存在的文件
		DeleteFile.delete(arff_path);
		SaveArff.save(vectors, arff_path);
		LOG.info("save arff file, " + arff_path);
	}
}
