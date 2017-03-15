<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
var genderList = ${genderList};
var typeList = ${typeList};
var sourceList = ${sourceList};
var loadGrid = function(){
	$("#client-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
			 {field:'ck',checkbox:true},
		  	 {field:'vacClientName',title:'客户姓名', width:100,align:"center"}, 
		  	 {field:'numClientGender',title:'性别', width:50,align:"center",formatter:function(value,row,index){
		  		var c = row.numClientGender;
		  		if(c == undefined || c === "") return;
		  		for(var data in genderList){
		  			if(c == genderList[data].value){
		  				return genderList[data].label;
		  			}
		  		}
		  	 }},  
		  	{field:'numClientType',title:'客户类型', width:60,align:"center",formatter:function(value,row,index){
			  		var c = row.numClientType;
			  		if(c == undefined || c == "") return;
			  		for(var data in typeList){
			  			if(c == typeList[data].value){
			  				return typeList[data].label;
			  			}
			  		}
			  	 }},  
		    {field:'vacClientNumber',title:'手机/电话', width:100,align:"center"} ,
		  	{field:'zjType',title:'证件类别', width:100,align:"center",formatter:function(value,row,index){
		  		 if(null !=row.vacClientIdentityCode && row.vacClientIdentityCode != ""){
		  			 return "身份证";
		  		 }else  if(null != row.vacClientMilitaryCode && row.vacClientMilitaryCode != ""){
		  			 return "军官证";
		  		 }else  if(null != row.vacClientEepCode && row.vacClientEepCode != ""){
		  			 return "港澳台通行证";
		  		 }else  if(null != row.vacClientPassportCode && row.vacClientPassportCode != ""){
		  			 return "外国籍护照";
		  		 }else{
		  			 return "";
		  		 }
		  	 }},   
		  	 {field:'zjCode',title:'证件号码', width:150,align:"center",formatter:function(value,row,index){
		  		 if(null !=row.vacClientIdentityCode && row.vacClientIdentityCode != ""){
		  			 return row.vacClientIdentityCode;
		  		 }else  if(null != row.vacClientMilitaryCode && row.vacClientMilitaryCode != ""){
		  			 return row.vacClientMilitaryCode;
		  		 }else  if(null != row.vacClientEepCode && row.vacClientEepCode != ""){
		  			 return row.vacClientEepCode;
		  		 }else  if(null != row.vacClientPassportCode && row.vacClientPassportCode != ""){
		  			 return row.vacClientPassportCode;
		  		 }else{
		  			 return "";
		  		 }
		  	 }},   
		  	 {field:'vacClientAddress',title:'客户常住地址', width:150,align:"center",formatter:function(value,row,index){
		  		 return row.vacClientProvinceName + row.vacClientCityName + value;
		  	 }},   
		  	 {field:'numClientSource',title:'来源', width:60,align:"center",formatter:function(value,row,index){
			  		var s = row.numClientSource;
			  		if(s == undefined || s == "") return;
			  		for(var data in sourceList){
			  			if(s == sourceList[data].value){
			  				return sourceList[data].label;
			  			}
			  		}
			  	 }} ,
		  	 {field:'vacClientMemo',title:'备注', width:120,align:"center"}                                   
  		]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/tb/client/clientListData",
	            data:$("#client-form").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".client_view").hide();
	            		$(".client_datanull").show();
	            	}else{
	            		$(".client_view").show();
	            		$(".client_datanull").hide();
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
	function searchClient() {
		//做表单验证
		//验证表单
		var validate = $("#client-form").form("validate");
		if(!validate){
			return;
		} else{
			$("#searchClient").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#client-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchClient").linkbutton('enable');
		}
	}
	
	//重置
	 function reloadCur(){
			 $("#client-form").form("clear");
	}
	
	//删除客户
	 function deleteClient(){
	     // 返回被选中的行 然后集成的其实是 对象数组 
	     var row = $('#client-datagrid').datagrid('getSelections');  
	     var i = 0;  
	     var ids = "";  
	     if (row.length== 0) { 
	    	 $.messager.alert('提示','请选择需要删除的客户!');
	     } 
	     if (row.length>0) {  
	    	 $("#deleteClient").linkbutton('disable');
	         for(i;i<row.length;i++){  
		     	ids += row[i].id;  
		         if(i < row.length-1){  
		         	ids += ',';  
		         }else{  
		             break;  
		         }  
		     }
	         
	         $.messager.confirm('提示','是否删除客户信息？', function(r) {  
	             if (r) {  
	                 $.post('${ctx}/tb/client/deleteClient', {  
	                     id : ids
	                 }, function(result) {  
	                	 if(result.code == 1){
	                		 $.messager.alert('提示',result.message);
	                	 }else{
	                		 $.messager.alert('提示','删除成功');
	                  		 $('#client-datagrid').datagrid('reload'); 
	                	 }
	                	 $("#deleteClient").linkbutton('enable');
	                 });  
	             }else{
	            	 $("#deleteClient").linkbutton('enable');
	             }  
	         });  
	     }  
	}
	//打开dialog框
	 function newClient() {
		 $('#client-dialog').dialog('open').dialog('setTitle','新增客户信息');
		 $('#save-client-form').form('clear');
		 url = '${ctx}/tb/client/addClient';
   }  
	function editClient(){
		var rows = $('#client-datagrid').datagrid('getSelections');
		if(rows.length==0 || rows.length>1){
			$.messager.alert('提示','请选择一条数据进行修改!');
			return;
		}
		var row = $('#client-datagrid').datagrid('getSelected');
		if (row){
			$('#client-dialog').dialog('open').dialog('setTitle','修改客户信息');
			if(null !=row.vacClientIdentityCode && row.vacClientIdentityCode != ""){
		  		row.zjType="身份证";	
				row.zjCode = row.vacClientIdentityCode;
	  		 } else  if(null != row.vacClientMilitaryCode && row.vacClientMilitaryCode != ""){
	  			row.zjType="军官证";	
				row.zjCode = row.vacClientMilitaryCode;
	  		 }else  if(null != row.vacClientEepCode && row.vacClientEepCode != ""){
	  			row.zjType="港澳台通行证";	
				row.zjCode = row.vacClientEepCode;
	  		 }else  if(null != row.vacClientPassportCode && row.vacClientPassportCode != ""){
	  			row.zjType="外国籍护照";	
				row.zjCode = row.vacClientPassportCode;
	  		 }else{
	  			row.zjType="";	
				row.zjCode = "";
	  		 } 
			$('#save-client-form').form('load',row);
			url = '${ctx}/tb/client/editClient/'+row.id;
		}
	}
	//点击保存客户信息
	function saveClient(){
		if($("#vacClientName2").val().trim() == "" ){
			$.messager.alert('提示','请输入客户姓名');
			return ;
		}
		if($("#vacClientNumber2").val().trim() == "" ){
			$.messager.alert('提示','请输入手机/电话');
			return ;
		}
		$.messager.progress();	// 显示进度条
		$('#save-client-form').form('submit', {    
			url:url, 
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	var dataObj= eval('(' + data + ')');
	        	if(dataObj.code == 0){
	        		$.messager.alert('提示','数据保存成功!');
	        		$("#vacClientName2").textbox("setValue","");
	        		$("#vacClientNumber2").textbox("setValue","");
	        		$("#numClientGender2").textbox("setValue","");       
	        		$("#numClientType2").textbox("setValue","");
	        		$("#numClientProvinceId2").textbox("setValue","");
	        		$("#numClientCityId2").textbox("setValue","");
	        		$("#zjType2").textbox("setValue","");
	        		$("#zjCode2").textbox("setValue","");
	        		$("#vacClientMemo2").textbox("setValue","");
	        		$('#client-dialog').dialog('close');
		        	$("#client-datagrid").datagrid('load');
		        }else{
		        	$.messager.alert('提示','数据保存失败!原因：'+dataObj.message);
		        } 
		    }    
		});  
	}
</script>
<script type="text/javascript">
$(function() {  	
	  // 下拉框选择控件，下拉框的内容是动态查询数据库信息  
	  //dialog获取证件类型和证件号码
	  
	  //dialog保存——省份地市
	  $('#numClientProvinceId2').combobox(
				{
					editable : false, //不可编辑状态
					cache : false,
					panelHeight : '300px',//自动高度适合
					valueField : 'id', //值字段
					textField : 'vacProvinceName', //显示的字段
					url : '${ctx}/sms/province/findAllProvince',
					value : '${tbClients.numClientProvinceId}',
					onChange : function(pid) {
						$("#numClientCityId2").combobox("setValue", '');
						var orderIncPid = $("#numClientProvinceId2")
								.combobox('getValue');
						if (orderIncPid == '') {
							orderIncPid = 0;
						}
						$.ajax({
							type : "GET",
							url : '${ctx}/sms/city/findCityByProvId/'
									+ $("#numClientProvinceId2")
											.combobox('getValue'),
							cache : false,
							dataType : "json",
							success : function(data) {
								$("#numClientCityId2").combobox(
										"loadData", data);
							}
						});
					}
				});
		var orderIncCId = $('#numClientCityId2').val();
		var orderIncProId = $('#numClientProvinceId2').val();
		if (orderIncCId != '') {
			$('#numClientCityId2')
					.combobox(
							{
								editable : false, //不可编辑状态
								cache : false,
								panelHeight : 'auto',//自动高度适合
								valueField : 'id', //值字段
								textField : 'vacCityName', //显示的字段
								url : '${ctx}/sms/city/findCityByProvId/${tbClients.numClientProvinceId}',
								value : '${tbClients.numClientCityId}'
							});
		} else {
			if(orderIncProId != ''){
				$.ajax({
					type : "GET",
					url : '${ctx}/sms/city/findCityByProvId/'
							+ orderIncProId,
					cache : false,
					dataType : "json",
					success : function(data) {
						$("#numClientCityId2").combobox(
								"loadData", data);
					}
				});
			}
			
			$('#numClientCityId2').combobox({
				editable : false, //不可编辑状态
				cache : false,
				panelHeight : 'auto',//自动高度适合
				valueField : 'id', //值字段
				textField : 'vacCityName'//显示的字段
			});
		}
	$('#numClientProvinceId').combobox({ 
      editable:false, //不可编辑状态
      cache: false,
      panelHeight: '300px',//自动高度适合
      valueField:'id', //值字段
      textField:'vacProvinceName', //显示的字段
      url:'${ctx}/sms/province/findAllProvince',
		  onHidePanel: function(){
		      $("#numClientCityId").combobox("setValue",'');
		      var clientPid = $("#numClientProvinceId").combobox('getValue');
		      if(clientPid == ''){
		    	  clientPid = 0;
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

	  $('#numClientCityId').combobox({ 
	      editable:false, //不可编辑状态
	      cache: false,
	      panelHeight: 'auto',//自动高度适合
	      valueField:'id', //值字段
	      textField:'vacCityName'//显示的字段
 });
});
	 
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="client-form" method="post" action=""  modelAttribute="tbClients">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">客户姓名</span>
					<input class="easyui-textbox textbox_input" id="vacClientName" name="vacClientName"  value="${vacClientName}"   validType="length[0,20]" >	
					<span class="div_margin_left">手机/电话</span>
					<input class="easyui-textbox textbox_input"  id="vacClientNumber" name="vacClientNumber"  value="${vacClientNumber}"  validType="length[0,20]" />
					<span style="margin-left: 55px;">性别</span>
					<input class="easyui-combobox combobox_input" id="numClientGender" name="numClientGender" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/gender'" />
				</div>
				<br/>
				<div>
					<span class="div_margin_left">客户类型</span>
					<input class="easyui-combobox combobox_input" id="numClientType" name="numClientType" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/consultant_type'" />
					<span class="div_margin_left">常住地址</span>
					<input class="easyui-combobox combobox_input" id="numClientProvinceId" name="numClientProvinceId"/>-
					<input class="easyui-combobox combobox_input" id="numClientCityId" name="numClientCityId"/>
				</div>
				<br/>
					<div class="div_margin_left">
					<a href="javascript:searchClient();" id="searchClient"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				</div>
			<br/>
			<br/>
		</form>
			<div class="client_view">
				<table class="easyui-datagrid" data-options="
				toolbar:'#tb-tools'" id="client-datagrid" style="width: 100%;" pagination="true">
				</table>
				<br/>
				<br/>
			</div>
			<div id="tb-tools" style="padding:5px;height:auto">
			<permission:hasPermission action="tb/client/newClient">
				<a href="javascript:newClient();" class="easyui-linkbutton" id="newClient" iconCls="icon-add" style="color:blue;background-color: #BCDCDC;" plain="true">新增客户</a>
				</permission:hasPermission>
				<permission:hasPermission action="tb/client/editClient">
				<a href="javascript:editClient();" class="easyui-linkbutton" id="editClient" iconCls="icon-edit" style="color:blue;background-color: #BCDCDC;" plain="true">修改客户</a>
				</permission:hasPermission>
				<permission:hasPermission action="tb/client/deleteClient">
				<a href="javascript:deleteClient();" class="easyui-linkbutton" id="deleteClient" iconCls="icon-remove" style="color:red;background-color: #BCDCDC;" plain="true">删除客户</a>
				</permission:hasPermission>
			</div>
			<div class="client_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
			
	<div id="client-dialog" class="easyui-dialog" style="width:460px;height:400px;padding:10px 20px"
		closed="true"  buttons="#dlg-buttons">
	<form id="save-client-form" method="post" action="" modelAttribute="tbClient">
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>客户&nbsp;姓名</span>
			<input id="vacClientName2" class="easyui-textbox textbox_input" name="vacClientName" required="true">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>手机/电话</span>
			<input id="vacClientNumber2" name="vacClientNumber" class="easyui-textbox textbox_input" required="true">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2">&nbsp;&nbsp;性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</span>
			<input  style="line-height:24px;"  id="numClientGender2" class="div_margin_left easyui-combobox combobox_input" name="numClientGender"  editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/gender'" />
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2">&nbsp;&nbsp;客&nbsp;户&nbsp;类&nbsp;型</span>
			<input class="easyui-combobox combobox_input" id="numClientType2" name="numClientType"  editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/consultant_type'" >
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2">&nbsp;&nbsp;省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</span>
			<input class="easyui-combobox combobox_input" id="numClientProvinceId2" name="numClientProvinceId">-<input class="easyui-combobox combobox_input" name="numClientCityId" id="numClientCityId2">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2">&nbsp;&nbsp;证&nbsp;件&nbsp;类&nbsp;型</span>
			<input class="easyui-combobox combobox_input" id="zjType2" name="zjType" editable="false"
	   					data-options="valueField:'value',width:130,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/papers_type'"/>
			<input id="zjCode2"  name="zjCode" value="fuzhi" style="width:150px;" class="easyui-textbox textbox_input">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2">&nbsp;&nbsp;客&nbsp;户&nbsp;地&nbsp;址</span>
			<input id="vacClientAddress2" name="vacClientAddress" class="easyui-textbox textbox_input">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2">&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</span>
			<input id="vacClientMemo2" name="vacClientMemo" class="easyui-textbox textbox_input">
	</form>
		<div id="dlg-buttons" style="text-align:center;" >
			<a href="#" class="easyui-linkbutton"  onclick="saveClient()"  iconCls="icon-ok">保存</a>
			<a href="#" class="easyui-linkbutton"  onclick="javascript:$('#client-dialog').dialog('close');"  iconCls="icon-cancel">取消</a>
		</div>
</div>
</div>
