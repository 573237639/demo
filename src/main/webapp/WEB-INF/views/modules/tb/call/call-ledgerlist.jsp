<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
var number = $("#vacLedgerNumber").val();
var loadGrid = function(){
	$("#callLedger-datagrid").datagrid({
		striped:true,
  		checkOnSelect:true ,
  		singleSelect:true,
  		fitColumns:true,
	    columns:[[
  	  		 {field:'id',title:'ID',width:100},
  	  		 {field:'vacLedgerNumber',title:'来电号码', width:200,align:"center"}, 
		  	 {field:'vacLedgerAgentName',title:'话务员姓名', width:100,align:"center"}, 
		  	 {field:'vacLedgerBusinessName',title:'业务类型', width:200,align:"center"},  
		  	 {field:'gmtCreatedString',title:'来电时间', width:150,align:"center"},                                          
		  	 {field:'vacLedgerClientAccount',title:'台账-客户自述', width:300,align:"center",editor:'textbox'}, 
		  	 {field:'vacLedgerLawyerSuggestion',title:'台账-律师建议', width:300,align:"center",editor:'textbox'},                                      
		  	 {field:'isOrder',title:'工单数量', width:80,align:"center"},
		  	 {field:'dic',title:'操作', width:80,align:"center",formatter:function(value,row,index){
	  	  			var btns =  '<a href="javascript:showCallMessage(\''
	  	  				+ row.vacLedgerNumber + '\',\''+ row.vacLedgerClientAccount +'\',\''+ row.vacLedgerLawyerSuggestion +'\');" class="lkbtn" ><span style="color:red">内容详情</span></a>&nbsp;'
					return btns;
	  	  		}}  
  		]],
  		loader:function(param,success,error){
  			param.vacLedgerNumber = $("#vacLedgerNumber").val();
	    	$.ajax({
	            url:"${ctx}/tb/call/callLedger-datagrid",
	            checkbox: "true", 
	            data:param,
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
	            	if(data.total == 0){
	            		$(".call_view").hide();
	            		$(".call_datanull").show();
	            	}else{
	            		$(".call_view").show();
	            		$(".call_datanull").hide();
	            	}
	                success(data);
	            }
	        })
	    }
	});
}
setTimeout(loadGrid,1000);

</script>
<script type="text/javascript">
function showCallMessage(number,account,suggestion){
	$.messager.show({
		title:'内容详情',
		msg:'客户号码：'+number+'<br/>客户自述：'+account+'<br/>律师建议：'+suggestion+'<br/>',
		showType:'fade',
		width:600,
		height:300,
		timeout:0,
		style:{
			right:'',
			bottom:''
		}
	});
	
}
</script>
<script type="text/javascript">
		$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
				});
			}
		});
		
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#callLedger-datagrid').datagrid('validateRow', editIndex)){
				$('#callLedger-datagrid').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickCell(index, field){
			if (endEditing()){
				$('#callLedger-datagrid').datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				editIndex = index;
			}
		}
	</script>

<div class="call_view">
		<table class="easyui-datagrid" id="callLedger-datagrid" style="width:100%;" pagination ="true" data-options="onClickCell: onClickCell">
		</table> 
	</table>
</div>
<div class="call_datanull" style="display: none;text-align:center;">
	暂无结果
</div>
				
