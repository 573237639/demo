<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" charset="utf-8">
	var portalLayout;
	var portal;
	$(function() {
		portalLayout = $('#portalLayout').layout({
			fit : true
		});
		$(window).resize(function() {
			portalLayout.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'audit-query',
			title : '待审核工单查询',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order-audit-query',
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					$('#audit-query').panel('refresh');
				}
			} ]
		}, {
			id : 'audit-list',
			title : '待审核工单列表',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/order/order-audit-list',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$('#audit-list').panel('refresh');
				}
			}]

		}];

		portal = $('#portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-audit-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-audit-state');
		if (!state) {
			state = 'audit-query,audit-list';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portal.portal('resize');

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
			var panels = portal.portal('getPanels', columnIndex);
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
					portal.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="portalLayout">
		<div data-options="region:'center',border:false" >
			<div id="portal" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>