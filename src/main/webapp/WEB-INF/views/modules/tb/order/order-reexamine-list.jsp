<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script>
var loadGrid = function(){
	$("#reexamine-order-datagrid").datagrid({
		striped:true,
  		checkOnSelect:false ,
  		fitColumns:true,
	    columns:[[
		  	 {field:'dateOrderAuditString',title:'工单初审时间', width:200,align:"center"}, 
		  	 {field:'vacOrderTitle',title:'工单标题', width:150,align:"center"},  
		  	{field:'vacReexamineName',title:'姓名', width:150,align:"center"},
		  	{field:'vacReexamineNumber',title:'电话号码', width:150,align:"center"},
		  	 {field:'vacTaskStateName',title:'复审结果', width:160,align:"center",formatter:function(value,row,index){
		  		 if(row.numReexamineTaskstate == <%=CommonField.REEXAMINE_TASKSTATE_COMMON%>){
		  			 return "<%=CommonField.REEXAMINE_TASKSTATE_COMMONNAME%>";
		  		 }else if(row.numReexamineTaskstate == <%=CommonField.REEXAMINE_TASKSTATE_CANCLE%>){
		  			 return "<%=CommonField.REEXAMINE_TASKSTATE_CANCLENAME%>";
		  		 }else{
		  			 return "";	 
		  		 }
		  	 }},  
		  	 {field:'vacSituation',title:'复审情况', width:100,align:"center",formatter:function(value,row,index){
		  		if(row.numReexamineTaskstate == <%=CommonField.REEXAMINE_TASKSTATE_COMMON%>){
		  			 return "<%=CommonField.REEXAMINE_TASKSTATE_SITUATIONREADY%>";
		  		}else if(row.numReexamineTaskstate == <%=CommonField.REEXAMINE_TASKSTATE_CANCLE%>){
		  			 return "<%=CommonField.REEXAMINE_TASKSTATE_SITUATIONCANCLE%>";
		  		 }else{
		  			 return "";	 
		  		 }
		  	 }}, 
		  	 {field:'dateCompleteTimeString',title:'复审时间', width:100,align:"center"},   
		  	 {field:'vacReexamineOpinion',title:'处理结果', width:150,align:"center"},
		  	 {field:'dic',title:'操作', width:80,align:"center",formatter:function(value,row,index){
	  	  			var btns =  '<a href="javascript:showOpinion(\''
	  	  				+ row.vacOrderTitle + '\',\''
	  	  				+ row.vacReexamineOpinion + '\');" class="lkbtn" ><span style="color:red">详情</span></a>&nbsp;'
					return btns;
	  	  		}}
  		]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
//   			param.trialTimeStart = $("#trialTimeStart").textbox("getValue");
//   			param.trialTimeEnd = $("#trialTimeEnd").textbox("getValue");
	    	$.ajax({
	            url:"${ctx}/tb/reexamine/tbReexamineListDataGrid",
// 	            data:param,
				data:$("#reexamine-order-from").serialize(),	
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.code  == 1){
	            		$.messager.alert('提示', '原因：' + data.message);
	            		$(".order_reexamine_view").hide();
	            		$(".order_reexamine_datanull").show();
	            	}else if(data.total == 0){
	            		$(".order_reexamine_view").hide();
	            		$(".order_reexamine_datanull").show();
	            	}else{
	            		$(".order_reexamine_view").show();
	            		$(".order_reexamine_datanull").hide();
	            	}
	                success(data);
	            }
	        })
  		}
	});
}
setTimeout(loadGrid,1000);
</script>

<script type="text/javascript">
	//点击搜索	
	function searchReexamineOrder() {
		
		//做时间校验
		var trialTimeStart = $("#trialTimeStart").textbox("getValue");
		var trialTimeEnd = $("#trialTimeEnd").textbox("getValue"); 
		if(trialTimeStart != '' && trialTimeEnd !=''){
			if(trialTimeStart > trialTimeEnd){
				$.messager.alert("消息提醒","结束时间不能小于开始时间","warning");
				return;
			}
		}
		//做表单验证
		//验证表单
		var validate = $("#reexamine-order-from").form("validate");
		if(!validate){
			$.messager.alert("消息提醒","工单初审时间!","warning");
			return;
		} else{
			$("#searchReexamineOrder").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#reexamine-order-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchReexamineOrder").linkbutton('enable');
		}
	}
	
	function showOpinion(title,opinion){
		$.messager.show({
			title:'内容详情',
			msg:'<span style="color:red">工单标题：</span>'+title+'<br/><span style="color:red">处理结果：</span>'+opinion+'<br/>',
			showType:'fade',
			width:400,
			height:300,
			timeout:0,
			style:{
				right:'',
				bottom:''
			}
		});
	}
	
	
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="reexamine-order-from" method="post" action=""  modelAttribute="tbReexamine">
		<input type="hidden" id="page" name="page">
		<input type="hidden" id="rows" name="rows">
			<br/>
				<br/>
				<div >
					<span class="div_margin_left">工单初审时间</span>
				   	<input class="easyui-datetimebox" id="trialTimeStart" name="trialTimeStart"  editable="false" style="width:200px;height:26px;">
    				<input class="easyui-datetimebox" id="trialTimeEnd" name="trialTimeEnd"   editable="false"  style="width:200px;height:26px;">
					<span class="div_margin_left">姓名</span>
					 <input value="${tbReexamine.vacReexamineName}" name="vacReexamineName" class="easyui-textbox textbox_input" validType="length[0,50]"  />
					<span class="div_margin_left">电话号码</span>
					 <input value="${tbReexamine.vacReexamineNumber}" name="vacReexamineNumber" class="easyui-textbox textbox_input" validType="length[0,20]"  />
					
   					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchReexamineOrder();" id="searchReexamineOrder"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
   				</div>
				<br/>
			<br/>
		</form>
			<div class="order_reexamine_view">
				<table class="easyui-datagrid" id="reexamine-order-datagrid" 	style="width: 100%;" pagination="true">
				</table>
				<br/>
				<br/>
			</div>
			<div class="order_reexamine_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
	</div>
