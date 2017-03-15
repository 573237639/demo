<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%


int agentBarWidth = 940; // 班长席
int agentBarHeight= 52;

%>
<!-- <html xmlns="http://www.w3.org/1999/xhtml"> -->
<!-- <HEAD> -->
  
  <SCRIPT language="JavaScript" >
  
	var g_CallIn = 0;
	var g_CallID = "";
	var g_Caller = "";
	var g_Called = ""; 
	//---------本次新增方法------- 外呼接口 +++++++++++++++++++++++++
	function agentDial(dnis)
	{	
		alert("外呼事件，被叫："+dnis);
		try{
			OKAgentBar.DoMakeCall("", dnis, 100, "12345678"); //AgentDN(坐席),Called,iKey,iKeyVal
		} catch(e) { 
			ShowInfo("执行 agentDial 异常");
		}
	}

	// 来电事件(主叫号码, 被叫号码, 技能组)
    function call_Alerting(pCaller, pCalled, pSkill)
    {
		alert("来电事件，号码："+pCaller);
        try{
			ShowInfo("来电, Caller["+ pCaller +"]Called["+ pCalled +"]Skill["+ pSkill +"]");
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

	// 挂机事件
    function call_CallRelease(pCaller, pCalled)
    {
        try{
    		ShowInfo("挂机事件call_CallRelease, Caller["+ pCaller +"]Called["+ pCalled +"]");
		} catch(e) { 
			ShowInfo("执行 call_CallRelease 异常");
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
	function call_CallAsnwered(callid,pCaller, pCalled){
		ShowInfo("话务接通返回事件call_CallAsnwered");
        try{
			ShowInfo("call_CallAsnwered, ["+ callid +"]Caller["+ pCaller +"]Called["+ pCalled +"]");
			var TimeLong = OKAgentBar.DoGetTalkTimeLong(0); //取本次通话时长
			//CallID,Caller,Called,TalkTimelong
// 			var pageResult = g_CallID + "," + g_Caller + "," + g_Called + "," + TimeLong + "";		
// 			EventReleased(pageResult);
		} catch(e) { 
			ShowInfo("执行 call_CallAsnwered 异常");
		}
	}
	
	//短消息到达事件
	function AgentBar_DeliverSysMsg(pCaller, pCalled){
		ShowInfo("短消息到达事件AgentBar_DeliverSysMsg--->, Caller["+ pCaller +"]Called["+ pCalled +"]");
        try{
// 			ShowInfo("AgentBar_DeliverSysMsg, Caller["+ pCaller +"]Called["+ pCalled +"]");
// 			var TimeLong = OKAgentBar.DoGetTalkTimeLong(0); //取本次通话时长
			//CallID,Caller,Called,TalkTimelong
// 			var pageResult = g_CallID + "," + g_Caller + "," + g_Called + "," + TimeLong + "";		
// 			EventReleased(pageResult);
		} catch(e) { 
			ShowInfo("执行 AgentBar_DeliverSysMsg 异常");
		}
	}
	
	//签入返回事件
    function AgentBar_LoginReturn( iReturnCode )
    {
    	ShowInfo("签入返回事件AgentBar_LoginReturn---->"+iReturnCode);
        document.all.m_LoginStatus.value = iReturnCode;
        if(iReturnCode != 0) return;
    }
	
	//签出返回事件
    function AgentBar_LogoutReturn( iReturnCode )
    {
		alert(0);
    	ShowInfo("签出返回事件AgentBar_LogoutReturn---->"+iReturnCode);
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

   
	// 应答事件(主叫号码, 被叫号码, CallID)
    function call_CallAnswer( pCaller,  pCalled,  pCallID)
    {
		alert("应答, Caller["+ pCaller +"]Called["+ pCalled +"]CallID["+ pCallID +"]");
        try{
			ShowInfo("应答, Caller["+ pCaller +"]Called["+ pCalled +"]CallID["+ pCallID +"]");
			g_CallID = pCallID;
			g_Caller = pCaller;
			g_Called = pCalled;

            var call_task_id = OKAgentBar.DoGetAssociateData(53);
            if(call_task_id.length > 0 && parseInt(call_task_id,10) > 0) { 
				// 预测外呼转座席来电应答
				// 可在这里添加代码
            } else {
                if(g_CallIn > 0) {                  
                    // 外线来电座席应答
					// 可在这里添加代码
                } else {                    
                    // 手工外呼应答
					// 可在这里添加代码
                }
            }
        } catch(e) { 
			ShowInfo("执行 call_CallAnswer 异常");
		}
    }
     // 测试显示
	function ShowInfo(Info)
	{
// 		try{
// 			document.all.CallInfo.innerHTML = document.all.CallInfo.innerHTML + "<br>" + Info + "";
// 		} catch(e) { }
	}
	function ClearInfo() 
	{
		document.all.CallInfo.innerHTML = "";
	}

   
//------------------------------------分割线----------------------------------------------------------------------------

	

	//---------本次新增方法------- 软电话的转IVR收集扣款帐号接口  +++++++++++++++++++++++++
	/*function toIvrForAccountNO(customerName, customerGender, bank, amount)
	{
	   try{
    		OKAgentBar.DoSetAssociateData(700,customerName);// 客户姓名
			if(parseInt(customerGender) == 1)
				OKAgentBar.DoSetAssociateData(701,"先生");// 姓别：先生、小姐（为了cti方便，这里请直接填汉字）
			else
				OKAgentBar.DoSetAssociateData(701,"小姐");
			OKAgentBar.DoSetAssociateData(702,bank);// 扣款银行名称
			OKAgentBar.DoSetAssociateData(703,amount);// 扣款金额
			OKAgentBar.DoTransferIVR(1);//收集客户扣款银行信息
        } catch(e) {
			ShowInfo("执行 toIvrForAccountNO 异常");
		}
	}

	//---------本次新增事件------- 用户按完扣款帐号，转回座席，调用弹屏接口（不可在该事件中做耗时的长时间操作） +++++++++++++++++++++++++
	function popPage(info,isComing)
	{
		try{
			ShowInfo("popPage, info=["+ info +"]");
			//此处为软电话弹屏接口的实现：CRM系统实现此方法		
		} catch(e) {
			ShowInfo("执行 popPage 异常");
		}
	}
	

	//---------本次新增事件------- 坐席转IVR再次转回坐席事件
	function AgentBar_TransferIVRReturn(iReturnCode) //iReturnCode 0--客户完成输入  1--客户未完成输入
	{
        try{
			ShowInfo("转回, iReturnCode["+ iReturnCode +"]");
			var AccNO = "";
			var FeeTag= "";
			if(parseInt(iReturnCode) == 0) { // 客户完成输入
				AccNO = OKAgentBar.DoGetAssociateData(800);//银行帐号
				FeeTag= OKAgentBar.DoGetAssociateData(801);//实时扣款信息
			}
			//CallID,Caller,Called,AccNO,FeeTag
			var info = g_CallID + "," + g_Caller + "," + g_Called + "," + AccNO + "," + FeeTag + "";
			popPage(info,false);
		} catch(e) { 
			ShowInfo("执行 AgentBar_TransferIVRReturn 异常");
		}
	}
	
	
	function call_CallAlertingEx(pCallID, pCaller, pCalled, pSkill)
	{
		alert("来电事件call_CallAlertingEx");
	}
	function call_CallAlerting(pCaller, pCalled,pSkill){
		alert("call_CallAlerting来电事件");
	}
	*/
	
	function call_CallAsnwered2(pCaller, pCalled, pCallID,calltaskid,logid,taskid,remain){
		ShowInfo("应答事件call_CallAnswer2["+ pCaller +"]=pCalled=["+ pCalled +"]=pCallID=["+ pCallID +"]=calltaskid=["+ calltaskid +"]=logid=["+ logid +"]=taskid=["+ taskid +"]=remain=["+ remain +"]");
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
  </SCRIPT>
<!--   Sub OKAgentBar_CallAlertingEX(CallID,CallerDN,CalledDN ,Skills) -->
<!--     'msgbox "OKAgentBar_CallAlertingEX[" + CallID + "][" + CallerDN + "][" + CalledDN + "][" + Skills + "]" -->
<!-- 	'msgbox "aaaaaaaaaaa" -->
<!--     call_CallAlertingEX CallID,CallerDN,CalledDN ,Skills -->
<!--   End Sub -->
<!--   Sub OKAgentBar_CallAsnweredEx(callid, caller, called) -->
<!--     'msgbox "OKAgentBar_CallAsnweredEx["+ callid +"][" + caller + "][" + called + "]" -->
<!--     call_CallAsnweredEx callid,caller, called  -->
<!--   End Sub -->

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
  Sub OKAgentBar_CallAsnwered(callid, caller, called)
    'msgbox "OKAgentBar_CallAsnwered["+ callid +"][" + caller + "][" + called + "]"
    call_CallAsnwered callid,caller, called 
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

  
<!-- </HEAD> -->
<div>
<BODY background="../images/bg_title.jpg" leftmargin="0" topmargin="5"  UNLOAD="body_on_unload()">
<!--onload="startServer()"-->
	<div id="sessionInfoDiv" style="position: absolute; right: 0px; top: 0px;" class="alert alert-info">
				[<strong>${token.data}</strong>]，欢迎你！
	</div>
	<div style="position: absolute; right: 0px; bottom: 0px;">
		<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'cog'">注销</a>
	</div>
	<div id="layout_north_pfMenu" style="width: 120px; display: none;">
		<div onclick="changeThemeFun('default');" title="default">default</div>
		<div onclick="changeThemeFun('gray');" title="gray">gray</div>
		<div onclick="changeThemeFun('metro');" title="metro">metro</div>
		<div onclick="changeThemeFun('bootstrap');" title="bootstrap">bootstrap</div>
		<div onclick="changeThemeFun('black');" title="black">black</div>
		<div class="menu-sep"></div>
		<div onclick="changeThemeFun('cupertino');" title="cupertino">cupertino</div>
		<div onclick="changeThemeFun('dark-hive');" title="dark-hive">dark-hive</div>
		<div onclick="changeThemeFun('pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
		<div onclick="changeThemeFun('sunny');" title="sunny">sunny</div>
		<div class="menu-sep"></div>
		<div onclick="changeThemeFun('metro-blue');" title="metro-blue">metro-blue</div>
		<div onclick="changeThemeFun('metro-gray');" title="metro-gray">metro-gray</div>
		<div onclick="changeThemeFun('metro-green');" title="metro-green">metro-green</div>
		<div onclick="changeThemeFun('metro-orange');" title="metro-orange">metro-orange</div>
		<div onclick="changeThemeFun('metro-red');" title="metro-red">metro-red</div>
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
     <OBJECT ID="OKAgentBar" NAME="OKAgentBar" WIDTH="<%=agentBarWidth%>" HEIGHT="<%=agentBarHeight%>" 
     CLASSID="CLSID:5220C3B8-3F25-4F78-B982-08A71C569363" 
     CODEBASE="OKAgentBar.cab#version=1,0,0,68">
		<PARAM NAME="AgentID" VALUE="8001"><!--工号 ${agentID }-->
		<PARAM NAME="AgentDN" VALUE="1071"><!--分机 ${agentDN }-->
		<PARAM NAME="AgentName" VALUE="测试班长"><!--坐席姓名-->
		<PARAM NAME="AgentDNType" VALUE="4"><!--分机类别 1--本地模拟分机 2--本地数字坐席 3--远端坐席 4--SIP坐席  5--H323坐席-->
		<PARAM NAME="AgentSkill" VALUE="接听座席=2;外呼座席=1;"><!--签入技能组-->
		<PARAM NAME="LCHost" VALUE="192.168.126.242"><!--连接的Host地址-->		
		<PARAM NAME="LCPort" VALUE="9876"><!--连接的Host端口-->	
		<PARAM NAME="LCUserName" VALUE="user"><!--保留-->
		<PARAM NAME="LCPassword" VALUE="pwd"><!--保留-->
		<PARAM NAME="AutoAnswerTimeLong" VALUE="60000"><!--自动应答时间-->
		<PARAM NAME="DebugMode" VALUE="1"><!--是否打印调试信息-->
		<PARAM NAME="Monitor" VALUE="1"><!--是否班长席 ${monitor }-->
		<PARAM NAME="AgentPwd" VALUE="111111"><!--登录密码（保留）-->
		<PARAM NAME="AutoLogin" VALUE="0"><!--是否自动登录-->
		<PARAM NAME="IDLEAlarmTimeLong" VALUE="30"><!--空闲自动提醒时间（分钟）-->
		<PARAM NAME="SIPServer" VALUE="192.168.126.242"><!--保留-->
		<PARAM NAME="SIPUser" VALUE="8001"><!--保留-->
		<PARAM NAME="SIPPwd" VALUE="12345"><!--保留-->
		<PARAM NAME="FaxServer" VALUE="192.168.126.242"><!--传真服务器ftp地址-->
		<PARAM NAME="FaxUser" VALUE="ftp"><!--传真服务器ftp用户名-->
		<PARAM NAME="FaxPwd" VALUE="ftp123"><!--传真服务器ftp密码-->
		<PARAM NAME="AllowMakeCall" VALUE="1"><!--是否允许按外拨按钮呼出-->
		<PARAM NAME="AllowChat" VALUE="1"><!--是否允许信使-->
		<PARAM NAME="PhoneEncryption" VALUE="0"><!--电话号码是否加密-->
		<PARAM NAME="HaveSocketServer" VALUE="0"><!--软电话是否有做Socket服务端 一般为0，有特殊情况咨询本软件开发商-->
		<PARAM NAME="UseNoReadyType" VALUE="0"><!--是否使用置忙类型(吃饭，小休，开会，培训，其他)-->
		<PARAM NAME="UseHotKey" VALUE="0"><!--是否采用快捷键Alt+A应答来话-->
		<PARAM NAME="UseSelMakeCallCaller" VALUE="0"><!--外拨时是否可以选择CallerList指定的主叫呼出-->
		<PARAM NAME="CallerList" VALUE=""><!--呼出或咨询时，系统指定的主叫号码-->
     </OBJECT>     
    </TD>
  </tr>
  <TR>
    <TD ALIGN="center" colspan='2' >
 		<input type='button' name='dial_out' value='外拨' onclick="agentDial('15221172648');"><!--agentDial() 1157 -->
		<input type='button' name='toIVR'   value='转IVR' onclick="toIvrForAccountNO('林子',1,'工商银行','123.45')">
		<input type='button' name='clearInfo' value='清除' onclick="ClearInfo()">
		<input type='button' name='xiaoxiu' value='小休' onclick="OKAgentBar.DoSetAgentStatusEx(3,1)">
		<input type='button' name='peixun' value='培训' onclick="OKAgentBar.DoSetAgentStatusEx(3,3)">
	</TD>
  </TR>
  <!--获取拨打日记-->
  <TR>
    <TD ALIGN="center" colspan='2' id="CallInfo">		
	</TD>
  </TR>
</TABLE>
</BODY>
<!-- </html> -->
</div>
