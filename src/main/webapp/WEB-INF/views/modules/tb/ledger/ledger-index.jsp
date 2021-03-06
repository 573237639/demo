<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<script type="text/javascript" charset="utf-8">
	var ledgerLayout;
	var portalLedgerIndex;
	$(function() {
		ledgerLayout = $('#ledgerLayout').layout({
			fit : true
		});
		$(window).resize(function() {
			ledgerLayout.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'ledger_query',
			title : '台账查询',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-query',
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					 $('#ledger_query').panel('refresh');
				}
			} ]
		}, {
			id : 'ledger_list',
			title : '台账列表',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-list',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					 $('#ledger_list').panel('refresh');
				}
			}]

		}];

		portalLedgerIndex = $('#portal_ledger_index').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-ledger-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-ledger-state');
		if (!state) {
			state = 'ledger_query,ledger_list';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portalLedgerIndex.portal('resize');

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
			var panels = portalLedgerIndex.portal('getPanels', columnIndex);
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
					portalLedgerIndex.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="ledgerLayout">
		<div data-options="region:'center',border:false" >
			<div id="portal_ledger_index" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>