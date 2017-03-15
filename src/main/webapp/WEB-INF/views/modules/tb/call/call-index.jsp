<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/include/header.jsp"%> --%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript" charset="utf-8">
	var portalLayout_callIndex;
	var portal_callIndex;
	$(function() {
		portalLayout_callIndex = $('#portalLayout_callIndex').layout({
			fit : true
		});
		$(window).resize(function() {
			portalLayout_callIndex.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'callQuery',
			title : '',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/call/call-query?ledgerId='+${ledgerId} 
			
		}, {
			id : 'callLedgerList',
// 			title : '历史来电 <span style="color:gray;margin-left: 20px;">来电次数 :</span> '+callNum,
			title : '历史来电 ',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/call/call-ledgerlist',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#callLedgerList").panel("refresh");
				}
			}]

		}, {
			id : 'callOrderList',
// 			title : '历史工单<span style="color:gray;margin-left: 20px;">工单数 : </span>'+orderNum,
			title : '历史工单',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/call/call-orderlist',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
					$("#callOrderList").panel("refresh");
				}
			}]

		}];

		portal_callIndex = $('#portal_callIndex').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-callindex-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-callindex-state');
		if (!state) {
			state = 'callQuery,callLedgerList,callOrderList';
		}
		addPortalPanels(state);
		portal_callIndex.portal('resize');

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
			var panels = portal_callIndex.portal('getPanels', columnIndex);
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
					portal_callIndex.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
	<div id="portalLayout_callIndex">
		<div data-options="region:'center',border:false" >
			<div id="portal_callIndex" style="position: relative;height: auto;">
				<div></div>
			</div>
		</div>
	</div>