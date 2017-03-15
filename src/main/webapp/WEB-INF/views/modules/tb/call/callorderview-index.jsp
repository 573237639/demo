<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript" charset="utf-8">
	var callorderviewLayout;
	var callorderviewPortal;
	$(function() {
		var id = $("#id").val();
		callorderviewLayout = $('#callorderviewLayout').layout({
			fit : true
		});
		$(window).resize(function() {
			callorderviewLayout.layout('panel', 'center').panel('resize', {
				width : 1,
				height : 1
			});
		});

		panels = [ {
			id : 'p1',
			title : '工单信息',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/call/callorderview-detail/'+id,
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					
				}
			} ]
		}, {
			id : 'p2',
			title : '历史工单',
			height : 'auto',
			collapsible : true,
			href : '${ctx}/tb/call/callorderview-order',
			tools : [{
				iconCls : 'database_refresh',
				handler : function() {
				}
			}]

		}];

		callorderviewPortal = $('#callorderviewPortal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-callorderview-state', getPortalState(), {
					expires : 7
				});
			}
		});
		var state = $.cookie('portal-callorderview-state');
		if (!state) {
			state = 'p1,p2';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		callorderviewPortal.portal('resize');

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
			var panels = callorderviewPortal.portal('getPanels', columnIndex);
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
					callorderviewPortal.portal('add', {
						panel : p,
						columnIndex : 0
					});
				}
			}
	}
</script>
	<div id="callorderviewLayout" style="height: 400px">
		<div data-options="region:'center',border:false" >
		<input type="hidden"   id="id" name="id" value="${id }">
			<div id="callorderviewPortal" style="position: relative">
				<div></div>
			</div>
		</div>
	</div>