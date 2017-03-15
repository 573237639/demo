<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript" charset="utf-8">
	$(function() {
		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});
		$('#layout_east_onlinePanel').panel({
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
				}
			} ]
		});
	});
</script>
<script type="text/javascript">
	//定时执行,刷新累计服务数据边框数据
	function myrefresh() {
		//查询最近5分钟的最新公告
		$.ajax({
					url : "${ctx}/sys/notice/getMessage",
					type : "post",
					dataType : "json",
					jsonpCallback : "callback",
					success : function(data) {
						if (data.code == 0) {
							var message = data.getMessage;
							for ( var i in message) {
								$.messager
										.show({
											title : '最新公告通知',
											msg : "<div style='text-align: center;font-size:14px;font-weight: bold;'>"
													+ message[i].vacTitle
													+ "</div><br/>"
													+ message[i].textContent,
											width : 400,
											height : 200,
											timeout : 0,
											showType : 'slide'
										});
							}
						}
					},
					error : function() {
					}
				});
	}
	window.setInterval(myrefresh, 5 * 60 * 1000); //一分钟刷新一次
</script>
<script type="text/javascript">
/**
 * 判断工单最后完成期限距离当前时间的差（超时：红色，三小时以内：橙色）
 */
function compareDate(orderDate){
	if(orderDate == undefined || !isNaN(orderDate)){
		return 2;
	}
	var d1 = new Date();
	var d2 = new Date();
	var date = new Date();
	orderDate = orderDate.replace(/-/g,'/');
	var o = new Date(orderDate);
	if((o.getTime() < addHours(d1,3)) && (o.getTime() > date.getTime())){
		return "color:orange";
	}else{
		return "color:red";
	}
}
	function addHours(date, value) {
	    date.setHours(date.getHours() + value);
	    return date;
	}
	function message(id) {
		window.parent.window.newTab(id, "/tb/order/order-edit-index", "工单编辑"+id,
				"icon-blank");
	}

	//定时执行,刷新累计服务数据边框数据
	function orderRefresh(){ 
		$.ajax({
			url : "${ctx}/tb/order/getAlertOrderList",
	        type:"post",
	        dataType:"json",
	        jsonpCallback:"callback",
	        success:function(data){
				if(data.code == 5){
					var message = data.getOrderList;
					var mess="<table  cellspacing='1' width='100%' align='center'><tr style='height:30px'><td>工单类型</td><td>工单流水号</td><td>分配状态</td><td>初审状态</td><td>操作</td></tr>";
					mess+="<tr><td colspan=5><div class='demo_line_02'></div></td></tr>";
					for(var i in message){
   						var numAllotUserId = message[i].numAllotUserId;
   						var numOrderStatus = message[i].numOrderStatus;
   						var bgcolor =compareDate(message[i].dateOrderTermString);
   						mess +="<tr style='"+bgcolor+";height:30px'><td>"
	   						+(message[i].vacOrderType==10?"普通":(message[i].vacOrderType==20?"紧急":""))+"</td><td>"
	   						+message[i].vacOrderSerial+"</td><td>"
	   						+(null===numAllotUserId || numAllotUserId === ''?"待分配":"已分配")+"</td><td>"
	   						+(null===numOrderStatus || numOrderStatus === ''?"":"待审核")
   							+"</td><td><a href='javascript:message("+message[i].id+");'>查看</a></td></tr>";

	   					mess +="<tr><td colspan=5><div class='demo_line_02'></div></td></tr>";
	   				}
   					mess+="</table>";
	 				$.messager.show({
						title:'工单提醒',
						msg:mess,
						width:400,
						height:200,
						timeout:10*1000,
						showType:'slide'
					});
				}
			},error:function(){
				
			}
		});
	}
	window.setInterval(orderRefresh, 20 * 60 * 1000); //20分钟刷新一次
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
<!-- 	<div data-options="region:'north',border:false" -->
<!-- 		style="wheight: 180px; overflow: hidden;"> -->
<!-- 		<div id="layout_east_calendar"></div> -->
<!-- 	</div> -->

</div>
