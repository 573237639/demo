<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<!-- 引入编辑器 -->
<link type="text/css" rel="stylesheet" href="${ctxStatic}/kindeditor-4.1.10/themes/default/default.css" />
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/kindeditor.js"></script>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script>
	var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
					uploadJson : '${ctx}/file_upload',//上传
	                fileManagerJson : '${ctx}/file_manager',//文件管理
	                allowFileManager : true,
	                afterBlur: function () { this.sync(); },//数据同步
			});
		});
</script>
<script type="text/javascript">
	//点击提交	
	function submitNotice() {
		
		//验证表单
		var validate = $("#addnotice-form").form("validate");
		if(!validate){
// 			$.messager.alert("消息提醒","请检查你输入的数据!","warning");
			return;
		} else{
			var html = window.editor.html();
			var vacTitle = $("#vacTitle").val();
			//做校验
			if(vacTitle == ''){
				$.messager.alert('提示','标题不能为空');
				return ;
			}
			if(html == ''){
				$.messager.alert('提示','内容不能为空');
				return ;
			}
			$("#submitNotice").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$.ajax({
			    url:"${ctx}/sys/notice/saveNotice",
			    data: {vacTitle : vacTitle,textContent : html},
			    type:"post",
			    dataType:"json",
			    jsonpCallback:"callback",
			    success: function(data){
		            $("#submitNotice").linkbutton('enable');
			    	$.messager.progress("close");	// 关闭进度条
			    	if(data.code == 0){
		        		$.messager.alert('提示','保存成功!');
			        }else{
			        	$.messager.alert('提示','保存失败!');
			        } 
			    }
			})
		}
		
	}
	
	//重置
	 function reloadCur(){
		 $(':input','#vacTitle').val('');  
		 window.editor.html('');
	 }
	
	 function listNotice(){
			window.parent.window.newTab('',"/sys/notice/noticeList","公告管理:","icon-save");
		}

</script>
	<div class="easyui-panel" style="padding:4px">

			<form id="addnotice-form" method="post" action=""  modelAttribute="sysNotice">
			<br/>
			<br/>
			<div >
			<span class="div_margin_left  div_color_red" >* </span><span >标题</span>
				<input class="easyui-textbox"  style="width:700px;" id="vacTitle" name="vacTitle" value="${sysNotice.vacTitle }"  required="required"  validType="length[0,30]" />
			</div>
			<br/>
			<br/>
			<div style="vertical-align: top; display: inline-flex;">
			<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >内容 </span>
				<textarea name="content" style="width:800px;height:400px;margin-left: 10px;visibility:hidden;" required="required">${sysNotice.textContent}</textarea>
			</div>
			<br/>
			<br/>
			<div style="margin-left: 58px;">
			<a href="javascript:submitNotice();" id="submitNotice"  iconCls="icon-search" class="easyui-linkbutton a_button">发   布</a>
			<a href="javascript:reloadCur();" id="reloadCur"  iconCls="icon-reload"  class="easyui-linkbutton a_button">重 置</a>
			<a href="javascript:listNotice();" id="submitNotice"  iconCls="icon-edit"  class="easyui-linkbutton a_button">返回列表</a>
			</div>
			<br/>
			<br/>
		</form>
	</div>





