<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/style.css"/>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/saveQCBase.js"></script>
<script type="text/javascript">
var qcCategoryList = [["101136995","一、标准开头语5分","","1"],["101136996","二、有效倾听5分","","1"],["101136997","三、有效提问判断用户诉求5分","","1"],["101136998","四、用户诉求确认10分","","1"],["101136999","五、有效互动5分","","1"],["101137000","六、讲解法律依据5分","","2"],["101137001","七、帮助用户分析案情5分","","2"],["101137003","八、向用户讲解具体方案10分","","2"],["101137004","九、提供相关法律风险提示5分","","2"],["101137005","十、确认是否解决用户问题5分","","1"],["101137006","十一、询问用户是否有其他需求5分","","1"],["101137027","十二、征求用户意见建议（评估）5分","","1"],["101137028","十三、礼貌用语5分","","2"],["101137030","十四、服务态度10分","","2"],["101137031","十五、表达能力10分","","2"],["101137032","十六、业务操作5分","","2"]] ;
var qcProList = [["100803520","有无标准开头语5分","101136995","5","0.6","1","1"],["100803524","是否正确理解用户意图","101136996","5","0.6","1","1"],["100843593","通过提问判断确认用户真实诉求","101136997","5","0.6","1","1"],["100852874","得到用户对诉求描述的确认","101136998","10","1.0","1","1"],["100852861","引导用户理清关键问题","101136999","5","0.6","1","1"],["100852880","详细罗列并解释相关法律依据","101137000","5","0.6","1","1"],["101137130","分析案情并给出专业解决方案","101137001","5","0.6","1","1"],["100852856","解决用户实际问题，陈诉详实易懂","101137003","10","0.6","1","1"],["100852857","提示用户注意评估法律风险","101137004","5","0.6","1","1"],["100852878","得到用户解决方案的确认","101137005","5","0.6","1","1"],["100852879","主动询问用户是否还有其他需求","101137006","5","0.6","1","1"],["100852859","有无确认话术10分","101137027","10","0.8","0","0"],["100852888","规范结束语，礼貌挂机","101137027","5","0.6","1","1"],["100852892","有无标准结束语5分","101137027","5","0.8","0","0"],["100852882","有无标准话术5分","101137027","5","0.8","0","0"],["100852889","措辞礼貌避免口语化现象","101137028","5","0.6","1","1"],["101137138","语气、语音、语调适当，服务态度热情等","101137030","10","0.6","1","1"],["101137157","适当调整表达形式，便于用户理解","101137031","10","0.6","1","1"],["101137139","业务操作专业","101137032","5","0.6","1","1"]] ;

var loadGrid = function(){
	$("#call-datagrid").datagrid({
		striped:true,
  		fitColumns:true,
	    columns:[[
			 {field:'ck',checkbox:true},
		  	 {field:'id',title:'id', width:100,align:"center"}, 
		  	 ]],
  		loader:function(param,success,error){
  			$("#page").val(param.page);
  			$("#rows").val(param.rows);
	    	$.ajax({
	            url:"${ctx}/tb/callStay/tbcallListData",
	            data:$("#call-form").serialize(),
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
	<div class="easyui-panel" style="padding:4px">
		<form id="call-form" method="post" action=""  modelAttribute="tbCalls">
				<input type="hidden" id="page" name="page">
				<input type="hidden" id="rows" name="rows">
			<br/>
			<br/>
				<div>
    				<span class="div_margin_left">客户姓名</span>
					<input class="easyui-textbox textbox_input" id="vacClientName" name="vacClientName"  value="${vacClientName}"   validType="length[0,20]" >	
				</div>
				<br/>
				<br/>
					<div class="div_margin_left">
					<a href="javascript:searchCall();" id="searchCall"  iconCls="icon-search" class="easyui-linkbutton  a_button">搜  索</a>
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
				</div>
			<br/>
			<br/>
		</form>
			<div class="call_view">
				<table class="easyui-datagrid" data-options="
				toolbar:'#tb-tools'" id="call-datagrid" style="width: 100%;" pagination="true">
				</table>
				<br/>
				<br/>
			</div>
			<div id="tb-tools" style="padding:5px;height:auto">
			<permission:hasPermission action="tb/callStay/checkCallStay">
				<a href="javascript:checkStay();" class="easyui-linkbutton" id="checkStay" iconCls="icon-add" style="color:blue;background-color: #BCDCDC;" plain="true">质检录入</a>
				</permission:hasPermission>
			</div>
			<div class="call_datanull" style="display: none;text-align:center;">
				暂无结果
			</div>
</div>
<script type="text/javascript">
function checkStay(){
	var selectedNode = $('#call-datagrid').datagrid('getSelections');
	if(selectedNode.length < 1){
		$.messager.alert('消息提示','请最少选中一行进行质检.');
		return;
	}
	//获取选中的唯一流水号
	var serialArr = [];
	for(var i=0; i<selectedNode.length; i++){
		serialArr.push(selectedNode[i].id);
	}
	//绘制质检页面
	var checkBo = createCategory();
	var pro_dialog = $('#call-dialog').dialog({
	    title: '质检录入',
	    width: 700,
	    height: 500,
	    closed: false,
	    cache: false,
	    collapsible:true,
	    maximizable:true,
	    content:checkBo ,
	    buttons: [{ 
            text: '保存', 
            iconCls: 'icon-save',
            handler:function(){
				if(!checkout()){
					$.messager.alert('消息提示','请选择分类.');
					 return;
				};
				var proArr = projectScores();
				var json = eval(proArr);
				var scores = 0;
				for(var i=0; i<json.length; i++){
					scores=scores+json[i].pro_scores;
				}
				var dataParam = {
						serialArr:serialArr,
						proArr:proArr,
						summary:$("#summary").val(),
						improve:$("#improve").val(),
						comment:$("#comment").val(),
						memo:$("#memo").val(),
						scores:scores
				};
				$.ajax({
		            url:"${ctx}/tb/callStay/callStaySave",
		            type:"post",
		            data:dataParam,
		            dataType:"json",
		            jsonpCallback:"callback",
		            success: function(data){
		            	console.info(data);
		            	var dataObj= eval(data);
			        	if(dataObj.code === 0){
		                $.messager.alert('消息提示','质检录入成功.');
		                $('#call-dialog').dialog('close');
		                $("#call-datagrid").datagrid('load');
		            }else{
			        	$.messager.alert('提示','数据保存失败!原因：'+dataObj.message);
			        } 
		            }
		        });
		}
        },{ 
            text: '关闭', 
            iconCls: 'icon-cancel' , 
            handler: function(dialog){ 
            	$('#call-dialog').dialog('close');
            }  
        }] 
	});
	pro_dialog.show();
	_load(qcProList);
}

</script>
	<div id="call-dialog"></div>