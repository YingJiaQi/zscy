package com.syard.web.API;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.syard.service.api.PreWebContentManagerService;

@Controller
@RequestMapping("/PreWebContentManager")
public class PreWebContentManager {
	@Autowired
	private PreWebContentManagerService webContentManagerService;
	
	/**
	 * 跳转到其它页面
	 * 
	 * @param value
	 * @param cate 用于取get方式的值
	 * @return
	 */
	@RequestMapping(value = "{value}")
	public ModelAndView pageJump(@PathVariable("value") String value, String cate) {
		ModelAndView mv = new ModelAndView(value);
		System.out.println(cate);
		mv.addObject("indexParam", cate);//将参数传递到页面
		return  mv;
	}
	/**
	 * 前台页面获取关于我们数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getAboutUsCommponyProfile", method = RequestMethod.POST)
	public ResponseEntity<?> getAboutUsCommponyProfile(@RequestBody Map<String,Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getAboutUsCommponyProfile(param),HttpStatus.OK);
	}
	/**
	 * 前台新闻中心页面获取数据
	 * @return
	 */
	@RequestMapping(value="/getNewsCentorData", method = RequestMethod.POST)
	public ResponseEntity<?> getNewsCentorData(){
		return new ResponseEntity<Object>(webContentManagerService.getNewsCentorData(),HttpStatus.OK);
	}
	/**
	 * 前台磁性制品页面请求磁铁分类数据
	 * @return
	 */
	@RequestMapping(value="/getMagnetClassficationData", method = RequestMethod.POST)
	public ResponseEntity<?> getMagnetClassficationData(){
		return new ResponseEntity<Object>(webContentManagerService.getMagnetClassficationData(),HttpStatus.OK);
	}
	/**
	 * 前台磁性制品页面请求 ---根据分类名，查找该分类下的所有数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getMagnetClassficationDataByTitle", method = RequestMethod.POST)
	public ResponseEntity<?> getMagnetClassficationDataByTitle(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getMagnetClassficationDataByTitle(param),HttpStatus.OK);
	}
	/**
	 * 前台磁性制品页面请求 ---根据用途名，查找该用途下的所有数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getMagnetDataByUsedName", method = RequestMethod.POST)
	public ResponseEntity<?> getMagnetDataByUsedName(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getMagnetClassficationDataByTitle(param),HttpStatus.OK);
	}
	/**
	 * 前台主页，头条推荐获取数据
	 * @return
	 */
	@RequestMapping(value="/getHeadlinePromoteData",method = RequestMethod.POST)
	public ResponseEntity<?> getHeadlinePromoteData(){
		return new ResponseEntity<Object>(webContentManagerService.getHeadlinePromoteData(),HttpStatus.OK);
	}
	/**
	 * 前台主页，获取产品中心数据
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getProductCentorData", method = RequestMethod.POST)
	public ResponseEntity<?> getProductCentorData(@RequestBody Map<String,Object> param){
		return new ResponseEntity<Object>(webContentManagerService.getProductCentorData(param),HttpStatus.OK);
	}
	/**
	 * 前台主页，获取我们的优势数据
	 * @return
	 */
	@RequestMapping(value="/getOurAdviceData", method = RequestMethod.POST)
	public ResponseEntity<?> getOurAdviceData(){
		return new ResponseEntity<Object>(webContentManagerService.getOurAdviceData(),HttpStatus.OK);
	}
	/**
	 * 前台页面，获取产品详情
	 * @return
	 */
	@RequestMapping(value="/getCommodityDetailByID", method = RequestMethod.POST)
	public ResponseEntity<?> getCommodityDetailByID(@RequestBody Map<String,String> param){
		return new ResponseEntity<Object>(webContentManagerService.getCommodityDetailByID(param),HttpStatus.OK);
	}
	/**
	 * 前台页面，获取磁性制品页面中轮播图片数据
	 * @return
	 */
	@RequestMapping(value="/getCarouselsPics", method = RequestMethod.POST)
	public ResponseEntity<?> getCarouselsPics(){
		return new ResponseEntity<Object>(webContentManagerService.getCarouselsPics(),HttpStatus.OK);
	}
	/**
	 * 前台采购报价页面获取数据
	 * @return
	 */
	@RequestMapping(value="/getByPriceData", method = RequestMethod.POST)
	public ResponseEntity<?> getByPriceData(){
		return new ResponseEntity<Object>(webContentManagerService.getByPriceData(),HttpStatus.OK);
	}
	/**
	 * 前台生产视频页面获取数据
	 * @return
	 */
	@RequestMapping(value="/getProductVideoData", method = RequestMethod.POST)
	public ResponseEntity<?> getProductVideoData(){
		return new ResponseEntity<Object>(webContentManagerService.getProductVideoData(),HttpStatus.OK);
	}
}
