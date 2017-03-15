<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
var genderList = ${genderList};
var typeList = ${typeList};
var queueList = ${queueList};
var handleList = ${handleList};

var loadGrid = function(){
	$("#ledgerlist-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
			 {field:'ck',checkbox:true},
  	  		 {field:'id',title:'ID',width:20},
		  	 {field:'gmtCreatedString',title:'来电时间', width:150,align:"center"}, 
		  	 {field:'vacClientName',title:'客户姓名', width:80,align:"center",formatter:function(value,row,index){
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
		  		 }{
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
		  	 {field:'isOrder',title:'是否有工单', width:60,align:"center",formatter:function(value,row,index){
		  		if(value==undefined || value == "") return "否";
		  		return value == 0 ? "否":"是";
		  	 }},                            
		  	 {field:'dic',title:'操作', width:180,align:"center",formatter:function(value,row,index){
	  	  			var btns = "";
	  	  			if(!row.isDeleted){
	  	  				btns += '<permission:hasPermission action="tb/ledger/ledger-view-index"><a href="javascript:viewIndex('+row.id+');" class="lkbtn"><font style="color:red;font-size:14px;font-weight:bold;">查看</font></a></permission:hasPermission>&nbsp;'
	  	  				btns += '<permission:hasPermission action="tb/ledger/ledger-edit-index"><a href="javascript:editIndex('+row.id+');" class="lkbtn"><font style="color:red;font-size:14px;font-weight:bold;">修改</font></a></permission:hasPermission>&nbsp;'
	  	  	  			btns += '<permission:hasPermission action="tb/ledger/orderadd-index"><a href="javascript:orderaddIndex('+row.id+');" class="lkbtn"><font style="color:red;font-size:14px;font-weight:bold;">生成工单</font></a></permission:hasPermission>&nbsp;'
	  	  			}
					return btns;
	  	  		}}                                         
  		]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/tb/ledger/tbLedgerListDataGrid",
	            checkbox: "true", 
	            data:$("#tbLedgerQueryForm").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".ledger_list_view").hide();
	            		$(".ledger_list_datanull").show();
	            	}else{
	            		$(".ledger_list_view").show();
	            		$(".ledger_list_datanull").hide();
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
// 	var row = $('#ledgerlist-datagrid').datagrid('getSelected');
	window.parent.window.newTab(id,"/tb/ledger/ledger-view-index","台帐查看"+id,"icon-save");

}
//修改
function editIndex(id){
// 	var row = $('#ledgerlist-datagrid').datagrid('getSelected');
	window.parent.window.newTab(id,"/tb/ledger/ledger-edit-index","台帐编辑"+id,"icon-edit");
}
//生成工单
function orderaddIndex(id){
// 	var row = $('#ledgerlist-datagrid').datagrid('getSelected');
	window.parent.window.newTab(id,"/tb/ledger/orderadd-index","生成工单"+id,"icon-edit");
}
//删除台账
function deleteLedger(){
     // 返回被选中的行 然后集成的其实是 对象数组  
     var row = $('#ledgerlist-datagrid').datagrid('getSelections');  
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
         $.messager.confirm('提示','是否将所选台账设为无效？', function(r) {  
             if (r) {  
                 $.post('${ctx}/tb/ledger/deleteLedger', {  
                     id : ids ,
                     isDeleted:false
                 }, function(result) {  
                	 if(result.code == 1){
                		 $.messager.alert('提示',result.message);
                	 }else{
                		 $.messager.alert('提示','设为无效成功');
                  		 $('#ledgerlist-datagrid').datagrid('reload'); 
                	 }
                	 $("#deleteLedger").linkbutton('enable');
                 });  
             }else{
            	 $("#deleteLedger").linkbutton('enable');
             }  
         });  
     }  
}

</script>
<div class="easyui-panel" style="padding:4px">
			<div class="ledger_list_view">
				<table class="easyui-datagrid" id="ledgerlist-datagrid" data-options="
				toolbar:'#ledger-tools'" style="width:100%;" pagination="true" >
				</table>
				<div id="ledger-tools" style="padding:5px;height:auto">
				<permission:hasPermission action="tb/ledger/deleteLedger">
				<a href="javascript:deleteLedger();" class="easyui-linkbutton" id="deleteLedger" iconCls="icon-remove" style="color:red;background-color: #BCDCDC;" plain="true">设为无效</a>
				</permission:hasPermission>
				</div>
				<br/>
				<br/>
			</div>
			<div class="ledger_list_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
</div>

