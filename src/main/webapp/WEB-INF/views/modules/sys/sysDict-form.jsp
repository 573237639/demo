<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
.margin-top20{margin-top:20px;}
.fontSize14{font-size:14px;}
.line-height30{line-height:30px;}
.text-align-right{font-weight:700;text-align:right;font-size:12px;}
.add-textBoxW{width:20%;margin-left:30px;}
</style>
<script type="text/javascript">
$(document).ready(function() {
	 $("#value").focus();
	 $("#inputForm").validate({
		submitHandler: function(form){
			var validate = $('#inputForm').form('validate');
			if(!validate) return;
			//loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
	
});

function submitFun(){
	$("#inputForm").submit(); 
}
</script>

<form id="inputForm" class="margin-top20" action="${ctx}/sys/dict/save" method="post" data-options="novalidate:true" style="height:90%">
	<input type="hidden" name="id" value="${sysDict.id }">
	<table class="line-height30" style="width:98%;">
		<tr>
			<td class="text-align-right" style="width:30%"><label>键值:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="required:true" name="value" id="value" value="${sysDict.value }"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>标签:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="required:true" name="label" id="label" value="${sysDict.label }"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>类型:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="required:true" name="type" id="type" value="${sysDict.type }"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>描述:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="required:true" name="description" id="description" value="${sysDict.description }"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>排序:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-numberbox add-textBoxW form-control input-sm" data-options="required:true" name="sort" id="sort" value="${sysDict.sort }"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right"></td>
			<td>
				<a href="javascript:submitFun();" id="addBtn" class="easyui-linkbutton margin-left20">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:reloadCur();" class="easyui-linkbutton margin-left20">重  置</a>
			</td>
		</tr>
	</table>
</form>




