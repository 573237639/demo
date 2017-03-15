<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script>
var handleList = ${handleList};
var typeList = ${typeList};
var genderList = ${genderList};
var queueList = ${queueList};
var loadGrid = function(){
	$("#lnvalid-ledger-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
			 {field:'ck',checkbox:true},
		  	 {field:'vacClientName',title:'客户姓名', width:100,align:"center",formatter:function(value,row,index){
		  		 return row.tbClients.vacClientName;
		  	 }}, 
		  	 {field:'numClientGender',title:'性别', width:50,align:"center",formatter:function(value,row,index){
		  		var c = row.tbClients;
		  		if(c == undefined || c == "") return;
		  		var v = c.numClientGender;
		  		for(var data in genderList){
		  			if(v == genderList[data].value){
		  				return genderList[data].label;
		  			}
		  		}
		  	 }},  
		  	 {field:'vacLedgerProvinceName',title:'来电归属', width:60,align:"center"},  
		  	 {field:'vacLedgerNumber',title:'手机/电话', width:100,align:"center"},  
		  	 {field:'zjType',title:'证件类别', width:100,align:"center",formatter:function(value,row,index){
		  		 if(null !=row.tbClients.vacClientIdentityCode && row.tbClients.vacClientIdentityCode != ""){
		  			 return "身份证";
		  		 }else  if(null != row.tbClients.vacClientMilitaryCode && row.tbClients.vacClientMilitaryCode != ""){
		  			 return "军官证";
		  		 }else  if(null != row.tbClients.vacClientEepCode && row.tbClients.vacClientEepCode != ""){
		  			 return "港澳台通行证";
		  		 }else  if(null != row.tbClients.vacClientPassportCode && row.tbClients.vacClientPassportCode != ""){
		  			 return "外国籍护照";
		  		 }{
		  			 return "";
		  		 }
		  	 }},   
		  	 {field:'zjCode',title:'证件号码', width:150,align:"center",formatter:function(value,row,index){
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
		  	 }},   
		  	 {field:'numClientType',title:'咨询人类别', width:100,align:"center",formatter:function(value,row,index){
		  		 	var v = row.tbClients.numClientType;
			  		if(v == undefined || v == "") return;
			  		for(var data in typeList){
			  			if(v == typeList[data].value){
			  				return typeList[data].label;
			  			}
			  		}
		  	 }},   
		  	 {field:'vacLedgerBusinessName',title:'业务类型', width:220,align:"left"},  
		  	 {field:'vacLedgerAgentName',title:'话务员姓名', width:100,align:"center"},  
		  	 {field:'numLedgerCallQueue',title:'来电队列', width:100,align:"center",formatter:function(value,row,index){
			  		if(value==undefined || value === "") return;
			  		if(value == 0){
				  		for(var data in queueList){
				  			if(value == queueList[data].value ){
				  				return queueList[data].label;
				  			}
				  		}
			  		}else{
			  			return value;
			  		}
		  	 }},  
		  	 {field:'numLedgerHandle',title:'处理情况', width:80,align:"center",formatter:function(value,row,index){
			  		if(value==undefined || value == "") return;
			  		for(var data in handleList){
			  			if(value == handleList[data].value){
			  				return handleList[data].label;
			  			}
			  		}
		  	 }},  
		  	 {field:'isOrder',title:'是否有工单', width:80,align:"center",formatter:function(value,row,index){
			  		if(value==undefined || value == "") return "否";
			  		return value == 0 ? "否":"是";
		  	 }},                            
		  	 {field:'dic',title:'操作', width:150,align:"center",formatter:function(value,row,index){
	  	  			var btns = "";
	  	  			if(row.isDeleted){
	  	  				btns += '<permission:hasPermission action="tb/ledger/ledger-view-index"><a href="javascript:viewIndex('+row.id+');" class="lkbtn"><font style="color:red;font-size:14px;font-weight:bold;">查看</font></a></permission:hasPermission>&nbsp;'
	  	  			}
					return btns;
	  	  		}}                                         
  		]],
  		loader:function(param,success,error){
  			param.callTimeStart = $("#callTimeStart").textbox("getValue");
  			param.callTimeEnd = $("#callTimeEnd").textbox("getValue"); 
  			param.vacClientName = $("#vacClientName").textbox("getValue");
  			param.vacLedgerNumber = $("#vacLedgerNumber").textbox("getValue");
  			param.vacLedgerBusinessType = $("#vacLedgerBusinessType").textbox("getValue");
  			param.isDeleted = true;
	    	$.ajax({
	            url:"${ctx}/tb/ledger/tbLedgerListDataGrid",
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".incalid_ledger_view").hide();
	            		$(".incalid_ledger_datanull").show();
	            	}else{
	            		$(".incalid_ledger_view").show();
	            		$(".incalid_ledger_datanull").hide();
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
	function searchLnvalidLedger() {
		
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
		var validate = $("#lnvalid-ledger-from").form("validate");
		if(!validate){
// 		　　$.messager.alert("消息提醒","请检查你输入的数据!","warning");
			return;
		} else{
			$("#searchLnvalidLedger").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#lnvalid-ledger-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchLnvalidLedger").linkbutton('enable');
		}
	}
	
	//查看
	function viewIndex(id){
		window.parent.window.newTab(id,"/tb/ledger/ledger-view-index","台帐查看"+id,"icon-save");
	}
	
	//重置
	 function reloadCur(){
		 	 $("#callTimeStart").textbox("setValue","");
		 	 $("#vacLedgerBusinessType").textbox("setValue","");
			 $("#callTimeEnd").textbox("setValue","");
			 $("#vacClientName").textbox("setValue","");
			 $("#vacLedgerNumber").textbox("setValue","");		
	 }
	
	//设为有效
	 function deleteLedger(){
	     // 返回被选中的行 然后集成的其实是 对象数组 
	     var row = $('#lnvalid-ledger-datagrid').datagrid('getSelections');  
	     var i = 0;  
	     var ids = "";  
	     if (row.length== 0) { 
	    	 $.messager.alert('提示','请选择台账!');
	     } 
	     if (row.length>0) {  
	    	 $("#deleteLedger").linkbutton('disable');
	         for(i;i<row.length;i++){  
		     	ids += row[i].id;  
		         if(i < row.length-1){  
		         	ids += ',';  
		         }else{  
		             break;  
		         }  
		     }
	         $.messager.confirm('提示','是否将所选台账设为有效？', function(r) {  
	             if (r) {  
	                 $.post('${ctx}/tb/ledger/deleteLedger', {  
	                     id : ids,
	                     isDeleted : true
	                 }, function(result) {  
	                	 if(result.code == 1){
	                		 $.messager.alert('提示',result.message);
	                	 }else{
	                		 $.messager.alert('提示','设为有效成功');
	                  		 $('#lnvalid-ledger-datagrid').datagrid('reload'); 
	                	 }
	                	 $("#deleteLedger").linkbutton('enable');
	                 });  
	             }else{
	            	 $("#deleteLedger").linkbutton('enable');
	             }  
	         });  
	     }  
	}
	
	
	 function check() {
			//做一个提示
			$.messager.confirm('确认框', '请确认导出', function(r){
				if (r){			
					//坐时间校验
					var callTimeStart = $("#callTimeStart").textbox("getValue");
					var callTimeEnd = $("#callTimeEnd").textbox("getValue"); 
					var d1 = new Date(callTimeStart.replace(/-/g,"/"));
					var d2 = new Date(callTimeEnd.replace(/-/g,"/"));
					d1.setDate(d1.getDate()+31);
					if(callTimeEnd == ''){
					    $.messager.alert("消息提醒","结束时间不能为空","warning");
					    return ;
					}else if(callTimeStart > callTimeEnd){
					    $.messager.alert("消息提醒","结束时间不能小于开始时间","warning");
					    return ;
					}else if(d1 < d2 ){
						$.messager.alert("消息提醒","只能导出31天范围的数据，请重新选择时间","warning");
						return ;
					}else{
	 					document.getElementById("lnvalid-ledger-from").submit();
					}
				}
			});
		}
	
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="lnvalid-ledger-from" method="post" action="method=lnvalidledgerexcel"  modelAttribute="tbLedger,tbClients">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">客户姓名</span>
					<input class="easyui-textbox textbox_input" id="vacClientName"  name="vacClientName"  value="${tbClients.vacClientName}"   validType="length[0,20]" >	
					<span class="div_margin_left">手机/电话</span>
					<input class="easyui-textbox textbox_input" id="vacLedgerNumber"  name="vacLedgerNumber"  value="${tbLedger.vacLedgerNumber }"  validType="length[8,13]" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="div_margin_left">业务类型</span>
					<input class="easyui-combotree"  style="width:300px;height:26px;" id="vacLedgerBusinessType" name="vacLedgerBusinessType"  editable="false"
					data-options="editable:false,valueField:'id',width:150,panelHeight:300,textField:'text',url:'${ctx}/sys/specialty/findAllBusinessType'" />
				</div>
				<br/>
				<div >
					<span class="div_margin_left">来电时间</span>
				   	<input class="easyui-datetimebox" id="callTimeStart" name="callTimeStart"  value="new date();"  editable="false" style="width:200px;height:26px;">
    				<input class="easyui-datetimebox" id="callTimeEnd" name="callTimeEnd"   editable="false"  style="width:200px;height:26px;">	
   				</div>
					<br/>
					<div class="div_margin_left">
					<a href="javascript:searchLnvalidLedger();" id="searchLnvalidLedger"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				<permission:hasPermission action="method=lnvalidledgerexcel">
					<input type="button" value="导    出"  class=" easyui-linkbutton a_button" onclick = "check();" />
				</permission:hasPermission>
				</div>
							<br/>
			<br/>
		</form>
			<div class="incalid_ledger_view">
				<table class="easyui-datagrid"  id="lnvalid-ledger-datagrid" data-options="
				toolbar:'#lnvalid-ledger-tools'"	style="width: 100%;" pagination="true">
				</table>
				<div id="lnvalid-ledger-tools" style="padding:5px;height:auto">
				<permission:hasPermission action="tb/ledger/deleteLedger">
				<a href="javascript:deleteLedger();" class="easyui-linkbutton" id="deleteLedger" iconCls="icon-remove" style="color:blue;background-color: #BCDCDC;" plain="true">设为有效</a>
				</permission:hasPermission>
				</div>
				<br/>
				<br/>
			</div>
			<div class="incalid_ledger_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
	</div>





