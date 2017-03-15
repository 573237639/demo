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
	
	$("#inputForm").validate({
		submitHandler: function(form){
			//loading('正在提交，请稍等...');
			var validate = $('#inputForm').form('validate');
			if(!validate) return;
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
	
	function checkNode(node,checked){
		var childrenLen = node.children;
		//向下子节点
		if(childrenLen.length>0){
			for(var i=0;i<childrenLen.length;i++){
				var childrenNode = $('#roleActTree').tree('find', childrenLen[i].id);
				$('#roleActTree').tree('update', {
					target: childrenNode.target,
					checked: checked
				});
				checkNode(childrenNode,checked)
			}
		}
		//向上父节点
		if(node.pid != 0 && checked){
			var parentNode = $('#roleActTree').tree('find', node.pid);
			$('#roleActTree').tree('update', {
				target: parentNode.target,
				checked: checked
			});
			parentCheckNode(parentNode,checked)
		}
	}
	function parentCheckNode(node,checked){
		//向上父节点
		if(node.pid != 0 && checked){
			var parentNode = $('#roleActTree').tree('find', node.pid);
			$('#roleActTree').tree('update', {
				target: parentNode.target,
				checked: checked
			});
		}
	}
	var loadMenuTree = function(){
		$('#roleActTree').tree({    
		    url: "${ctx}/sys/menus/roleActMenuTree?roleId=${sysRole.id}",    
			method:'post',//请求的方式，默认是post
			animate:false,//在树展开或关闭时是否开启动画效果。默认false
			checkbox:true,//是否开启复选框，默认false
			cascadeCheck:false,//是否开启级联选择，默认true
			onlyLeafCheck:false,//定义是否只有叶子节点显示复选框,默认false
			lines:false,//定义是否显示线,默认false
			dnd:false,//定义是否开启拖放，默认false
			formatter:function(node){
				//对文字内容进行格式化
				return node.text;
			},
			onBeforeCheck : function(node,checked){
				checkNode(node,checked);
				/* var childrenLen = node.children;
				//向下子节点
				if(childrenLen.length>0){
					for(var i=0;i<childrenLen.length;i++){
						var childrenNode = $('#roleActTree').tree('find', childrenLen[i].id);
						$('#roleActTree').tree('update', {
							target: childrenNode.target,
							checked: checked
						});
					}
				}
				//向上父节点
				if(node.pid != 0 && checked){
					var parentNode = $('#roleActTree').tree('find', node.pid);
					$('#roleActTree').tree('update', {
						target: parentNode.target,
						checked: checked
					});
				} */
			},
			onLoadSuccess : function(node, data){
				$('#roleActTree').tree("collapseAll");
			}
		});  
	}
	$(function(){
		loadMenuTree();
	});
	
});

function submitFun(){
	var nodes = $('#roleActTree').tree('getChecked')
	var strnode = "";
	for(var i=0;i<nodes.length;i++){
    	var node = nodes[i];
		if(node.attributes){
			strnode += ("m_" + node.id);
		}else{
			strnode += ("a_" + node.id);
		}
		if(i != nodes.length-1) strnode += ",";
	}
	$("#treemenuids").val(strnode);
	$("#inputForm").attr("action","${ctx}/sys/role/save");
	$("#inputForm").submit(); 
}
</script>

<form id="inputForm" class="margin-top20 " method="post" data-options="novalidate:true" style="height:90%">
	<input type="hidden" name="id" value="${sysRole.id}"/>
	<input type="hidden" name="treemenuids" id="treemenuids"/>
	<input type="hidden" name="oldName" value="${sysRole.name }"/>
	<table class="line-height30" style="width:98%;">
		<tr>
			<td class="text-align-right" style="width:30%"><label>角色名称:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="required:true" name="name" id="name" value="${sysRole.name}"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>描述:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="required:true" name="remarks" id="remarks" value="${sysRole.remarks}"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>角色权限:</label></td>
			<td style="width:70%;padding-left:30px;">
				<ul id="roleActTree"></ul>
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



