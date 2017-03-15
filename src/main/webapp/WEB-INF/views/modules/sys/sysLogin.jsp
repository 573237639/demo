<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
<title></title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/screen.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/login.css">
</head>
  <body style="text-align:center;">
      <div class="logina-logo" style="height: 103px">
        <a href="">
            <img src="${ctxStatic}/images/logo.png"  alt="" />
        </a>
    </div>
    <div class="logina-main main" >
        <div class="tab-con" style="width:450px; height:300px;margin:0 auto;">
            <form id="form-login"  name="myForm" method="post" action="${ctx}/login">
            
                <div id='login-error' class="error-tip">${message}</div>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th>账户</th>
                            <td width="245"><input type="text"  name="name" placeholder="用户名" autocomplete="off" value="${email}"></td>
                            <td></td>
                        </tr>
                        <tr>
                            <th>密码</th>
                            <td width="245">
                                <input type="password" name="password" value="${password }" placeholder="请输入密码" autocomplete="off">
                            </td>
                            <td>
                            	<input type="hidden" name="ReturnURL" value="${ReturnURL}">
                            	<input type="hidden" id="macAddress" name="macAddress" value="">
                            </td>
                        </tr>
                        <tr>
                            <th></th>
                            <td width="245"><input class="confirm" type="submit" value="登  录" style="color:#fff;background:#33a4ec;"></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" name="refer" value="site/">
            </form>
        </div>
        <!-- <div class="reg">
        </div> -->
    </div>
    <div id="footer">
        <div class="copyright">Copyright © 2015 kisso. All Rights Reserved.</div>
    </div>
</body>
<OBJECT id="locator" classid="CLSID:76A64158-CB41-11D1-8B02-00600806D9B6" height="0px" width="0px"></OBJECT>
<OBJECT id="foo" classid="CLSID:75718C9A-F029-11d1-A1AC-00C04FB6C223" height="0px" width="0px"></OBJECT>
<SCRIPT language="JScript">
    // 如果用IP地址取分机，请注释掉以下几行
    var service = locator.ConnectServer();
    var MACAddr;
    var IPAddr;
    var sDNSName;
    service.Security_.ImpersonationLevel=3;
    service.InstancesOfAsync(foo, 'Win32_NetworkAdapterConfiguration');
</SCRIPT>
 <SCRIPT language="JScript" event="OnObjectReady(objObject, objAsyncContext)" for="foo">
	if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true)
	{
		if(objObject.MACAddress != null && objObject.MACAddress != "undefined")
			MACAddr = objObject.MACAddress;
		if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined")
			IPAddr = objObject.IPAddress(0);
		if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")
			sDNSName = objObject.DNSHostName;
                var dstStr = "";
                for(vi=0; vi < MACAddr.length; vi++)
                {
                        if(MACAddr.charAt(vi) == ':') dstStr += '-';
                        else dstStr += MACAddr.charAt(vi);
                }
                MACAddr = dstStr;
        }
    //alert(MACAddr);
  </SCRIPT>
	<SCRIPT language="JScript" event="OnCompleted(hResult, pErrorObject, pAsyncContext)" for="foo">
	myForm.macAddress.value=MACAddr;
  </SCRIPT>	

</html>