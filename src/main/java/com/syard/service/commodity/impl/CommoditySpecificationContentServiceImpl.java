package com.syard.service.commodity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.syard.common.utils.CustomRuntimeException;
import com.syard.dao.CategorySpecificationLinkDao;
import com.syard.dao.CommoditySpecificationDao;
import com.syard.pojo.CategorySpecificationLink;
import com.syard.pojo.CommoditySpecification;
import com.syard.service.BaseService;
import com.syard.service.commodity.CommoditySpecificationService;
import com.syard.vo.CommoditySpecificationVo;
import com.syard.vo.PageBean;
import com.syard.web.Commodity.CommoditySpecificationManage;

@Service
public class CommoditySpecificationContentServiceImpl extends BaseService<CommoditySpecification>  implements CommoditySpecificationService {
	@Autowired
	private CommoditySpecificationDao commoditySpecificationDao;
	@Autowired
	private CategorySpecificationLinkDao categorySpecificationLinkDao;
	
	private static Logger log = LoggerFactory.getLogger(CommoditySpecificationManage.class);
	public Map<String, Object> getCommoditySpecificationList(PageBean pageBean) {
		PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
		
		
		String nodeID = pageBean.getNodeID();
		List<CategorySpecificationLink> dfl = null;
		if(StringUtils.isNoneBlank(nodeID)){
			if(StringUtils.isNotBlank(pageBean.getDocId())){
				//说明有要删除的关联文件
				//dfl = categorySpecificationLinkDao.findAllAssociatedByFolderIdButDocid(nodeID, pageBean.getDocId());
			}else{
				Example ex = new Example(CategorySpecificationLink.class);
				ex.createCriteria().andEqualTo("categoryId", nodeID);
				dfl = categorySpecificationLinkDao.selectByExample(ex);
			}
		}

		Example ex = new Example(CommoditySpecification.class);
		if(pageBean.getIsDeleted() != null || pageBean.getIsDeleted()+"" != ""){
			ex.createCriteria().andEqualTo("isDel", pageBean.getIsDeleted());
		}
		if(StringUtils.isNoneBlank(pageBean.getStartTime()+"") && !StringUtils.equals(pageBean.getStartTime()+"", "null")){
			ex.createCriteria().andGreaterThanOrEqualTo("createTime", pageBean.getStartTime());
		}
		if(StringUtils.isNoneBlank(pageBean.getEndTime()+"") && pageBean.getEndTime() != null){
			ex.createCriteria().andLessThanOrEqualTo("createTime", pageBean.getEndTime());
		}
		if(StringUtils.isNoneBlank(pageBean.getKey()) && pageBean.getKey() != null){
			ex.createCriteria().andLike("specificationName", pageBean.getKey());
		}
		List<CommoditySpecification> uList = commoditySpecificationDao.selectByExample(ex);
		//PageInfo<CommoditySpecification> pResult = new PageInfo<CommoditySpecification>(uList);
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(dfl != null && dfl.size() >0){
			List<CommoditySpecificationVo> dlist = new ArrayList<CommoditySpecificationVo>();
			//遍历list,关联勾选
			for(CommoditySpecification specification: uList){
				int i = 0;
				for(CategorySpecificationLink dflk: dfl){
					if(StringUtils.equals(dflk.getCategorySpecificationId(), specification.getId())){
						//说明此条数据要加item状态
						CommoditySpecificationVo dva = CommoditySpecificationVoADD(specification);
						dlist.add(dva); 
						i=1;
					}
				}
				if(i == 0){
					CommoditySpecificationVo dv = CommoditySpecificationToVo(specification);
					dlist.add(dv);
				}
			}
			result.put("rows", dlist);
		}else{
			result.put("rows", uList);
		}
		
		result.put("total", uList.size());
		return result;
	}
	private CommoditySpecificationVo CommoditySpecificationToVo(CommoditySpecification doc) {
		CommoditySpecificationVo csv = new CommoditySpecificationVo();
		csv.setCreateTime(doc.getCreateTime());
		csv.setId(doc.getId());
		csv.setIsDel(doc.getIsDel());
		csv.setSpecificationName(doc.getSpecificationName());
		csv.setUpdateTime(doc.getUpdateTime());
		return csv;
	}
	private CommoditySpecificationVo CommoditySpecificationVoADD(CommoditySpecification doc) {
		CommoditySpecificationVo csv = new CommoditySpecificationVo();
		csv.setChecked(true);
		csv.setCreateTime(doc.getCreateTime());
		csv.setId(doc.getId());
		csv.setIsDel(doc.getIsDel());
		csv.setSpecificationName(doc.getSpecificationName());
		csv.setUpdateTime(doc.getUpdateTime());
		return csv;
	}
	public Map<String, Object> addCommoditySpecification(CommoditySpecification commoditySpecification) {
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			commoditySpecification.setId((UUID.randomUUID()+"").replaceAll("-", ""));
			commoditySpecification.setCreateTime(new Date());
			commoditySpecification.setUpdateTime(commoditySpecification.getCreateTime());
			commoditySpecificationDao.insert(commoditySpecification);
			result.put("flag", "true");
			result.put("msg", "添加成功");
		} catch (Exception e) {
			result.put("msg", "添加失败");
			log.error("addCommoditySpecification exception"+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}
	public Map<String, Object> deleteCommoditySpecificationById(String id) {
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			//逻辑删除
			CommoditySpecification cs = commoditySpecificationDao.selectByPrimaryKey(id);
			cs.setIsDel(1);
			cs.setUpdateTime(new Date());
			int up = commoditySpecificationDao.updateByPrimaryKey(cs);
			
			if(up == 1){
				result.put("msg", "删除成功");
				result.put("flag", "true");
			}else{
				throw new CustomRuntimeException("删除失败");
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			log.error("deleteCommoditySpecificationById exception"+e.getMessage());
		}
		return result;
	}
	public Map<String, Object> getCommoditySpecificationById(String id) {
		CommoditySpecification selectByPrimaryKey = commoditySpecificationDao.selectByPrimaryKey(id);
		if(selectByPrimaryKey != null){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", selectByPrimaryKey);
			result.put("success", "true");
			return result;
		}else{
			throw new CustomRuntimeException("查找该参数失败");
		}
	}
	public Map<String, Object> updateCommoditySpecificationById(CommoditySpecification commoditySpecification) {
		commoditySpecification.setUpdateTime(new Date());
		int updateByPrimaryKey = commoditySpecificationDao.updateByPrimaryKey(commoditySpecification);
		if(updateByPrimaryKey >0){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("msg", "更新成功");
			result.put("success", "true");
			return result;
		}else{
			throw new CustomRuntimeException("更新参数失败");
		}
	}
	
}
