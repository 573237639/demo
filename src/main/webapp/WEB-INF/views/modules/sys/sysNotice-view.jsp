<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<!-- 引入编辑器 -->
<link type="text/css" rel="stylesheet" href="${ctxStatic}/kindeditor-4.1.10/themes/default/default.css" />
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/kindeditor.js"></script>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script>
        KindEditor.ready(function(K) {
                window.editor = K.create('#editor_id').readonly(true);
        });
</script>
<script type="text/javascript">
function editNotice(id){
	window.parent.window.newTab(id,"/sys/notice/sysNotice-edit","修改公告:","icon-save");
}

function listNotice(){
	window.parent.window.newTab('',"/sys/notice/noticeList","公告管理:","icon-save");
}


</script>
	<div class="easyui-panel" style="padding:4px;margin-left: 20px;" align="left">
			<form id="addnotice-form" method="post" action=""  modelAttribute="sysNotice">
			<br/>
			<br/>
			<input class="hidden"   id="id" name="id" value="${sysNotice.id }"  />
			<span style="font-size: 26px;font-weight: bold;">${sysNotice.vacTitle }</span>
			<br/>
			<span style="font-size: 16px;color: gray;">${sysNotice.gmtModifiedString}</span>
			<br/>
			<br/>
			<textarea disabled  id="editor_id"   name="textContent"  style="width:700px;height:500px;" >${sysNotice.textContent}</textarea>
			<br/>
			<a href="javascript:editNotice(${sysNotice.id });" id="submitNotice"  iconCls="icon-edit" class="easyui-linkbutton a_button ">编 辑</a>
			<a href="javascript:listNotice();" id="submitNotice"  iconCls="icon-edit"  class="easyui-linkbutton a_button">返回列表</a>
			<br/><br/>
		</form>
	</div>





