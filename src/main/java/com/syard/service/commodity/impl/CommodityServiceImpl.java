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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syard.dao.CommodityDao;
import com.syard.pojo.Commodity;
import com.syard.service.BaseService;
import com.syard.service.commodity.CommodityService;
import com.syard.vo.PageBean;
@Service
public class CommodityServiceImpl extends BaseService<Commodity>  implements CommodityService {
	@Autowired
	private CommodityDao commodityDao;
	
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
		if(StringUtils.isNoneBlank(pageBean.getKey3()) && !StringUtils.equals(pageBean.getKey3(), "all")){
			example.createCriteria().andEqualTo("status", pageBean.getKey3());
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

}
