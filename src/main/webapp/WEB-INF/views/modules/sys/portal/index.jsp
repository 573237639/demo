<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script>
var noticeLoadGrid = function(){
	$("#noticeindex-datagrid").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
  	  		{field:'vacTitle',title:'标题',width:'30%',align:"left",formatter:function(value,row,index){
	  			return  '<a href="javascript:viewNotice('+row.id+');" class="lkbtn"  ><span style="color:#000000">'+value+'</span></a>&nbsp;'
	  		}},
  	  		{field:'gmtModifiedString',title:'发布时间',width:200,align:"left"}
  		]], 
  		loader:function(param,success,error){
  			param.page = 0;
  			param.rows = 10;
	    	$.ajax({
	            url:"${ctx}/sys/notice/noticeListData",
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
setTimeout(noticeLoadGrid,1000);
</script>
<script type="text/javascript">

function viewNotice(id){
	window.parent.window.newTab(id,"/sys/notice/sysNotice-view","公告详情","icon-save");
}
</script>
<script type="text/javascript">


document.onkeydown = function (e) {
	var ev = window.event || e;
	var code = ev.keyCode || ev.which;
	if (code == 116) {
		if(ev.preventDefault) {
			ev.preventDefault();
		} else {
			ev.keyCode = 0;
			ev.returnValue = false;
		}
	}
}

function fillLedger(){
	window.parent.window.newTab('',"/tb/ledger/fill-ledger-list","待填写来电台账","icon-save");
}

function auditOrder(){
	window.parent.window.newTab('',"/tb/order/order-audit-index","待审核工单列表","icon-save");
}

function addNotice(){
	window.parent.window.newTab('',"/sys/notice/sysNotice-add","新增公告:","icon-save");
}

function countLedger(){
// 	alert("日台账总生成量");
}

</script>
<!-- 知识库 -->
<script>
var repLoadGrid = function(){
	$("#listGridEast").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
  	  		{field:'title',title:'标题',width:'30%',align:"left"},
  	  		{field:'content',title:'内容',width:200,align:"left",formatter:function(value,row,index){
 	  			return  '<div style="display:none;" id="rep_'+row.id+'">'+row.content+'</div><a href="javascript:showContent(\''
			+ row.title + '\',\''+ row.id +'\');" class="lkbtn" ><span style="color:red">内容详情</span></a>&nbsp;'+
			'<a href="javascript:showDetails(\''
			+ row.id+ '\',\''
			+ row.repType+ '\',\''
		+ row.title
		+ '\');" class="lkbtn"  ><span style="color:red">添加意见</span></a>&nbsp;'
	  		}}
  		]], 
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	    		url : "${ctx}/sys/repository/getRepInfos",
	    		data:$("#repositoryForm").serialize(),
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
setTimeout(repLoadGrid,1000);
</script>
<script>
	//查询
	function queryList() {
		$("#listGridEast").datagrid('load');
		return;
	}

	function showDetails(id, type, title) {
		$('#dlg').dialog('open').dialog('setTitle','意见栏');
		$("#repositoryTtile").html(title);
		$("#repositoryId").val(id);
		$("#repositoryType").val(type);
		$("#repositoryTitle").val(title);
		$("#repSuggest").val("");

	}
	
	function showContent(title,id){
		$('#dlg-title').dialog('open').dialog('setTitle','内容详情');
		$("#repositoryTtile_c").html(title);
		$("#repositoryContent_c").html($("#rep_"+id).html());
	}
	
	function addrepSuggest(){
		var rid = $("#repositoryId").val();
		var rtype = $("#repositoryType").val();
		var rtitle = $("#repositoryTitle").val();
		var repSuggest = $("#repSuggest").val();
		if(repSuggest.length==0 || repSuggest == null){
			$.messager.alert('提示','知识意见不能为空');
			return;
		}
		$.ajax({
		    url:"${ctx}/sys/repository/addrepSuggest",
		    data: {varOpinionId:rid,varOpinionType:rtype,varOpinionTitle:rtitle,varOpinionContent:repSuggest},
		    type:"post",
		    dataType:"json",
		    jsonpCallback:"callback",
		    success: function(data){
	    		if(data.code == 200){
	            	$.messager.alert('提示','知识意见提交成功');
	            	$('#dlg').dialog('close');
	            }else{
	            	$.messager.alert('提示','知识意见提交失败');
	            }
		    }
		})
	}
</script>
<div style="margin: 10px;margin-left: 20px;margin-top: 40px;">
	<button class="btn"   onclick="fillLedger()"  style="width:200px;height:120px;background-color:#fafafa;border-color:#d5d5d5;border-width: 1px;">
	<br/>
	<span style="color:#666666;font-size: 10px;">待填写台账</span><br/><span style="font-size: 46px;color:#31b0d5 ">${fill}</span></button>
	<button class="btn"   onclick="auditOrder()"style="width:200px;height:120px;background-color:#fafafa;border-color:#d5d5d5;border-width: 1px;margin-left: 10px;">
	<br/>
	<span style="color:#666666;font-size: 10px;">待审核工单</span><br/><span style="font-size: 46px;color:#31b0d5 ">${audit}</span></button>
	<button class="btn"   onclick="countLedger()"style="width:200px;height:120px;background-color:#fafafa;border-color:#d5d5d5;border-width: 1px;margin-left: 10px;">
	<br/>
	<span style="color:#666666;font-size: 10px;">日台账登录人总生成量</span><br/><span style="font-size: 46px;color:#31b0d5 ">${adminCount}</span></button>
	<button class="btn"   onclick="countLedger()"style="width:200px;height:120px;background-color:#fafafa;border-color:#d5d5d5;border-width: 1px;margin-left: 10px;">
	<br/>
	<span style="color:#666666;font-size: 10px;">日台账系统总生成量</span><br/><span style="font-size: 46px;color:#31b0d5 ">${systemCount}</span></button>

</div>
<br/>
<p/>
<div class="div_margin_left demo_line_02"></div>
<p/>
<!-- 知识库 -->
<br/>
<span style="font-size: 20px;margin-left: 20px;" >知识库信息</span><span style="font-style: right"></span>
<div style="margin: 10px;margin-left: 20px;">
	<form id="repositoryForm" method="post">
		<input type="hidden" id="page" name="page"/>
		<input type="hidden" id="rows" name="rows">
		<input class="easyui-combobox combobox_input" id="repType" name="repType" value="type1" editable="false"
			data-options="valueField:'value',width:100,height:30,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/repository_type'">

		 <input class="easyui-textbox textbox_input"  id="title"
			name="title" validType="length[0,30]" style="width: 160px;height: 30px;">
		<a id="searchRepo" href="javascript:void(0)" onclick="queryList()" iconCls="icon-search"  class="easyui-linkbutton a_button">搜索</a> <br />
	</form>
</div>
<div id="listGridEast_div" style="margin: 10px;margin-left: 20px;">
	<table class="easyui-datagrid" id="listGridEast" style="width: 100%;margin-left: 20px;" pagination="true"  ></table>
</div>
<br/>
<p/>
<div class="div_margin_left demo_line_02"></div>
<p/>
<br/>
<!-- 公告 -->
<span style="font-size: 20px;margin-left: 20px;" >最新公告</span><span style="font-style: right"></span>
<permission:hasPermission action="sys/notice/sysNotice-add">
	<a href="javascript:addNotice();" id="addNotice"  class="easyui-linkbutton a_button">发布公告</a>
</permission:hasPermission>
<div style="margin: 10px;margin-left: 20px;">
	<table class="easyui-datagrid" id="noticeindex-datagrid" 	style="width: 100%;margin-left: 20px;" ></table>
</div>
<!-- 知识库内容显示 -->
<div id="dlg-title" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
	<div class="ftitle">
	标题：<p style="font-size: 12px; font-weight: 700;" id="repositoryTtile_c"></p>
	内容：<p style="font-size: 12px; font-weight: 700;" id="repositoryContent_c"></p>
	</div>
</div>
<!-- 意见栏 -->
<div id="dlg" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
		closed="true" buttons="#dlg-buttons">
	<form id="addrepSuggest" method="post">
		<input type="hidden" id="repositoryId"/>
		<input type="hidden" id="repositoryType"/>
		<input type="hidden" id="repositoryTitle"/>
		<div class="ftitle">
		标题：<p style="font-size: 12px; font-weight: 700;" id="repositoryTtile"></p>
		</div>
		意见栏：<br/>
		<textarea id="repSuggest" style="width:96%;height:90px;"></textarea>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="addrepSuggest()">Save</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
</div>