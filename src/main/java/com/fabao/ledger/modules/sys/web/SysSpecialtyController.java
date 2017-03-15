package com.fabao.ledger.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.common.pojo.BusinessTypeTree;
import com.fabao.ledger.modules.sys.entity.SysSpecialty;
import com.fabao.ledger.modules.sys.service.SysSpecialtyManager;
import com.fabaoframework.modules.config.Global;
import com.fabaoframework.modules.web.BaseController;

import com.google.gson.Gson;


@Controller
@RequestMapping("/sys/specialty")
public class SysSpecialtyController extends BaseController {
	
	@Autowired
	private SysSpecialtyManager sysSpecialtyManager;
	
	private static String lawType =  Global.getConfig("falv.typeid");
	
	/**
	 * 查询所有
	 * @param id
	 * @return
	 */
	@RequestMapping("/findAllBusinessType2")
	@ResponseBody
	public String findAllBusinessType2(Long id){
		return findAll(id,false);
	}
	
	/**
	 * 查询有效
	 * @return
	 */
	@RequestMapping("/findAllBusinessType")
	@ResponseBody
	public String findAllBusinessType(Long id){
		return findAll(id,true);
	}
	
	

	public String findAll(Long id,Boolean isDeleted){
		List<BusinessTypeTree> listTree = new ArrayList<BusinessTypeTree>();
		if(null == id || id.equals("")){//首次进来，查两级
			SysSpecialty entity = new SysSpecialty();
			if(isDeleted){
				entity.setIsDeleted(false);//查询有效
			}
			entity.setNumCurrentLevel(1);
			List<SysSpecialty> list = sysSpecialtyManager.getByEntity(entity);
			for(SysSpecialty sysSpecialty:list){
					BusinessTypeTree uiTree = new BusinessTypeTree();
					uiTree.setId(sysSpecialty.getId().toString());
					uiTree.setText(sysSpecialty.getVacName());
					if(sysSpecialty.getNumLeaf() == 0){
						uiTree.setState("closed");
						uiTree.setChildren(getChildrenOne(sysSpecialty.getId(),isDeleted));
					}
					listTree.add(uiTree);
			}
		}else{
			SysSpecialty entity = new SysSpecialty();
			entity.setNumParentId(id.intValue());
			if(isDeleted){
				entity.setIsDeleted(false);//查询有效
			}
			List<SysSpecialty> list = sysSpecialtyManager.getByEntity(entity);
			for(SysSpecialty sysSpecialty:list){
					BusinessTypeTree uiTree = new BusinessTypeTree();
					uiTree.setId(sysSpecialty.getId().toString());
					uiTree.setText(sysSpecialty.getVacName());
					//判断是否是最后一个节点
					if(sysSpecialty.getNumLeaf() == 0){
						uiTree.setState("closed");
						uiTree.setChildren(getChildren(sysSpecialty.getId(),isDeleted));
					}
					listTree.add(uiTree);
			}
		}
		Gson gson  = new Gson();
		return gson.toJson(listTree);
	}
	
	
	
	@RequestMapping("/findAllLawType")
	@ResponseBody
	public String findAllLawType(Long id){
		List<BusinessTypeTree> listTree = new ArrayList<BusinessTypeTree>();
		if(null == id || id.equals("")){//首次进来，查两级
			SysSpecialty entity = new SysSpecialty();
			entity.setNumParentId(Integer.parseInt(lawType));
			List<SysSpecialty> list = sysSpecialtyManager.getByEntity(entity);
			for(SysSpecialty sysSpecialty:list){
					BusinessTypeTree uiTree = new BusinessTypeTree();
					uiTree.setId(sysSpecialty.getId().toString());
					uiTree.setText(sysSpecialty.getVacName());
					//判断是否是最后一个节点
					if(sysSpecialty.getNumLeaf() == 0){
						uiTree.setState("closed");
						uiTree.setChildren(getChildrenOne(sysSpecialty.getId(),false));
					}
					listTree.add(uiTree);
			}
		}else{
			SysSpecialty entity = new SysSpecialty();
			entity.setNumParentId(id.intValue());
			List<SysSpecialty> list = sysSpecialtyManager.getByEntity(entity);
			for(SysSpecialty sysSpecialty:list){
					BusinessTypeTree uiTree = new BusinessTypeTree();
					uiTree.setId(sysSpecialty.getId().toString());
					uiTree.setText(sysSpecialty.getVacName());
					//判断是否是最后一个节点
					if(sysSpecialty.getNumLeaf() == 0){
						uiTree.setState("closed");
						uiTree.setChildren(getChildren(sysSpecialty.getId(),false));
					}
					listTree.add(uiTree);
			}
		}
		Gson gson  = new Gson();
		return gson.toJson(listTree);
	}
	
	
	
	public List<BusinessTypeTree> getChildrenOne(Long id,Boolean isDeleted){
		List<BusinessTypeTree> listTree = new ArrayList<BusinessTypeTree>();
		SysSpecialty entity = new SysSpecialty();
		entity.setNumParentId(id.intValue());
		if(isDeleted){
			entity.setIsDeleted(false);//查询有效
		}
		List<SysSpecialty> list = sysSpecialtyManager.getByEntity(entity);
		for(SysSpecialty sysSpecialty:list){
				BusinessTypeTree uiTree = new BusinessTypeTree();
				uiTree.setId(sysSpecialty.getId().toString());
				uiTree.setText(sysSpecialty.getVacName());
				if(sysSpecialty.getNumLeaf() == 0){
					uiTree.setState("closed");
				}
				listTree.add(uiTree);
		}
		return listTree;
	}
	
	public List<BusinessTypeTree> getChildren(Long id,Boolean isDeleted){
		List<BusinessTypeTree> listTree = new ArrayList<BusinessTypeTree>();
		SysSpecialty entity = new SysSpecialty();
		entity.setNumParentId(id.intValue());
		if(isDeleted){
			entity.setIsDeleted(false);//查询有效
		}
		List<SysSpecialty> list = sysSpecialtyManager.getByEntity(entity);
		for(SysSpecialty sysSpecialty:list){
				BusinessTypeTree uiTree = new BusinessTypeTree();
				uiTree.setId(sysSpecialty.getId().toString());
				uiTree.setText(sysSpecialty.getVacName());
				if(sysSpecialty.getNumLeaf() == 0){
					uiTree.setState("open");
					uiTree.setChildren(getChildren(sysSpecialty.getId(),isDeleted));
				}
				listTree.add(uiTree);
		}
		return listTree;
	}

	@ResponseBody
	@RequestMapping(value="/getById/{id}",method=RequestMethod.POST)
	public String getById(@PathVariable("id") Long id){
		SysSpecialty sysSpecialty = sysSpecialtyManager.getById(id);
		Gson gson  = new Gson();
		return gson.toJson(sysSpecialty);
	}

}
