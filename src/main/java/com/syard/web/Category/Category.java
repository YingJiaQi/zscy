package com.syard.web.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.syard.service.Category.CategoryService;
import com.syard.vo.VEasyuiTree;

@Controller
@RequestMapping("/category")
public class Category {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/getCatagoryList", method = RequestMethod.POST)
	public ResponseEntity<?> getMenuList(){
		List<VEasyuiTree> vtList = new ArrayList<VEasyuiTree>();
		vtList = categoryService.getCategoryList();
		return new ResponseEntity<Object>(vtList, HttpStatus.OK);
	}
	@RequestMapping(value = "/getCategoryListWithCommodity", method = RequestMethod.POST)
	public ResponseEntity<?> getCategoryListWithCommodity(){
		List<VEasyuiTree> vtList = new ArrayList<VEasyuiTree>();
		vtList = categoryService.getCategoryListWithCommodity();
		return new ResponseEntity<Object>(vtList, HttpStatus.OK);
	}
	@RequestMapping(value="/modifyNode", method=RequestMethod.POST)
	public ResponseEntity<?> updateNodeName(@RequestBody Map<String, Object> param){
		Map<String, Object> result = categoryService.modifyNode(param.get("id").toString(), param.get("text").toString(), param.get("parentId").toString());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	@RequestMapping(value="/removeNode", method=RequestMethod.POST)
	public ResponseEntity<?> removeNode(@RequestBody Map<String, Object> param){
		Map<String, Object> result = categoryService.removeNode(param.get("ids").toString());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	@RequestMapping(value="/specificationList", method = RequestMethod.GET)
	public String toCategoryList(){
		return "Back_R_SpecificationList";
	}
	@RequestMapping(value="/addAssociate", method = RequestMethod.POST)
	public ResponseEntity<?> addAssociate(@RequestBody Map<String, Object> param){
		Map<String, Object> result = categoryService.addAssociate(param);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	@RequestMapping(value="/getAssociatedListById", method = RequestMethod.POST)
	public ResponseEntity<?> getAssociatedListById(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(categoryService.getAssociateListById(param),HttpStatus.OK);
	}
	@RequestMapping(value="/delAssociate", method = RequestMethod.POST)
	public ResponseEntity<?> delAssociate(@RequestBody Map<String, Object> param){
		return new ResponseEntity<Object>(categoryService.delAssociate(param),HttpStatus.OK);
	}
}
