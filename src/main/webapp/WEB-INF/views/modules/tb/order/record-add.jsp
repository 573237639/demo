<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	//点击保存追加记录
	function addRecords(){
		$("#orderId").val($("input[name='tbOrders.id']").val());
		if($("#vacSupplementContent").val().trim() == "" ){
			$.messager.alert('提示','请输入追加记录');
			return ;
		}
		$("#addRecords").linkbutton('disable');
		$.messager.progress();	// 显示进度条
		$('#tbSupplementForm').form('submit', {    
			url:"${ctx}/tb/order/addTbSupplementData", 
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	var dataObj= eval('(' + data + ')');
	        	if(dataObj.code == 0){
	        		$.messager.alert('提示','数据保存成功!');
	        		$("#vacSupplementContent").textbox("setValue","");
		        	$("#addRecords").linkbutton('enable');
		        	$("#recordadd-datagrid").datagrid('load');
		        }else{
		        	$.messager.alert('提示','数据保存失败!原因：'+dataObj.message);
		        } 
		    }    
		});  
	}
</script>
<script type="text/javascript">
//给表单赋值
var loadGrid = function(){
	$("#recordadd-datagrid").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
  	  		 {field:'id',title:'ID',width:"5%"},
		  	 {field:'vacSupplementContent',title:'追加内容', width:"60%",align:"center"},                         
		  	 {field:'gmtCreatedString',title:'追加时间', width:"20%",align:"center",formatter:function(value,row,index){
		  		 return row.gmtCreatedString;
		  	 }},                                                                    
		  	 {field:'vacCreaterName',title:'追加人', width:"10%",align:"center"}                                                
  		]],
  		loader:function(param,success,error){ 
  			param.id = $("input[name='tbOrders.id']").val();
	    	$.ajax({
	            url:"${ctx}/tb/order/recordDataGrid",
	            checkbox: "true", 
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".tb_recordview").hide();
	            	}else{
	            		$(".tb_recordview").show();
	            	}
	                success(data);
	            }
	        })
  		}

	});
}
setTimeout(loadGrid,1000);

</script>


<!-- 对结果进行判断 -->
<div class="tb_recordview" >
		<table class="easyui-datagrid" id="recordadd-datagrid" style="width:100%;" pagination ="true"></table>
</div>
<br/>
	<div class="div_margin_left" >
		<form id="tbSupplementForm" method="post"  action="" >
				<input type="hidden" id ="orderId" name="orderId" >	
				<input class="easyui-textbox "  id="vacSupplementContent" name="vacSupplementContent"  data-options="multiline:true"  validType="length[0,2000]"  style="width:600px">
				<br/>
				<br/>	
				<permission:hasPermission action="tb/order/recordDataGrid">		
				<a href="javascript:addRecords();" id="addRecords"  iconCls="icon-save"   class="easyui-linkbutton  a_button">追     加</a>
				</permission:hasPermission>
				<br/>
				<br/>
		</form>	
	</div>