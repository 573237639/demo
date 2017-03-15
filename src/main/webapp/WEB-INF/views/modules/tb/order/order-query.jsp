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
		var validate = $("#tbOrdersListForm").form("validate");
		if(!validate){
// 		　　$.messager.alert("消息提醒","请检查你输入的数据!","warning");
		   return;
		}
		$("#searchOrder").linkbutton('disable');
		$.messager.progress();	// 显示进度条
		$("#orderlist-datagrid").datagrid('load');
		$.messager.progress("close");	// 关闭进度条
    	$("#searchOrder").linkbutton('enable');
	}
	//重置
	 function reloadCur(){
		 $(':input','#tbOrdersListForm')  
		 .not('#page')  
		 .not('#rows')
		 .not('#printbutton')
		 .val('')  
		 .removeAttr('checked')  
		 .removeAttr('selected'); 
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
		    	  ledgerPid = 0;
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


function orderCheckDate() {
	$.messager.confirm('确认框', '请确认导出', function(r){
		if (r){				
			document.getElementById("tbOrdersListForm").submit();
		}
	});
}
	 
</script>
		<form id="tbOrdersListForm" method="post" action="method=orderexcel"  modelAttribute="tbClients,tbOrders">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
				<br/>
				<div>	
					<span class="div_margin_left">客 户姓名</span>
					 <input name="vacClientName" value="${tbClients.vacClientName}" class="easyui-textbox textbox_input"/>
					
					<span style="margin-left: 55px;">性别</span>
					<input class="easyui-combobox combobox_input" name="numClientGender" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/gender'" />
					
					<span class="div_margin_left">证件&nbsp;类型</span>
					<input class="easyui-combobox combobox_input" name="zjType" value="${tbClients.zjType}" editable="false"
	   					data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/papers_type'"/>
					
					<input id="zjCode"  name="zjCode" class="easyui-textbox textbox_input"   value="${tbClients.zjCode}">
					
				</div>
				<br/>
				<div>
					<span class="div_margin_left">手机/电话</span>
					<input name="vacOrderNumber" value="${vacOrderNumber}" class="easyui-textbox textbox_input" />
					
					<span class="div_margin_left">咨询人类别</span>
					<input class="easyui-combobox combobox_input" name="numClientType" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/consultant_type'" />
						
				    <span>工单产生时间</span>
				  	  <input class="easyui-datetimebox" id="gmtCreatedBegin" name="gmtCreatedBegin"  editable="false"  style="width:200px;height:26px;">-
				   	 <input class="easyui-datetimebox" id="gmtCreatedEnd" name="gmtCreatedEnd" editable="false" style="width:200px;height:26px;">
					
				</div>
				<br/>
				<div>
					<span class="div_margin_left"> 初 &nbsp;&nbsp;审&nbsp;&nbsp;人</span>
					<input  name="" class="easyui-textbox textbox_input" value="" />
				
					<span class="div_margin_left">初审&nbsp;结&nbsp;果</span>
					<input class="easyui-combobox combobox_input" name="numOrderStatus" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/status_type'" />
					
					<span class="div_margin_left">任务&nbsp;类&nbsp;型</span>
					<input class="easyui-combobox combobox_input" name="vacOrderTaskType" editable="false"
						data-options="valueField:'value',width:150,panelHeight:300,textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/task_type'" />
					<span class="div_margin_left">业务类型</span>
					<input class="easyui-combotree"  style="width:300px;height:26px;"id="vacOrderBusinessType"  name="vacOrderBusinessType" editable="false"
						data-options="editable:false,valueField:'id',width:150,panelHeight:300,textField:'text',url:'${ctx}/sys/specialty/findAllBusinessType'" />
				</div>
				<br/>
				<div>
					<span class="div_margin_left">工单类型</span>
					<input class="easyui-combobox combobox_input" name="vacOrderType" editable="false"
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/order_type'" />
						
					<span class="div_margin_left">制 &nbsp;&nbsp;单 &nbsp;&nbsp;人</span>
					<input  name="vacOrderAgentName" class="easyui-textbox textbox_input" value="${tbClients.vacClientName}" />
						
					<span class="div_margin_left"> &nbsp;&nbsp;接收区域</span>
					<input class="easyui-combobox combobox_input" id="numOrderReceiveProvinceId" name="numOrderReceiveProvinceId"/>-
					<input class="easyui-combobox combobox_input" id="numOrderReceiveCityId" name="numOrderReceiveCityId"/>
				</div>
				<br/>
	 <div class="div_margin_left">
		<a href="javascript:submitForm();" id="searchOrder"  iconCls="icon-search" class="easyui-linkbutton a_button">搜索</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:reloadCur();" iconCls ="icon-reload" class="easyui-linkbutton a_button">重  置</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<permission:hasPermission action="method=orderexcel">
		<input type="button" id="printbutton" value="导    出"  onclick="orderCheckDate();" class="easyui-linkbutton a_button"/>&nbsp;&nbsp;&nbsp;&nbsp;
		</permission:hasPermission>
	</div>
				<br/>
	</form>