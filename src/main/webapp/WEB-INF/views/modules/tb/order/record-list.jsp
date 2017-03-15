<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
//给表单赋值
var loadGrid = function(){
	$("#recordlist-datagrid").datagrid({
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
	            		$(".tb_order_recordview").hide();
	            		$(".tb_order_datanull").show();
	            	}else{
	            		$(".tb_order_recordview").show();
	            		$(".tb_order_datanull").hide();
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
<div class="tb_order_recordview" >
		<table class="easyui-datagrid" id="recordlist-datagrid" style="width:100%;" pagination ="true"></table>
</div>
<div class="tb_order_datanull" style="text-align: center;">
	暂无结果
</div>