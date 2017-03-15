<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <%@ include file="/WEB-INF/views/include/header.jsp"%> --%>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/common.js"></script>

<script type="text/javascript">

	//点击保存台账	
	function saveCallLedger(status){
		if(status == <%=CommonField.LEDGER_STATUS_SUCCESS %>){
			//性别
			var genderFlag = false;
			var genderRadio = $("input[name='numClientGender']");
			for(var i = 0;i < genderRadio.length;i++){
				   if(genderRadio[i].checked){
					   genderFlag = true;
					   break;
				   }
				}
			if(!genderFlag){
				$.messager.alert("消息提醒","请选择性别","warning");
				return;
			}
			//咨询人类别
			var typeFlag = false;
			var typeRadio = $("input[name='numClientType']");
			for(var i = 0;i < typeRadio.length;i++){
				   if(typeRadio[i].checked){
					   typeFlag = true;
					   break;
				   }
				}
			if(!typeFlag){
				$.messager.alert("消息提醒","请选择咨询人类别!","warning");
				return;
			}
			var validate = $("#tbCallLedgerForm").form("validate");
			if(!validate){
				return;
			} 
		
			$("#saveCallLedger").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$('#tbCallLedgerForm').form('submit', {    
				url:"${ctx}/tb/call/saveCallLedger/"+status, 
			    success:function(data){
		        	$("#saveCallLedger").linkbutton('enable');
			    	$.messager.progress("close");	// 关闭进度条
			    	var dataObj=eval("("+data+")");
		        	if(dataObj.code == 0){
			        	$.messager.alert('提示','台帐保存成功!');
			        	$("#listCallGrid").datagrid('load');
			        }else{
			        	$.messager.alert('提示','台帐保存失败!原因：'+dataObj.message);
			        } 
			    }    
			}); 
		}else{
			var id = $("input[name='tbLedger.id']").val();
			g_ledger_id = id;//用于判断是否打开过来电
			var vacLedgerTalkDur = $("input[name='vacLedgerTalkDur']").val();
			if(id == ''){
				return ;
			}
			//发送一个ajax请求
	    	$.ajax({
	            url:"${ctx}/tb/call/saveCallLedgerByFail",
				data:$("#tbCallLedgerForm").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
			        $("#listCallGrid").datagrid('load');
	            }
	        })
		}
	}
	
	function saveCallOrder(status){
		//性别
		var genderFlag = false;
		var genderRadio = $("input[name='numClientGender']");
		for(var i = 0;i < genderRadio.length;i++){
			   if(genderRadio[i].checked){
				   genderFlag = true;
				   break;
			   }
			}
		if(!genderFlag){
			$.messager.alert("消息提醒","请选择性别","warning");
			return;
		}
		//咨询人类别
		var typeFlag = false;
		var typeRadio = $("input[name='numClientType']");
		for(var i = 0;i < typeRadio.length;i++){
			   if(typeRadio[i].checked){
				   typeFlag = true;
				   break;
			   }
			}
		if(!typeFlag){
			$.messager.alert("消息提醒","请选择咨询人类别!","warning");
			return;
		}
		//处理情况
		var handleFlag = false;
		var handleRadio = $("input[name='numLedgerHandle']");
		for(var i = 0;i < handleRadio.length;i++){
		   if(handleRadio[i].checked){
			   handleFlag = true;
			   break;
		   }
		}
		if(!handleFlag){
			$.messager.alert("消息提醒","请选择处理情况!","warning");
			return;
		}
		var validate = $("#tbCallLedgerForm").form('enableValidation').form("validate");
		if(!validate){
			return;
		} else{
			//先保存台帐
			$('#tbCallLedgerForm').form('submit', {    
				url:"${ctx}/tb/call/saveCallLedger/"+status, 
			    success:function(data){
			    	var dataObj=eval("("+data+")");
		        	if(dataObj.code == 0){
		        		var id = $("input[name='tbLedger.id']").val();
		        	 	$('#tt').tabs('add',{   
		        	 	    title:'生成工单'+id,
		        	  	    href:"${ctx}/tb/call/call-order/"+id, 
		        	  	    closable:true
		        	 	}); 
		        	}else{
			        	$.messager.alert('提示','台帐保存失败!原因：'+dataObj.message);
			        } 
			    }    
			});  
		}
	}
	

	    /* 初始化下载表格信息 */
	  $(function() {  	
		  // 下拉框选择控件，下拉框的内容是动态查询数据库信息
	  		$('#numClientProvinceId').combobox({ 
	          editable:false, //不可编辑状态
	          cache: false,
	          panelHeight: '300px',//自动高度适合
	          valueField:'id', //值字段
	          textField:'vacProvinceName', //显示的字段
	          url:'${ctx}/sms/province/findAllProvince',
	          value:'${tbClients.numClientProvinceId}',
			  onHidePanel: function(){
			      $("#numClientCityId").combobox("setValue",'');
			      var clientPid = $("#numClientProvinceId").combobox('getValue');
			      if(clientPid == ''){
			    	  clientPid = 0
			      }
			      $.ajax({
			        type: "GET",
			        url:'${ctx}/sms/city/findCityByProvId/'+clientPid,
			        cache: false,
			        dataType : "json",
			        success: function(data){
			        	$("#numClientCityId").combobox("loadData",data);
			         }
			       }); 	
			  }
	  		}); 
			var clientCId = $('#numClientCityId').val();
			var clientProId = $('#numClientProvinceId').val();
			if(clientCId != ''){
				  $('#numClientCityId').combobox({ 
				      editable:false, //不可编辑状态
				      cache: false,
				      panelHeight: 'auto',//自动高度适合
				      valueField:'id', //值字段
				      textField:'vacCityName',//显示的字段
					  url:'${ctx}/sms/city/findCityByProvId/${tbClients.numClientProvinceId}',
					  value:'${tbClients.numClientCityId}'	
			     });
			}else{
				 if(clientProId != ''){
				      $.ajax({
					        type: "GET",
					        url:'${ctx}/sms/city/findCityByProvId/'+clientProId,
					        cache: false,
					        dataType : "json",
					        success: function(data){
					        	$("#numClientCityId").combobox("loadData",data);
					         }
					       }); 	
				  }
				  $('#numClientCityId').combobox({ 
				      editable:false, //不可编辑状态
				      cache: false,
				      panelHeight: 'auto',//自动高度适合
				      valueField:'id', //值字段
				      textField:'vacCityName'//显示的字段
			     });
			}
		  
		  
	  		$('#numLedgerIncidentPorvinceId').combobox({ 
		          editable:false, //不可编辑状态
		          cache: false,
		          panelHeight: '300px',//自动高度适合
		          valueField:'id', //值字段
		          textField:'vacProvinceName', //显示的字段
		          url:'${ctx}/sms/province/findAllProvince',
		          value:'${tbLedger.numLedgerIncidentPorvinceId}',
		          onChange: function(pid){
				      $("#numLedgerIncidentCityId").combobox("setValue",'');
				      var ledgerPid = $("#numLedgerIncidentPorvinceId").combobox('getValue');
				      if(ledgerPid == ''){
				    	  ledgerPid = 0;
				      }
				      $.ajax({
				        type: "GET",
				        url:'${ctx}/sms/city/findCityByProvId/'+ledgerPid,
				        cache: false,
				        dataType : "json",
				        success: function(data){
				        	$("#numLedgerIncidentCityId").combobox("loadData",data);
				         }
				       }); 	
				  }
		  		}); 
				var ledgerCId = $('#numLedgerIncidentCityId').val();
				var ledgerProId = $('#numLedgerIncidentPorvinceId').val();
				if(ledgerCId != ''){
					  $('#numLedgerIncidentCityId').combobox({ 
							required:false,
							editable:false,//不可编辑，只能选择
							cache: false,
							panelHeight: 'auto',//自动高度适合
							valueField:'id', //值字段
							textField:'vacCityName', //显示的字段
							url:'${ctx}/sms/city/findCityByProvId/${tbLedger.numLedgerIncidentPorvinceId}',
							value:'${tbLedger.numLedgerIncidentCityId}'		  
				     });
				}else{
					 if(ledgerProId != ''){
					      $.ajax({
						        type: "GET",
						        url:'${ctx}/sms/city/findCityByProvId/'+ledgerProId,
						        cache: false,
						        dataType : "json",
						        success: function(data){
						        	$("#numLedgerIncidentCityId").combobox("loadData",data);
						         }
						       }); 	
					  }
					  $('#numLedgerIncidentCityId').combobox({ 
							required:false,
							editable:false,//不可编辑，只能选择
							cache: false,
							panelHeight: 'auto',//自动高度适合
							valueField:'id', //值字段
							textField:'vacCityName'//显示的字段  
				     });
				}
			  var vacLedgerBusinessType = '${tbLedger.vacLedgerBusinessType}' ;
			  if(vacLedgerBusinessType != ""){
			      $.ajax({
				        type: "POST",
				        url:'${ctx}/sys/specialty/getById/${tbLedger.vacLedgerBusinessType}',
				        cache: false,
				        dataType : "json",
				        success: function(data){
				 			  $('#vacLedgerBusinessType').combotree('setValue', 
				 					{
				 						id: vacLedgerBusinessType,
				 						text: data.vacName
				 					}
				 			  );
				         }
				       }); 
			  }
			  $('#vacLedgerBusinessType').combotree({    
				    url: '${ctx}/sys/specialty/findAllBusinessType',    
				    editable:false,
				    panelHeight:250
				}); 
			  $("#vacLedgerBusinessType").combotree({
				  editable:false,
				  onBeforeSelect:
				  function(node){
					  var  leaf = $(this).tree('isLeaf',node.target);
					  if(!leaf){
						  //如果不是叶子,清除选中
						  $(this).treegrid("unselect");
					  }
				  }});
			  
			  var numSpecialtyId = '${tbLedger.numSpecialtyId}' ;
			  if(numSpecialtyId != ""){
			      $.ajax({
				        type: "POST",
				        url:'${ctx}/sys/specialty/getById/${tbLedger.numSpecialtyId}',
				        cache: false,
				        dataType : "json",
				        success: function(data){
				 			  $('#numSpecialtyId').combotree('setValue', 
				 					{
				 						id: numSpecialtyId,
				 						text: data.vacName
				 					}
				 			  );
				         }
				       }); 
			  }
			  $('#numSpecialtyId').combotree({    
				    url: '${ctx}/sys/specialty/findAllLawType',    
				    editable:false,
				    panelHeight:160
				}); 
			  $("#numSpecialtyId").combotree({					    
				    editable:false,
				    onBeforeSelect:
				  function(node){
					  var  leaf = $(this).tree('isLeaf',node.target);
					  if(!leaf){
						  //如果不是叶子,清除选中
						  $(this).treegrid("unselect");
					  }
				  }});
			  
			  var numClientSource = $("#numClientSource").val();
			  //客户来源
			  if(numClientSource != ""){
				  $("input[name='numClientSource']").each(function(){  
					    $(this).attr("disabled",true);  
					});
			  }
	  });

		function globalAlert(name, msg, level) {
			$.messager.alert(name, msg, level);
		}
  
// 		var t = ${t};
// 		if(t == 0){
			$(function(){
				se=window.setInterval(second,1000);
			});
// 		}
	
</script>
<script type="text/javascript">
var loadGridCall = function(){
	$("#callLedger-datagrid").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
  	  		 {field:'id',title:'ID',width:80},
  	  		 {field:'vacLedgerNumber',title:'来电号码', width:130,align:"center"}, 
		  	 {field:'vacLedgerAgentName',title:'话务员姓名', width:100,align:"center"}, 
		  	 {field:'vacLedgerBusinessName',title:'业务类型', width:200,align:"center"},  
		  	 {field:'gmtCreatedString',title:'来电时间', width:150,align:"center"},                                          
		  	 {field:'vacLedgerClientAccount',title:'台账-客户自述', width:300,align:"center",editor:'textbox'}, 
		  	 {field:'vacLedgerLawyerSuggestion',title:'台账-律师建议', width:300,align:"center",editor:'textbox'},                                      
		  	 {field:'isOrder',title:'工单数量', width:80,align:"center"},
		  	 {field:'dic',title:'操作', width:80,align:"center",formatter:function(value,row,index){
	  	  			var btns =  '<a href="javascript:showCallMessage(\''
	  	  				+ row.vacLedgerNumber + '\',\''+ row.vacLedgerClientAccount +'\',\''+ row.vacLedgerLawyerSuggestion +'\');" class="lkbtn" ><span style="color:red">内容详情</span></a>&nbsp;'
					return btns;
	  	  		}}  
  		]],
  		loader:function(param,success,error){
  			param.vacLedgerNumber = $("#vacLedgerNumber").val();
	    	$.ajax({
	            url:"${ctx}/tb/call/callLedger-datagrid",
	            checkbox: "true", 
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	                success(data);
	            }
	        })
	    }
	});
}
setTimeout(loadGridCall,1000);

</script>
<script type="text/javascript">
function showCallMessage(number,account,suggestion){
	$.messager.show({
		title:'内容详情',
		msg:'<span style="color:red">客户号码：</span>'+number+'<br/><span style="color:red">客户自述：</span>'+account+'<br/><span style="color:red">律师建议：</span>'+suggestion+'<br/>',
		showType:'fade',
		width:600,
		height:300,
		timeout:0,
		style:{
			right:'',
			bottom:''
		}
	});
}
</script>
<script type="text/javascript">
		$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
				});
			}
		});
		
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#callLedger-datagrid').datagrid('validateRow', editIndex)){
				$('#callLedger-datagrid').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickCell(index, field){
			if (endEditing()){
				$('#callLedger-datagrid').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
			}
		}
	</script>
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
  			param.vacOrderBusinessType = $("#vacOrderBusinessType1").combobox("getValue");
  			param.numOrderStatus = $("#numOrderStatus1").textbox("getValue");
  			param.vacOrderNumber = $("#vacLedgerNumber").val();
	    	$.ajax({
	            url:"${ctx}/tb/call/callorder-datagrid",
	            checkbox: "true", 
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
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
			<form id="tbCallLedgerForm" method="post"   action=""   modelAttribute="tbClients,tbLedger"   data-options="novalidate:true">
			<div id="tt" class="easyui-tabs" >
			<div title="台帐详情" style="padding:10px">
				<div>
					<span class="div_margin_left span_title" >来电信息    </span>  
					<span  style="font-size: 12px;color: gray;margin-left: 8px;">通话时长</span>
					<input id="showtime" name="vacLedgerTalkDur" style="width: 100px;" value="${tbLedger.vacLedgerTalkDur }" >
					<span id="message" style="color:#ff0000;width:200px;" id="message" ></span>
					<br/>
					<span style="color: red" id="e"> ${error }</span>
				</div>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="display: none;" >
						<span class="div_margin_left">台帐id</span>
						<input name="tbLedger.id"  value="${tbLedger.id }"  >
						<span class="div_margin_left">客户id</span>
						<input name="tbClients.id"  value="${tbClients.id }" >
						<input id ="numClientSource"  value="${tbClients.numClientSource }">
				</div>
				<div>
					<span class="div_margin_left">   流 水 号 </span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerSerial"  value="${tbLedger.vacLedgerSerial }"  readonly="readonly"  >
					<span class="div_margin_left"> 来电号码</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerNumber"  id="vacLedgerNumber" value="${tbLedger.vacLedgerNumber }"  readonly="readonly" />
					<span class="div_margin_left"> 来电归属</span>
					<input type="hidden"  name="numLedgerProvinceId" value="${tbLedger.numLedgerProvinceId}">
					<input type="hidden"  name="numLedgerCityId" value="${tbLedger.numLedgerCityId}">
					<input type="hidden"  name="vacLedgerProvinceName" value="${tbLedger.vacLedgerProvinceName}">
					<input type="hidden"  name="vacLedgerCityName" value="${tbLedger.vacLedgerCityName}">
					<input class="easyui-textbox textbox_input"    value="${tbLedger.vacLedgerProvinceName} -  ${tbLedger.vacLedgerCityName} "  readonly="readonly" >
				</div>
				<br/>
				<div>
					<span class="div_margin_left">来电队列</span>
					<input  class="easyui-textbox textbox_input"  name="numLedgerCallQueue"   value="${tbLedger.numLedgerCallQueue }" readonly="readonly" >
					<span class="div_margin_left"> 话务员工号</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerAgentCode"   value="${tbLedger.vacLedgerAgentCode }"  readonly="readonly" >
					<span class="div_margin_left"> 话务员姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerAgentName"  value="${tbLedger.vacLedgerAgentName }"  readonly="readonly" >
				</div>
				<br/>
				<p/>
				<span class="div_margin_left span_title" >客户信息</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div >
					<span class="div_margin_left  div_color_red" >* </span><span >客户姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacClientName"  value="${tbClients.vacClientName}"  required="required" validType="length[0,20]"   >		
					<span class="div_margin_left  div_color_red" >* </span><span >性别</span>
					<c:forEach items="${genderList}" var="m" >       
					<span  class="redio_span" >               
			            <input  class="redio_input"  type="radio" id="numClientGender${m.value }" name="numClientGender" value="${m.value }" <c:if test="${tbClients.numClientGender == m.value}">checked="checked"</c:if>  >${m.label }       
			        </span>
			        </c:forEach>
			        <span class="div_margin_left  " >备注</span>
			        <input class="easyui-textbox textbox_input"  name="vacClientMemo"  value="${tbClients.vacClientMemo}"  disabled="disabled"  >	 
				</div>
							<br/>
							<div>
					<span class="div_margin_left  div_color_red" >* </span><span >客户来源</span>
			        <c:forEach items="${sourceList}" var="m" >       
					<span  class="redio_span" >               
			            <input  class="redio_input"  type="radio" id="numClientSource${m.value }" name="numClientSource" value="${m.value }" <c:if test="${tbClients.numClientSource == m.value}">checked="checked"</c:if>  >${m.label }       
			        </span>
			        </c:forEach> 
				</div>
				<br/>
					<div >			        
					<span class="div_margin_left  div_color_red" >* </span><span >咨询人类别</span>
					<c:forEach items="${typeList}" var="m" >
					<span  class="redio_span" >               
			            <input  class="redio_input" type="radio" id="numClientType${m.value }" name="numClientType" value="${m.value }" <c:if test="${tbClients.numClientType == m.value}">checked="checked"</c:if>  >
			            ${m.label }    
			            </span>   
			        </c:forEach>
				</div>
				<br/>
				<div>
					<span class="div_margin_left " >法律类型</span>
					<input id="numSpecialtyId"  name="numSpecialtyId" value="${tbLedger.numSpecialtyId }" style="width:270px;height:26px;">  
					<span class="div_margin_left  div_color_red" >* </span><span >业务类型</span>
					<input id="vacLedgerBusinessType"  name="vacLedgerBusinessType" value="${tbLedger.vacLedgerBusinessType }" style="width:270px;height:26px;" required="required">  
				</div>
				<br/>
				<div>
					<span class="div_margin_left " >常住地址</span>
					<input class="easyui-combobox combobox_input"  id="numClientProvinceId" name="numClientProvinceId"   value= "${tbClients.numClientProvinceId }"  >
					<input class="easyui-combobox combobox_input" id="numClientCityId" name="numClientCityId"   value= "${tbClients.numClientCityId }"  >
					<input class="easyui-textbox textbox_input"  id="vacClientAddress"  name="vacClientAddress"   value="${tbClients.vacClientAddress}"   style="width:300px;height:26px;"  validType="length[0,50]"   >
				</div> 
				<br/>
				<div>
					<span class="div_margin_left " >事发地址</span>
					<input class="easyui-combobox combobox_input"  id="numLedgerIncidentPorvinceId" name="numLedgerIncidentPorvinceId"   value= "${tbLedger.numLedgerIncidentPorvinceId }"  />
					<input class="easyui-combobox combobox_input" id="numLedgerIncidentCityId" name="numLedgerIncidentCityId"   value= "${tbLedger.numLedgerIncidentCityId }" >
					<input class="easyui-textbox textbox_input"  name="vacLedgerIncidentAddress"  value="${tbLedger.vacLedgerIncidentAddress }"  style="width:300px;height:26px;"   validType="length[0,50]"   >
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >咨询内容</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="vertical-align: top; display: inline-flex;">
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >客户自述</span>
					<input class="easyui-textbox " name="vacLedgerClientAccount" data-options="multiline:true" value="${tbLedger.vacLedgerClientAccount }" style="width:300px;height:100px"  required="required" validType="length[0,2000]"   >
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >处理意见</span>	
					<input class="easyui-textbox " name="vacLedgerLawyerSuggestion"  data-options="multiline:true" value="${tbLedger.vacLedgerLawyerSuggestion }"  style="width:300px;height:100px" required="required" validType="length[0,2000]"  >
				</div>	
				<br/>	
				<br/>
				<br/>
				<span class="div_margin_left span_title" >处理情况</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>		
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >处理情况</span>
					<c:forEach items="${handleList}" var="m" >  
					<span  class="redio_span" >                        
			            <input class="redio_input"  type="radio" id="numLedgerHandle${m.value }" name="numLedgerHandle" value="${m.value }" <c:if test="${tbLedger.numLedgerHandle == m.value}">checked="checked"</c:if>  />${m.label }       
			       </span >
			        </c:forEach> 
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >生成工单必填</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div >
					<span class="div_margin_left  " ><span >证件类型（生成工单必填）</span>
					<c:forEach items="${papersTypeList}" var="m" >      
						<span  class="redio_span" >                  
			            <input  class="redio_input"   type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbClients.zjType == m.value}">checked="checked"</c:if>  />${m.label }       
			     </span>
			        </c:forEach> 
			     </div>
			     <br/>
				<div>
					<span class="div_margin_left" ><span >证件号码（生成工单必填）</span>
					<input class="easyui-textbox textbox_input"   id="zjCode"  name="zjCode"  value="${tbClients.zjCode}"  validType="length[0,20]"  >
				</div>
				<br/>
				<br/>
				<div class="div_margin_left"  >
					<permission:hasPermission action="tb/call/saveCallLedger">
					<a href="javascript:saveCallLedger(<%=CommonField.LEDGER_STATUS_SUCCESS %>);" id="saveCallLedger"  iconCls="icon-save"  class="easyui-linkbutton a_button">保存台帐</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</permission:hasPermission>
					<permission:hasPermission action="tb/call/call-order">
					<a href="javascript:saveCallOrder(<%=CommonField.LEDGER_STATUS_FILL %>);" id="saveCallOrder"  iconCls="icon-save" class="easyui-linkbutton a_button">生成工单</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</permission:hasPermission>
				</div>
				<br/>
				<br/>
			</div>
		</div>
	</form>
<p/>
<div class="div_margin_left demo_line_02"></div>
<p/>
<!-- 历史台帐 -->
<span style="font-size: 20px;margin-left: 20px;" >历史台帐</span><span style="font-style: right"></span>
<div style="margin: 10px;margin-left: 20px;">
		<table class="easyui-datagrid" id="callLedger-datagrid" style="width: 100%;margin-left: 20px;" pagination="true"  data-options="onClickCell: onClickCell"></table>
</div>
<p/>
<div class="div_margin_left demo_line_02"></div>
<p/>
<!-- 历史台帐 -->
<span style="font-size: 20px;margin-left: 20px;" >历史工单</span><span style="font-style: right"></span>
<div style="margin: 10px;margin-left: 20px;">
		<form id="callorderlist-from" method="post" action=""  modelAttribute="tbOrders">
				<div >
					<span class="div_margin_left">业务类型</span>
					<input class="easyui-combotree"  style="width:300px;height:28px;" id="vacOrderBusinessType1" name="vacOrderBusinessType" editable="false"
					data-options="editable:false,valueField:'id',width:200,panelHeight:300,textField:'text',url:'${ctx}/sys/specialty/findAllBusinessType'" />
						
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="div_margin_left">初审状态</span>
					<input class="easyui-combobox combobox_input" id="numOrderStatus1"　name="numOrderStatus"  style="width:100px;height:28px;" editable="false" 
						data-options="valueField:'value',width:200,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/status_type'" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:submitForm();" id="searchOrder"  iconCls="icon-search"  class="easyui-linkbutton a_button">搜	索</a>
				</div>
		</form>
</div>
<div style="margin: 10px;margin-left: 20px;">
		<table class="easyui-datagrid" id="callorderlist-datagrid" 	style="width: 100%;" pagination="true"></table>
</div>
</div>
				