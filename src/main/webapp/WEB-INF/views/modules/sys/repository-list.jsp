<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<html>
<head>
<!-- 引入编辑器 -->
<link type="text/css" rel="stylesheet" href="${ctxStatic}/kindeditor-4.1.10/themes/default/default.css" />
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/kindeditor.js"></script>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/lang/zh_CN.js"></script>
<title>知识库管理</title>
<style type="text/css">
.margin-top20{margin-top:20px;}
.fontSize14{font-size:14px;}
.line-height30{line-height:30px;}
.text-align-right{font-weight:700;text-align:right;font-size:12px;}
.add-textBoxW{width:20%;margin-left:30px;}
</style>
</head>
<body>
<div id="repositoryTabs" class="easyui-tabs" tabPosition="left" style="width:98%;height:90%;margin:auto;padding-top:10px;">
	<div title="知识列表" class="tab">
		<div class="easyui-panel" style="padding:4px;width:100%;height:100%;" >
			<div class="easyui-panel" style="padding:4px;width:100%;height:20%;" >
				<form id="repositoryForm" method="post">  
					<br/>
					<font style="font-size:12px;font-weight:700;">知识类型:</font>
					<input class="easyui-combobox" id="type" name="type" value="" editable="false"
		  					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/repository_type'">
					<input class="easyui-textbox" id="description" name="description" validType="length[0,30]"  style="width:200px"
					data-options="width:200"> 
					<a id="searchRepo" href="javascript:void(0)" onclick="queryList()" class="easyui-linkbutton" >搜索</a> 
					<br/>
				</form>
			</div>
			<table class="easyui-datagrid" id="listGrid" style="width:100%;height:88%;">
			
			</table>
		</div>
	</div>
	<div title="创建知识" class="tab" >
		<div class="easyui-panel" style="padding:4px;padding-top:20px;width:100%;height:100%;">
			<input type="hidden" id="addid">
			<table class="line-height30" style="width:98%;">
				<tr>
					<td class="text-align-right" style="width:10%"><label>标题:</label></td>
					<td style="width:90%;padding-left:30px;">
						<input class="easyui-textbox add-textBoxW form-control input-sm" style="width:200px" data-options="required:true" validType="length[0,30]" id="addtitle"/>
						&nbsp;&nbsp;&nbsp;
						<font style="font-size:12px;font-weight:700;">知识类型:</font>
						<input class="easyui-combobox" id="addtype" editable="false"
		  					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/repository_type'">
					</td>
				</tr>
				<tr style="height:20px;"></tr>
				<tr>
					<td class="text-align-right" style="width:10%"><label>正文:</label></td>
					<td style="width:90%;padding-left:30px;">
						<textarea id="addcontent" style="width:89%;height:500px;margin-left: 10px;"></textarea>
					</td>
				</tr>
				<tr style="height:20px;"></tr>
				<tr>
					<td class="text-align-right"></td>
					<td>
						<a href="javascript:submitRepository();" id="addBtn" class="easyui-linkbutton margin-left20">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:reloadCur();" class="easyui-linkbutton margin-left20">重  置</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
<script>
var loadGrid = function(){
	$("#listGrid").datagrid({
	    fitColumns:true,
	    singleSelect:true,
	    pagination:true,
	    rownumbers:true,
	    url:"${ctx}/sys/repository/getRepInfos",
	    columns:[[
  	  		{field:'title',title:'标题',width:200},
  	  		{field:'addTime',title:'时间',width:100,align:"center"},
  	  		{field:'id',title:'操作',width:50,align:"center",formatter:function(value,row,index){
  	  			if(row==undefined) return;
  	  			return '<a href="javascript:editRepository(\''+row.id+'\',\''+row.repType+'\');" class="lkbtn">修改</a>'
  	  		}}
  		]], 
  		onLoadSuccess : function(){
  			$('.lkbtn').linkbutton({});
  		},
  		loader:function(param,success,error){
  			param.repType = $("#type").combobox("getValue");
  			if(param.repType == ""){
  				param.repType = $("#type").combobox("getData")[0].value;
  				$("#type").combobox("setValue",param.repType);
  			}
  			param.title = $("#description").textbox("getValue");
	    	$.ajax({
	            url:"${ctx}/sys/repository/getRepInfos",
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
var windowH_ = $(window).height();
$('#repositoryTabs').tabs({
	height: windowH_,
	//清空表单
	onUnselect : function(title,index){
		if(index == 1){
			window.editor.html("");
			$("#addtitle").textbox("setValue",""); 
			$("#addid").val(""); 
			$("#addtype").combobox("setValue","");
		}
	},
	onSelect : function(title,index){
	}
});

setTimeout(loadGrid,1000);

/**
 * 修改字典信息
 * rid  字典ID
 */
function editRepository(rid,retype){
	$.ajax({
	    url:"${ctx}/sys/repository/getRepInfoById",
	    data: {id:rid,repType:retype},
	    type:"post",
	    dataType:"json",
	    jsonpCallback:"callback",
	    success: function(data){
    		if(data.code == 200){
    			var info = data.info;
    			window.editor.html(info.content);
    			$("#addtitle").textbox("setValue",info.title); 
    			$("#addid").val(rid); 
    			$("#addtype").combobox("setValue",info.repType); 
            	$('#repositoryTabs').tabs('select', 1);
            }else{
            	$.messager.alert('提示','获取知识失败');
               	queryList(); 
            }
	    }
	})
	$('#repositoryTabs').tabs('select', 0);
}

//查询
function queryList(){
	$("#listGrid").datagrid('load');
	return;
}

//-------------------------知识库创建开始----------------------
var editor;
	KindEditor.ready(function(K) {
		editor = K.create('#addcontent', {
				uploadJson : '${ctx}/file_upload',//上传
                fileManagerJson : '${ctx}/file_manager',//文件管理
                allowFileManager : true,
                afterBlur: function () { this.sync(); },//数据同步
		});
	});


//点击提交	
function submitRepository() {
	$("#addBtn").linkbutton('disable');
	$.messager.progress();	// 显示进度条
	var html = window.editor.html();
	var addtitle = $("#addtitle").textbox("getValue"); 
	var addtype = $("#addtype").combobox("getValue"); 
	var id = $("#addid").val(); 
	$.ajax({
	    url:"${ctx}/sys/repository/addOrUpdate",
	    data: {id:id,title : addtitle,content : html,type:addtype},
	    type:"post",
	    dataType:"json",
	    jsonpCallback:"callback",
	    success: function(data){
	    	$.messager.progress("close");	// 关闭进度条
            $("#addBtn").linkbutton('enable');
    		if(data.code == 200){
               	if(id!='' && id!=null)
    	           	$.messager.alert('提示','知识新建成功'); 
               	else
	               	$.messager.alert('提示','知识更新成功'); 
               	$("#type").combobox("setValue",addtype);
               	queryList();
            	$('#repositoryTabs').tabs('select', 0);
            }else{
            	$.messager.alert('提示','知识新建失败'); 
            }
	    }
	})
}

//重置
 function reloadCur(){
	 $(':input','#vacTitle').val('');  
	 window.editor.html('');
 }
</script>
</html>

