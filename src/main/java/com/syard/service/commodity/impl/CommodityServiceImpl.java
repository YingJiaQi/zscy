package com.syard.service.commodity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.syard.dao.CommodityDao;
import com.syard.dao.CommodityDescDao;
import com.syard.dao.CommoditySpecificationContentDao;
import com.syard.dao.OtherSourceDao;
import com.syard.pojo.Commodity;
import com.syard.pojo.CommodityDesc;
import com.syard.pojo.CommoditySpecificationContent;
import com.syard.pojo.OtherSource;
import com.syard.service.BaseService;
import com.syard.service.commodity.CommodityService;
import com.syard.vo.CommodityVo;
import com.syard.vo.PageBean;
@Service
public class CommodityServiceImpl extends BaseService<Commodity>  implements CommodityService {
	@Autowired
	private CommodityDao commodityDao;
	@Autowired
	private CommoditySpecificationContentDao commoditySpecificationContentDao;
	@Autowired
	private CommodityDescDao commodityDescDao;
	@Autowired
	private OtherSourceDao otherSourceDao;
	
	public Boolean addCommodity(Commodity cy) {
		cy.setCreateTime(new Date());
		cy.setUpdateTime(cy.getCreateTime());
		Integer save = super.save(cy);
		return save > 0 ? true: false;
	}

	public void deleteCommodity(String id) {
		super.deleteById(Long.parseLong(id));
	}

	public Map<String, Object> getCommodityList(PageBean pageBean) {
		Map<String, Object> result = new HashMap<String,Object>();
		Example example = new Example(Commodity.class);
		if(StringUtils.isNoneBlank(pageBean.getKey()))example.createCriteria().andLike("categoryName", "%"+pageBean.getKey()+"%");
		if(StringUtils.isNoneBlank(pageBean.getStartTime()))example.createCriteria().andGreaterThanOrEqualTo("createTime", pageBean.getStartTime());
		if(StringUtils.isNoneBlank(pageBean.getEndTime()))example.createCriteria().andLessThanOrEqualTo("createTime", pageBean.getEndTime());
		example.createCriteria().andEqualTo("isDel", 0);
		if(StringUtils.isNoneBlank(pageBean.getKey3()) && !StringUtils.equals(pageBean.getKey3(), "all")){
			List<Object> count = new ArrayList<Object>();
			count.add(1);count.add(0);
			example.createCriteria().andIn("isDel", count);
		}else if(StringUtils.isNoneBlank(pageBean.getKey3()) && !StringUtils.equals(pageBean.getKey3(), "1")){
			example.createCriteria().andEqualTo("isDel", 1);
		}
		if(StringUtils.isNoneBlank(pageBean.getKey2()))example.createCriteria().andLike("title", "%"+pageBean.getKey2()+"%");
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		
		//List<Commodity> clist =  commodityDao.findDataList(pageBean);
		//分页
		int size = 0;
		if((pageBean.getPage()-1)*pageBean.getRows() > selectByExample.size()){
			//开始行大于总记录数，此时无数据
			size = 0;
		}else{
			if((pageBean.getPage()-1)*pageBean.getRows()+pageBean.getRows() < selectByExample.size()){
				//前面所有页数总和 + 当前页数 < 总记录数，
				size = (pageBean.getPage()-1)*pageBean.getRows()+pageBean.getRows();
			}else{
				//此时取集合大小
				size = selectByExample.size();
			}
		}
		//分页
		List<Commodity> resultList = new ArrayList<Commodity>();
		for(int j =0; j < selectByExample.size(); j++){
			if(j >= (pageBean.getPage()-1)*pageBean.getRows() && j <= size-1){
				resultList.add(selectByExample.get(j));
			}
		}
		
		result.put("total", resultList.size());
		result.put("rows", resultList);
		return result;
	}
	public Map<String, String> deleteCommodityById(Map<String, Object> param) {
		Map<String, String> result = new HashMap<String, String>();
		Commodity selectByPrimaryKey = commodityDao.selectByPrimaryKey(param.get("id").toString());
		selectByPrimaryKey.setIsDel(1);
		selectByPrimaryKey.setTitle(selectByPrimaryKey.getTitle()+"deleted");
		selectByPrimaryKey.setUpdateTime(new Date());
		int updateByPrimaryKey = commodityDao.updateByPrimaryKey(selectByPrimaryKey);
		//删除商品描述信息
		Example desc = new Example(CommodityDesc.class);
		desc.createCriteria().andEqualTo("commodityId", param.get("id").toString());
		List<CommodityDesc> commDescList = commodityDescDao.selectByExample(desc);
		CommodityDesc commDesc = commDescList.get(0);
		commDesc.setIsDel(1);
		commDesc.setUpdateTime(new Date());
		commodityDescDao.updateByPrimaryKey(commDesc);
		//删除该商品规格参数
		Example specification = new Example(CommoditySpecificationContent.class);
		specification.createCriteria().andEqualTo("commodityId", param.get("id").toString());
		List<CommoditySpecificationContent> commSpecificationList = commoditySpecificationContentDao.selectByExample(specification);
		CommoditySpecificationContent commSpecification = commSpecificationList.get(0);
		commSpecification.setIsDel(1);
		commSpecification.setUpdateTime(new Date());
		commoditySpecificationContentDao.updateByPrimaryKey(commSpecification);
		
		
		if(updateByPrimaryKey > 0){
			result.put("success", "true");
			result.put("msg", "删除成功");
		}else{
			result.put("success", "false");
			result.put("msg", "删除失败");
		}
		return result;
	}

	public Commodity getCommodityById(String id) {
		return commodityDao.selectByPrimaryKey(id);
	}

	public CommodityVo getCommodityWithUpdateById(String id) {
		CommodityVo cv = new CommodityVo();
		//根据商品ID，获取商品基本参数
		cv = getBasicCommodityData(id, cv);
		//根据商品id,获取商品的类目参数及其内容
		cv.setCategorySpecification(getCommoditySpecificationContent(id));
		//根据商品ID，获取商品的描述信息
		cv.setDescribe(getCommodityDescribe(id));
		return cv;
	}

	/**
	 * 根据商品ID，获取商品的描述信息
	 * @param id
	 * @return
	 */
	private CommodityDesc getCommodityDescribe(String id) {
		Example example = new Example(CommodityDesc.class);
		example.createCriteria().andEqualTo("commodityId", id);
		List<CommodityDesc> slist = commodityDescDao.selectByExample(example);
		return slist.size() > 0 ? slist.get(0):null;
	}

	/**
	 * 根据商品id,获取商品的类目参数及其内容
	 * @param id
	 * @return
	 */
	private List<CommoditySpecificationContent> getCommoditySpecificationContent(String id) {
		Example example = new Example(CommoditySpecificationContent.class);
		example.createCriteria().andEqualTo("commodityId", id);
		List<CommoditySpecificationContent> clist = commoditySpecificationContentDao.selectByExample(example);
		return clist;
	}

	/**
	 * 根据商品ID，获取商品基本参数
	 * @param id
	 * @param cv
	 */
	private CommodityVo getBasicCommodityData(String id, CommodityVo cv) {
		Commodity cc = commodityDao.selectByPrimaryKey(id);
		cv.setBarcode(cc.getUsedType()==null?"":cc.getUsedType());
		cv.setCategoryName(cc.getCategoryName()==null?"":cc.getCategoryName());
		cv.setCreateTime(cc.getCreateTime());
		cv.setHot(cc.getHot()==null?0:cc.getHot());
		cv.setId(cc.getId()==null?"":cc.getId());
		cv.setIsDel(cc.getIsDel());
		cv.setNum(cc.getNum());
		cv.setPrice(cc.getPrice());
		cv.setSellPoint(cc.getSell_point()==null?"":cc.getSell_point());
		cv.setTitle(cc.getTitle()==null?"":cc.getTitle());
		cv.setUpdateTime(cc.getUpdateTime());
		/*StringBuffer sb = new StringBuffer();
		for(int i=0;i<cc.getImage().split(",").length;i++){
			sb = sb.append("<img src='"+cc.getImage().split(",")[i]+"' title='"+cv.getId()+".png'/>");
		}*/
		cv.setImage(cc.getImage()==null?"": cc.getImage());
		return cv;
	}

	@Override
	public Boolean checkRepeatWithTitle(String title) {
		Example example = new Example(Commodity.class);
		example.createCriteria().andEqualTo("title", title);
		List<Commodity> selectByExample = commodityDao.selectByExample(example);
		Example os = new Example(OtherSource.class);
		os.createCriteria().andEqualTo("sourceTitle", title);
		List<OtherSource> selectByExample2 = otherSourceDao.selectByExample(os);
		return selectByExample.size() > 0 || selectByExample2.size() >0 ? true:false;
	}

}
