package com.fabao.ledger.modules.sys.web;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabao.ledger.common.utils.CacheUtils;
import com.fabao.ledger.common.utils.DictUtils;
import com.fabao.ledger.modules.sys.entity.SysDict;
import com.fabao.ledger.modules.sys.service.SysDictManager;
import com.fabaoframework.modules.page.Page;
import com.fabaoframework.modules.page.PageRequest;
import com.google.common.collect.Maps;


/**
 * @Description: 字典工具
 * @author FB - LISI
 * @date 2016年6月6日 下午3:24:48
 */
@Controller
@RequestMapping("/sys/dict")
public class SysDictController {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SysDictController.class);
	@Autowired
	private SysDictManager sysDictManager;
	
	/**
	 * 跳转字典列表页 --
	 * @param model
	 * @return
	 */
	@RequestMapping("dictList")
	public String list(Model model, HttpServletRequest request){
		String type = request.getParameter("type");
		type = type == null ? "" : type;
		model.addAttribute("type",type);
		return "modules/sys/sysDict-list";
	}
	
	/**
	 * 字典列表数据获取 --
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("dictListData")
	@ResponseBody
	public Map<String,Object> dictListData(int page , int rows, SysDict sys){
		sys.setIsDeleted(false);
		PageRequest<SysDict> pr = new PageRequest<SysDict>(sys);
		pr.setPageNo(page);
		pr.setPageSize(rows);
		/*Map<String,Object> res  = (Map<String,Object>)CacheUtils.get(ConstantUtils.getMethodName(this.getClass().getName()+"["+sys.getType()+"]"));
		if(res!=null&&res.size()>0){
			return res;
		}*/
		Map<String,Object> res  = Maps.newHashMap();
		Page<SysDict> pages = sysDictManager.findByPageRequest(pr);
		res.put("rows", pages.getResult());
		res.put("total", pages.getTotalCount());
		//CacheUtils.put(ConstantUtils.getMethodName(this.getClass().getName()+"["+sys.getType()+"]"), res);
		return res;
	}
	
	/**
	 * 跳转字典编辑新增页 --
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "dictInfoAE", method = RequestMethod.GET)
	public String dictInfoAE(Model model, Long did, int opt){
		SysDict dict = null;
		if(did!=null && did!=0l){
			dict = sysDictManager.getById(did);
			if(opt == 0){	//添加键值
				dict.setId(null);
				dict.setValue(null);
				dict.setLabel(null);
				dict.setSort(0);
			}
		}
		dict = dict == null ? new SysDict() : dict;
		model.addAttribute("sysDict", dict);
		
		return "modules/sys/sysDict-form";
	}
	
	/**
	 * 保存
	 * @param sysDict
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("save")
	public String save(SysDict sysDict, Model model){
		sysDict.setIsDeleted(false);
		sysDictManager.saveOrUpdate(sysDict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
		
		return "redirect:/sys/dict/dictList?repage&type="+sysDict.getType();
	}
	
	/**
	 * 删除
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(Long id){
		Map<String,Object> remap = Maps.newHashMap();
		remap.put("code", 0);
		try {
			SysDict dict = sysDictManager.getById(id);
			if(dict != null) {
				dict.setIsDeleted(true);
				sysDictManager.update(dict);
			}
			CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
		} catch (Exception e) {
			e.printStackTrace();
			remap.put("code", 0);
			remap.put("msg", e.getMessage());
		}
		return remap;
	}
	
	@RequestMapping("/findDictType")
	@ResponseBody
	public JSONArray findDictType(){
		List<Map<String, Object>> list = sysDictManager.getTypes();
		JSONArray dictJson = JSONArray.fromObject(list);
		return dictJson;
	}
	
	@ResponseBody
	@RequestMapping(value="/findDictTypeBy/{type}",method=RequestMethod.POST)	
	public JSONArray findDictTypeBy(@PathVariable("type") String type){
		List<Map<String, Object>> list = sysDictManager.getListByType(type);
		JSONArray dictJson = JSONArray.fromObject(list);
		return dictJson;
	}
	
}
