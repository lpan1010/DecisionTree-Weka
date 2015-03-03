package com.mapbar.decisionTree.data;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mapbar.decisionTree.object.DataSample;
import com.mapbar.decisionTree.util.ReadFile;
import com.mapbar.entity.POIEntity;
import com.mapbar.util.common.CSVParserUtil;

/**
 * 加载训练数据
 * @author lpan
 *
 */
public class LoadTrainData {
	private static final Logger LOG = Logger.getLogger(LoadTrainData.class);
	/**
	 * 读取样本数据
	 * @param filename 读入文件
	 * @param len 一个样本占用多少行
	 * @param sourceIndex 输入source POI 在第几行
	 * @param targetIndex 输入target POI 在第几行
	 * @return
	 */
	public static ArrayList<DataSample> GenerateDataSample(String filename, int len, int sourceIndex, int targetIndex){
		ArrayList<DataSample> samples = new ArrayList<DataSample>();
		ArrayList<String> lines = ReadFile.readFile(filename, "UTF-8");
		String[] sixlines = new String[len];
		for(int i = 1; i <= lines.size(); i++){
			if(i % len == 0){
				String source = sixlines[sourceIndex];
				String target = sixlines[targetIndex];
				LOG.info("source:"+source);
				LOG.info("target:"+target);
				try {
					String[] items1 = CSVParserUtil.split(source);
					String[] items2 = CSVParserUtil.split(target);
					if (items1.length != 8 || items2.length != 8) {
						continue;
					}
					DataSample sample = new DataSample();
					POIEntity sourceEntity = new POIEntity(items1);
					POIEntity targetEntity = new POIEntity(items2);
					//如果是公交车站类的数据，直接清掉
					if(sourceEntity.type.startsWith("17") || targetEntity.type.startsWith("17")){
						continue;
					}
					sample.setSource(sourceEntity);
					sample.setTarget(targetEntity);
					samples.add(sample);
					
				} catch (Exception e) {
					LOG.error("read file error");
				}
			}
			else{
				sixlines[i%len-1] = lines.get(i-1);
			}
		}
		return samples;
	}
	//测试
	public static void main(String[] args){
		String filename1 = "/home/lpan/workspace/resource/panchong/testData/地产/answer.txt";
		GenerateDataSample(filename1, 6, 2, 4);
		String filename2 = "/home/lpan/workspace/resource/panchong/testData/地产/poi_35-80.txt";
		GenerateDataSample(filename2, 5, 1, 2);
	}

}
