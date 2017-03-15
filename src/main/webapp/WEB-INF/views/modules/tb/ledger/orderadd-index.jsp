<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript" charset="utf-8">
	var orderadd_layout;
	var portal_orderadd;
	var orderaddOrderNum = ${orderaddOrderNum};
	$(function() {
		var id = $("#id").val();
		orderadd_layout = $('#orderadd_layout').layout({
			fit : true
		});
		$(window).resize(function() {
			orderadd_layout.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'orderadd-detail',
			title : '工单信息',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/orderadd-detail/'+id,
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					 $('#orderadd-detail').panel('refresh');
				}
			} ]
		},{
			id : 'orderadd-order',
			title : '历史工单<span style="color:gray;margin-left: 20px;">工单数  :</span> '+orderaddOrderNum,
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/ledger/orderadd-order',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					 $('#orderadd-order').panel('refresh');
				}
			}]

		}];

		portal_orderadd = $('#portal_orderadd').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal_orderadd-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal_orderadd-state');
		if (!state) {
			state = 'orderadd-detail,orderadd-order';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		portal_orderadd.portal('resize');

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
			var panels = portal_orderadd.portal('getPanels', columnIndex);
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
					portal_orderadd.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
</head>
<body>
	<div id="orderadd_layout">
		<div data-options="region:'center',border:false" >
		<input type="hidden" id="id" name="id" value="${id }">
			<div id="portal_orderadd" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>
</body>
</html>