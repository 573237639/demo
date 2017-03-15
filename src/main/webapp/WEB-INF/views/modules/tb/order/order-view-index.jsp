<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" charset="utf-8">
	var layout_orderview;
	var portal_orderview;
	var orderviewOrderNum = ${orderviewOrderNum};
	$(function() {
		var id = $("#id").val();
		layout_orderview = $('#layout_orderview').layout({
			fit : true
		});
		$(window).resize(function() {
			layout_orderview.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'order_view_detail',
			title : '工单详情',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order-view-detail/'+id
		}, {
			id : 'order_view_order',
			title : '历史工单<span style="color:gray;margin-left: 20px;">工单数  :</span> '+orderviewOrderNum,
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order-view-order',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#order_view_order").panel("refresh");
				}
			}]
		}, {
			id : 'order_view_record',
			title : '追加记录',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/record-add',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#order_view_record").panel("refresh");
				}
			}]

		}];

		portal_orderview = $('#portal_orderview').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-orderview-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-orderview-state');
		if (!state) {
// 			state = 'order_view_detail,ledger_view_call';/*冒号代表列，逗号代表行*/
			state = 'order_view_detail,order_view_order,order_view_record';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portal_orderview.portal('resize');

	});

	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	function getPortalState() {
		var aa = [];
		for ( var columnIndex = 0; columnIndex < 2; columnIndex++) {
			var cc = [];
			var panels = portal_orderview.portal('getPanels', columnIndex);
			for ( var i = 0; i < panels.length; i++) {
				cc.push(panels[i].attr('id'));
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}
	function addPortalPanels(portalState) {
			var cc = portalState.split(',');
			for ( var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					portal_orderview.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="layout_orderview">
		<div data-options="region:'center',border:false" >
		<input type="hidden" id="id" name="id" value="${id }">
			<div id="portal_orderview" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>
