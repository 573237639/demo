<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script>
var loadGrid = function(){
	$("#fill-ledger-datagrid").datagrid({
		striped:true,
  		singleSelect:true,
  		fitColumns:true,
        nowrap:false,  
	    columns:[[
  	  		{field:'gmtCreatedString',title:'来电时间',align:"center",width:10},
		  	{field:'vacClientName',title:'客户姓名', width:10,align:"center",formatter:function(value,row,index){
		  		 return row.tbClients.vacClientName;
		  	}},  
		  	{field:'vacLedgerProvinceName',title:'来电归属', width:10,align:"center"},  
		  	{field:'vacLedgerNumber',title:'手机/电话', width:10,align:"center"}, 
  	  		{field:'dd',title:'操作',width:10,align:"center",formatter:function(value,row,index){
  	  			var btns = "";
  	  			if(!row.isDeleted){
  	  				btns += '<permission:hasPermission action="tb/ledger/ledger-edit-index"><a href="javascript:editLedger('+row.id+');" class="lkbtn" ><span style="color:red;font-size:14px;font-weight:bold;">继续填写</span></a></permission:hasPermission>&nbsp;'
  	  			}
				return btns;
  	  		}}
  		]],
  		loader:function(param,success,error){
  			param.callTimeStart = $("#callTimeStart").textbox("getValue");
  			param.callTimeEnd = $("#callTimeEnd").textbox("getValue"); 
  			param.vacClientName = $("#vacClientName").textbox("getValue");
  			param.vacLedgerNumber = $("#vacLedgerNumber").textbox("getValue");
  			param.numLedgerStatus = <%=CommonField.LEDGER_STATUS_FILL%>;
	    	$.ajax({
	            url:"${ctx}/tb/ledger/tbLedgerListDataGrid",
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".fill_ledger_view").hide();
	            		$(".fill_ledger_datanull").show();
	            	}else{
	            		$(".fill_ledger_view").show();
	            		 success(data);
	            		$(".fill_ledger_datanull").hide();
	            	}
	               
	            }
	        })
  		}
	});
}
setTimeout(loadGrid,1000);
</script>

<script type="text/javascript">
	//点击搜索	
	function searchFillLedger() {
		
	//做时间校验
		var callTimeStart = $("#callTimeStart").textbox("getValue");
		var callTimeEnd = $("#callTimeEnd").textbox("getValue"); 
		if(callTimeStart != '' && callTimeEnd !=''){
			if(callTimeStart > callTimeEnd){
				　　$.messager.alert("消息提醒","结束时间不能小于开始时间","warning");
				　　return;
			}
		}
		//做表单验证
		//验证表单
		var validate = $("#fill-ledger-from").form("validate");
		if(!validate){
// 		　　$.messager.alert("消息提醒","请检查你输入的数据!","warning");
		　　return;
		} else{
		　　//提交	
				$("#searchFillLedger").linkbutton('disable');
				$.messager.progress();	// 显示进度条
				$("#fill-ledger-datagrid").datagrid('load');
				$.messager.progress("close");	// 关闭进度条
		    	$("#searchFillLedger").linkbutton('enable');
		}
	}
	
	
	function editLedger(id){
		window.parent.window.newTab(id,"/tb/ledger/ledger-edit-index","台帐编辑:"+id,"icon-edit");
	}
	
	//重置
	 function reloadCur(){
			 $("#callTimeStart").textbox("setValue","");
  			 $("#callTimeEnd").textbox("setValue","");
  			 $("#vacClientName").textbox("setValue","");
  			 $("#vacLedgerNumber").textbox("setValue","");		
	 }
	
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="fill-ledger-from" method="post" action=""  modelAttribute="tbLedger,tbClients">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">客户姓名</span>
					<input class="easyui-textbox textbox_input" id="vacClientName"  name="vacClientName"  value="${tbClients.vacClientName}"   validType="length[0,20]" >	
					<span class="div_margin_left">手机/电话</span>
					<input class="easyui-textbox textbox_input" id="vacLedgerNumber"  name="vacLedgerNumber"  value="${tbLedger.vacLedgerNumber }"  validType="length[8,13]" />
						&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<br/>
				<div >
					<span class="div_margin_left">来电时间</span>
				   	<input class="easyui-datetimebox" id="callTimeStart" name="callTimeStart"  editable="false" style="width:200px;height:26px;">
    				<input class="easyui-datetimebox" id="callTimeEnd" name="callTimeEnd"   editable="false"  style="width:200px;height:26px;">	
   				</div>
				<br/>
					<div class="div_margin_left">
					<a href="javascript:searchFillLedger();" id="searchFillLedger"  OnClientClick=""  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				</div>
							<br/>
			<br/>
		</form>
			<div class="fill_ledger_view" >
				<table class="easyui-datagrid" id="fill-ledger-datagrid" 	style="width:100%;height: auto"  pagination="true">
				</table>
			</div>
			<div class="fill_ledger_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
	</div>





