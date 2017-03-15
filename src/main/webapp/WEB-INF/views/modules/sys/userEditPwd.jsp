<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#userEditPwd_form').form({
			url : '${ctx}/user/editPwd',
			dataType:"json",
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.code==1) {
					//parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="userEditPwd_form" method="post">
			<input type="hidden" name="id" value="${token.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>用户名</th>
					<td>${token.data}</td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="password" type="password" placeholder="请输入密码" class="easyui-validatebox span2" data-options="required:true"></td>
				</tr>
			</table>
		</form>
	</div>
</div>