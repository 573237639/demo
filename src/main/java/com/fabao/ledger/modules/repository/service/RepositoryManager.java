package com.fabao.ledger.modules.repository.service;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.fabao.ledger.modules.repository.util.HttpKit;
import com.fabaoframework.modules.config.Global;

/**
 * @Description: 知识库管理
 * @author FB - LISI
 * @date 2016年10月27日 下午3:13:32
 */
public class RepositoryManager {
	
	private static Logger logs = Logger.getLogger(RepositoryManager.class);
	
	private static final String REPOSITORY_RUL = Global.getConfig("repository.url");
	
	/**
	 * 通过查询条件查询所有知识库信息
	 * @param pageFrom 当前页
	 * @param pageSize 页大小
	 * @param repType 知识类型
	 * @param title 标题
	 * @return
	 */
	public static JSONObject getRepInfos(int pageFrom, int pageSize, String repType, String title){
		JSONObject param = new JSONObject();
		param.put("pageFrom", pageFrom-1);
		param.put("pageSize", pageSize);
		if(StringUtils.isEmpty(repType))
			repType = "test";
		param.put("repType", repType);
		if(!StringUtils.isEmpty(title))
			param.put("title", title);
		
		String result = "";
		try {
			logs.info("Ledger-知识库管理-根据条件查询所有知识库信息-入参>>>>"+param.toString());
			result = HttpKit.doPostByraw(REPOSITORY_RUL+"/getRepInfos", param.toString(),"UTF-8",true);
			logs.info("Ledger-知识库管理-根据条件查询所有知识库信息-出参>>>>"+result);
		} catch (Exception e) {
			e.printStackTrace();
			logs.info("Ledger-知识库管理-根据条件查询所有知识库信息-调用异常>>>>调用链接:"+REPOSITORY_RUL+"/getRepInfos" + ",异常信息:"+e.getMessage());
		}
		return JSONObject.fromObject(result);
	}

	/**
	 * 添加或者更新知识
	 * @param id		主键,不为空则表示更新规则,否则添加,添加知识需要新增知识类型(可重复创建)
	 * @param type		知识类型
	 * @param title		标题
	 * @param content	内容
	 * @return
	 */
	public static JSONObject addOrUpdateRepository(String id, String type,
			String title, String content, String userId) {
		JSONObject param = new JSONObject();
		param.put("addUserId", userId);
		param.put("repType", type);
		param.put("title", title);
		param.put("content", content);
		logs.info("Ledger-知识库管理-添加或者更新知识-入参>>>>"+param.toString() + ",id="+id);
		String result = "{}";
		String doPostUrl = "";
		try {
			if(StringUtils.isEmpty(id)){	//新增
				logs.info("Ledger-知识库管理-添加或者更新知识-新增操作");
				System.err.println("新增" + param.toString());
				//先进行知识类型添加
				JSONObject addType = new JSONObject();
				addType.put("repType", type);
				logs.info("Ledger-知识库管理-添加或者更新知识-新增知识类型-入参>>>>"+addType.toString());
				doPostUrl = REPOSITORY_RUL+"/addType";
				String addTypeResult = HttpKit.doPostByraw(REPOSITORY_RUL+"/addType", addType.toString(),"UTF-8",true);
				logs.info("Ledger-知识库管理-添加或者更新知识-新增知识类型-出参>>>>"+addTypeResult);
				JSONObject re = JSONObject.fromObject(addTypeResult);
				if(re.containsKey("status") && "200".equals(re.getString("status"))){
					doPostUrl = REPOSITORY_RUL+"/addRepInfo";
					result = HttpKit.doPostByraw(REPOSITORY_RUL+"/addRepInfo", param.toString(),"UTF-8",true);
				}
			}else{	//更新
				param.put("id", id);
				logs.info("Ledger-知识库管理-添加或者更新知识-更新操作");
				System.err.println("更新" + param.toString());
				doPostUrl = REPOSITORY_RUL+"/updateRepInfo";
				result = HttpKit.doPostByraw(REPOSITORY_RUL+"/updateRepInfo", param.toString(),"UTF-8",true);
			}
			logs.info("Ledger-知识库管理-添加或者更新知识-出参>>>>"+result);
		} catch (Exception e) {
			e.printStackTrace();
			logs.info("Ledger-知识库管理-添加或者更新知识-调用异常>>>>调用链接:"+ doPostUrl + ",异常信息:"+e.getMessage());
		}
		return JSONObject.fromObject(result);
	}

	/**
	 * 根据id查询知识信息
	 * @param id
	 * @return
	 */
	public static JSONObject getRepInfoById(String id, String repType) {
		JSONObject param = new JSONObject();
		param.put("repType", repType);
		param.put("id", id);
		
		String result = "";
		try {
			logs.info("Ledger-知识库管理-根据ID查询知识信息-入参>>>>"+param.toString());
			System.err.println(param.toString());
			result = HttpKit.doPostByraw(REPOSITORY_RUL+"/getRepInfoById", param.toString(),"UTF-8",true);
			logs.info("Ledger-知识库管理-根据ID查询知识信息-出参>>>>"+result);
		} catch (Exception e) {
			e.printStackTrace();
			logs.info("Ledger-知识库管理-根据ID查询知识信息-调用异常>>>>调用链接:"+REPOSITORY_RUL+"/getRepInfoById" + ",异常信息:"+e.getMessage());
		}
		return JSONObject.fromObject(result);
	}
}
