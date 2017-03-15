package com.fabao.ledger.common.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.fabao.ledger.common.enums.zjType;
import com.fabao.ledger.common.utils.CommonField;
import com.fabao.ledger.modules.tb.entity.TbLedger;
import com.fabao.ledger.modules.tb.entity.TbOrders;
import com.fabaoframework.modules.utils.DateUtils;


public class ViewExcel extends AbstractExcelView{
	
    
	public void buildExcelDocument(Map model, HSSFWorkbook workbook,   
            HttpServletRequest request, HttpServletResponse response)   
            throws Exception { 
    	  Integer type = (Integer) model.get("type");
		  if(type.equals(CommonField.LEDGER_EXPORT_EXCEL_TYPE)){
			  ledgerExportExecl(workbook, model);
		  }else if(type.equals(CommonField.ORDER_EXPORT_EXCEL_TYPE)){
			  orderExportExecl(workbook, model);
		  }
    }
    public void orderExportExecl(HSSFWorkbook workbook, Map model) {
		List<TbOrders> dtos = (List) model.get("list");
		int count = 0;
		if(dtos != null && dtos.size()>0){
			count = dtos.size();
		}
		HSSFSheet sheet = workbook.createSheet("工单列表");   
        HSSFCell cell = getCell(sheet, 0, 0);   
        setText(cell, "工单列表");   
        getCell(sheet, 1, 0).setCellValue("日期："+DateUtils.getDateTime());   
        getCell(sheet, 1, 4).setCellValue("总数："+count);  
        
        getCell(sheet, 2, 0).setCellValue("编号");  
        getCell(sheet, 2, 1).setCellValue("工单产生时间");  
        getCell(sheet, 2, 2).setCellValue("制单人工号");  
        getCell(sheet, 2, 3).setCellValue("制单员");  
        getCell(sheet, 2, 4).setCellValue("客户姓名");  
        getCell(sheet, 2, 5).setCellValue("性别"); 
        getCell(sheet, 2, 6).setCellValue("手机/电话"); 
        getCell(sheet, 2, 7).setCellValue("来电归属"); 
        getCell(sheet, 2, 8).setCellValue("咨询人类别"); 
        getCell(sheet, 2, 9).setCellValue("证件类别"); 
        getCell(sheet, 2, 10).setCellValue("证件号码"); 
        getCell(sheet, 2, 11).setCellValue("业务类型"); 
        getCell(sheet, 2, 12).setCellValue("工单标题"); 
        getCell(sheet, 2, 13).setCellValue("工单类型"); 
        getCell(sheet, 2, 14).setCellValue("任务类型"); 
        getCell(sheet, 2, 15).setCellValue("联系地址"); 
        getCell(sheet, 2, 16).setCellValue("常住地址"); 
        getCell(sheet, 2, 17).setCellValue("事发地址"); 
        getCell(sheet, 2, 18).setCellValue("事项说明"); 
        getCell(sheet, 2, 19).setCellValue("处理意见"); 
        getCell(sheet, 2, 20).setCellValue("接受区域"); 
        getCell(sheet, 2, 21).setCellValue("初审结果"); 
        getCell(sheet, 2, 22).setCellValue("初审人"); 
        getCell(sheet, 2, 23).setCellValue("初审时间"); 
        int row = 3;
		if(dtos != null && dtos.size() > 0){
			for(TbOrders dto : dtos ){
		        HSSFRow sheetRow = sheet.createRow(row);   
		        sheetRow.createCell(0).setCellValue(dto.getId());//编号  
		        sheetRow.createCell(1).setCellValue(dto.getGmtCreatedString());//工单产生时间  
		        sheetRow.createCell(2).setCellValue(dto.getVacOrderAgentCode());//制单人工号  
		        sheetRow.createCell(3).setCellValue(dto.getVacOrderAgentName());//制单员  
		        //判断clients是否为空
		        if(null != dto.getTbClients() && !dto.getTbClients().equals("")){
		        	sheetRow.createCell(4).setCellValue(dto.getTbClients().getVacClientName());//客户姓名  
		        	sheetRow.createCell(5).setCellValue(getGender(model, dto));//性别
		        	sheetRow.createCell(7).setCellValue(getCallArea(dto.getVacCallAreaProvinceName(),dto.getVacCallAreaCityName()));//来电归属
		        	sheetRow.createCell(8).setCellValue(getType(model, dto));//咨询人类别
			        sheetRow.createCell(9).setCellValue(getzjType(dto));//证件类别
			        sheetRow.createCell(10).setCellValue(getzjCode(dto));//证件号码
			        sheetRow.createCell(16).setCellValue(dto.getTbClients().getVacClientAddress());//联系地址
		        }
		        sheetRow.createCell(6).setCellValue(dto.getVacOrderNumber());//手机
		        sheetRow.createCell(11).setCellValue(dto.getVacOrderBusinessName());//业务类型
		        sheetRow.createCell(12).setCellValue(dto.getVacOrderTitle());//工单标题
		        sheetRow.createCell(13).setCellValue(getOrderType(model, dto));//工单类型
		        sheetRow.createCell(14).setCellValue(getTaskType(model, dto));//任务类型
		        sheetRow.createCell(15).setCellValue(dto.getVacOrderContactAddress());//常住地址
		        sheetRow.createCell(17).setCellValue(dto.getVacOrderIncidentAddress());//事发地址
		        sheetRow.createCell(18).setCellValue(dto.getVacOrderAccount());//事项说明
		        sheetRow.createCell(19).setCellValue(dto.getVacOrderSuggestion());//处理意见
		        sheetRow.createCell(20).setCellValue(dto.getVacOrderReceiveProvinceName()+"-"+dto.getVacOrderReceiveCityName());//接收区域
		    	sheetRow.createCell(21).setCellValue(getStatus(model, dto));//初审结果
		        sheetRow.createCell(22).setCellValue(dto.getModifierName());//初审人
		        sheetRow.createCell(23).setCellValue(dto.getGmtModifiedString());//初审时间
		        row ++;
			}
		}
	}
	

	public void ledgerExportExecl(HSSFWorkbook workbook, Map model) {
		List<TbLedger> dtos = (List) model.get("list");
		int count = 0;
		if(dtos != null && dtos.size()>0){
			count = dtos.size();
		}
		HSSFSheet sheet = workbook.createSheet("台帐列表");   
        HSSFCell cell = getCell(sheet, 0, 0);   
        setText(cell, "台帐列表");   
        getCell(sheet, 1, 0).setCellValue("日期："+DateUtils.getDateTime());   
        getCell(sheet, 1, 1).setCellValue("总数："+count); 
        getCell(sheet, 2, 0).setCellValue("编号");  
        getCell(sheet, 2, 1).setCellValue("来电时间");  
        getCell(sheet, 2, 2).setCellValue("客户姓名");  
        getCell(sheet, 2, 3).setCellValue("性别"); 
        getCell(sheet, 2, 4).setCellValue("来电归属"); 
        getCell(sheet, 2, 5).setCellValue("电话"); 
        getCell(sheet, 2, 6).setCellValue("证件类别"); 
        getCell(sheet, 2, 7).setCellValue("证件号码"); 
        getCell(sheet, 2, 8).setCellValue("咨询人类别"); 
        getCell(sheet, 2, 9).setCellValue("业务类型一级"); 
        getCell(sheet, 2, 10).setCellValue("业务类型二级"); 
        getCell(sheet, 2, 11).setCellValue("业务类型三级"); 
        getCell(sheet, 2, 12).setCellValue("业务类型四级"); 
        getCell(sheet, 2, 13).setCellValue("通话时长(秒)"); 
        getCell(sheet, 2, 14).setCellValue("话务员"); 
        getCell(sheet, 2, 15).setCellValue("来电队列"); 
        getCell(sheet, 2, 16).setCellValue("客户自述"); 
        getCell(sheet, 2, 17).setCellValue("处理意见"); 
        getCell(sheet, 2, 18).setCellValue("处理情况"); 
        getCell(sheet, 2, 19).setCellValue("是否有工单"); 
        
        int row = 3;
		
		if(dtos != null && dtos.size() > 0){
			for(TbLedger dto : dtos ){
		        HSSFRow sheetRow = sheet.createRow(row);   
		        //判断clients是否为空
		        if(null != dto.getTbClients() && !dto.getTbClients().equals("")){
		        	sheetRow.createCell(0).setCellValue(dto.getId());//编号
		        	sheetRow.createCell(1).setCellValue(dto.getGmtCreatedString());//来电时间
		        	sheetRow.createCell(2).setCellValue(dto.getTbClients().getVacClientName());//客户姓名  
		        	sheetRow.createCell(3).setCellValue(getGender(model, dto));//性别
			        sheetRow.createCell(6).setCellValue(getzjType(dto));//证件类别
			        sheetRow.createCell(7).setCellValue(getzjCode(dto));//证件号码
			        sheetRow.createCell(8).setCellValue(getType(model, dto));//咨询人类别
		        }
		        sheetRow.createCell(4).setCellValue(dto.getVacLedgerProvinceName()+"-"+dto.getVacLedgerCityName());//来电归属
		        sheetRow.createCell(5).setCellValue(dto.getVacLedgerNumber());//手机
		        sheetRow.createCell(9).setCellValue(dto.getVacLedgerBusinessName1());//业务类型一级
		        sheetRow.createCell(10).setCellValue(dto.getVacLedgerBusinessName2());//业务类型二级
		        sheetRow.createCell(11).setCellValue(dto.getVacLedgerBusinessName3());//业务类型三级
		        sheetRow.createCell(12).setCellValue(dto.getVacLedgerBusinessName4());//业务类型四级
		        sheetRow.createCell(13).setCellValue(dto.getVacLedgerTalkDur());//通话时长
		        sheetRow.createCell(14).setCellValue(dto.getVacLedgerAgentName());//话务员姓名
		        sheetRow.createCell(15).setCellValue(getQueue(model, dto));//来电队列
		        sheetRow.createCell(16).setCellValue(dto.getVacLedgerClientAccount());//客户自述
		        sheetRow.createCell(17).setCellValue(dto.getVacLedgerLawyerSuggestion());//处理意见
		        sheetRow.createCell(18).setCellValue(getHandle(model, dto));//处理情况
		        sheetRow.createCell(19).setCellValue(dto.getIsOrder() == 0 ? "否":"是");//是否有工单
		        row ++;
			}
		}
	}
	
	
	private String getCallArea(String provinceName,String cityName){
		
		if(provinceName == null){
			provinceName="";
		}
		
		if(cityName == null){
			cityName="";
		}
		
		return provinceName+"-"+cityName;
	}

	
	private String getQueue(Map model, TbLedger dto) {
		String queue ="";
		if(null != dto.getNumLedgerCallQueue() && dto.getNumLedgerCallQueue().equals("0")){
			List<Map<String, Object>> queueList = (List<Map<String, Object>>) model.get("queueList");
			for(Map<String, Object> map :queueList){
				String v = map.get("value").toString();
				String t = dto.getNumLedgerCallQueue().toString();
				if(t.equals(v)){
					queue = map.get("label").toString();
					break;
				}
			}
		}else{
			queue = dto.getNumLedgerCallQueue();
		}
		return queue;
	}
		
	
	private String getHandle(Map model, TbLedger dto) {
		String handle = "";
		if(dto.getNumLedgerHandle() != null){
			List<Map<String, Object>> handleList = (List<Map<String, Object>>) model.get("handleList");
			for(Map<String, Object> map :handleList){
				String v = map.get("value").toString();
				String t = dto.getNumLedgerHandle().toString();
				if(t.equals(v)){
					handle = map.get("label").toString();
					break;
				}
			}
		}
		return handle;
	}
	
	private String getzjType(TbLedger dto) {
		 String t1 = "";
		 if(null !=dto.getTbClients().getVacClientIdentityCode() && dto.getTbClients().getVacClientIdentityCode() != ""){
			t1 = zjType.IDENTITY.getName();
		 }else  if(null != dto.getTbClients().getVacClientMilitaryCode() && dto.getTbClients().getVacClientMilitaryCode() != ""){
			t1 = zjType.MILITARY.getName();//证件类别
		 }else  if(null != dto.getTbClients().getVacClientEepCode() && dto.getTbClients().getVacClientEepCode() != ""){
			t1 = zjType.MOBILE.getName();//证件类别
		 }else  if(null != dto.getTbClients().getVacClientPassportCode() && dto.getTbClients().getVacClientPassportCode() != ""){
			t1 = zjType.PASSPORT.getName();//证件类别
		 }
		 return t1;
	}
	
	private String getzjCode(TbLedger dto) {
		 String t2 = "";
		 if(null !=dto.getTbClients().getVacClientIdentityCode() && dto.getTbClients().getVacClientIdentityCode() != ""){
			t2 = dto.getTbClients().getVacClientIdentityCode();
		 }else  if(null != dto.getTbClients().getVacClientMilitaryCode() && dto.getTbClients().getVacClientMilitaryCode() != ""){
			t2 = dto.getTbClients().getVacClientMilitaryCode();
		 }else  if(null != dto.getTbClients().getVacClientEepCode() && dto.getTbClients().getVacClientEepCode() != ""){
			t2 = dto.getTbClients().getVacClientEepCode();
		 }else  if(null != dto.getTbClients().getVacClientPassportCode() && dto.getTbClients().getVacClientPassportCode() != ""){
			t2 = dto.getTbClients().getVacClientPassportCode();
		 }
		 return t2;
	}
	
	private String getType(Map model, TbLedger dto) {
		String type = "";
		if(dto.getTbClients() != null && dto.getTbClients().getNumClientType() !=null){
			List<Map<String, Object>> typeList = (List<Map<String, Object>>) model.get("typeList");
			for(Map<String, Object> map :typeList){
				String v = map.get("value").toString();
				String t = dto.getTbClients().getNumClientType().toString();
				if(t.equals(v)){
					type = map.get("label").toString();
					break;
				}
			}
		}
		return type;
	}
	
	private String getGender(Map model, TbLedger dto) {
		String gender = "";
		if(dto.getTbClients() != null && dto.getTbClients().getNumClientGender() !=null){		
			List<Map<String, Object>> genderList = (List<Map<String, Object>>) model.get("genderList");
			for(Map<String, Object> map :genderList){
				String v = map.get("value").toString();
				String g = dto.getTbClients().getNumClientGender().toString();
				if(g.equals(v)){
					gender =  map.get("label").toString();
					break;
				}
			}
		}
		return gender;
	}  

	private String getzjType(TbOrders dto) {
		 String t1 = "";
		 if(null !=dto.getTbClients().getVacClientIdentityCode() && dto.getTbClients().getVacClientIdentityCode() != ""){
			t1 = zjType.IDENTITY.getName();
		 }else  if(null != dto.getTbClients().getVacClientMilitaryCode() && dto.getTbClients().getVacClientMilitaryCode() != ""){
			t1 = zjType.MILITARY.getName();//证件类别
		 }else  if(null != dto.getTbClients().getVacClientEepCode() && dto.getTbClients().getVacClientEepCode() != ""){
			t1 = zjType.MOBILE.getName();//证件类别
		 }else  if(null != dto.getTbClients().getVacClientPassportCode() && dto.getTbClients().getVacClientPassportCode() != ""){
			t1 = zjType.PASSPORT.getName();//证件类别
		 }
		 return t1;
	}
	
	private String getzjCode(TbOrders dto) {
		 String t2 = "";
		 if(null !=dto.getTbClients().getVacClientIdentityCode() && dto.getTbClients().getVacClientIdentityCode() != ""){
			t2 = dto.getTbClients().getVacClientIdentityCode();
		 }else  if(null != dto.getTbClients().getVacClientMilitaryCode() && dto.getTbClients().getVacClientMilitaryCode() != ""){
			t2 = dto.getTbClients().getVacClientMilitaryCode();
		 }else  if(null != dto.getTbClients().getVacClientEepCode() && dto.getTbClients().getVacClientEepCode() != ""){
			t2 = dto.getTbClients().getVacClientEepCode();
		 }else  if(null != dto.getTbClients().getVacClientPassportCode() && dto.getTbClients().getVacClientPassportCode() != ""){
			t2 = dto.getTbClients().getVacClientPassportCode();
		 }
		 return t2;
	}
	
	private String getType(Map model, TbOrders dto) {
		String type = "";
		if(dto.getTbClients() != null && dto.getTbClients().getNumClientType() !=null){	
			List<Map<String, Object>> typeList = (List<Map<String, Object>>) model.get("typeList");
			for(Map<String, Object> map :typeList){
				String v = map.get("value").toString();
				String t = dto.getTbClients().getNumClientType().toString();
				if(t.equals(v)){
					type = map.get("label").toString();
					break;
				}
			}
		}
		return type;
	}
	
	private String getGender(Map model, TbOrders dto) {
		String gender = "";
		List<Map<String, Object>> genderList = (List<Map<String, Object>>) model.get("genderList");
		for(Map<String, Object> map :genderList){
			String v = map.get("value").toString();
			String g = dto.getTbClients().getNumClientGender().toString();
			if(g.equals(v)){
				gender =  map.get("label").toString();
				break;
			}
		}
		return gender;
	}  
	private String getTaskType(Map model, TbOrders dto) {
		String task = "";
		if(dto.getVacOrderTaskType() != null ){	
			List<Map<String, Object>> handleList = (List<Map<String, Object>>) model.get("taskTypeList");
			for(Map<String, Object> map :handleList){
				String v = map.get("value").toString();
				String t = dto.getVacOrderTaskType().toString();
				if(t.equals(v)){
					task = map.get("label").toString();
					break;
				}
			}
		}
		return task;
	}
	private String getStatus(Map model, TbOrders dto) {
		String status = "";
		if(dto.getNumOrderStatus() != null ){	
			List<Map<String, Object>> handleList = (List<Map<String, Object>>) model.get("statusList");
			for(Map<String, Object> map :handleList){
				String v = map.get("value").toString();
				String t = dto.getNumOrderStatus().toString();
				if(t.equals(v)){
					status = map.get("label").toString();
					break;
				}
			}
		}
		return status;
	}
	private String getOrderType(Map model, TbOrders dto) {
		String orderType = "";
		if(dto.getVacOrderType() != null ){	
			List<Map<String, Object>> handleList = (List<Map<String, Object>>) model.get("orderTypeList");
			for(Map<String, Object> map :handleList){
				String v = map.get("value").toString();
				String t = dto.getVacOrderType().toString();
				if(t.equals(v)){
					orderType = map.get("label").toString();
					break;
				}
			}
		}
		return orderType;
	}
}