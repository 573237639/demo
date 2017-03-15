<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var orderTypeList = ${orderTypeList};
var taskTypeList = ${taskTypeList};
var statusTypeList = ${statusTypeList};
var loadGrid = function(){
	$("#callorderview-datagrid").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
	   	  		 {field:'id',title:'ID',width:100,hidden:true},
	  	  		 {field:'gmtCreated',title:'工单产生时间', width:100,align:"center",formatter:function(value,row,index){
			  		 if(null !=row.gmtCreated && row.gmtCreated != ""){
			  			 return row.gmtCreatedString;
			  		 }else{
			  			 return "";
			  		 }
			  	 }
	   	  		 },
			  	 {field:'vacOrderBusinessType',title:'业务类型', width:100,align:"center"},                         
			  	 {field:'vacOrderType',title:'工单类型', width:100,align:"center",formatter:function(value,row,index){
				  		if(value==undefined) return;
				  		for(var data in orderTypeList){
				  			if(value == orderTypeList[data].value){
				  				return orderTypeList[data].label;
				  			}
				  		}
				  	 }
			  	 },                                          
			  	 {field:'vacOrderTaskType',title:'任务类型', width:300,align:"center",formatter:function(value,row,index){
				  		if(value==undefined) return;
				  		for(var data in taskTypeList){
				  			if(value == taskTypeList[data].value){
				  				return taskTypeList[data].label;
				  			}
				  		}
				  	 }}, 
			  	 {field:'vacOrderTaskType',title:'初审结果', width:300,align:"center",formatter:function(value,row,index){
				  		if(value==undefined) return;
				  		for(var data in statusTypeList){
				  			if(value == statusTypeList[data].value){
				  				return statusTypeList[data].label;
				  			}
				  		}
				  	 }},
			  	 {field:'vacOrderTaskType',title:'复审结果', width:100,align:"center"},                                           
			  	 {field:'vacOrderTaskType',title:'结果反馈', width:100,align:"center"},
			  	 {field:'vacOrderAgentName',title:'制单人', width:100,align:"center"}
	  		]],
	  		loader:function(param,success,error){
		    	$.ajax({
		            url:"callorderview-datagrid/"+id,
		            checkbox: "true", 
		            data:param,
		            type:"post",
		            dataType:"json",
		            jsonpCallback:"callback",
		            success: function(data){
		            	if(data.total == 0){
		            		$(".call_order_view_view").hide();
		            		$(".call_order_view_datanull").show();
		            	}else{
		            		$(".call_order_view_view").show();
		            		$(".call_order_view_datanull").hide();
		            	}
		                success(data);
		            }
		        });
	  		}

	});
}
setTimeout(loadGrid,1000);
</script>

				<div >
					<span align="left" style="font-size: 20px;">历史工单  </span>
					<span align="left" >工单数  </span>
				</div>
				<div class="easyui-panel" style="padding:4px">
				<div class="call_order_view_view">
				<table class="easyui-datagrid" id="callorderview-datagrid" style="width:100%;" pagination="true">
				</table>
				</div>
				<div class="call_order_view_datanull" style="display: none;text-align:center;">
					暂无结果
				</div>				
				</div>
				
