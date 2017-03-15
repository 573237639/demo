<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
var orgTypeList = ${orgTypeList};
var loadGrid = function(){
	$("#organization-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
			 {field:'ck',checkbox:true},
		  	 {field:'vacOrgName',title:'机构名称', width:100,align:"left"}, 
			 {field:'numOrgType',title:'机构类型', width:50,align:"center",formatter:function(value,row,index){
		  		var c = row.numOrgType;
		  		if(c == undefined || c == "") return;
		  		for(var data in orgTypeList){
		  			if(c == orgTypeList[data].value){
		  				return orgTypeList[data].label;
		  			}
		  		}
		  	 }},
			 {field:'vacOrgTel',title:'联系电话', width:50,align:"left"},
		  	 {field:'vacOrgTime',title:'办公时间', width:150,align:"center"},  
		  	 {field:'vacOrgAddress',title:'详细地址', width:200,align:"left"}  
			]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/cms/organization/organizationListData",
	            data:$("#organization-form").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".organization_view").hide();
	            		$(".organization_datanull").show();
	            	}else{
	            		$(".organization_view").show();
	            		$(".organization_datanull").hide();
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
	function searchOrganization() {
		//做表单验证
		//验证表单
		var validate = $("#organization-form").form("validate");
		if(!validate){
			return;
		} else{
			$("#searchOrganization").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#organization-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchOrganization").linkbutton('enable');
		}
	}
	
	//重置
	 function reloadCur(){
			 $("#organization-form").form("clear");
			 $("#provinceName").combobox('setValue',"广东省");
	}
	
	//删除机构信息
	 function deleteCmsOrganization(){
	     // 返回被选中的行 然后集成的其实是 对象数组 
	     var row = $('#organization-datagrid').datagrid('getSelections');  
	     var i = 0;  
	     var ids = "";  
	     if (row.length== 0) { 
	    	 $.messager.alert('提示','请选择需要删除的机构!');
	     } 
	     if (row.length>0) {  
	    	 $("#deleteCmsOrganization").linkbutton('disable');
	         for(i;i<row.length;i++){  
		     	ids += row[i].id;  
		         if(i < row.length-1){  
		         	ids += ',';  
		         }else{  
		             break;  
		         }  
		     }
	         
	         $.messager.confirm('提示','是否删除机构信息？', function(r) {  
	             if (r) {  
	                 $.post('${ctx}/cms/organization/deleteCmsOrganization', {  
	                     id : ids
	                 }, function(result) {  
	                	 if(result.code == 1){
	                		 $.messager.alert('提示',result.message);
	                	 }else{
	                		 $.messager.alert('提示','删除成功');
	                  		 $('#organization-datagrid').datagrid('reload'); 
	                	 }
	                	 $("#deleteCmsOrganization").linkbutton('enable');
	                 });  
	             }else{
	            	 $("#deleteCmsOrganization").linkbutton('enable');
	             }  
	         });  
	     }  
	}
	//打开dialog新增机构框
	 function newCmsOrganization() {
		 $('#organization-dialog').dialog('open').dialog('setTitle','新增机构信息');
		 $('#save-organization-form').form('clear');
		 url = '${ctx}/cms/organization/addCmsOrganization';
   }  
	//打开机构编辑框
	function editCmsOrganization(){
		var rows = $('#organization-datagrid').datagrid('getSelections');
		if(rows.length==0 || rows.length>1){
			$.messager.alert('提示','请选择一条数据进行修改!');
			return;
		}
		var row = $('#organization-datagrid').datagrid('getSelected');
		if (row){
			$('#organization-dialog').dialog('open').dialog('setTitle','修改机构信息');
			$('#save-organization-form').form('load',row);
			url = '${ctx}/cms/organization/editCmsOrganization/'+row.id;
		}
	}
	//点击保存/修改机构信息
	function saveCmsOrganization(){          
		if($("#numOrgType2").combobox('getValue').trim() == "" ){
			$.messager.alert('提示','请输入机构类型!');
			return ;
		}
		if($("#vacOrgName2").val().trim() == "" ){
			$.messager.alert('提示','请输入机构名称!');
			return ;
		}
		if($("#vacOrgAddress2").val().trim() == "" ){
			$.messager.alert('提示','请输入详细地址!');
			return ;
		}
		if($("#vacOrgTime2").val().trim() == "" ){
			$.messager.alert('提示','请输入办公时间!');
			return ;
		}
		if($("#vacOrgTel2").val().trim() == "" ){
			$.messager.alert('提示','请输入联系电话!');
			return ;
		}
		$.messager.progress();	// 显示进度条
		$('#save-organization-form').form('submit', {    
			url:url, 
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	var dataObj= eval('(' + data + ')');
	        	if(dataObj.code == 0){
	        		$.messager.alert('提示','数据保存成功!');
	        		$('#save-organization-form').form("clear");
	        		$('#organization-dialog').dialog('close');
		        	$("#organization-datagrid").datagrid('load');
		        }else{
		        	$.messager.alert('提示','数据保存失败!原因：'+dataObj.message);
		        } 
		    }    
		});  
	}
</script>
<script >
$(function(){
	      $.ajax({
		        type: "GET",
		        url:'${ctx}/sms/city/findCityByProvId/16',
		        cache: false,
		        dataType : "json",
		        success: function(data){
		        	$("#vacCityName").combobox("loadData",data);
		         }
		       }); 	
	  $('#vacCityName').combobox({ 
	      editable:false, //不可编辑状态
	      cache: false,
	      panelHeight: '300',//自动高度适合
	      valueField:'vacCityName', //值字段
	      textField:'vacCityName'//显示的字段
    });
})
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="organization-form" method="post" action=""  modelAttribute="cmsOrganization">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">机构名称 </span>
					<input class="easyui-textbox textbox_input" id="vacOrgName" name="vacOrgName"  value="${vacOrgName}" />	
					<span style="margin-left: 55px;">机构类型  </span>
					<input class="easyui-combobox combobox_input" id="numOrgType" name="numOrgType" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/org_law_type'" />
					<span class="div_margin_left">联系电话  </span>
					<input class="easyui-textbox textbox_input"  id="vacOrgTel" name="vacOrgTel"  value="${vacOrgTel}" />
					<span class="div_margin_left">地区  </span>
					<input class="easyui-combobox combobox_input" id="provinceName" value="广东省" disabled="disabled"/> -
					<input class="easyui-combobox combobox_input" id="vacCityName" name="vacCityName"/>
				</div>
				<br/>
				<div>
					<span class="div_margin_left">详细地址</span>
					<input class="easyui-textbox textbox_input" style="width: 700px;" id="vacOrgAddress" name="vacOrgAddress"  value="${vacOrgAddress}" />
				</div>
				<br/>
					<div class="div_margin_left">
					<a href="javascript:searchOrganization();" id="searchOrganization"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				</div>
			<br/>
			<br/>
		</form>
			<div class="organization_view">
				<table class="easyui-datagrid" data-options="
				toolbar:'#org-tools'" id="organization-datagrid" style="width: 100%;" pagination="true">
				</table>
				<br/>
				<br/>
			</div>
			<div id="org-tools" style="padding:5px;height:auto">
			<permission:hasPermission action="cms/organization/newCmsOrganization">
				<a href="#" class="easyui-linkbutton" id="newCmsOrganization" iconCls="icon-add"  onclick="newCmsOrganization()"  style="color:blue;background-color: #BCDCDC;" plain="true">新增机构</a>
				</permission:hasPermission>
				<permission:hasPermission action="cms/organization/editCmsOrganization">
				<a href="javascript:editCmsOrganization();" class="easyui-linkbutton" id="editCmsOrganization" iconCls="icon-edit" style="color:blue;background-color: #BCDCDC;" plain="true">修改机构</a>
				</permission:hasPermission>
				<permission:hasPermission action="cms/organization/deleteCmsOrganization">
				<a href="javascript:deleteCmsOrganization();" class="easyui-linkbutton" id="deleteCmsOrganization" iconCls="icon-remove" style="color:red;background-color: #BCDCDC;" plain="true">删除机构</a>
				</permission:hasPermission>
			</div>
			<div class="organization_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
			
	<div id="organization-dialog" class="easyui-dialog" style="width:700px;height:300px;padding:10px 20px"
		closed="true"  buttons="#dlg-buttons">
	<form id="save-organization-form" method="post" action="" modelAttribute="cmsOrg">
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>机构类型</span>
			<input class="easyui-combobox combobox_input" id="numOrgType2" name="numOrgType" editable="false" required="true"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/org_law_type'" />
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>机构名称</span>
			<input id="vacOrgName2" class="easyui-textbox textbox_input" style="width: 500px;" name="vacOrgName" required="true">
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>详细地址</span>
			<input class="easyui-textbox textbox_input" id="vacOrgAddress2" style="width: 500px;" name="vacOrgAddress" required="true" />
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>办公时间</span>
			<input class="easyui-textbox textbox_input" id="vacOrgTime2" style="width: 500px;" name="vacOrgTime" required="true" />
			<p style="line-height:2px;"></p>
			<span class="div_margin_left textbox_title_2"><span class="textbox_title_1" >* </span>联系电话</span>
			<input class="easyui-textbox textbox_input"  id="vacOrgTel2" style="width: 500px;" name="vacOrgTel" required="true"/>
	</form>
		<div id="dlg-buttons" style="text-align:center;" >
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCmsOrganization()">保存</a>
			<a href="#" class="easyui-linkbutton"  onclick="javascript:$('#organization-dialog').dialog('close')"  iconCls="icon-cancel">取消</a>
		</div>
</div>
</div>
