<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" charset="utf-8">
	var layout_orderEdit;
	var portal_orderEdit;
	var orderEditOrderNum = ${orderEditOrderNum};
	$(function() {
		id = $("#id").val();
		layout_orderEdit = $('#layout_orderEdit').layout({
			fit : true
		});
		$(window).resize(function() {
			layout_orderEdit.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'order_edit_detail',
			title : '工单详情',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order-edit-detail/'+id
		}, {
			id : 'order_edit_order',
			title : '历史工单<span style="color:gray;margin-left: 20px;">工单数  :</span> '+orderEditOrderNum,
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order-edit-order',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#order_edit_order").panel("refresh");
				}
			}]
		}, {
			id : 'order_edit_record',
			title : '追加记录',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/record-list',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#order_edit_record").panel("refresh");
				}
			}]

		}];

		portal_orderEdit = $('#portal_orderEdit').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-orderedit-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-orderedit-state');
		if (!state) {
// 			state = 'order_edit_detail,ledger_edit_call';/*冒号代表列，逗号代表行*/
			state = 'order_edit_detail,order_edit_order,order_edit_record';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portal_orderEdit.portal('resize');

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
			var panels = portal_orderEdit.portal('getPanels', columnIndex);
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
					portal_orderEdit.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="layout_orderEdit">
		<div data-options="region:'center',border:false" >
		<input type="hidden" id="id" name="id" value="${id }">
			<div id="portal_orderEdit" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>
