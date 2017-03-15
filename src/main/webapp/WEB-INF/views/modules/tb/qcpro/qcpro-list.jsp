<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
var enabledDisabledList = ${enabledDisabledList};
var qccateTypeList = ${qccateTypeList};
var loadGrid = function(){
	$("#qcpro-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
				 {field:'ck',checkbox:true},
				 {field:'id',title:'项目编号', width:100,align:"center"}, 
			 	 {field:'vacQcproName',title:'项目名称', width:50,align:"center"},  
		   	     {field:'numQcproScore',title:'项目分数', width:60,align:"center"},  
				 {field:'numQcproMustCheckBit',title:'是否必合格', width:100,align:"center",formatter:function(value,row,index){
				  		var c = row.numQcproMustCheckBit;
				  		if(c == undefined || c === "") return;
				  		for(var data in qccateTypeList){
				  			if(c == qccateTypeList[data].value){
				  				return qccateTypeList[data].label;
				  			}
				  		}
				  	 }},
				 {field:'numQcproCheckScore',title:'合格比例', width:100,align:"center"}, 
			     {field:'numQcproBit',title:'是否可用', width:100,align:"center",formatter:function(value,row,index){
				  		var c = row.numQcproBit;
				  		if(c == undefined || c === "") return;
				  		for(var data in enabledDisabledList){
				  			if(c == enabledDisabledList[data].value){
				  				return enabledDisabledList[data].label;
				  			}
				  		}
				  	 }} ,
			     {
						field : 'vacCategoryName',
						title : '项目分类',
						width : 100,
						align : "center"
					}
		    ]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/tb/qcpro/qcproListData",
	            data:$("#qcpro-form").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".qcpro_view").hide();
	            		$(".qcpro_datanull").show();
	            	}else{
	            		$(".qcpro_view").show();
	            		$(".qcpro_datanull").hide();
	            	}
	                success(data);
	            }
	        });
  		}
	});
};
setTimeout(loadGrid,1000);
</script>

<script type="text/javascript">
	//点击搜索	
	function searchQcpro() {
		//做表单验证
		//验证表单
		var validate = $("#qcpro-form").form("validate");
		if(!validate){
			return;
		} else{
			$("#searchQcpro").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#qcpro-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchQcpro").linkbutton('enable');
		}
	}
	
	//重置
	 function reloadCur(){
			 $("#qcpro-form").form("clear");
	}
	
	//打开dialog框
	 function newQcpro() {
		 $('#qcpro-dialog').dialog('open').dialog('setTitle','新增质检项目信息');
		 $('#save-qcpro-form').form('clear');
		 url = '${ctx}/tb/qcpro/addQcPro';
   }  
	function editQcpro(){
		var rows = $('#qcpro-datagrid').datagrid('getSelections');
		if(rows.length==0 || rows.length>1){
			$.messager.alert('提示','请选择一条数据进行修改!');
			return;
		}
		var row = $('#qcpro-datagrid').datagrid('getSelected');
		if (row){
			$('#qcpro-dialog').dialog('open').dialog('setTitle','修改质检项目');
			$('#save-qcpro-form').form('load',row);
			url = '${ctx}/tb/qcpro/editQcPro/'+row.id;
		}
	}
	//点击保存质检项目信息
	function saveQcpro(){
		var validate = $("#save-qcpro-form").form("validate");
		if(!validate){
		$.messager.alert("消息提醒","所有项为必填项!","warning");
		   return;
		}
		$.messager.progress();	// 显示进度条
		$('#save-qcpro-form').form('submit', {    
			url:url, 
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	var dataObj= eval('(' + data + ')');
	        	if(dataObj.code == 0){
	        		$.messager.alert('提示','数据质检项目成功!');
	        		$("#qcpro-form").form("clear");
	        		$('#qcpro-dialog').dialog('close');
		        	$("#qcpro-datagrid").datagrid('load');
		        }else{
		        	$.messager.alert('提示','数据质检项目失败!原因：'+dataObj.message);
		        } 
		    }    
		});  
	}
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="qcpro-form" method="post" action=""  modelAttribute="tbQcPros,tbQcCategory">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">项目名称</span>
					<input class="easyui-textbox textbox_input" id="vacQcproName" name="vacQcproName"  value="${vacQcproName}">	
					<span class="div_margin_left">项目分数</span>
					<input class="easyui-textbox textbox_input" style="width: 60px;" id="numQcproScore" name="numQcproScore"  value="${numQcproScore}" />
					至 <input class="easyui-textbox textbox_input" style="width: 60px;" id="numQcproScore1" name="numQcproScore"  value="${numQcproScore}" />
					<span style="margin-left: 55px;">质检分类</span>
					<input class="easyui-combobox combobox_input" id="numCategoryId" name="numCategoryId" editable="false"
						data-options="valueField:'id',width:260,panelHeight:'200',textField:'varCategoryName',url:'${ctx}/tb/qccategory/findCategory'" />
					<span style="margin-left: 55px;">是否必合格</span>
					<input  style="line-height:24px;"  id="numQcproMustCheckBit" class="div_margin_left easyui-combobox combobox_input" name="numQcproMustCheckBit" editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/qcpro_qualified'" />
				</div>
				<br/>
					<div class="div_margin_left">
					<a href="javascript:searchQcpro();" id="searchQcpro"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				</div>
			<br/>
			<br/>
		</form>
			<div class="qcpro_view">
				<table class="easyui-datagrid" data-options="
				toolbar:'#tb-tools'" id="qcpro-datagrid" style="width: 100%;" pagination="true">
				</table>
				<br/>
				<br/>
			</div>
			<div id="tb-tools" style="padding:5px;height:auto">
			<permission:hasPermission action="tb/qcpro/newQcpro">
				<a href="javascript:newQcpro();" class="easyui-linkbutton" id="newQcpro" iconCls="icon-add" style="color:blue;background-color: #BCDCDC;" plain="true">新增项目</a>
				</permission:hasPermission>
				<permission:hasPermission action="tb/qcpro/editQcpro">
				<a href="javascript:editQcpro();" class="easyui-linkbutton" id="editQcpro" iconCls="icon-edit" style="color:blue;background-color: #BCDCDC;" plain="true">修改项目</a>
				</permission:hasPermission>
			</div>
			<div class="qcpro_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
			
	<div id="qcpro-dialog" class="easyui-dialog" style="width:460px;height:340px;padding:10px 20px"
		closed="true"  buttons="#dlg-buttons">
	<form id="save-qcpro-form" method="post" action="" modelAttribute="tbQcPro">
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>项&nbsp;&nbsp;&nbsp;目&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;类</span>
			<input  style="line-height:24px;"  id="numCategoryId2" class="div_margin_left easyui-combobox combobox_input" name="numCategoryId" required="true" editable="false" 
						data-options="valueField:'id',width:260,panelHeight:'200',textField:'varCategoryName',url:'${ctx}/tb/qccategory/findCategory'" />
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>项&nbsp;&nbsp;&nbsp;目&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;称</span>
			<input id="vacQcproName2" style="width: 260px;" class="easyui-textbox textbox_input" name="vacQcproName" required="true">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>是&nbsp;否&nbsp;必&nbsp;合&nbsp;格</span>
			<input  style="line-height:24px;"  id="numQcproMustCheckBit2" class="div_margin_left easyui-combobox combobox_input" name="numQcproMustCheckBit" required="true" editable="false" 
						data-options="valueField:'value',width:260,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/qcpro_qualified'" />
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>质&nbsp;&nbsp;&nbsp;检&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;数</span>
			<input id="numQcproScore2" name="numQcproScore"  style="width: 260px;" class="easyui-textbox textbox_input" required="true">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>合格比例/分数</span>
			<input id="numQcproCheckScore2"  style="width: 260px;" name="numQcproCheckScore" class="easyui-textbox textbox_input" required="true">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>是&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;可&nbsp;&nbsp;&nbsp;用</span>
				<input  style="line-height:24px;"  id="isDeleted2" class="div_margin_left  easyui-combobox combobox_input" name="isDeleted" required="true" editable="false" 
						data-options="valueField:'value',width:260,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/enabled_disabled'" />
	</form>
		<div id="dlg-buttons" style="text-align:center;" >
			<a href="#" class="easyui-linkbutton"  onclick="saveQcpro();"  iconCls="icon-ok">保存</a>
			<a href="#" class="easyui-linkbutton"  onclick="javascript:$('#qcpro-dialog').dialog('close');"  iconCls="icon-cancel">取消</a>
		</div>
</div>
</div>
