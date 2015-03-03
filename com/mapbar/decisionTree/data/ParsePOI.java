package com.mapbar.decisionTree.data;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.mapbar.POIParser.POIParser;
import com.mapbar.POIParser.loader.ParserResourceLoader;
import com.mapbar.decisionTree.object.DataSample;
import com.mapbar.decisionTree.object.ParsedDataSample;
import com.mapbar.deduplication.conf.Configuration;
import com.mapbar.entity.POIEntityParsed;

public class ParsePOI {
	private static final Logger LOG = Logger.getLogger(ParsePOI.class);
	
	private Configuration conf = Configuration.getInstance();
	private POIParser analyser = new POIParser();
	private ParserResourceLoader resource = ParserResourceLoader.getInstance();
	
	public ParsePOI(){
		init();
	}
	//初始化，配置解析所需文件的路径，以及资源的加载
	public void init(){
		ResourceBundle rb = ResourceBundle.getBundle("DecisionTree");
		String parse_conf_path = rb.getString("parse_conf_path");
		conf.setCfgPath(parse_conf_path);
		LOG.info(parse_conf_path);
		resource.load();
	}
	/**
	 * 解析训练样本
	 * @param samples
	 * @return
	 */
	public ArrayList<ParsedDataSample> parse(ArrayList<DataSample> samples){
		if(samples == null || samples.size() == 0){
			LOG.info("samples is null.");
			return null;
		}
		ArrayList<ParsedDataSample> parsedDataSamples = new ArrayList<ParsedDataSample>();
		for(int i = 0, size = samples.size(); i < size; i++){
			DataSample sample = samples.get(i);
			ParsedDataSample pds = parse(sample);
			parsedDataSamples.add(pds);
		}
//		LOG.info("parse data finished. ");
		return parsedDataSamples;
	}
	
	public ParsedDataSample parse(DataSample sample){
		POIEntityParsed parsedSourceEntity = analyser.analyse(sample.getSource());
		POIEntityParsed parsedTargetEntity = analyser.analyse(sample.getTarget());
		ParsedDataSample pds = new ParsedDataSample();
		pds.setParsedSourcePOI(parsedSourceEntity);
		pds.setParsedTargetPOI(parsedTargetEntity);
		return pds;
	}
	
	public static void main(String[] args){
		ParsePOI pp = new ParsePOI();
		pp.init();
	}
}
