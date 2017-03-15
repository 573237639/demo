<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
var loadGrid = function(){
	$("#ledgereditno-datagrid").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
		  	 {field:'modifierName',title:'操作人', width:100,align:"center"},                         
		  	 {field:'gmtModifiedString',title:'操作时间', width:100,align:"center"},                                                                    
		  	 {field:'isDeleted',title:'操作内容', width:150,align:"center",formatter:function(value,row,index){
	  	  			if(value){
	  	  				return "设为无效";
	  	  			}else{
	  	  				return "设为有效";
	  	  			}
	  	  		}}                                           
  		]],
  		loader:function(param,success,error){
  			param.vacLedgerNumber = $("#vacLedgerNumber").val();
	    	$.ajax({
	            url:"${ctx}/tb/ledger/ledgereditno-datagrid",
	            checkbox: "true", 
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".ledger_edit_no_view").hide();
	            		$(".ledger_edit_no_datanull").show();
	            	}else{
	            		$(".ledger_edit_no_view").show();
	            		$(".ledger_edit_no_datanull").hide();
	            	}
	                success(data);
	            }
	        })
  		}

	});
}
setTimeout(loadGrid,1000);
</script>
	<div class="ledger_edit_no_view">
<table class="easyui-datagrid" id="ledgereditno-datagrid" style="width:100%;" pagination ="true"></table>
				</div>
			<div class="ledger_edit_no_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>			
