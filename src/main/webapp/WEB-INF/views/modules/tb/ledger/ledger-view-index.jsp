<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" charset="utf-8">
	var ledgerLayoutView;
	var portal_ledgerview;
	var ledgerViewCallNum = ${ledgerViewCallNum};
	var ledgerViewOrderNum = ${ledgerViewOrderNum};
	$(function() {
		var id = $("#id").val();
		ledgerLayoutView = $('#ledgerLayoutView').layout({
			fit : true
		});
		$(window).resize(function() {
			ledgerLayoutView.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'ledger_view_detail',
			title : '台账详情',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-view-detail/'+id,
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_view_detail").panel("refresh");
				}
			} ]
		}, {
			id : 'ledger_view_call',
			title : '历史来电<span style="color:gray;margin-left: 20px;">来电次数 :</span> '+ledgerViewCallNum,
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-view-call',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_view_call").panel("refresh");
				}
			}]

		}, {
			id : 'ledger_view_order',
			title : '历史工单<span style="color:gray;margin-left: 20px;">工单数  :</span> '+ledgerViewOrderNum,
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-view-order',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_view_order").panel("refresh");
				}
			}]
		}, {
			id : 'ledger_view_record',
			title : '追加记录',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/record-add',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_view_record").panel("refresh");
				}
			}]

		}, {
			id : 'ledger_view_no',
			title : '台帐无效记录',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger_view_no',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_view_no").panel("refresh");
				}
			}]

		}];

		portal_ledgerview = $('#portal_ledgerview').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-ledgerview-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-ledgerview-state');
		if (!state) {
			state = 'ledger_view_detail,ledger_view_call,ledger_view_order,ledger_view_record,ledger_view_no';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portal_ledgerview.portal('resize');

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
			var panels = portal_ledgerview.portal('getPanels', columnIndex);
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
					portal_ledgerview.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="ledgerLayoutView">
		<div data-options="region:'center',border:false" >
		<input type="hidden"   id="id" name="id" value="${id }">
			<div id="portal_ledgerview" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>