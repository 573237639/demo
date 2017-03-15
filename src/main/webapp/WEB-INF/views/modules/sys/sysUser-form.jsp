<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
.margin-top20{margin-top:20px;}
.fontSize14{font-size:14px;}
.line-height30{line-height:30px;}
.text-align-right{font-weight:700;text-align:right;font-size:12px;}
.add-textBoxW{width:20%;margin-left:30px;}
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
<form id="userInfoForm" class="margin-top20" action="${ctx}/sys/user/userInfoAE" method="post" data-options="novalidate:true" style="height:90%" >
	<input type="hidden" name="id" id="userId" value="${sysUser.id}">
	<input type="hidden" name="password" value="${sysUser.password}">
	<input type="hidden" name="userType" value="${sysUser.userType}">
	<input type="hidden" name="roleIds" id="roleIds">
	<table class="line-height30" style="width:98%;">
		<tr>
			<td class="text-align-right" style="width:30%"><label>话务工号:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:true" name="hwNum" id="hwNum" value="${sysUser.hwNum}"/>
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>分机号:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-textbox add-textBoxW" data-options="required:false" name="extNum" id="extNum" value="${sysUser.extNum}"/>
			</td>
		</tr>
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
	   					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_seats',required:true">
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>角色:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-combobox" id="role" name="role" value="${sysUser.role}" editable="false"
	   					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_role',required:true">
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>所属分组:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input class="easyui-combobox" id="ingroup" name="ingroup" value="${sysUser.ingroup}" editable="false"
	   					data-options="valueField:'value',width:200,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/user_ingroup',required:true">
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>性别:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input type="radio" name="sex" value="1" ${sysUser.sex == 1 ? 'checked' : ''}/>男 &nbsp;&nbsp; 
				<input type="radio" name="sex" value="3" ${sysUser.sex == 3 ? 'checked' : ''}/>女 &nbsp;&nbsp; 
			</td>
		</tr>
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
		<!-- 
		<tr style="height:20px;"></tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>密码:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input id="password" name="password" validType="length[4,32]" class="easyui-textbox add-textBoxW" data-options="required:true" type="password" value=""/>
			</td>
		</tr>
		<tr style="height:20px;">
			<td class="text-align-right" style="width:30%"></td>
			<td style="width:70%;padding-left:30px;">
				<font style="color:gray;">${sysUser.password!=null ? "若不修改密码,请留空" : ""}</font>
			</td>
		</tr>
		<tr>
			<td class="text-align-right" style="width:30%"><label>确认密码:</label></td>
			<td style="width:70%;padding-left:30px;">
				<input id="password1" name="password1" validType="equalTo['#password']" class="easyui-textbox add-textBoxW" data-options="required:true" type="password" invalidMessage="两次输入密码不匹配"/>
			</td>
		</tr> -->
		<tr>
			<td class="text-align-right" style="width:30%"><label>用户角色:</label></td>
			<td style="width:70%;padding-left:30px;">
				<c:if test="${not empty roles}">
					<c:forEach items="${roles}" var="role">
						<input type="checkbox" name="roleId" value="${role.id}" />${role.name} &nbsp;&nbsp; 
					</c:forEach>
				</c:if>
			</td>
		</tr>
		<tr style="height:20px;"></tr>
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
	//提交用户信息,用来保存,修改
	function userInfoSubmit(){
		if($('input[name="sex"]:checked').val() == undefined){
			$.messager.alert('提示','请选择性别');
			return;
		}
		var chk_value =[]; 
		$('input[name="roleId"]:checked').each(function(){ 
			chk_value.push($(this).val()); 
		}); 
		if(chk_value.length==0){
			$.messager.alert('提示','请选择至少一个用户角色');
			return;
		}
		$("#roleIds").val(chk_value);
		$('#userInfoForm').form('submit', {    
			onSubmit: function(param){  
				var validate = $('#userInfoForm').form('validate');
				if(!validate) return false;
			},   
		    success:function(data){
        		$.messager.progress("close");	// 关闭进度条
		    	data = eval('(' + data + ')');
		        if(data.code == 0){
		        	//保存成功
		        	$('#sysUserTabs').tabs('select',0);
		        	$.messager.alert('提示','信息操作成功!');
		        	queryList();
		        }else{
		        	$.messager.alert('提示','页面保存失败!');
		        }    
		    }    
		});
	}
	$(function(){
		var userRoleIds = ${userRoleIds};
		if(userRoleIds!=null && userRoleIds.length>0){
			for(var i=0;i<userRoleIds.length;i++){
				$('input[name="roleId"][value="'+userRoleIds[i]+'"]').attr("checked", true);
			}
		}
	});
</script>


