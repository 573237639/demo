<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" charset="utf-8">
	var layout_ledgerEdit;
	var portal_ledgerEdit;
	var status = ${status};
	$(function() {
		var id = $("#id").val();
		layout_ledgerEdit = $('#layout_ledgerEdit').layout({
			fit : true
		});
		$(window).resize(function() {
			layout_ledgerEdit.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'ledger_edit_detail',
			title : '台账详情',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-edit-detail/'+id
		}, {
			id : 'ledger_edit_call',
			title : '历史来电',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-edit-call',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_edit_call").panel("refresh");
				}
			}]

		}, {
			id : 'ledger_edit_order',
			title : '历史工单 ',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-edit-order',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_edit_order").panel("refresh");
				}
			}]
		}, {
			id : 'ledger_edit_record',
			title : '追加记录',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/record-list',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_edit_record").panel("refresh");
				}
			}]

		}, {
			id : 'ledger-edit-no',
			title : '台帐无效记录',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/ledger-edit-no',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#ledger_edit_no").panel("refresh");
				}
			}]

		}];

		portal_ledgerEdit = $('#portal_ledgerEdit').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-ledgeredit-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-ledgeredit-state');
		if (!state) {
			//在这里做一个判断
			if(status  == <%=CommonField.LEDGER_STATUS_FILL%>){
				//如果是待填写台帐，继续填写，只显示明细、历史来电、历史工单
				state = 'ledger_edit_detail,ledger_edit_call,ledger_edit_order';
			}else{
				state = 'ledger_edit_detail,ledger_edit_call,ledger_edit_order,ledger_edit_record,ledger-edit-no';/*冒号代表列，逗号代表行*/
			}
		}
		addPortalPanels(state);
		portal_ledgerEdit.portal('resize');

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
			var panels = portal_ledgerEdit.portal('getPanels', columnIndex);
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
					portal_ledgerEdit.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="layout_ledgerEdit">
		<div data-options="region:'center',border:false" >
		<input type="hidden" id="id" name="id" value="${id }">
			<div id="portal_ledgerEdit" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>