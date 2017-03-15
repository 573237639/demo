<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<style type="text/css">
.margin-top20{margin-top:20px;}
.fontSize14{font-size:14px;}
.line-height30{line-height:30px;}
.text-align-right{font-weight:700;text-align:right;font-size:12px;}
.add-textBoxW{width:20%;margin-left:30px;}

.changepwd-tr{display:none;}
</style>
<script>
$(document).ready(function() {
	$.extend($.fn.validatebox.defaults.rules, {
    	/*必须和某个字段相等*/
    	equalTo: { validator: function (value, param) { return $(param[0]).val() == value; }, message: '字段不匹配' },
    	//移动手机号码验证
        phone: {//value值为文本框中的值
            validator: function (value) {
                var reg = /^1[3|4|5|8|9]\d{9}$/;
                return reg.test(value);
            },
            message: '输入手机号码格式不准确.'
        }
   	});
	
});
</script>
<div style="height: 40px;"></div>
<form id="userInfoForm" action="${ctx}/sys/user/userCenterUpdate" method="post" data-options="novalidate:true" style="height:90%" >
	<input type="hidden" name="id" id="userId" value="${sysUser.id}">
	<table class="line-height30" style="width:98%;">
		<tr>
			<td class="text-align-right" style="width:30%"><label>话务工号:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:true,disabled:true" name="hwNum" id="hwNum" value="${sysUser.hwNum}"/>
			</td>
		</tr>
		<!-- 
		<tr>
			<td class="text-align-right" style="width:30%"><label>分机号:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:true" name="extNum" id="extNum" value="${sysUser.extNum}"/>
			</td>
		</tr> -->
		<tr>
			<td class="text-align-right" style="width:30%"><label>用户名:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:true" name="username" id="username" value="${sysUser.username}"/>
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>真实姓名:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:true" name="realname" id="realname" value="${sysUser.realname}"/>
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>坐席类型:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-combobox" id="seats" name="seats" value="${sysUser.seats}" editable="false"
	   					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_seats',disabled:true">
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>角色:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-combobox" id="role" name="role" value="${sysUser.role}" editable="false"
	   					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_role',disabled:true">
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>所属分组:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-combobox" id="ingroup" name="ingroup" value="${sysUser.ingroup}"
	   					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_ingroup',disabled:true">
			</td>
		</tr>
		<!-- 
		<tr>
			<td class="text-align-right" style="width:30%"><label>性别:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input type="radio" name="sex" value="1" ${sysUser.sex == 1 ? 'checked' : ''}/>男 &nbsp;&nbsp; 
				<input type="radio" name="sex" value="3" ${sysUser.sex == 3 ? 'checked' : ''}/>女 &nbsp;&nbsp; 
			</td>
		</tr> -->
		<tr>
			<td class="text-align-right" style="width:30%"><label>邮箱:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:false,validType:'email'" name="email" id="email" value="${sysUser.email}"/>
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>手机号:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:false,validType:'phone'" name="phone" id="phone" value="${sysUser.phone}"/>
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label></label></td>
			<td style="width:70%;padding-left:30px;">
				<font style="font-weight:700;color:red;font-size:12px;">修改密码</font> <input type="checkbox" name="changepwd" id="changepwd" value="1" onclick="changepwd_c(this)"/> 
			</td>
		</tr>
		<tr class="changepwd-tr">
			<td class="text-align-right" style="width:30%"><label>密码:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input id="password" name="password" validType="length[4,32]" class="easyui-textbox add-textBoxW" type="password" value=""/>
			</td>
		</tr>
		<tr class="changepwd-tr">
			<td class="text-align-right" style="width:30%"><label>确认密码:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input id="password1" name="password1" validType="equalTo['#password']" class="easyui-textbox add-textBoxW" type="password" invalidMessage="两次输入密码不匹配"/>
			</td>
		</tr>
		<tr style="height:10px;"></tr>
		<tr>
			<td class="text-align-right"></td>
			<td>
				<a href="javascript:userInfoSubmit();" id="addBtn" class="easyui-linkbutton margin-left20">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:reloadCur();" class="easyui-linkbutton margin-left20">重  置</a>
			</td>
		</tr>
	</table>
</form>

<script>
//重置
function reloadCur(){
	window.location.reload(true);
}
	//提交用户信息,用来保存,修改
	function userInfoSubmit(){
		$('#userInfoForm').form('submit', {    
			onSubmit: function(param){  
				if($("#changepwd")[0].checked == false){
					param.changepwd = 0;
				}
				var validate = $('#userInfoForm').form('validate');
				if(!validate) return false;
			},   
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	data = eval('(' + data + ')');
		        if(data.code == 0){
		        	//保存成功
		        	$('#sysUserTabs').tabs('select',0);
		        	$("#addBtn").linkbutton('enable');
		        	$.messager.alert('提示','信息操作成功!');
		        	setTimeout(function(){
		        		location.reload();
		        	},500);
		        }else{
		        	$.messager.alert('提示','页面保存失败!');
		        }    
		    }    
		});
	}
	
	function changepwd_c(obj){
		if($(obj)[0].checked){
			$(".changepwd-tr").show();
			$("#password").textbox({
				required:true,
				width:$("#phone").width()
			});
			$("#password1").textbox({
				required:true,
				width:$("#phone").width()
			});
		}else{
			$(".changepwd-tr").hide();
			$("#password").textbox({required:false});
			$("#password1").textbox({required:false});
		}
	}
</script>


