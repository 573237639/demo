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
			columns : [ [ 
			{
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
			}] ],
  		loader:function(param,success,error){
  			param.vacOrderBusinessType = $("#vacOrderBusinessType").combobox("getValue");
  			param.numOrderStatus = $("#numOrderStatus").textbox("getValue");
  			param.vacOrderNumber = $("#vacLedgerNumber").val();
	    	$.ajax({
	            url:"${ctx}/tb/call/callorder-datagrid",
	            checkbox: "true", 
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".call_order_view").hide();
	            		$(".call_order_datanull").show();
	            	}else{
	            		$(".call_order_view").show();
	            		$(".call_order_datanull").hide();
	            	}
	                success(data);
	            }
	        })
	    }
		});
	}
	setTimeout(loadGrid, 1000);
</script>
<script type="text/javascript">
	//点击搜索	
	function submitForm() {
		$("#searchOrder").linkbutton('disable');
		$.messager.progress();	// 显示进度条
		$("#callorderlist-datagrid").datagrid('load');
		$.messager.progress("close");	// 关闭进度条
    	$("#searchOrder").linkbutton('enable');
	}

</script>
	<div class="easyui-panel" style="padding:4px">
<br/>
		<form id="callorderlist-from" method="post" action=""  modelAttribute="tbOrders">
				<div >
					<span class="div_margin_left">业务类型</span>
					<input class="easyui-combotree"  style="width:300px;height:28px;" id="vacOrderBusinessType" name="vacOrderBusinessType" editable="false"
					data-options="editable:false,valueField:'id',width:200,panelHeight:300,textField:'text',url:'${ctx}/sys/specialty/findAllBusinessType'" />
						
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="div_margin_left">初审状态</span>
					<input class="easyui-combobox combobox_input" id="numOrderStatus"　name="numOrderStatus"  style="width:100px;height:28px;" editable="false" 
						data-options="valueField:'value',width:200,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/status_type'" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:submitForm();" id="searchOrder"  iconCls="icon-search"  class="easyui-linkbutton a_button">搜	索</a>
				</div>
		</form>
		<br/>
		<div class="call_order_view">
		<table class="easyui-datagrid" id="callorderlist-datagrid" 	style="width: 100%;" pagination="true">
		</table>
					</div>
			<div class="call_order_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
	</div>