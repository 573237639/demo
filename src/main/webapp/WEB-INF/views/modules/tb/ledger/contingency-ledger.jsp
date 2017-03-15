<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
 <meta http-equiv="X-UA-Compatible" content="IE=9"> 
<script type="text/javascript">
	function newLedger(){
		var number = $("#vacLedgerNumber1").val();
		if (number == '') {
			$.messager.alert("消息提醒", "请检查你输入的来电号码!", "warning");
			return;
		}
		//做一个提示
		$.messager.confirm('确认框', '点击确定会生成一条台账,请确认', function(r){
			if (r){			
				window.parent.window.newTab(number,"/tb/call/new-ledger","台帐应急新增:"+number,"icon-save");
			}	
		});
		
	}
	
</script>
</head>
	<body>

		<div id="call-p" class="easyui-panel" title="应急台账信息"
			style="width: auto; height: auto; padding: 10px;">
						<span class="div_margin_left">来电号码</span> <input
				class="easyui-textbox textbox_input" id="vacLedgerNumber1"
				value="${tbLedger.vacLedgerNumber }"
				required="required" validType="length[8,13]"
				style="width: 160px; height: 30px;" />
	
			<permission:hasPermission action="/tb/call/call-detail">
				<a href="javascript:newLedger();" iconCls="icon-search"
					class="easyui-linkbutton  a_button">生成台账</a>
			</permission:hasPermission>
		</div>
	
	</body>
</html>


