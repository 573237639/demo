package com.fabao.ledger.modules.tb.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.fabao.ledger.modules.tb.entity.Reexamine;
import com.fabao.ledger.modules.tb.entity.TbOrders;
import com.fabao.ledger.modules.tb.entity.TbReexamine;
import com.tepper.www.TaskWebserviceImpl;

public class QuartzJob {
	private static final Logger log = Logger.getLogger(QuartzJob.class);
	@Autowired
	private TaskWebserviceImpl taskWebservice;
	@Autowired
	private TbOrdersManager tbOrdersManager;
	@Autowired
	private TbReexamineManager tbReexamineManager;
	 public void work() {
			Properties prop =  new  Properties();    
	        InputStream in = this.getClass().getResourceAsStream("/ordertaskxml.properties"); 
			try {
				prop.load(in);
			} catch (IOException e) {
				log.error("读取配置文件ordertaskxml异常"+e.getMessage());
				e.printStackTrace();
			}
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			Date date = new Date();
			Date beforeDate = new Date(date .getTime() - 1800000);
			String dateStringBegin = sdf.format(beforeDate);
			String dateStringEnd = sdf.format(date);
		    String jsonxml = taskWebservice.getTaskResult(prop.getProperty("source").trim(), dateStringBegin, dateStringEnd);
			//dom解析xml
			if(jsonxml.length() > 0 && jsonxml.indexOf("</xml>") > 0){
				String[] xs = jsonxml.split("</xml>"); 
				for(String x:xs){
					String protocolXML = x+"</xml>";
					Reexamine reexamine = getReexamine(protocolXML);
					TbReexamine tbReexamine = getTbReexamine(reexamine);
					saveTbReexamine(tbReexamine);
				}
			}
		  
		 }
	 

	 
	 	/**
	 	 * 解析xml至对象Reexamine
	 	 * @param protocolXML
	 	 * @return
	 	 */
		public Reexamine  getReexamine(String protocolXML) {   
			
			Reexamine reexamine = new Reexamine(); //新建标签对象 
			
			//开始解析xml文件
			try {    
			 Document doc= DocumentHelper.parseText(protocolXML);    
	         Element root = doc.getRootElement();//获取根节点   
//	         Element source = root.element(Reexamine.SOURCE);
//	         reexamine.setSource(source!=null?source.getTextTrim():"");//接收系统注册编号 20电话平台 30 网络平台 40 OA
	         Element sourceid = root.element(Reexamine.SOURCEID);
	         reexamine.setSourceid(sourceid!=null?sourceid.getTextTrim():"");//接收系统任务id 即 工单id
	         TbOrders tborder = tbOrdersManager.getById(Long.parseLong(reexamine.getSourceid()));
	         if(null!=tborder){
		         reexamine.setOrderAuditTime(tborder.getGmtModifiedString()); //获取工单初审时间
		         reexamine.setOrderTitle(tborder.getVacOrderTitle());//工单标题
		         reexamine.setVacName(tborder.getVacOrderAgentName());//制单员姓名
		         reexamine.setVacNumber(tborder.getVacOrderNumber());//电话号码
	         }
	         Element opinion = root.element(Reexamine.OPINION);
	         reexamine.setOpinion(opinion!=null?opinion.getTextTrim():"");//处理结果
	         
	         Element completetime = root.element(Reexamine.COMPLETETIME);
	         reexamine.setCompleteTime(completetime!=null?completetime.getTextTrim():"");//完成时限
	         
//	         Element tasktype = root.element(Reexamine.TASKTYPE);
//	         reexamine.setTasktype(tasktype!=null?tasktype.getTextTrim():"");//任务类型，参照督办系统代码对照表
	         
	         Element taskState = root.element(Reexamine.TASKSTATE);
	         reexamine.setTaskState(taskState!=null?taskState.getTextTrim():"");//办结状态 50正常办结 80 终止
	         
//	         if(reexamine.getTaskState().equals(CommonField.REEXAMINE_TASKSTATE_COMMON)){//50 正常状态
//	        	 reexamine.setTaskStateName(CommonField.REEXAMINE_TASKSTATE_COMMONNAME);//复审情况 
//	        	 reexamine.setSituation(CommonField.REEXAMINE_TASKSTATE_SITUATIONREADY);//复审结果
//	         }else if(reexamine.getTaskState().equals(CommonField.REEXAMINE_TASKSTATE_CANCLE)){//80终止
//	        	 reexamine.setTaskStateName(CommonField.REEXAMINE_TASKSTATE_CANCLENAME);
//	        	 reexamine.setSituation(CommonField.REEXAMINE_TASKSTATE_SITUATIONCANCLE);
//	         }
	         
//	         List<Taskdesc> desclist = new ArrayList<Taskdesc>();
//	         Element taskdesc = root.element(Reexamine.TASKDESC);
//	         if(null!=taskdesc){
//	         List list = taskdesc.elements(Taskdesc.ITEM);
//	         for (int i = 0; i < list.size(); i++) {  
//	        	 Item item = new Item();
//	        	 Taskdesc t = new Taskdesc();
//	             Element items = (Element) list.get(i);    
//	             Element name = items.element(Taskdesc.NAME);    
//	             Element content = items.element(Taskdesc.CONTENT); 
//	             t.setName(name!=null?name.getTextTrim():"");
//	             t.setContent(content!=null?content.getTextTrim():"");
//	             t.setItem(item);
//	             desclist.add(t); 
//	         }    
//	         }
//	         reexamine.setTaskdesc(desclist);//
//	         
//	         List<TaskTodoResult> taskTodoList = new ArrayList<TaskTodoResult>();
//	         Element taskTodo = root.element(Reexamine.TASKTODO);
//	         if(null!=taskTodo){
//	         List listtodo = taskTodo.elements(TaskTodoResult.ITEM);
//	         for (int i = 0; i < listtodo.size(); i++) {  
//	        	 TaskTodoResult todo = new TaskTodoResult();
//	             Element item = (Element) listtodo.get(i);    
//	             Element taskDoType = item.element(TaskTodoResult.TASKDOTYPE);
//	             Element ename = item.element(TaskTodoResult.ENAME);    
//	             Element cname = item.element(TaskTodoResult.CNAME);
//	             Element content = item.element(TaskTodoResult.CONTENT); 
//	             Element result = item.element(TaskTodoResult.RESULT); 
//	             
//	             Options options = new Options();
//	             Element optionsElement = item.element(TaskTodoResult.OPTIONS);
//	             if(optionsElement !=null){
//	            	 Element option =  optionsElement.element(Options.OPTION);
//	            	 options.setOption(option!=null?option.getTextTrim():"");
//	             }
//	             todo.setOptions(options);
//	             todo.setEname(ename!=null?ename.getTextTrim():"");
//	             todo.setCname(cname!=null?cname.getTextTrim():"");
//	             todo.setContent(content!=null?content.getTextTrim():"");
//	             todo.setTaskDoType(taskDoType!=null?taskDoType.getTextTrim():"");
//	             todo.setResult(result !=null ? result.getTextTrim() : "");
//	             taskTodoList.add(todo);
//	         }    
//	         }
//	         reexamine.setTaskTodo(taskTodoList);//答复内容  审批状态
	         
//	         Element tounitname = root.element(Reexamine.TOUNITNAME);
//	         reexamine.setTounitname(tounitname!=null?tounitname.getTextTrim():"");//任务接收者主体名称
	         
//	         List<FileList> fileList = new ArrayList<FileList>();
//	         Element fileElement = root.element(Reexamine.FILELIST);
//	         if(null!=fileElement){
//	         List listfile = fileElement.elements(FileList.ITEM);
//	         for (int i = 0; i < listfile.size(); i++) {  
//	        	 FileList file = new FileList();
//	             Element items = (Element) listfile.get(i);    
//	             Element name = items.element(FileList.NAME);    
//	             Element content = items.element(FileList.CONTENT); 
//	             file.setName(name!=null?name.getTextTrim():"");
//	             file.setContent(content!=null?content.getTextTrim():"");
//	             fileList.add(file);
//	         }    
//	         }
//	         reexamine.setFileList(fileList);//附件列表
	 		} catch (DocumentException e) {    
	        	log.error("获取复审结果异常"+e.getMessage());
	        } 
			return reexamine;
		}
	 
	 /**
	  * 返回复审结果实体对象
	  * @param reexamine
	  * @return
	  */
	 public TbReexamine getTbReexamine(Reexamine reexamine){
		TbReexamine tbReexamine = new TbReexamine();//新建返回实体对象
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		tbReexamine.setNumReexamineSourceid(Integer.parseInt(reexamine.getSourceid()));//工单id
		tbReexamine.setVacReexamineOpinion(reexamine.getOpinion());//处理结果
		tbReexamine.setVacReexamineName(reexamine.getVacName());
		tbReexamine.setVacReexamineNumber(reexamine.getVacNumber());
		Date completeTime = null;
		Date orderAuditTime = null;
		try {
			if(null != reexamine.getCompleteTime() && !reexamine.getCompleteTime().equals("")){
				completeTime = sdf.parse(reexamine.getCompleteTime());
				tbReexamine.setDateCompleteTime(completeTime);//完成时间	
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("复审结果完成时间异常",e);
		}
		try {
			if(null != reexamine.getOrderAuditTime() && !reexamine.getOrderAuditTime().equals("")){
			orderAuditTime = sdf.parse(reexamine.getOrderAuditTime());
			tbReexamine.setDateOrderAudit(orderAuditTime); //获取工单初审时间
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("复审结果工单初审时间异常",e);
		}
		
		tbReexamine.setNumReexamineTaskstate(Integer.parseInt(reexamine.getTaskState()));//复审结果编码
		tbReexamine.setVacOrderTitle(reexamine.getOrderTitle());//工单标题
		return tbReexamine;
	}
	 
	 public void saveTbReexamine(TbReexamine tbReexamine){
		 TbReexamine entity = new TbReexamine();
         entity.setNumReexamineSourceid(tbReexamine.getNumReexamineSourceid());
         List<TbReexamine> list =  tbReexamineManager.getByEntity(entity);
         if(null==list || list.size()==0){
        	 try {
				tbReexamineManager.save(tbReexamine);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("保存复审结果异常",e);
			}
         }

	 }
	 

	 
	 
}
