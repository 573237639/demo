<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script>
var typeList = ${typeList};
var genderList = ${genderList};
var orderTypeList = ${orderTypeList};
var taskTypeList = ${taskTypeList};
var statusTypeList = ${statusTypeList};
	var loadGrid = function() {

		$("#callorderlist-datagrid").datagrid({
			striped:true,
	  		checkOnSelect:true ,
	  		fitColumns:true,
			height:"auto",
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},  {
				field : 'id',
				title : 'ID',
				hidden:true
			}, {
				field : 'vacClientName',
				title : '客户姓名',
				width : 100,
				align : "center",
				formatter:function(value,row,index){
			  		 return row.tbClients.vacClientName;
			  	 }
			},{
				field:'numClientGender',title:'性别', width:60,align:"center",formatter:function(value,row,index){
		  		var c = row.tbClients;
		  		if(c == undefined || c == "") return;
		  		var v = c.numClientGender;
		  		for(var data in genderList){
		  			if(v == genderList[data].value){
		  				return genderList[data].label;
		  			}
		  		}
		  	 }
			},  {
				field : 'vacOrderNumber',
				title : '手机/电话',
				width : 80,
				align : "center"
			}, {
				field : 'zjType',
				title : '证件类别',
				width : 70,
				align : "center",formatter:function(value,row,index){
			  		 if(null !=row.tbClients.vacClientIdentityCode && row.tbClients.vacClientIdentityCode != ""){
			  			 return "身份证";
			  		 }else  if(null != row.tbClients.vacClientMilitaryCode && row.tbClients.vacClientMilitaryCode != ""){
			  			 return "军官证";
			  		 }else  if(null != row.tbClients.vacClientEepCode && row.tbClients.vacClientEepCode != ""){
			  			 return "港澳台通行证";
			  		 }else  if(null != row.tbClients.vacClientPassportCode && row.tbClients.vacClientPassportCode != ""){
			  			 return "外国籍护照";
			  		 }else{
			  			 return "";
			  		 }
			  	 }
			}, {
				field : 'zjCode',
				title : '证件号码',
				width : 100,
				align : "center",formatter:function(value,row,index){
			  		 if(null !=row.tbClients.vacClientIdentityCode && row.tbClients.vacClientIdentityCode != ""){
			  			 return row.tbClients.vacClientIdentityCode;
			  		 }else  if(null != row.tbClients.vacClientMilitaryCode && row.tbClients.vacClientMilitaryCode != ""){
			  			 return row.tbClients.vacClientMilitaryCode;
			  		 }else  if(null != row.tbClients.vacClientEepCode && row.tbClients.vacClientEepCode != ""){
			  			 return row.tbClients.vacClientEepCode;
			  		 }else  if(null != row.tbClients.vacClientPassportCode && row.tbClients.vacClientPassportCode != ""){
			  			 return row.tbClients.vacClientPassportCode;
			  		 }else{
			  			 return "";
			  		 }
			  	 }
			},{field:'numClientType',title:'咨询人类别', width:100,align:"center",formatter:function(value,row,index){
	  		 	var v = row.tbClients.numClientType;
		  		if(v == undefined || v == "") return;
		  		for(var data in typeList){
		  			if(v == typeList[data].value){
		  				return typeList[data].label;
		  			}
		  		}
	  	 }},  {
				field : 'vacOrderBusinessName',
				title : '业务类型',
				width : 220,
				align : "center"
			},{
				field : 'vacOrderAgentName',
				title : '制单人',
				width : 60,
				align : "center"
			},  {
				field : 'gmtCreated',
				title : '工单产生时间',
				width : 150,
				align : "center",formatter:function(value,row,index){
			  		 if(null !=row.gmtCreated && row.gmtCreated != ""){
			  			 return row.gmtCreatedString;
			  		 }else{
			  			 return "";
			  		 }
			  	 }
			}, {
				field : 'vacOrderType',
				title : '工单类型',
				width : 80,
				align : "center",formatter:function(value,row,index){
			  		if(value==undefined) return;
			  		for(var data in orderTypeList){
			  			if(value == orderTypeList[data].value){
			  				return orderTypeList[data].label;
			  			}
			  		}
			  	 }
			}, {
				field : 'vacOrderTaskType',
				title : '任务类型',
				width : 100,
				align : "center",formatter:function(value,row,index){
			  		if(value==undefined) return;
			  		for(var data in taskTypeList){
			  			if(value == taskTypeList[data].value){
			  				return taskTypeList[data].label;
			  			}
			  		}
			  	 }
			}, {
				field : 'numOrderStatus',
				title : '初审状态',
				width : 80,
				align : "center",formatter:function(value,row,index){
			  		if(value==undefined) return;
			  		for(var data in statusTypeList){
			  			if(value == statusTypeList[data].value){
			  				return statusTypeList[data].label;
			  			}
			  		}
			  	 }
			},
			{field:'dis',title:'操作',width:60,align:"center",formatter:function(value,row,index){
  	  			var btns = "";
  	  			if(row.numOrderStatus==0){
  	  				btns += '<permission:hasPermission action="tb/call/callorderview-index"><a href="javascript:orderViewIndex('+row.id+');"><font style="color:red">查看</font></a></permission:hasPermission>&nbsp;'
  	  			}else{
  	  				btns += '<permission:hasPermission action="tb/call/callorderview-index"><a href="javascript:orderViewIndex('+row.id+');"><font style="color:red">查看</font></a></permission:hasPermission>&nbsp;'
  	  			}
				return btns;
  	  		}}] ],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"callorderListDatagrid/"+serial,
	            checkbox: "true", 
	            data:$("#callorderlist-from").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".order_call_view").hide();
	            		$(".order_call_datanull").show();
	            	}else{
	            		$(".order_call_view").show();
	            		$(".order_call_datanull").hide();
	            	}
	                success(data);
	            }
	        })
	    }
		});
	}
	setTimeout(loadGrid, 1000);
	
	function orderViewIndex(id){
		$('#tt').tabs('add',{    
		    title:'工单查看'+id,    
	 	    href:"${ctx}/tb/call/callorderview-index/"+id, 
	 	    closable:true
		}); 
	}
	
	
</script>
	<div class="easyui-panel" style="padding:4px">
	<div class="order_call_view">
		<table class="easyui-datagrid" id="callorderlist-datagrid" 	style="width: 100%;" pagination="true">
		</table>
	</div>
	<div class="order_call_datanull" style="display: none;text-align:center;">
		暂无结果
	</div>
	</div>