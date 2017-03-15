<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" charset="utf-8">
	var layout_ordernopass;
	var portal_ordernopass;
	var orderviewOrderNum = ${orderviewOrderNum};
	$(function() {
		var id = $("#id").val();
		layout_ordernopass = $('#layout_ordernopass').layout({
			fit : true
		});
		$(window).resize(function() {
			layout_ordernopass.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'order_nopass_detail',
			title : '工单详情',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order_nopass_detail/'+id
		}, {
			id : 'order_nopass_order',
			title : '历史工单<span style="color:gray;margin-left: 20px;">工单数  :</span> '+orderviewOrderNum,
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order_nopass_detail',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#order_nopass_order").panel("refresh");
				}
			}]
		}, {
			id : 'order_nopass_record',
			title : '追加记录',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/record-add',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#order_nopass_record").panel("refresh");
				}
			}]

		}];

		portal_ordernopass = $('#portal_ordernopass').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-ordernopass-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-ordernopass-state');
		if (!state) {
// 			state = 'order_nopass_detail,ledger_view_call';/*冒号代表列，逗号代表行*/
			state = 'order_nopass_detail,order_nopass_order,order_nopass_record';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portal_ordernopass.portal('resize');

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
			var panels = portal_ordernopass.portal('getPanels', columnIndex);
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
					portal_ordernopass.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="layout_ordernopass">
		<div data-options="region:'center',border:false" >
		<input type="hidden" id="id" name="id" value="${id }">
			<div id="portal_ordernopass" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>
