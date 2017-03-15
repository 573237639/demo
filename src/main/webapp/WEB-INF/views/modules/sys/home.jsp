<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/common.js"></script>
<%
int agentBarWidth = 940; // 班长席
int agentBarHeight= 52;
%>
<c:if test="${monitor == 0 }">
<%
 agentBarWidth = 897; // 班长席
 agentBarHeight= 52;
%>
</c:if>
<script type="text/javascript">
var g_ledger_id = 0;
var g_pSkill = 0;
var g_CallIn = 0;
var g_CallID = "";
var g_Caller = "";
var g_Called = ""; 

// 来电事件(主叫号码, 被叫号码, 技能组)
function call_CallAlerting(pCaller, pCalled, pSkill)
{
// 	alert("来电事件，号码："+pCaller);
    try{
		ShowInfo("来电, Caller["+ pCaller +"]Called["+ pCalled +"]Skill["+ pSkill +"]");
		g_pSkill = pSkill;
		g_Caller = pCaller;
		g_Called = pCalled;
        var call_task_id = OKAgentBar.DoGetAssociateData(53);
        if(call_task_id.length <= 0 || parseInt(call_task_id,10) <= 0) {
            g_CallIn = 1; // 外线来电
        } else{
            g_CallIn = 0; // 预测外呼转座席来电
        }
    } catch(e) {
		ShowInfo("执行 call_Alerting 异常");
	}
}

//短消息到达事件
function AgentBar_DeliverSysMsg(pCaller, pCalled){
	ShowInfo("短消息到达事件AgentBar_DeliverSysMsg--->, Caller["+ pCaller +"]Called["+ pCalled +"]");
    try{
			ShowInfo("AgentBar_DeliverSysMsg, Caller["+ pCaller +"]Called["+ pCalled +"]");
			var TimeLong = OKAgentBar.DoGetTalkTimeLong(0); //取本次通话时长
		//CallID,Caller,Called,TalkTimelong
			var pageResult = g_CallID + "," + g_Caller + "," + g_Called + "," + TimeLong + "";		
			EventReleased(pageResult);
	} catch(e) { 
		ShowInfo("执行 AgentBar_DeliverSysMsg 异常");
	}
}
//签入返回事件
function AgentBar_LoginReturn( iReturnCode )
{
	ShowInfo("签入返回事件AgentBar_LoginReturn---->"+iReturnCode);
}

//签出返回事件
function AgentBar_LogoutReturn( iReturnCode )
{
	startclock1();
	ShowInfo("签出返回事件AgentBar_LogoutReturn---->"+iReturnCode);
}
// 开始外呼事件(主叫号码，被叫号码，CallID，iKey, pKeyVal) 其中iKey,pKeyVal对应DoMakeCall的iKey,iKeyVal
function call_CallBeginMakeCall(pCaller, pCalled, pCallID, iKey, pKeyVal)
{
	ShowInfo("开始外呼事件call_CallBeginMakeCall, Caller["+ pCaller +"]Called["+ pCalled +"]CallID["+ pCallID +"]iKey["+ iKey +"]pKeyVal["+ pKeyVal +"]");
    try{
		ShowInfo("外呼, Caller["+ pCaller +"]Called["+ pCalled +"]CallID["+ pCallID +"]iKey["+ iKey +"]pKeyVal["+ pKeyVal +"]");
        g_CallIn = 0;
		g_CallID = pCallID;
		g_Caller = pCaller;
		g_Called = pCalled;
        // 可在这里添加代码
    } catch(e) { 
		ShowInfo("执行 call_CallBeginMakeCall 异常");
	}
}
// 电话振铃事件
function call_CallDelivered(AlertingDN, CallerDN, CalledDN, LastDN, iStatus)
{
    try{
		ShowInfo("电话振铃事件call_CallDelivered, Caller["+AlertingDN+"]["+CallerDN+"]["+CalledDN+"]["+LastDN+"]["+ iStatus +"]");
	} catch(e) { 
		ShowInfo("执行 call_CallDelivered 异常");
	}
}

//话务接通返回事件
	function call_CallAsnwered2(pCallID,pCaller, pCalled, calltaskid,logid,taskid,remain){
//		ShowInfo("应答事件call_CallAnswer2,pCaller=["+ pCaller +"]=pCalled=["+ pCalled +"]=pCallID=["+ pCallID +"]=calltaskid=["+ calltaskid +"]=logid=["+ logid +"]=taskid=["+ taskid +"]=remain=["+ remain +"]");
 		//获取一个随机数
 		var t = "来电";
 		var number = pCaller;
 		if(g_pSkill == 0 || g_pSkill == 'undefined'){//外呼取被叫
 			number = pCalled;
 		    t ="外呼";
 		}
 		if(!$('#w').html()==""){
	 		$('#w').window('close', false); 
 		}
		if(pCallID != ''){
			$('#w').window({    
				title:t+"-"+Math.round(Math.random()*10) +"-"+number,
			    width:1050,    
			    height:600, 
			    padding:30,
// 			    modal:true,
		        onBeforeClose:function(){
		        	saveCallLedger(1);
		        	stopclock();
		        }
			});
			$('#w').window('refresh', encodeURI("${ctx}/tb/call/call-detail?callid="+pCallID+"&number="+number+"&pSkill="+g_pSkill)); 
		}
	}
// 挂机事件
function call_CallRelease(pCaller, pCalled)
{
	pauseclock();
    try{
    	g_CallID = "";
		g_Caller = "";
		g_Called = "";
		ShowInfo("挂机事件call_CallRelease, Caller["+ pCaller +"]Called["+ pCalled +"]");
	} catch(e) { 
		ShowInfo("执行 call_CallRelease 异常");
	}
}

//设置的时间到时触发该事件
function checkWorkEnd(){
	ShowInfo("设置的时间到时触发该事件checkWorkEnd");
}

// 页面释放时调用（本函数一定要释放）
function body_on_unload()
{
    try{  
        OKAgentBar.UnLoad();            
    } catch(e) { }
}
// 测试显示
function ShowInfo(Info)
{
//	try{
//		document.all.CallInfo.innerHTML = document.all.CallInfo.innerHTML + "<br>" + Info + "";
//	} catch(e) { }
}
function ClearInfo() 
{
	document.all.CallInfo.innerHTML = "";
}
//---------本次新增事件------- 电话挂断回调事件接口（不可在该事件中做耗时的长时间操作） +++++++++++++++++++++++++
function EventReleased(pageResult)
{
	try{
		ShowInfo("EventReleased, pageResult=["+ pageResult +"]");
		//用户挂机后用于记录通话信息，例如通话时长等调用时机
	}catch(e){
		ShowInfo("执行 EventReleased 异常");
	}
}

function autoWin(){
	if(!$('#w').is(":visible")&&g_ledger_id==0&&g_CallID!=""){
		call_CallAsnwered2(g_CallID,g_Caller, g_Called, "","","","");
	}
}

</script>
<title>广东12348台账系统</title>
<script type="text/javascript">
	var index_tabs;
	var index_tabsMenu;
	var index_layout;
	/**
	   * @author bestone
	   * 
	   * @requires jQuery,EasyUI,jQuery cookie plugin
	   * 
	   * 更换EasyUI主题的方法
	   * 
	   * @param themeName
	   *            主题名称
	   */
	  function changeThemeFun(themeName) {
	  	if ($.cookie('easyuiThemeName')) {
	  		$('#layout_north_pfMenu').menu('setIcon', {
	  			target : $('#layout_north_pfMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
	  			iconCls : 'emptyIcon'
	  		});
	  	}
	  	$('#layout_north_pfMenu').menu('setIcon', {
	  		target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
	  		iconCls : 'tick'
	  	});

	  	var $easyuiTheme = $('#easyuiTheme');
	  	var url = $easyuiTheme.attr('href');
	  	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	  	$easyuiTheme.attr('href', href);

	  	var $iframe = $('iframe');
	  	if ($iframe.length > 0) {
	  		for ( var i = 0; i < $iframe.length; i++) {
	  			var ifr = $iframe[i];
	  			try {
	  				$(ifr).contents().find('#easyuiTheme').attr('href', href);
	  			} catch (e) {
	  				try {
	  					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
	  				} catch (e) {
	  				}
	  			}
	  		}
	  	}

	  	$.cookie('easyuiThemeName', themeName, {
	  		expires : 7
	  	});

	  };

	  function logoutFun() {
	  	$.getJSON('${ctx}/logout', {
	  		t : new Date()
	  	}, function(result) {
	  		$.messager.alert("提示",result.msg);
	  		window.location='${ctx}/login';
	  	});
	  }

	  function editCurrentUserPwd() {
	  	parent.$.modalDialog({
	  		title : '修改密码',
	  		width : 300,
	  		height : 250,
	  		href : '${ctx}/user/userEditPwd',
	  		buttons : [ {
	  			text : '修改',
	  			handler : function() {
	  				var f = parent.$.modalDialog.handler.find('#userEditPwd_form');
	  				f.submit();
	  			}
	  		} ]
	  	});
	  }
	  
	$(function(){
		//index_layout
		index_layout = $('#index_layout').layout({
			fit : true
		});
		
		//index_tabs
		index_tabs = $('#index_tabs').tabs({
			fit:true,
			border:false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				index_tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			},
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					var href = index_tabs.tabs('getSelected').panel('options').href;
					if (href) {/*说明tab是以href方式引入的目标页面*/
						var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
						index_tabs.tabs('getTab', index).panel('refresh');
					} else {/*说明tab是以content方式引入的目标页面*/
						var panel = index_tabs.tabs('getSelected').panel('panel');
						var frame = panel.find('iframe');
						try {
							if (frame.length > 0) {
								for ( var i = 0; i < frame.length; i++) {
									frame[i].contentWindow.document.write('');
									frame[i].contentWindow.close();
									frame[i].src = frame[i].src;
								}
								if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
									try {
										CollectGarbage();
									} catch (e) {
									}
								}
							}
						} catch (e) {
						}
					}
				}
			}, {
				iconCls : 'delete',
				handler : function() {
					var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					var tab = index_tabs.tabs('getTab', index);
					if (tab.panel('options').closable) {
						index_tabs.tabs('close', index);
					} else {
						$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
					}
				}
			} ]
		});
		
		//index_tabsMenu
		index_tabsMenu = $('#index_tabsMenu').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('title');
				
				if (type === 'refresh') {
					index_tabs.tabs('getTab', curTabTitle).panel('refresh');
					return;
				}

				if (type === 'close') {
					var t = index_tabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						index_tabs.tabs('close', curTabTitle);
					}
					return;
				}

				var allTabs = index_tabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					index_tabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});
	});
</script>

  <SCRIPT LANGUAGE="vbscript" ID="ClientEventHandlersVBS">
  Sub OKAgentBar_CallAlerting(caller,called,skill)
    'msgbox "OKAgentBar_CallAlerting[" + caller + "][" + called + "][" + skill + "]"
	'msgbox "aaaaaaaaaaa"
    call_CallAlerting caller, called, skill
  End Sub
  Sub OKAgentBar_CallBeginMakeCall(CallID, CallerDN, CalledDN, iKey, pKeyVal)
    'msgbox "OKAgentBar_CallBeginMakeCall[" + caller + "][" + called + "][" + CallID + "]["+ iKey +"]["+ pKeyVal +"]"
    call_CallBeginMakeCall CallerDN, CalledDN, CallID, iKey, pKeyVal
  End Sub
  Sub OKAgentBar_CallRelease(caller,called)
    'msgbox "OKAgentBar_CallRelease[" + caller + "][" + called + "]"
    call_CallRelease caller, called
  End Sub
  Sub OKAgentBar_CallDelivered(AlertingDN, CallerDN, CalledDN, LastDN, iStatus)
    'msgbox "OKAgentBar_CallDelivered[" + AlertingDN + "][" + caller + "][" + called + "][" + CallID + "]["+ LastDN +"]["+ iStatus +"]"
    call_CallDelivered AlertingDN, CallerDN, CalledDN, LastDN, iStatus
  End Sub
  Sub OKAgentBar_CallAsnwered2(pCaller, pCalled, pCallID,calltaskid,logid,taskid,remain)
    'msgbox "OKAgentBar_CallAnswer2["+ pCaller +"][" + pCalled + "][" + pCallID + "]["+ calltaskid +"][" + logid + "][" + taskid + "][" + remain + "]"
    call_CallAsnwered2 pCaller, pCalled, pCallID,calltaskid,logid,taskid,remain 
  End Sub
  Sub OKAgentBar_LoginReturn(iReturnCode)
    'document.all.aaa.value = "LoginReturn iReturnCode= "+ iReturnCode
    AgentBar_LoginReturn iReturnCode
  End Sub
  Sub OKAgentBar_LogoutReturn(iReturnCode)
    'document.all.aaa.value = "LoginReturn iReturnCode= "+ iReturnCode
    AgentBar_LogoutReturn iReturnCode
  End Sub
  Sub OKAgentBar_DeliverSysMsg(agent, msg)
    AgentBar_DeliverSysMsg msg
  End Sub
  Sub OKAgentBar_IDLEAlert(ReturnCode)
    'msgbox  ReturnCode
    checkWorkEnd ReturnCode
  End Sub
  </SCRIPT>
</head>
<body  UNLOAD="body_on_unload()">
<div id="w"></div>
	<div id="index_layout">
<div data-options="region:'north'" style="height: 70px; overflow: hidden;" class="logo">
	<div id="sessionInfoDiv" style="position: absolute; right: 0px; top: 0px;" class="alert alert-info">
				【<strong>${realname}</strong>(<strong>${agentID}</strong>)】，欢迎你！
	</div>
	<div style="position: absolute; right: 0px; bottom: 0px;">
		<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_btMenu',iconCls:'cog'">呼叫弹框</a>
		<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a>
		 <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a> 
		<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'cog'">注销</a>
	</div>
	<div id="layout_north_btMenu" style="width: 100px; display: none;">
		<div onclick="autoWin();">呼叫弹框</div> 
	</div>
	<div id="layout_north_pfMenu" style="width: 120px; display: none;">
		<div onclick="changeThemeFun('default');" title="default">default</div>
		<div onclick="changeThemeFun('gray');" title="gray">gray</div>
		<div onclick="changeThemeFun('metro');" title="metro">metro</div>
	</div>
	<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
		<div onclick="editCurrentUserPwd();">修改密码</div>
	</div>
	<div id="layout_north_zxMenu" style="width: 100px; display: none;">
		<div onclick="logoutFun();">退出系统</div>
	</div>
  <TABLE border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
   <TD WIDTH="140">
     &nbsp;
   </TD>
   <TD ALIGN="left">
   <c:if test="${agentID != '' && agentDN != ''}">
     <OBJECT ID="OKAgentBar" NAME="OKAgentBar" WIDTH="<%=agentBarWidth%>" HEIGHT="<%=agentBarHeight%>" 
     CLASSID="CLSID:5220C3B8-3F25-4F78-B982-08A71C569363" 
     CODEBASE="OKAgentBar.cab#version=1,0,0,68">
		<PARAM NAME="AgentID" VALUE="${agentID }">
		<PARAM NAME="AgentDN" VALUE="${agentDN }">
		<PARAM NAME="AgentName" VALUE="测试班长">
		<PARAM NAME="AgentDNType" VALUE="4">
		<PARAM NAME="AgentSkill" VALUE="接听座席=2;外呼座席=1;">
		<PARAM NAME="LCHost" VALUE="192.168.126.242">		
		<PARAM NAME="LCPort" VALUE="9876">	
		<PARAM NAME="LCUserName" VALUE="user">
		<PARAM NAME="LCPassword" VALUE="pwd">
		<PARAM NAME="AutoAnswerTimeLong" VALUE="60000">
		<PARAM NAME="DebugMode" VALUE="1">
		<PARAM NAME="Monitor" VALUE="${monitor }">
		<PARAM NAME="AgentPwd" VALUE="111111">
		<PARAM NAME="AutoLogin" VALUE="0">
		<PARAM NAME="IDLEAlarmTimeLong" VALUE="${idleAlertTimelong}">
		<PARAM NAME="SIPServer" VALUE="192.168.126.242">
		<PARAM NAME="SIPUser" VALUE="8001">
		<PARAM NAME="SIPPwd" VALUE="12345">
		<PARAM NAME="FaxServer" VALUE="192.168.126.242">
		<PARAM NAME="FaxUser" VALUE="ftp">
		<PARAM NAME="FaxPwd" VALUE="ftp123">
		<PARAM NAME="AllowMakeCall" VALUE="1">
		<PARAM NAME="AllowChat" VALUE="1">
		<PARAM NAME="PhoneEncryption" VALUE="0">
		<PARAM NAME="HaveSocketServer" VALUE="0">
		<PARAM NAME="UseNoReadyType" VALUE="0">
		<PARAM NAME="UseHotKey" VALUE="0">
		<PARAM NAME="UseSelMakeCallCaller" VALUE="0">
		<PARAM NAME="CallerList" VALUE="">
     </OBJECT>  
     </c:if>   
    </TD>
  </tr>
  <TR>
    <TD ALIGN="center" colspan='2' id="CallInfo">		
	</TD>
  </TR>
</TABLE>


</div>
		<div data-options="region:'west',href:'${ctx}/sys/layout/west',split:true" style="width: 200px; overflow: hidden;"></div>
		<div data-options="region:'center'" title="欢迎使用广东12348台账系统" style="overflow: hidden;">
			<div id="index_tabs" style="overflow: hidden;">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<iframe src="${ctx}/sys/portal/index" frameborder="0" style="border: 0; width: 100%; height: 98%;"></iframe>
				</div>
			</div>
		</div>
		<div data-options="region:'east',href:'${ctx}/sys/layout/east'" title="" style="width: 10px; overflow: hidden;"></div>
		<div data-options="region:'south',href:'${ctx}/sys/layout/south',border:false" style="height: 30px; overflow: hidden;"></div>
	</div>

	<div id="index_tabsMenu" style="width: 120px; display: none;">
		<div title="refresh" data-options="iconCls:'transmit'">刷新</div>
		<div class="menu-sep"></div>
		<div title="close" data-options="iconCls:'delete'">关闭</div>
		<div title="closeOther" data-options="iconCls:'delete'">关闭其他</div>
		<div title="closeAll" data-options="iconCls:'delete'">关闭所有</div>
	</div>
</body>
</html>