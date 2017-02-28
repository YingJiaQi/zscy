package com.syard.service.api;

import java.util.Map;

public interface PreWebContentManagerService {
	/**
	 * 前台页面获取关于我们数据
	 * @param param
	 * @return
	 */
	Map<String, Object> getAboutUsCommponyProfile(Map<String, Object> param);
	/**
	 * 前台新闻中心页面获取数据
	 * @return
	 */
	Map<String, Object> getNewsCentorData();
	/**
	 * 前台磁性制品页面请求磁铁分类数据
	 * @return
	 */
	Map<String, Object> getMagnetClassficationData();
	/**
	 * 前台磁性制品页面请求 ---根据分类名(用途)，查找该分类下的所有数据
	 * @param param
	 * @return
	 */
	Map<String, Object> getMagnetClassficationDataByTitle(Map<String, Object> param);
	/**
	 * 前台主页，头条推荐获取数据
	 * @return
	 */
	Map<String, Object> getHeadlinePromoteData();
	/**
	 * 前台主页，获取产品中心数据
	 * @param param
	 * @return
	 */
	Map<String, Object> getProductCentorData(Map<String, Object> param);
	/**
	 * 前台主页，获取我们的优势数据
	 * @return
	 */
	Map<String, Object> getOurAdviceData();
	/**
	 * 前台页面，获取产品详情
	 * @return
	 */
	Map<String, Object> getCommodityDetailByID(Map<String, String> param);

}
