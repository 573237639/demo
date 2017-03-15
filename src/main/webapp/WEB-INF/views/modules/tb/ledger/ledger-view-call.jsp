<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
var loadGrid = function(){
	$("#viewcall-datagrid").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
  	  		 {field:'id',title:'ID',width:100},
  	  		 {field:'vacLedgerNumber',title:'手机/电话', width:100,align:"center"},
  	  		 {field:'vacLedgerAgentName',title:'话务员姓名', width:100,align:"center"}, 
		  	 {field:'vacLedgerBusinessName',title:'业务类型', width:200,align:"center"},                         
		  	 {field:'gmtCreatedString',title:'来电时间', width:200,align:"center"},                                          
		  	 {field:'vacLedgerClientAccount',title:'台账-客户自述', width:300,align:"center"}, 
		  	 {field:'vacLedgerLawyerSuggestion',title:'台账-律师建议', width:300,align:"center"},                             
		  	 {field:'dic',title:'操作', width:150,align:"center",formatter:function(value,row,index){
	  	  			var btns = "";
	  	  				btns += '<permission:hasPermission action="tb/ledger/ledger-view-index"><a href="javascript:viewIndex('+row.id+');" class="lkbtn"><font style="color:red">查看</font></a></permission:hasPermission>&nbsp;'
					return btns;
	  	  		}}                                            
  		]],
  		loader:function(param,success,error){
  			number = $("#vacLedgerNumber").val();
  			param.vacLedgerNumber = number;
  			param.isDeleted = false;
	    	$.ajax({
	            url:"${ctx}/tb/ledger/historyCallDataGrid",
	            checkbox: "true", 
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".ledger_call_view").hide();
	            		$(".ledger_call_datanull").show();
	            	}else{
	            		$(".ledger_call_view").show();
	            		$(".ledger_call_datanull").hide();
	            	}
	                success(data);
	            }
	        })
  		}

	});
}
setTimeout(loadGrid,1000);
//查看
function viewIndex(id){
	window.parent.window.newTab(id,"/tb/ledger/ledger-view-index","台帐查看"+id,"icon-save");

}

// //查看
// function viewOrderIndex(serial){
// 	var orderCount = 0;
// 	//在此判断工单数是否为1个，1个直接跳转明细，多个跳转列表
// 	//获取工单id
// 	if(orderCount > 1){
// 		window.parent.window.newTab(serial,"/tb/ledger/orderlist-index","工单列表:"+serial,"icon-save");
// 	}else if(orderCount == 1){
// 		window.parent.window.newTab(serial,"/tb/ledger/orderview-index","工单查看:"+serial,"icon-save");
// 	}else{
// 		$.messager.alert('提示','该台账暂无工单');
// 	}
// }

</script>
	<div class="ledger_call_view">
		<table class="easyui-datagrid" id="viewcall-datagrid" style="width:100%;" pagination ="true">
		</table>
	</div>
	<div class="ledger_call_datanull" style="display: none;text-align:center;">
		暂无结果
	</div>		
				
