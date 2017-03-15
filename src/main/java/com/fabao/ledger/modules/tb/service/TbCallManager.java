 /**
 * Copyright (c) 2005-2010 fabao.cn
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.fabao.ledger.modules.tb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fabao.ledger.common.pojo.CallStayEnum;
import com.fabao.ledger.modules.tb.dao.TbCallDao;
import com.fabao.ledger.modules.tb.dao.TbCallStayDao;
import com.fabao.ledger.modules.tb.dao.TbQcBaseDao;
import com.fabao.ledger.modules.tb.dao.TbQcInfoDao;
import com.fabao.ledger.modules.tb.entity.TbCall;
import com.fabao.ledger.modules.tb.entity.TbQcBase;
import com.fabaoframework.modules.mybatis.BaseManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class TbCallManager extends BaseManager<TbCallDao,TbCall>{
	private static final Logger logger = Logger.getLogger(TbCallManager.class);
	@Autowired
	private TbQcBaseDao tbQcBaseDao;
	@Autowired
	private TbCallDao tbCallDao;
	@Autowired
	private TbCallStayDao tbCallStayDao;
	@Autowired
	private TbQcInfoDao tbQcInfoDao;
@Override
@Autowired
public void setEntityDao(TbCallDao tbCallDao ) {
	this.entityDao=tbCallDao;
}


/***
 * 录入质检
 * 
 * @param dataMap
 *            (质检数据 质检明细)
 * @param mapLawyer
 *            (律师信息)
 */
public void addQCBaseAll(Map<String, String> dataMap,
		Map<String, String> mapLawyer) {
	try {
		String ids=dataMap.get("serialArr");
		List<TbQcBase> QCBlist = new ArrayList<TbQcBase>();
		List<TbCall> callList = tbCallDao.getBySerials(ids);
		createQCBase(QCBlist, callList, mapLawyer, dataMap);
		// 质检录入
		logger.error("质检录入start.....！");
		if(null != QCBlist && QCBlist.size() > 0){
			for(TbQcBase tb : QCBlist){
				tbQcBaseDao.insert(tb);
			}
		}
		logger.error("质检录入end.....！");
		// 待质检修改状态
		revisionStatus(ids);
		// 质检明细录入
		logger.error(" 质检明细录入start.....！");
//		saveQCInfo(QCBlist, dataMap);
		logger.error("质检明细录入end.....！");
	} catch (Exception e) {
		logger.error("质检录入操作失败！", e);
	}
}
/***
 * 新增质检明细
 * 
 * @param QCBlist
 *            (质检表数据 用于取ID)
 * @param dataMap
 *            (质检明细数据)
 */
/*public void saveQCInfo(List<TbQcBase> QCBlist, Map<String, String> dataMap) {
	try {
		List<TbQcInfo> QCIlist = new ArrayList<TbQcInfo>();
		JSONArray jsonArr = new JSONArray(dataMap.get("proArr"));
		for (int i = 0; i < QCBlist.size(); i++) {
			for (int j = 0; j < jsonArr.length(); j++) {
				TbQcInfo qci = new TbQcInfo();
				JSONObject jsonObj = jsonArr.getJSONObject(j);
				qci.setNumProId(jsonObj.getLong("pro_id"));
				qci.setNumScore(jsonObj.getInt("pro_scores"));
				qci.setNumQcBaseId(QCBlist.get(i).getNum_Id());
				qci.setNumBit(jsonObj.getInt("numBit"));
				QCIlist.add(qci);
			}
		}
		tbQcInfoDao.saveAll(QCIlist);
	} catch (Exception e) {
		logger.error("质检明细录入操作失败！", e);
	}
}*/
/***
 * 已录入的待质检状态修改
 * 
 * @param array
 *            (流水号)
 */
public void revisionStatus(String ids) {
	try {
		logger.info("已录入的待质检状态修改start...！");
		tbCallStayDao.updateBat(ids);
		logger.info("已录入的待质检状态修改end...！");
	} catch (Exception e) {
		logger.error("已录入的待质检状态修改失败！", e);
	}
}
/**
 * 创建质检对象集合
 * 
 * @param list
 *            (用于插入数据库)
 * @param callList
 *            (读取的数据源)
 * @param mapLawyer
 *            (律师姓名)
 * @param dataMap
 *            (页面数据)
 */
public void createQCBase(List<TbQcBase> list, List<TbCall> callList,
		Map<String, String> mapLawyer, Map<String, String> dataMap) {
	for (int i = 0; i < callList.size(); i++) {
		TbCall call = callList.get(i);
		TbQcBase qcb = new TbQcBase();
		qcb.setVacQcbaseSerial(call.getId().toString());
//		qcb.setNum_Lawyer_Id(call.getAgentId());
//		qcb.setVar_Lawyer_Name(mapLawyer.get(call.getAgentId()));// 律师姓名
//		qcb.setDate_Consult(call.getTalkBgTime());
		int scores = Integer.parseInt(dataMap.get("scores"));
		qcb.setNumQcbaseScore(scores);// 质检得分
//		float po = Float.parseFloat(dataMap.get("proportion"));
		int totalScore = Integer.parseInt(dataMap.get("totalScore"));
//		int Num_Check_Bit = scores >= (totalScore * po) ? CallStayEnum.QUALIFIED
//				.getType() : CallStayEnum.UNQUALIFIED.getType();
//		qcb.setNumQcbaseCheckBit(Num_Check_Bit);// 是否合格
//		qcb.setNum_Check_State(QCBaseEnum.NOT_THROUGH.getType());
		qcb.setVacQcbaseSummary(dataMap.get("summary") != null ? dataMap
				.get("summary") : "");// 咨询概述
		qcb.setVacQcbaseImprove(dataMap.get("improve") != null ? dataMap
				.get("improve") : "");// 需要改进
		qcb.setVacQcbaseComment(dataMap.get("comment") != null ? dataMap
				.get("comment") : "");// 改进建议
		qcb.setVacQcbaseMemo(dataMap.get("memo") != null ? dataMap
				.get("memo") : "");// 备注
		qcb.setVacLawId(dataMap.get("hwNum"));// 律师工号
		qcb.setVacLawyerName(dataMap.get("userName"));// 律师工号
		qcb.setVacQcbaseName(dataMap.get("userName"));// 质检员
		qcb.setDateQcbaseTime(new Date(System.currentTimeMillis()));//质检日期
		list.add(qcb);
	}
}
}
