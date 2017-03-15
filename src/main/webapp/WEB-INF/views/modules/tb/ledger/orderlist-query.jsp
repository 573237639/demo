<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	//点击搜索	
	function submitForm() {
		$("#searchOrder").linkbutton('disable');
		$.messager.progress();	// 显示进度条
		$("#orderlist-datagrid").datagrid('load');
		$.messager.progress("close");	// 关闭进度条
    	$("#searchOrder").linkbutton('enable');
	}
	
	  $("#vacOrderBusinessType").combotree({editable:false,onBeforeSelect:
		  function(node){
			  var  leaf = $(this).tree('isLeaf',node.target);
			  if(!leaf){
				  //如果不是叶子,清除选中
				  $(this).treegrid("unselect");
			  }
		  }});
</script>
<br/>
		<form id="orderlist-from" method="post" action=""  modelAttribute="tbOrders">
				<input type="hidden" id="page" name="page"/>
				<input type="hidden" id="rows" name="rows">
				<div >
					<span class="div_margin_left">业务类型</span>
					<input class="easyui-combotree"  style="width:300px;height:28px;" id="vacOrderBusinessType" name="vacOrderBusinessType"
					data-options="editable:true,valueField:'id',width:200,panelHeight:300,textField:'text',url:'${ctx}/sys/specialty/findAll'" />
						
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="div_margin_left">初审状态</span>
					<input class="easyui-combobox combobox_input" id="numOrderStatus"　name="numOrderStatus"  style="width:100px;height:28px;" editable="false" 
						data-options="valueField:'value',width:200,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/status_type'" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:submitForm();" id="searchOrder"  iconCls="icon-search"  class="easyui-linkbutton a_button">搜	索</a>
				</div>
	</form>
<br/>