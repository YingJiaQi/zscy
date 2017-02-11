package com.syard.service.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.entity.Example;
import com.syard.dao.SystemComponentsDao;
import com.syard.dao.UserDao;
import com.syard.pojo.SystemComponents;
import com.syard.service.user.SystemComponentsService;
import com.syard.vo.VEasyuiTree;

@Service
public class SystemComponentsServiceImpl implements SystemComponentsService {

	@Autowired
	private SystemComponentsDao sysCompDao;
	/*@Autowired
	private AdminAuthorityDao authorDao;*/
	@Autowired
	private UserDao userDao;

	public List<VEasyuiTree> getComponentList(String userId) {
		List<VEasyuiTree> easyTree = null;
		// 用户模块
		//List<String> authorList = authorDao.findAuthorityByUserId(userId);
		// 父节点
		Example example = new Example(SystemComponents.class);
		example.createCriteria().andEqualTo("parentComponent", "");
		List<SystemComponents> parentlist = sysCompDao.selectByExample(example);
		easyTree = changeType(parentlist, null);
		// 子节点
		List<SystemComponents> nodeList = null;
		List<VEasyuiTree> children = null;
		// 为父模块添加对应子模块
		for (int i = 0; i < parentlist.size(); i++) {
			String componentCode = parentlist.get(i).getComponentCode();
			Example examples = new Example(SystemComponents.class);
			examples.createCriteria().andEqualTo("parentComponent", componentCode);
			nodeList = sysCompDao.selectByExample(examples);
			//children = changeType(nodeList, authorList);
			children = changeType(nodeList, null);
			easyTree.get(i).setChildren(children);
		}
		return easyTree;
	}

	/**
	 * 将模块对象转换成tree结构
	 * 
	 * @param parentlist
	 * @return
	 */
	public List<VEasyuiTree> changeType(List<SystemComponents> parentlist, List<String> authorList) {
		List<VEasyuiTree> easyTree = new ArrayList<VEasyuiTree>();
		VEasyuiTree ve = null;
		boolean ischecked = false;
		for (int i = 0; i < parentlist.size(); i++) {
			ischecked = false;
			ve = new VEasyuiTree();
			// 用户已分配模块默认为选中
			if (authorList != null && authorList.size() > 0) {
				for (int j = 0; j < authorList.size(); j++) {
					if (parentlist.get(i).getId().equals(authorList.get(j))) {
						ischecked = true;
						break;
					}
				}
			}
			ve.setId(parentlist.get(i).getId());
			ve.setText(parentlist.get(i).getComponentName());
			ve.setIschecked(ischecked);
			if(parentlist.get(i).getParentComponent() != null && parentlist.get(i).getParentComponent() != ""){
				ve.setAttributes(parentlist.get(i).getComponentUrl());
			}
			ve.setState(null);
			easyTree.add(ve);
		}
		return easyTree;
	}

	/**
	 * 分配菜单模块
	 */
	@Transactional
	public void assignUserAuthor(String userId, List<String> componentId) {
		/*AdminAuthority author = null;
		// 删除原有菜单
		List<AdminAuthority> list = authorDao.findListByUserId(userId);
		authorDao.delete(list);
		User user = userDao.findOne(userId);
		// 重新分配菜单
		for (int i = 0; i < componentId.size(); i++) {
			author = new AdminAuthority();
			String uuid = (UUID.randomUUID() + "").replaceAll("-", "");
			author.setId(uuid);
			author.setUser(user);
			author.setComponentId(componentId.get(i));
			author.setIsDeleted(0);
			authorDao.save(author);
		}*/
	}

	public List<?> getMenuJsonByUserId(String userId) {
		/*// 查到所有子的id
		List<String> cidlist = authorDao.findAuthorityByUserId(userId);

		List<SystemComponents> clist = sysCompDao.findListByInIds(cidlist);
		// 遍历子 ,找到所有的父code-list
		List<String> pcodes = new ArrayList<>();
		for (SystemComponents c : clist) {
			if (Validator.isNotNull(c.getParentComponent())) {
				pcodes.add(c.getParentComponent());
			}
		}
		// 根据父code找到所有的父文件夹对象
		try {
			List<SystemComponents> plist = sysCompDao.findListByInCodes(pcodes);
			// 父-子 集合合并
			clist.addAll(plist);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 循环封装数据
		List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
		for (SystemComponents c : clist) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", c.getComponentCode());
			if (Validator.isNull(c.getParentComponent())) {
				m.put("pId", 0);
			} else {
				m.put("pId", c.getParentComponent());
			}

			m.put("name", c.getComponentName());
			if (Validator.isNotNull(c.getComponentUrl())) {
				m.put("page", c.getComponentUrl());
			}
			menu.add(m);
		}
		return menu;*/
		return null;
	}

}
