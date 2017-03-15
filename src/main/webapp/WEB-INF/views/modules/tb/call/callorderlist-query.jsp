<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	//点击搜索	
	function submitForm() {
		$("#searchOrder").linkbutton('disable');
		$.messager.progress();	// 显示进度条
		$("#callorderlist-datagrid").datagrid('load');
		$.messager.progress("close");	// 关闭进度条
    	$("#searchOrder").linkbutton('enable');
	}
	

</script>
<div style="padding: 10px 10px 20px 40px">
		<form id="callorderlist-from" method="post" action=""  modelAttribute="tbOrders">
				<input type="hidden" id="page" name="page"/>
				<input type="hidden" id="rows" name="rows">
				<div >
					<span>业务类型</span>
					<input class="easyui-combotree"  style="width:300px;height:32px;" name="vacOrderBusinessType"
					data-options="editable:true,valueField:'id',width:200,panelHeight:300,textField:'text',url:'${ctx}/sys/specialty/findAll'" />
						
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span>初审状态</span>
					<input class="easyui-combobox" name="numOrderStatus"  style="width:100px;height:32px;" 
						data-options="valueField:'value',width:200,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/status_type'" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:submitForm();" id="searchOrder"  iconCls="icon-search" style="text-align: center;height:32px;padding: 10px" class="easyui-linkbutton">搜	索</a>
				</div>
	</form>
</div>	