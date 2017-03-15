<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	//点击搜索	
	function searchLedger(){
		//做时间校验
		var callTimeStart = $("#callTimeStart").textbox("getValue");
		var callTimeEnd = $("#callTimeEnd").textbox("getValue"); 
		if(callTimeStart != '' && callTimeEnd !=''){
			if(callTimeStart > callTimeEnd){
				$.messager.alert("消息提醒","结束时间不能小于开始时间","warning");
				return;
			}
		}
		//做表单验证
		//验证表单
		var validate = $("#tbLedgerQueryForm").form("validate");
		if(!validate){
// 		　　$.messager.alert("消息提醒","请检查你输入的数据!","warning");
		   return;
		} else{
			$("#searchLedger").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$("#ledgerlist-datagrid").datagrid('load');
			$.messager.progress("close");	// 关闭进度条
	    	$("#searchLedger").linkbutton('enable');
		}
	}

	//重置
	 function reloadCur(){
		 $('#tbLedgerQueryForm').form('reset');
	 }
	
	 function ledgerCheckDate() {
			//做一个提示
			$.messager.confirm('确认框', '请确认导出', function(r){
				if (r){			
					//坐时间校验
					var callTimeStart = $("#callTimeStart").textbox("getValue");
					var callTimeEnd = $("#callTimeEnd").textbox("getValue"); 
					
					
					var d1 = new Date(callTimeStart.replace(/-/g,"/"));
					var d2 = new Date(callTimeEnd.replace(/-/g,"/"));
					d1.setDate(d1.getDate()+31);
					if(callTimeEnd == ''){
					    $.messager.alert("消息提醒","结束时间不能为空","warning");
					    return ;
					}else if(callTimeStart > callTimeEnd){
					    $.messager.alert("消息提醒","结束时间不能小于开始时间","warning");
					    return ;
					}else if(d1 < d2){
						$.messager.alert("消息提醒","只能导出31天范围的数据，请重新选择时间","warning");
						return ;
					}else{
	 					document.getElementById("tbLedgerQueryForm").submit();
					}
				}
			});
		}
	 
	 function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是 s1  =  "2006-12-18"  s2  =  "2007-01-05"   
	       var  aDate,  oDate1,  oDate2,  iDays    
	       aDate  =  sDate1.split("-")    
	       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式    
	       aDate  =  sDate2.split("-")    
	       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    
	       iDays  =  parseInt((oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数    
	       return  iDays    
	   }  
</script>
<script type="text/javascript">
$(function() {  	
	  // 下拉框选择控件，下拉框的内容是动态查询数据库信息
		$('#numLedgerProvinceId').combobox({ 
        editable:false, //不可编辑状态
        cache: false,
        panelHeight: '300px',//自动高度适合
        valueField:'id', //值字段
        textField:'vacProvinceName', //显示的字段
        url:'${ctx}/sms/province/findAllProvince',
		  onHidePanel: function(){
		      $("#numLedgerCityId").combobox("setValue",'');
		      var ledgerPid = $("#numLedgerProvinceId").combobox('getValue');
		      if(ledgerPid == ''){
		    	  ledgerPid = 0
		      }
		      $.ajax({
		        type: "GET",
		        url:'${ctx}/sms/city/findCityByProvId/'+ledgerPid,
		        cache: false,
		        dataType : "json",
		        success: function(data){
		        	$("#numLedgerCityId").combobox("loadData",data);
		         }
		       }); 	
		  }
		}); 

	  $('#numLedgerCityId').combobox({ 
	      editable:false, //不可编辑状态
	      cache: false,
	      panelHeight: 'auto',//自动高度适合
	      valueField:'id', //值字段
	      textField:'vacCityName'//显示的字段
   });
	    
});

</script>
				<form id="tbLedgerQueryForm" method="post" action="method=ledgerexcel"  
				 modelAttribute="tbClients,tbLedger">
				<input type="hidden" id="page" name="page"/>
				<input type="hidden" id="rows" name="rows">
			<br/>
				<div >	
					<span class="div_margin_left">客户姓名</span>
					<input  class="easyui-textbox textbox_input"  id="vacClientName" name="vacClientName"  value="${tbClients.vacClientName}"  validType="length[0,20]"  >
					<span style="margin-left: 55px;">性别</span>
					<input class="easyui-combobox combobox_input" name="numClientGender"  editable="false"  
 						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/gender'" />
					<span class="div_margin_left">证件类型</span>
					<input class="easyui-combobox combobox_input" name="zjType"  editable="false" 
	   					data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/papers_type'"/>
					<input id="zjCode"  name="zjCode" class="easyui-textbox textbox_input"   value="${tbClients.zjCode}"  validType="length[0,25]"  >
				</div>
				<br/>
				<div>
					<span class="div_margin_left">来电号码</span>
					<input class="easyui-textbox textbox_input"  id="vacLedgerNumber" name="vacLedgerNumber"  value="${tbLedger.vacLedgerNumber }"  validType="length[8,13]"  />
					<span class="div_margin_left">咨询人类别</span>
					<input class="easyui-combobox combobox_input" name="numClientType" editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/consultant_type'" />
				   	<span class="div_margin_left">来电时间</span>
				   	<input class="easyui-datetimebox" id="callTimeStart" name="callTimeStart"  value="new date();"  editable="false" style="width:200px;height:26px;">
    				<input class="easyui-datetimebox" id="callTimeEnd" name="callTimeEnd"   editable="false"  style="width:200px;height:26px;">	
    			</div>	
				<br/>
				<div>		
					<span class="div_margin_left">处理情况</span>
					<input class="easyui-combobox combobox_input" name="numLedgerHandle" editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/handle_type'" />
					<span class="div_margin_left">话务员姓名</span>
					<input class="easyui-textbox textbox_input"  id="vacLedgerAgentName"  name="vacLedgerAgentName"  value="${tbLedger.vacLedgerAgentName }"  validType="length[0,20]" >
					<span class="div_margin_left">话务员工号</span>
					<input  class="easyui-textbox textbox_input"  id="vacLedgerAgentCode"  name="vacLedgerAgentCode"  value="${tbLedger.vacLedgerAgentCode }"  validType="length[0,10]" >
				</div>	

				<br/>
				<div>
					<span class="div_margin_left">业务类型</span>
					<input class="easyui-combotree"  style="width:300px;height:26px;" id="vacLedgerBusinessType" name="vacLedgerBusinessType" editable="false"
					data-options="editable:false,valueField:'id',width:150,panelHeight:300,textField:'text',url:'${ctx}/sys/specialty/findAllBusinessType'" />
					<span class="div_margin_left">是否有工单</span>
					<input class="easyui-combobox combobox_input" name="isOrder"  editable="false" 
						data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/yes_no'" />
				</div>
				<br/>
				<div>
					<span class="div_margin_left">来电队列</span>
					<input  class="easyui-textbox textbox_input"  id="numLedgerCallQueue" name="numLedgerCallQueue"  validType="length[0,20]"  >

					<span class="div_margin_left">来 电 归 属</span>
					<input class="easyui-combobox combobox_input"  id="numLedgerProvinceId" name="numLedgerProvinceId"   editable="false" >
					<input class="easyui-combobox combobox_input" id="numLedgerCityId" name="numLedgerCityId"  editable="false"  >
				</div>	
				<br/>
				<br/>
				<div class="div_margin_left" >
					<a href="javascript:searchLedger();" id="searchLedger"  iconCls="icon-search"  class="easyui-linkbutton a_button">搜   索</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:reloadCur();" iconCls ="icon-reload"  class="easyui-linkbutton a_button">重   置</a>
					
					<permission:hasPermission action="method=ledgerexcel">
					<input type="button" value="导    出"  class=" easyui-linkbutton a_button" onclick = "ledgerCheckDate();" />
					</permission:hasPermission>
					
					
				</div>
				<br/>
				<br/>
					</form>
					