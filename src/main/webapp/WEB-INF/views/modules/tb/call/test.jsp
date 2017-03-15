<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<!-- 引入编辑器 -->
<link type="text/css" rel="stylesheet" href="${ctxStatic}/kindeditor-4.1.10/themes/default/default.css" />
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/kindeditor.js"></script>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/lang/zh_CN.js"></script>
<%-- <script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/kindeditor-4.1.10/kindeditor-min.js"></script> --%>
	<div style="margin:20px 0;">
		<a href="${ctx}/tb/call/win?number=15221172648" class="easyui-linkbutton"  >Open Call Window </a>
	</div>
	
		<script>
			var editor;
				KindEditor.ready(function(K) {
					editor = K.create('textarea[name="content"]', {
							uploadJson : '${ctx}/file_upload',//上传
			                fileManagerJson : '${ctx}/file_manager',//文件管理
			                allowFileManager : true,
			                afterBlur: function () { this.sync(); },//数据同步
					});
				});
		</script>
		<a href="${ctx}/tb/oracle/cxftest" class="easyui-linkbutton"  >点击请求服务端</a>


	<h3>默认模式</h3>
	<form>
<textarea name="content" style="width:800px;height:400px;visibility:hidden;">KindEditor</textarea>
	</form>
	
	<script type="text/javascript">
		var c=0
		var t
		function timedCount()
		{
			document.getElementById('txt').value=c
			c=c+1
			t=setTimeout("timedCount()",1000)
		}
		function stopCount()
		{
			clearTimeout(t)
		}
	</script> 
	
	<form>
<input type="button" value="开始计时！" onClick="timedCount()">
<input type="text" id="txt">
<input type="button" value="停止计时！" onClick="stopCount()">
</form> 
	
<script language="javascript">
	var se,m=0,h=0,s=0;  
	var v ="正常通话中";
	function second(){  
// 		if((ss%100)==0){s+=1;ss=1;}  
		if(s>0 && (s%60)==0){m+=1;s=0;}  
		if(m>0 && (m%60)==0){h+=1;m=0;}  
		if(h<10){t = "0"+h;}else{	t =h;	}
		t +=":";
		if(m<10){t += "0"+m;}else {t +=m;
			v  = "通话时长已经超过10分钟";
		}
		t +=":";
		if(s< 10){t += "0"+s;}else{t +=s;
			v  = "通话时长已经超过10秒";
		}
// 		t +=":";
// 		t +=ss;
		
		document.getElementById("showtime").value=t;   //这有一个给id为showtime的input赋值的语句，可以实现动态计时。
		document.getElementById("message").value = v;
		//其实所谓的动态计时，就是在很短的时间里不停给显示时间的地方更新数值，由于速度很快，这样计时器看起来时刻都在变化。但其实不是的，它从本质上还是静态的，这跟js的伪多线程原理是一样的。
		s+=1;  
	}  
	function startclock(){se=setInterval("second()",1000);}  //这个函数是要放到按钮的click事件上的
	function pauseclock(){clearInterval(se);}    //这个函数是要放到按钮的click事件上的
	function stopclock(){clearInterval(se);ss=1;m=h=s=0;}    //这个函数是要放到按钮的click事件上的
</script>  
	
<input name="s" type="button" value="开始计时" onClick="startclock()">  
<input name="s" type="button" value="暂停计时" onClick="pauseclock()">  
<input name="s" type="button" value="停止计时" onClick="stopclock()">  
<input name="showtime" style="color:#ff0000;width:200px;" id="showtime" type="text" value="00:00:00"> 
<input name="message" style="color:#ff0000;width:200px;" id="message" type="text" value=""> 

<br/>
<br/>
<br/>
<br/>
	<div style="margin:20px 0;">
		<a href="${ctx}/tb/oracle/queryCount" class="easyui-linkbutton"  >点击查询</a>

	</div>

<br/>
<br/>
<br/>
<br/>

<object id="czplayer" width="300" height="63" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" 
		codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=7.0" 
		align="top" border="0" type="application/x-oleobject">
     <param name="URL" value="ftp://ruanmh:123@192.168.28.155/musicTest.mp3">
</object>
<br/>

<!--  window open post -->
<script>   
   function openSpecfiyWindown( windowName )   {   
    window.open('about:blank',windowName,'width=700,height=400,menubar=no,scrollbars=no');   
   }   
  </script>

<form id="editForm" name="editForm" method="post" action="viewUser" target="colors123" onsubmit="openSpecfiyWindown( 'colors123' )">  
 <fieldset>  
  <input type="hidden" id="id" name="id" value="$!{User.id}" />  
  <input type="submit" />  
 </fieldset>  
</form>  

<script>
    function openNewSpecifiedWindow( windowName )
    {
     window.open('about:blank',windowName,'width=700,height=400,menubar=no,scrollbars=no');
    }
   </script>

<form id="editForm" name="editForm" method="post" action="viewUser" target="newWindow" onsubmit="openNewSpecifiedWindow( 'newWindow' )">
   <fieldset>
    <input type="hidden" id="id" name="id" value="/$!{User.id}" />
    <input type="submit" />
   </fieldset>
</form>






	
	
	
	
	
	
	