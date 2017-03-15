<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
.error{color:red;}
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
	 
	 //选择菜单图标绑定事件
	 $(":radio[name='icon']").click(function(){
		 var value = $(this).val();
		 var html = "<img src='${ctx}/static/easyui/ext/style/images/extjs_icons/"+ value +".png'>";
		 $("#showSelectIcon").html(html);
		 $("#incoList").hide();
	 });
});

function changeInput(v){
	var s = v.value;
	if(s==1){
		$("#space-id-4").hide();
		$("#form-id-group").hide();
		$("#actName").textbox({
			width : 250,
			required:false
		});
	}else{
		$("#space-id-4").show();
		$("#form-id-group").show();
		$("#actName").textbox({
			width : 250,
			required:true
		});
	}
}

function submitFun(){
	$("#inputForm").submit(); 
}

function resetCur(){
	$('#showSelectIcon').html('无')
	$('#inputForm').form('reset');
}
</script>

<form id="inputForm" class="margin-top20" action="${ctx}/sys/menus/save" method="post" data-options="novalidate:true" style="height:90%;">
	<input type="hidden" name="parentId" value="${parentMenu.id}"/>
	<table class="line-height30" style="width:98%;">
		<tr>
			<td class="text-align-right" style="width:30%"><label>父菜单:</label></td>
			<td style="width:70%;padding-left:30px;">${parentMenu.name}</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>菜单名:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="required:true" name="name" id="name"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>菜单类型:</label></td>
			<td style="width:70%;padding-left:30px;">
				<label><input type="radio" name="menuType" value="1" checked="checked" class="required" onclick="changeInput(this);">父级菜单</label>
				<label><input type="radio" name="menuType" value="2" class="required" onclick="changeInput(this);">叶子菜单</label>
			</td>
		</tr>
		<tr id="space-id-4" style="height:20px;display:none;"></tr>
		<tr id="form-id-group" style="display:none;">
			<td class="text-align-right" style="width:30%"><label>菜单动作:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="" name="actName" id="actName"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>菜单图标:</label></td>
			<td style="width:70%;padding-left:30px;">
				<label id="showSelectIcon">无</label>&nbsp;
				<a id="selectIcon" href="javascript:void(0);" onclick="$('.incoList').show()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">选择图标</a>
			</td>
		</tr>
		<tr style="height:20px;display: none;" class="incoList"></tr>
		<tr class="incoList" style="display: none;">
			<td class="text-align-right" style="width:30%"></td>
			<td style="width:70%;padding-left:30px;">
				<input type="radio" name="icon" value="anchor"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/anchor.png">anchor.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="asterisk_orange"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/asterisk_orange.png">asterisk_orange.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="attach"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/attach.png">attach.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="bell"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/bell.png">bell.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="book_open"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/book_open.png">book_open.png&nbsp;&nbsp;
				<br/>
				<input type="radio" name="icon" value="box"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/box.png">box.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="brick"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/brick.png">brick.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="bricks"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/bricks.png">bricks.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="bricks"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/bricks.png">bricks.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="briefcase"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/briefcase.png">briefcase.png&nbsp;&nbsp;
				<br/>
				<input type="radio" name="icon" value="building"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/building.png">building.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="bullet_disk"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/bullet_disk.png">bullet_disk.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="bullet_wrench"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/bullet_wrench.png">bullet_wrench.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="cog"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/cog.png">cog.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="coins"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/coins.png">coins.png&nbsp;&nbsp;
				<br/>
				<input type="radio" name="icon" value="color_swatch"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/color_swatch.png">color_swatch.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="color_wheel"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/color_wheel.png">color_wheel.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="comments"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/comments.png">comments.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="computer"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/computer.png">computer.png&nbsp;&nbsp;
				<input type="radio" name="icon" value="chart_bar"><img src="${ctx}/static/easyui/ext/style/images/extjs_icons/chart_bar.png">chart_bar.png&nbsp;&nbsp;
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>描述:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW form-control input-sm" data-options="" name="remarks" id="remarks"/>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right"></td>
			<td>
				<a href="javascript:submitFun();" id="addBtn" class="easyui-linkbutton margin-left20">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:resetCur();" class="easyui-linkbutton margin-left20">重  置</a>
			</td>
		</tr>
	</table>
</form>


