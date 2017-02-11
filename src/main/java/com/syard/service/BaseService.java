package com.syard.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syard.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {
	
	/**
	 * spring4 支持了泛型的注入
	 */
	@Autowired
	private Mapper<T> mapper;

	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	public T queryById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有信息
	 * @return
	 */
	public List<T> queryAll(){
		return mapper.select(null);
	}
	
	/**
	 * 根据条件查询一个对象的信息
	 * @param record
	 * @return
	 */
	public T queryOne(T record){
		return mapper.selectOne(record);
	}
	
	/**
	 * 根据条件查询list信息
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record){
		return mapper.select(record);
	}
	
	/**
	 * 根据条件进行分页查询
	 * @param record
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T record , Integer pageNum , Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);//一定要在查询之前使用，并且之后的第一个查询才会分页。
		List<T> list = mapper.select(record);
		return new PageInfo<T>(list);
	}
	
	/**
	 * 查询分页信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<T> queryPageList( Integer pageNum , Integer pageSize){
		return queryPageListByWhere(null, pageNum, pageSize);
	} 
	
	/**
	 * 保存信息
	 * @param record
	 * @return 修改的记录数
	 */
	public Integer save(T record){
		
		/**
		 * 创建时间，
		 * 修改时间
		 */
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());//保证更新时间，和创建时间相同
		return this.mapper.insert(record);
	}
	/**
	 * 修改信息
	 * @param record
	 * @return
	 */
	public Integer update(T record){
		record.setUpdateTime(new Date());
		return this.mapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 休息信息，如果字段null，不会修改数据库
	 * @param record
	 * @return
	 */
	public Integer updateSelective(T record){
		record.setUpdateTime(new Date());
		record.setCreateTime(null);//强制把创建时间设置空，修改
		return this.mapper.updateByPrimaryKeySelective(record);
	}
	
	/*
	10、deleteByWhere*/
	
	/**
	 * 根据主键删除对象
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id){
		return this.mapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据多个id删除信息
	 * @param clazz ，对象对应的class
	 * @param property 对象中的主键属性字段名
	 * @param ids 主键值的集合
	 * @return
	 */
	public Integer deleteByIds(Class<T> clazz , String property , List<Object> ids){
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, ids);//第一个参数是 java对象中的属性，
		return this.mapper.deleteByExample(example);
	}

	/**
	 * 根据条件删除信息
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record){
		return this.mapper.delete(record);
	}
	
}
