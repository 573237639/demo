<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
//点击搜索	
function submitForm() {
	//做时间校验
	var gmtCreatedBegin = $("#gmtCreatedBegin").textbox("getValue");
	var gmtCreatedEnd = $("#gmtCreatedEnd").textbox("getValue"); 
	if(gmtCreatedBegin != '' && gmtCreatedEnd !=''){
		if(gmtCreatedBegin > gmtCreatedEnd){
			$.messager.alert("消息提醒","结束时间不能小于开始时间","warning");
			return;
		}
	}
	//做表单验证
	//验证表单
	var validate = $("#tbOrdersAssignListForm").form("validate");
	if(!validate){
// 	　　$.messager.alert("消息提醒","请检查你输入的数据!","warning");
	return;
	} else{
		$("#searchOrder").linkbutton('disable');
		$.messager.progress();	// 显示进度条
		$("#orderlist-datagrid").datagrid('load');
		$.messager.progress("close");	// 关闭进度条
		$("#searchOrder").linkbutton('enable');
	}
}

//重置
 function reloadCur(){
	 $('#tbOrdersAssignListForm').form('reset');
 }
</script>
<script type="text/javascript">
$(function() {  	
	  // 下拉框选择控件，下拉框的内容是动态查询数据库信息
		$('#numOrderReceiveProvinceId').combobox({ 
      editable:false, //不可编辑状态
      cache: false,
      panelHeight: '300px',//自动高度适合
      valueField:'id', //值字段
      textField:'vacProvinceName', //显示的字段
      url:'${ctx}/sms/province/findAllProvince',
		  onHidePanel: function(){
		      $("#numOrderReceiveCityId").combobox("setValue",'');
		      var ledgerPid = $("#numOrderReceiveProvinceId").combobox('getValue');
		      if(ledgerPid == ''){
		    	  ledgerPid = 0
		      }
		      $.ajax({
		        type: "GET",
		        url:'${ctx}/sms/city/findCityByProvId/'+ledgerPid,
		        cache: false,
		        dataType : "json",
		        success: function(data){
		        	$("#numOrderReceiveCityId").combobox("loadData",data);
		         }
		       }); 	
		  }
		}); 

	  $('#numOrderReceiveCityId').combobox({ 
	      editable:false, //不可编辑状态
	      cache: false,
	      panelHeight: 'auto',//自动高度适合
	      valueField:'id', //值字段
	      textField:'vacCityName'//显示的字段
 });
});
	 
</script>
		<form id="tbOrdersAssignListForm" method="post" action="${ctf}/tb/order/orderList"  modelAttribute="tbClients,tbOrders">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="numOrderStatus" name="numOrderStatus" value="<%=CommonField.ORDER_STATUS_ASSIGNED %>">
				<br/>
				<div>	
					 <span>工单产生时间</span>
				  	  <input class="easyui-datetimebox" id="gmtCreatedBegin"  name="gmtCreatedBegin"  style="width:200px;height:26px;" editable="false" >-
				   	 <input class="easyui-datetimebox"  id="gmtCreatedEnd" name="gmtCreatedEnd"  style="width:200px;height:26px;" editable="false" >
					
					<span class="div_margin_left">客户姓名</span>
					 <input name="vacClientName" value="${tbClients.vacClientName}" class="easyui-textbox textbox_input" validType="length[0,20]"  />
					
					<span style="margin-left: 55px;">性别</span>
					<input class="easyui-combobox combobox_input" name="numClientGender"  editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/gender'" />
					
				</div>
				<br/>
				<div>
					<span class="div_margin_left">手机/电话</span>
					<input name="vacOrderNumber" value="${vacOrderNumber}" class="easyui-textbox textbox_input"  validType="length[8,13]" />
					
					<span class="div_margin_left">工单类型</span>
					<input class="easyui-combobox combobox_input" name="vacOrderType"  editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/order_type'" />
						
					<span class="div_margin_left"> &nbsp;&nbsp;接收区域</span>
					<input class="easyui-combobox combobox_input" id="numOrderReceiveProvinceId" name="numOrderReceiveProvinceId" editable="false" />-
					<input class="easyui-combobox combobox_input" id="numOrderReceiveCityId" name="numOrderReceiveCityId" editable="false" />
				</div>
				<br/>
				<div class="div_margin_left">
					<a href="javascript:submitForm();" id="searchOrder"  iconCls="icon-search" class="easyui-linkbutton a_button">搜索</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:reloadCur();" iconCls ="icon-reload" class="easyui-linkbutton a_button">重  置</a>
				</div>
	<br/>
	</form>