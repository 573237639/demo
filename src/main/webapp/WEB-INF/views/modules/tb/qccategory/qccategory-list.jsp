<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
var qccateTypeList = ${qccateTypeList};
var loadGrid = function(){
	$("#qccategory-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
				 {field:'ck',checkbox:true},
				 {field:'id',title:'质检分类编号', width:100,align:"center"}, 
			 	 {field:'varCategoryName',title:'质检分类', width:50,align:"center"},  
		   	     {field:'numCategoryRank',title:'序列', width:60,align:"center"},  
				 {field:'numCategoryType',title:'类别', width:100,align:"center",formatter:function(value,row,index){
				  		var c = row.numCategoryType;
				  		if(c == undefined || c == "") return;
				  		for(var data in qccateTypeList){
				  			if(c == qccateTypeList[data].value){
				  				return qccateTypeList[data].label;
				  			}
				  		}
				  	 }}
		    ]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/tb/qccategory/qccategoryListData",
	            data:$("#qccategory-form").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".qccategory_view").hide();
	            		$(".qccategory_datanull").show();
	            	}else{
	            		$(".qccategory_view").show();
	            		$(".qccategory_datanull").hide();
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
	function searchQccategory() {
		//做表单验证
		//验证表单
		var validate = $("#qccategory-form").form("validate");
		if(!validate){
			return;
		} else{
			$("#searchQccategory").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#qccategory-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchQccategory").linkbutton('enable');
		}
	}
	
	//重置
	 function reloadCur(){
			 $("#qccategory-form").form("clear");
	}
	
	//删除客户
	 function deleteQccategory(){
	     // 返回被选中的行 然后集成的其实是 对象数组 
	     var row = $('#qccategory-datagrid').datagrid('getSelections');  
	     var i = 0;  
	     var ids = "";  
	     if (row.length== 0) { 
	    	 $.messager.alert('提示','请选择需要删除的分类!');
	     } 
	     if (row.length>0) {  
	    	 $("#deleteQccategory").linkbutton('disable');
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
	                 $.post('${ctx}/tb/client/deleteQccategory', {  
	                     id : ids
	                 }, function(result) {  
	                	 if(result.code == 1){
	                		 $.messager.alert('提示',result.message);
	                	 }else{
	                		 $.messager.alert('提示','删除成功');
	                  		 $('#qccategory-datagrid').datagrid('reload'); 
	                	 }
	                	 $("#deleteQccategory").linkbutton('enable');
	                 });  
	             }else{
	            	 $("#deleteQccategory").linkbutton('enable');
	             }  
	         });  
	     }  
	}
	//打开dialog框
	 function newQccategory() {
		 $('#qccategory-dialog').dialog('open').dialog('setTitle','新增质检分类信息');
		 $('#save-qccategory-form').form('clear');
		 url = '${ctx}/tb/client/addClient';
   }  
	function editQccategory(){
		var rows = $('#qccategory-datagrid').datagrid('getSelections');
		if(rows.length==0 || rows.length>1){
			$.messager.alert('提示','请选择一条数据进行修改!');
			return;
		}
		var row = $('#qccategory-datagrid').datagrid('getSelected');
		if (row){
			$('#qccategory-dialog').dialog('open').dialog('setTitle','修改质检分类信息');
			$('#save-qccategory-form').form('load',row);
			url = '${ctx}/tb/client/editQccategory/'+row.id;
		}
	}
	//点击保存质检分类信息
	function saveQccategory(){
		if($("#vacClientName2").val().trim() == "" ){
			$.messager.alert('提示','请输入客户姓名');
			return ;
		}
		if($("#vacClientNumber2").val().trim() == "" ){
			$.messager.alert('提示','请输入手机/电话');
			return ;
		}
		$.messager.progress();	// 显示进度条
		$('#save-qccategory-form').form('submit', {    
			url:url, 
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	var dataObj= eval('(' + data + ')');
	        	if(dataObj.code == 0){
	        		$.messager.alert('提示','数据质检分类成功!');
	        		$("#qccategory-form").form("clear");
	        		$('#qccategory-dialog').dialog('close');
		        	$("#qccategory-datagrid").datagrid('load');
		        }else{
		        	$.messager.alert('提示','数据质检分类失败!原因：'+dataObj.message);
		        } 
		    }    
		});  
	}
</script>
	<div class="easyui-panel" style="padding:4px">
		<form id="qccategory-form" method="post" action=""  modelAttribute="tbClients">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">分类编号</span>
					<input class="easyui-textbox textbox_input" id="id" name="id"  value="${id}"   validType="length[0,20]" >	
					<span class="div_margin_left">质检分类</span>
					<input class="easyui-textbox textbox_input"  id="varCategoryName" name="varCategoryName"  value="${varCategoryName}"  validType="length[0,20]" />
					<span style="margin-left: 55px;">质检类别</span>
					<input class="easyui-combobox combobox_input" id="numCategoryType" name="numCategoryType" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/qccate_type'" />
				</div>
				<br/>
					<div class="div_margin_left">
					<a href="javascript:searchQccategory();" id="searchQccategory"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				</div>
			<br/>
			<br/>
		</form>
			<div class="qccategory_view">
				<table class="easyui-datagrid" data-options="
				toolbar:'#tb-tools'" id="qccategory-datagrid" style="width: 100%;" pagination="true">
				</table>
				<br/>
				<br/>
			</div>
			<div id="tb-tools" style="padding:5px;height:auto">
			<permission:hasPermission action="tb/qccategory/newQccategory">
				<a href="javascript:newQccategory();" class="easyui-linkbutton" id="newQccategory" iconCls="icon-add" style="color:blue;background-color: #BCDCDC;" plain="true">新增分类</a>
				</permission:hasPermission>
				<permission:hasPermission action="tb/qccategory/editQccategory">
				<a href="javascript:editQccategory();" class="easyui-linkbutton" id="editQccategory" iconCls="icon-edit" style="color:blue;background-color: #BCDCDC;" plain="true">修改分类</a>
				</permission:hasPermission>
				<permission:hasPermission action="tb/qccategory/deleteQccategory">
				<a href="javascript:deleteQccategory();" class="easyui-linkbutton" id="deleteQccategory" iconCls="icon-remove" style="color:red;background-color: #BCDCDC;" plain="true">删除分类</a>
				</permission:hasPermission>
			</div>
			<div class="qccategory_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
			
	<div id="qccategory-dialog" class="easyui-dialog" style="width:460px;height:400px;padding:10px 20px"
		closed="true"  buttons="#dlg-buttons">
	<form id="save-qccategory-form" method="post" action="" modelAttribute="tbClient">
					<span class="div_margin_left">质检分类</span>
					<input class="easyui-textbox textbox_input" id="varCategoryName2" name="varCategoryName"  value="${varCategoryName}">	
					<p style="line-height:2px;"></p>
					<span class="div_margin_left">序列</span>
					<input class="easyui-textbox textbox_input"  id="numCategoryRank2" name="numCategoryRank"  value="${numCategoryRank}"  />
					<p style="line-height:2px;"></p>
					<span style="margin-left: 55px;">质检类别</span>
					<input class="easyui-combobox combobox_input" id="numCategoryType2" name="numCategoryType" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/qccate_type'" />
	</form>
		<div id="dlg-buttons" style="text-align:center;" >
			<a href="#" class="easyui-linkbutton"  onclick="saveQccategory()"  iconCls="icon-ok">保存</a>
			<a href="#" class="easyui-linkbutton"  onclick="javascript:$('#qccategory-dialog').dialog('close');"  iconCls="icon-cancel">取消</a>
		</div>
</div>
</div>
