package com.mapbar.decisionTree.object;

import com.mapbar.entity.POIEntity;
/**
 * 数据样本，保存训练数据样本
 * @author lpan
 *
 */
public class DataSample {
	private POIEntity source;
	private POIEntity target;
	public POIEntity getSource() {
		return source;
	}
	public void setSource(POIEntity source) {
		this.source = source;
	}
	public POIEntity getTarget() {
		return target;
	}
	public void setTarget(POIEntity target) {
		this.target = target;
	}
}
