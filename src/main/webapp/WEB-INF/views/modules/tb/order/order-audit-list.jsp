<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	
<script>
var typeList = ${typeList};
var genderList = ${genderList};
var orderTypeList = ${orderTypeList};
var taskTypeList = ${taskTypeList};
var statusTypeList = ${statusTypeList};
var businessTypeList = ${businessTypeList};
	var loadGrid = function() {
		$("#orderlist-datagrid").datagrid({
			striped:true,
	  		checkOnSelect:true ,
	  		fitColumns:true,
			height:"auto",
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
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
			}, {
				field : 'vacOrderNumber',
				title : '手机/电话',
				width : 100,
				align : "center"
			}, {
				field : 'zjType',
				title : '证件类别',
				width : 100,
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
				width : 150,
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
			}, {
				field : 'numClientType',
				title : '咨询人类别',
				width : 100,
				align : "center",formatter:function(value,row,index){
		  		 	var v = row.tbClients.numClientType;
			  		if(v == undefined || v == "") return;
			  		for(var data in typeList){
			  			if(v == typeList[data].value){
			  				return typeList[data].label;
			  			}
			  		}
		  	 }
			}, {
				field : 'vacOrderBusinessName',
				title : '业务类型',
				width : 220,
				align : "center"
			
			}, {
				field : 'vacOrderAgentName',
				title : '制单人',
				width : 100,
				align : "center"
			}, {
				field : 'gmtCreatedString',
				title : '工单产生时间',
				width : 150,
				align : "center"
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
				field : 'numAllotUserId',
				title : '分配状态',
				width : 80,
				align : "center",formatter:function(value,row,index){
			  		if(value==undefined) return;
			  		if(row.numAllotUserId==null||row.numAllotUserId==""){
			  			return "待分配";
			  		}else{
			  			return "已分配";
			  		}
			  	 }
			}, {
				field : 'vacAllotUserName',
				title : '分配人',
				width : 80,
				align : "center"
			}, {
				field : 'dateAllotTimeString',
				title : '分配时间',
				width : 80,
				align : "center"
			},{
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
			{field:'dis',title:'操作',width:80,align:"center",formatter:function(value,row,index){
  	  			var btns = "";
  	  				btns += '<permission:hasPermission action="tb/order/order-edit-index"><a href="javascript:editOrder('+row.id+');"><font style="color:red;font-size:14px;font-weight:bold;">审核</font></a></permission:hasPermission>&nbsp;'
				return btns;
  	  		}}] ],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
  			$("#numOrderStatus").val(<%=CommonField.ORDER_STATUS_AUDIT%>);
	    	$.ajax({
	    		url : "${ctx}/tb/order/tbOrdersListDataGrid",
	            data:$("#tbOrdersListForm").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".order_audit_view").hide();
	            		$(".order_audit_datanull").show();
	            	}else{
	            		$(".order_audit_view").show();
	            		$(".order_audit_datanull").hide();
	            	}
	                success(data);
	            }
	        });
	    },rowStyler:function(index,row){
	    	if (compareDate(row.dateOrderTermString) == 1){   
	       	    return 'color:red;height:30px';   
		    }else if (compareDate(row.dateOrderTermString) == 2){
		    		return 'color:orange;height:30px'; 
		    }
		}
		});
	}
	setTimeout(loadGrid, 1000);
	function compareDate(orderDate){
		if(orderDate == undefined || !isNaN(orderDate)){
			return 3;
		}
    	var d1 = new Date();
    	var d2 = new Date();
    	var date = new Date();
    	orderDate = orderDate.replace(/-/g,'/');
    	var o = new Date(orderDate);
		if(o.getTime() < date.getTime()){
			return 1;
		}else if((o.getTime() < addHours(d1,3)) && (o.getTime() > date.getTime())){
			return 2;
		}else if(o.getTime() > addHours(d2,3) ){
			return 3;
		}
	}
	function addHours(date, value) {
        date.setHours(date.getHours() + value);
        return date;
    }
	
	function deleteOrder() {

	     // 返回被选中的行 然后集成的其实是 对象数组  
	     var row = $('#orderlist-datagrid').datagrid('getSelections');  
	     var i = 0;  
	     var ids = "";  
	     if (row.length== 0) { 
	    	 $.messager.alert('提示','请至少选择一条数据!');
	     } 
	  
	     if (row.length>0) {  
	    	 $("#deleteOrder").linkbutton('disable');
	         for(i;i<row.length;i++){  
		     	ids += row[i].id;  
		         if(i < row.length-1){  
		         	ids += ',';  
		         }else{  
		             break;  
		         }  
		     }
	         $.messager.confirm('提示','是否删除所选工单？', function(r) {  
	             if (r) {  
	                 $.post('${ctx}/tb/order/deleteOrder', {  
	                     id : ids  
	                 }, function(result) {  
	                	 if(result.code == 1){
	                		 $.messager.alert('提示',result.message);
	                	 }else{
	                		 $.messager.alert('提示','删除成功');
	                  		 $('#orderlist-datagrid').datagrid('reload'); 
	                	 }
	                	 $("#deleteOrder").linkbutton('enable');
	                 });  
	             }else{
	            	 $("#deleteOrder").linkbutton('enable');
	             }  
	         });  
	     }  
    }  
	//修改
	function editOrder(id){
// 		var row = $('#orderlist-datagrid').datagrid('getSelected');
		window.parent.window.newTab(id,"/tb/order/order-edit-index","工单编辑"+id,"icon-edit");
	}
	//查看
	function viewOrder(id){
// 		var row = $('#orderlist-datagrid').datagrid('getSelected');
		window.parent.window.newTab(id,"/tb/order/order-view-index","工单查看"+id,"icon-blank");
	}
</script>
<div class="easyui-panel" style="padding:4px">
		<div class="order_audit_view">
			<table class="easyui-datagrid" id="orderlist-datagrid"
			style="width: 100%;"  data-options="
				toolbar:'#order-audit-tools'" pagination="true">
			</table>
			<div id="order-audit-tools" style="padding:5px;height:auto">
			<permission:hasPermission action="tb/order/deleteOrder">
				<a href="javascript:deleteOrder();" class="easyui-linkbutton" id="deleteOrder" iconCls="icon-remove" style="color:red;background-color: #BCDCDC;" plain="true">删除工单</a>
			</permission:hasPermission>
			</div>
			<br/>
			<br/>
		</div>
		<div class="order_audit_datanull" style="display: none;text-align:center;">
			暂无结果
		</div>
	
</div>
