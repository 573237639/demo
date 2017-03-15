<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
var loadGrid = function(){
	$("#qcbase-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
					{field:'ck',checkbox:true},
					{field:'id',title:'质检编号', width:100,align:"center"}, 
					{field:'vacQcbaseSerial',title:'流水号', width:50,align:"center"},  
					{field:'vacLawId',title:'律师工号', width:60,align:"center"},  
					{field:'vacLawyerName',title:'律师姓名', width:60,align:"center"},
					{field:'dateQcbaseConsultString',title:'咨询日期', width:60,align:"center"},
					{field:'numQcbaseScore',title:'质检得分', width:60,align:"center"},
					{field:'numQcbaseCheckBit',title:'是否合格', width:60,align:"center"},
					{field:'numQcbaseCheckState',title:'质检审核', width:60,align:"center"},
					{field:'vacQcbaseSummary',title:'用户问题', width:60,align:"center"},
					{field:'vacQcbaseImprove',title:'律师意见', width:60,align:"center"},
					{field:'vacQcbaseComment',title:'综合评价与改进建议', width:60,align:"center"},
					{field:'vacQcbaseMemo',title:'备注', width:60,align:"center"},
					{field:'vacQcbaseName',title:'质检员', width:100,align:"center"},
					{field:'dateQcbaseTimeString',title:'质检时间', width:60,align:"center"}
		    ]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/tb/qcbase/qcbaseListData",
	            data:$("#qcbase-form").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".qcbase_view").hide();
	            		$(".qcbase_datanull").show();
	            	}else{
	            		$(".qcbase_view").show();
	            		$(".qcbase_datanull").hide();
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
	function searchQcbase() {
		//做表单验证
		//验证表单
		var validate = $("#qcbase-form").form("validate");
		if(!validate){
			return;
		} else{
			$("#searchQcbase").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#qcbase-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchQcbase").linkbutton('enable');
		}
	}
	
	//重置
	 function reloadCur(){
			 $("#qcbase-form").form("clear");
	}
	
	//删除客户
	 function deleteQcbase(){
	     // 返回被选中的行 然后集成的其实是 对象数组 
	     var row = $('#qcbase-datagrid').datagrid('getSelections');  
	     var i = 0;  
	     var ids = "";  
	     if (row.length== 0) { 
	    	 $.messager.alert('提示','请选择需要删除的分类!');
	     } 
	     if (row.length>0) {  
	    	 $("#deleteQcbase").linkbutton('disable');
	         for(i;i<row.length;i++){  
		     	ids += row[i].id;  
		         if(i < row.length-1){  
		         	ids += ',';  
		         }else{  
		             break;  
		         }  
		     }
	         
	         $.messager.confirm('提示','是否删除质检分类信息？', function(r) {  
	             if (r) {  
	                 $.post('${ctx}/tb/qcbase/deleteQcbase', {  
	                     id : ids
	                 }, function(result) {  
	                	 if(result.code == 1){
	                		 $.messager.alert('提示',result.message);
	                	 }else{
	                		 $.messager.alert('提示','删除成功');
	                  		 $('#qcbase-datagrid').datagrid('reload'); 
	                	 }
	                	 $("#deleteQcbase").linkbutton('enable');
	                 });  
	             }else{
	            	 $("#deleteQcbase").linkbutton('enable');
	             }  
	         });  
	     }  
	}
	//打开dialog框
	 function newQcbase() {
		 $('#qcbase-dialog').dialog('open').dialog('setTitle','新增质检分类信息');
		 $('#save-qcbase-form').form('clear');
		 url = '${ctx}/tb/qcbase/addQcbase';
   }  
	function editQcbase(){
		var rows = $('#qcbase-datagrid').datagrid('getSelections');
		if(rows.length==0 || rows.length>1){
			$.messager.alert('提示','请选择一条数据进行修改!');
			return;
		}
		var row = $('#qcbase-datagrid').datagrid('getSelected');
		if (row){
			$('#qcbase-dialog').dialog('open').dialog('setTitle','修改质检分类信息');
			$('#save-qcbase-form').form('load',row);
			url = '${ctx}/tb/qcbase/editQcbase/'+row.id;
		}
	}
	//点击保存质检分类信息
	function saveQcbase(){
		if($("#vacClientName2").val().trim() == "" ){
			$.messager.alert('提示','请输入客户姓名');
			return ;
		}
		if($("#vacClientNumber2").val().trim() == "" ){
			$.messager.alert('提示','请输入手机/电话');
			return ;
		}
		$.messager.progress();	// 显示进度条
		$('#save-qcbase-form').form('submit', {    
			url:url, 
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	var dataObj= eval('(' + data + ')');
	        	if(dataObj.code == 0){
	        		$.messager.alert('提示','数据质检分类成功!');
	        		$("#qcbase-form").form("clear");
	        		$('#qcbase-dialog').dialog('close');
		        	$("#qcbase-datagrid").datagrid('load');
		        }else{
		        	$.messager.alert('提示','数据质检分类失败!原因：'+dataObj.message);
		        } 
		    }    
		});  
	}
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="qcbase-form" method="post" action=""  modelAttribute="tbClients">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">流水号</span>
					<input class="easyui-textbox textbox_input" id="vacClientName" name="vacClientName"  value="${vacClientName}"   validType="length[0,20]" >	
					<span>质检时间</span>
				  	  <input class="easyui-datetimebox" id="gmtCreatedBegin" name="gmtCreatedBegin"  editable="false"  style="width:200px;height:26px;">-
				   	 <input class="easyui-datetimebox" id="gmtCreatedEnd" name="gmtCreatedEnd" editable="false" style="width:200px;height:26px;">
					
				</div>
				<p style="line-height:2px;"></p>
				<div>
    				<span class="div_margin_left">律师工号</span>
					<input class="easyui-textbox textbox_input" id="vacClientName" name="vacClientName"  value="${vacClientName}"   validType="length[0,20]" >	
					<span class="div_margin_left">是否合格</span>
					<input class="easyui-combobox combobox_input" id="numClientGender" name="numClientGender" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/qcpro_qualified'" />
				</div>
				<br/>
					<div class="div_margin_left">
					<a href="javascript:searchQcbase();" id="searchQcbase"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				</div>
			<br/>
			<br/>
		</form>
			<div class="qcbase_view">
				<table class="easyui-datagrid" data-options="
				toolbar:'#tb-tools'" id="qcbase-datagrid" style="width: 100%;" pagination="true">
				</table>
				<br/>
				<br/>
			</div>
			<div id="tb-tools" style="padding:5px;height:auto">
			<permission:hasPermission action="tb/client/newQcbase">
				<a href="javascript:newQcbase();" class="easyui-linkbutton" id="newQcbase" iconCls="icon-add" style="color:blue;background-color: #BCDCDC;" plain="true">新增分类</a>
				</permission:hasPermission>
				<permission:hasPermission action="tb/client/editQcbase">
				<a href="javascript:editQcbase();" class="easyui-linkbutton" id="editQcbase" iconCls="icon-edit" style="color:blue;background-color: #BCDCDC;" plain="true">修改分类</a>
				</permission:hasPermission>
				<permission:hasPermission action="tb/client/deleteQcbase">
				<a href="javascript:deleteQcbase();" class="easyui-linkbutton" id="deleteQcbase" iconCls="icon-remove" style="color:red;background-color: #BCDCDC;" plain="true">删除分类</a>
				</permission:hasPermission>
			</div>
			<div class="qcbase_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
			
	<div id="qcbase-dialog" class="easyui-dialog" style="width:460px;height:400px;padding:10px 20px"
		closed="true"  buttons="#dlg-buttons">
	<form id="save-qcbase-form" method="post" action="" modelAttribute="tbClient">
					<span class="div_margin_left">质检分类</span>
					<input class="easyui-textbox textbox_input" id="vacClientName" name="vacClientName"  value="${vacClientName}"   validType="length[0,20]" >	
					<p style="line-height:2px;"></p>
					<span class="div_margin_left">序列</span>
					<input class="easyui-textbox textbox_input"  id="vacClientNumber" name="vacClientNumber"  value="${vacClientNumber}"  validType="length[0,20]" />
					<p style="line-height:2px;"></p>
					<span style="margin-left: 55px;">质检类别</span>
					<input class="easyui-combobox combobox_input" id="numClientGender" name="numClientGender" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/gender'" />
	</form>
		<div id="dlg-buttons" style="text-align:center;" >
			<a href="#" class="easyui-linkbutton"  onclick="saveQcbase()"  iconCls="icon-ok">保存</a>
			<a href="#" class="easyui-linkbutton"  onclick="javascript:$('#qcbase-dialog').dialog('close');"  iconCls="icon-cancel">取消</a>
		</div>
</div>
</div>
