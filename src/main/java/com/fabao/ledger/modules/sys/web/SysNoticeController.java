package com.fabao.ledger.modules.sys.web;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fabao.ledger.modules.sys.entity.SysNotice;
import com.fabao.ledger.modules.sys.service.SysNoticeManager;
import com.fabao.ledger.modules.sys.util.DateTimeUtils;
import com.google.common.collect.Maps;


/**
 * @Description: 公告管理
 */
@Controller
@RequestMapping("/sys/notice")
public class SysNoticeController {
	private static final Logger log = Logger.getLogger(SysNoticeController.class);
	@Autowired
	private SysNoticeManager sysNoticeManager;
	
	/**
	 * 跳转公告列表页 --
	 * @param model
	 * @return
	 */
	@RequestMapping("noticeList")
	public String list(){
		return "modules/sys/sysNotice-list";
	}
	
	@RequestMapping("/*/image")
	public Map<String,Object> image(@RequestParam("imgFile") MultipartFile file){
		Map<String,Object> obj = Maps.newHashMap();
		obj.put("error", 0);  
		if(file.isEmpty()){
			System.out.println("没有数据");
			obj.put("url", "");  
			return obj;
		}else{
			System.out.println("我进入了上传方法");
			obj.put("url", file);  
			return obj;
		}
		
	}
	
	
	/**
	 * 公告列表数据获取 --
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/noticeListData")
	public Map<String,Object> noticeListData(int page , int rows, SysNotice sysNotice){
		sysNotice.setIsDeleted(false);
		try {
			return sysNoticeManager.getNoticeListByPage(page, rows, sysNotice);
		} catch (Exception e) {
			log.error("获取公告集合异常"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 定时器查询 弹框显示 公告列表数据获取 --
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("getMessage")
	@ResponseBody
	public Map<String,Object> getMessage(){
		SysNotice sysNotice = new SysNotice();
		sysNotice.setIsDeleted(false);
		sysNotice.setGmtModified(DateTimeUtils.get5MinuteDay());//设置查询当前5分钟的以内的数据
		Map<String,Object> remap = Maps.newHashMap();
		List<SysNotice> list = sysNoticeManager.getByEntity(sysNotice);
		remap.put("code", 1);
		if(null !=list && list.size() > 0){
			remap.put("code", 0);
			remap.put("getMessage", list);
		}
		return remap;
	}
	
	
	/**
	 * 跳转公告编辑新增页 --
	 * @param model
	 * @return
	 */
	@RequestMapping("sysNotice-add")
	public String noticeInfoAdd(){
		return "modules/sys/sysNotice-add";
	}
	
	/**
	 * 跳转公告编辑新增页 --
	 * @param model
	 * @return
	 */
	@RequestMapping("sysNotice-edit/{id}")
	public String noticeInfoEdit(@PathVariable("id") Long id,Model model){
		model.addAttribute("sysNotice", sysNoticeManager.getById(id));
		return "modules/sys/sysNotice-edit";
	}
	
	/**
	 * 跳转公告详情页 --
	 * @param model
	 * @return
	 */
	@RequestMapping("sysNotice-view/{id}")
	public String noticeInfoView(@PathVariable("id") Long id,Model model){
		model.addAttribute("sysNotice", sysNoticeManager.getById(id));
		return "modules/sys/sysNotice-view";
	}
	
	/**
	 * 保存
	 * @param sysNotice
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveNotice")
	public Map<String,Object> saveNotice(SysNotice sysNotice){
		Map<String,Object> remap = Maps.newHashMap();
		remap.put("code", 0);
		try {
			sysNoticeManager.saveOrUpdateNotice(sysNotice);
		} catch (Exception e) {
			e.printStackTrace();
			remap.put("code", 1);
			remap.put("msg", e.getMessage());
		}
		return remap;

	}
	
	
	/**
	 * 删除
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/deleteNotice")
	@ResponseBody
	public Map<String,Object> delete(Long id){
		Map<String,Object> remap = Maps.newHashMap();
		remap.put("code", 0);
		try {
			SysNotice notice = sysNoticeManager.getById(id);
			if(notice != null) {
				notice.setIsDeleted(true);
				sysNoticeManager.update(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
			remap.put("code", 0);
			remap.put("msg", e.getMessage());
		}
		return remap;
	}
	
  
}
