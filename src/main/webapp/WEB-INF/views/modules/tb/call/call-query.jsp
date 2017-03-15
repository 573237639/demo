<%@page import="com.fabao.ledger.common.utils.CommonField"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script  type="text/javascript"  charset="utf-8" src="${ctxStatic}/common.js"></script>

<script type="text/javascript">

	//点击保存台账	
	function saveCallLedger(status){
		if(status == <%=CommonField.LEDGER_STATUS_SUCCESS %>){
			//性别
			var genderFlag = false;
			var genderRadio = $("input[name='numClientGender']");
			for(var i = 0;i < genderRadio.length;i++){
				   if(genderRadio[i].checked){
					   genderFlag = true;
					   break;
				   }
				}
			if(!genderFlag){
				$.messager.alert("消息提醒","请选择性别","warning");
				return;
			}
			//咨询人类别
			var typeFlag = false;
			var typeRadio = $("input[name='numClientType']");
			for(var i = 0;i < typeRadio.length;i++){
				   if(typeRadio[i].checked){
					   typeFlag = true;
					   break;
				   }
				}
			if(!typeFlag){
				$.messager.alert("消息提醒","请选择咨询人类别!","warning");
				return;
			}
			//处理情况
			var handleFlag = false;
			var handleRadio = $("input[name='numLedgerHandle']");
			for(var i = 0;i < handleRadio.length;i++){
			   if(handleRadio[i].checked){
				   handleFlag = true;
				   break;
			   }
			}
			if(!handleFlag){
				$.messager.alert("消息提醒","请选择处理情况!","warning");
				return;
			}
			var validate = $("#tbCallLedgerForm").form("validate");
			if(!validate){
				return;
			} 
		
			$("#saveCallLedger").linkbutton('disable');
			$.messager.progress();	// 显示进度条
			$('#tbCallLedgerForm').form('submit', {    
				url:"${ctx}/tb/call/saveCallLedger/"+status, 
			    success:function(data){
		        	$("#saveCallLedger").linkbutton('enable');
			    	$.messager.progress("close");	// 关闭进度条
			    	var dataObj=eval("("+data+")");
		        	if(dataObj.code == 0){
			        	$.messager.alert('提示','台帐保存成功!');
			        	$("#listCallGrid").datagrid('load');
			        }else{
			        	$.messager.alert('提示','台帐保存失败!原因：'+dataObj.message);
			        } 
			    }    
			}); 
		}else{
			var id = $("input[name='tbLedger.id']").val();
			g_ledger_id = id;//用于判断是否打开过来电
			var vacLedgerTalkDur = $("input[name='vacLedgerTalkDur']").val();
			if(id == ''){
				return ;
			}
			//发送一个ajax请求
	    	$.ajax({
	            url:"${ctx}/tb/call/saveCallLedgerByFail",
				data:$("#tbCallLedgerForm").serialize(),
	            type:"post",
	            dataType:"json",
	            jsonpCallback:"callback",
	            success: function(data){
			        $("#listCallGrid").datagrid('load');
	            }
	        })
		}
	}
	
	function saveCallOrder(status){
		//性别
		var genderFlag = false;
		var genderRadio = $("input[name='numClientGender']");
		for(var i = 0;i < genderRadio.length;i++){
			   if(genderRadio[i].checked){
				   genderFlag = true;
				   break;
			   }
			}
		if(!genderFlag){
			$.messager.alert("消息提醒","请选择性别","warning");
			return;
		}
		//咨询人类别
		var typeFlag = false;
		var typeRadio = $("input[name='numClientType']");
		for(var i = 0;i < typeRadio.length;i++){
			   if(typeRadio[i].checked){
				   typeFlag = true;
				   break;
			   }
			}
		if(!typeFlag){
			$.messager.alert("消息提醒","请选择咨询人类别!","warning");
			return;
		}
		//处理情况
		var handleFlag = false;
		var handleRadio = $("input[name='numLedgerHandle']");
		for(var i = 0;i < handleRadio.length;i++){
		   if(handleRadio[i].checked){
			   handleFlag = true;
			   break;
		   }
		}
		if(!handleFlag){
			$.messager.alert("消息提醒","请选择处理情况!","warning");
			return;
		}
		var validate = $("#tbCallLedgerForm").form('enableValidation').form("validate");
		if(!validate){
			return;
		} else{
			//先保存台帐
			$('#tbCallLedgerForm').form('submit', {    
				url:"${ctx}/tb/call/saveCallLedger/"+status, 
			    success:function(data){
			    	var dataObj=eval("("+data+")");
		        	if(dataObj.code == 0){
		        		var id = $("input[name='tbLedger.id']").val();
		        	 	$('#tt').tabs('add',{   
		        	 	    title:'生成工单'+id,
		        	  	    href:"${ctx}/tb/call/call-order/"+id, 
		        	  	    closable:true
		        	 	}); 
		        	}else{
			        	$.messager.alert('提示','台帐保存失败!原因：'+dataObj.message);
			        } 
			    }    
			});  
		}
	}
	

	    /* 初始化下载表格信息 */
	  $(function() {  	
		  // 下拉框选择控件，下拉框的内容是动态查询数据库信息
	  		$('#numClientProvinceId').combobox({ 
	          editable:false, //不可编辑状态
	          cache: false,
	          panelHeight: '300px',//自动高度适合
	          valueField:'id', //值字段
	          textField:'vacProvinceName', //显示的字段
	          url:'${ctx}/sms/province/findAllProvince',
	          value:'${tbClients.numClientProvinceId}',
			  onHidePanel: function(){
			      $("#numClientCityId").combobox("setValue",'');
			      var clientPid = $("#numClientProvinceId").combobox('getValue');
			      if(clientPid == ''){
			    	  clientPid = 0
			      }
			      $.ajax({
			        type: "GET",
			        url:'${ctx}/sms/city/findCityByProvId/'+clientPid,
			        cache: false,
			        dataType : "json",
			        success: function(data){
			        	$("#numClientCityId").combobox("loadData",data);
			         }
			       }); 	
			  }
	  		}); 
			var clientCId = $('#numClientCityId').val();
			var clientProId = $('#numClientProvinceId').val();
			if(clientCId != ''){
				  $('#numClientCityId').combobox({ 
				      editable:false, //不可编辑状态
				      cache: false,
				      panelHeight: 'auto',//自动高度适合
				      valueField:'id', //值字段
				      textField:'vacCityName',//显示的字段
					  url:'${ctx}/sms/city/findCityByProvId/${tbClients.numClientProvinceId}',
					  value:'${tbClients.numClientCityId}'	
			     });
			}else{
				 if(clientProId != ''){
				      $.ajax({
					        type: "GET",
					        url:'${ctx}/sms/city/findCityByProvId/'+clientProId,
					        cache: false,
					        dataType : "json",
					        success: function(data){
					        	$("#numClientCityId").combobox("loadData",data);
					         }
					       }); 	
				  }
				  $('#numClientCityId').combobox({ 
				      editable:false, //不可编辑状态
				      cache: false,
				      panelHeight: 'auto',//自动高度适合
				      valueField:'id', //值字段
				      textField:'vacCityName'//显示的字段
			     });
			}
		  
		  
	  		$('#numLedgerIncidentPorvinceId').combobox({ 
		          editable:false, //不可编辑状态
		          cache: false,
		          panelHeight: '300px',//自动高度适合
		          valueField:'id', //值字段
		          textField:'vacProvinceName', //显示的字段
		          url:'${ctx}/sms/province/findAllProvince',
		          value:'${tbLedger.numLedgerIncidentPorvinceId}',
		          onChange: function(pid){
				      $("#numLedgerIncidentCityId").combobox("setValue",'');
				      var ledgerPid = $("#numLedgerIncidentPorvinceId").combobox('getValue');
				      if(ledgerPid == ''){
				    	  ledgerPid = 0;
				      }
				      $.ajax({
				        type: "GET",
				        url:'${ctx}/sms/city/findCityByProvId/'+ledgerPid,
				        cache: false,
				        dataType : "json",
				        success: function(data){
				        	$("#numLedgerIncidentCityId").combobox("loadData",data);
				         }
				       }); 	
				  }
		  		}); 
				var ledgerCId = $('#numLedgerIncidentCityId').val();
				var ledgerProId = $('#numLedgerIncidentPorvinceId').val();
				if(ledgerCId != ''){
					  $('#numLedgerIncidentCityId').combobox({ 
							required:false,
							editable:false,//不可编辑，只能选择
							cache: false,
							panelHeight: 'auto',//自动高度适合
							valueField:'id', //值字段
							textField:'vacCityName', //显示的字段
							url:'${ctx}/sms/city/findCityByProvId/${tbLedger.numLedgerIncidentPorvinceId}',
							value:'${tbLedger.numLedgerIncidentCityId}'		  
				     });
				}else{
					 if(ledgerProId != ''){
					      $.ajax({
						        type: "GET",
						        url:'${ctx}/sms/city/findCityByProvId/'+ledgerProId,
						        cache: false,
						        dataType : "json",
						        success: function(data){
						        	$("#numLedgerIncidentCityId").combobox("loadData",data);
						         }
						       }); 	
					  }
					  $('#numLedgerIncidentCityId').combobox({ 
							required:false,
							editable:false,//不可编辑，只能选择
							cache: false,
							panelHeight: 'auto',//自动高度适合
							valueField:'id', //值字段
							textField:'vacCityName'//显示的字段  
				     });
				}
			  var vacLedgerBusinessType = '${tbLedger.vacLedgerBusinessType}' ;
			  if(vacLedgerBusinessType != ""){
			      $.ajax({
				        type: "POST",
				        url:'${ctx}/sys/specialty/getById/${tbLedger.vacLedgerBusinessType}',
				        cache: false,
				        dataType : "json",
				        success: function(data){
				 			  $('#vacLedgerBusinessType').combotree('setValue', 
				 					{
				 						id: vacLedgerBusinessType,
				 						text: data.vacName
				 					}
				 			  );
				         }
				       }); 
			  }
			  $('#vacLedgerBusinessType').combotree({    
				    url: '${ctx}/sys/specialty/findAllBusinessType',    
				    editable:false,
				    panelHeight:300
				}); 
			  $("#vacLedgerBusinessType").combotree({
				  editable:false,
				  onBeforeSelect:
				  function(node){
					  var  leaf = $(this).tree('isLeaf',node.target);
					  if(!leaf){
						  //如果不是叶子,清除选中
						  $(this).treegrid("unselect");
					  }
				  }});
			  
			  var numSpecialtyId = '${tbLedger.numSpecialtyId}' ;
			  if(numSpecialtyId != ""){
			      $.ajax({
				        type: "POST",
				        url:'${ctx}/sys/specialty/getById/${tbLedger.numSpecialtyId}',
				        cache: false,
				        dataType : "json",
				        success: function(data){
				 			  $('#numSpecialtyId').combotree('setValue', 
				 					{
				 						id: numSpecialtyId,
				 						text: data.vacName
				 					}
				 			  );
				         }
				       }); 
			  }
			  $('#numSpecialtyId').combotree({    
				    url: '${ctx}/sys/specialty/findAllLawType',    
				    editable:false,
				    panelHeight:300
				}); 
			  $("#numSpecialtyId").combotree({					    
				    editable:false,
				    onBeforeSelect:
				  function(node){
					  var  leaf = $(this).tree('isLeaf',node.target);
					  if(!leaf){
						  //如果不是叶子,清除选中
						  $(this).treegrid("unselect");
					  }
				  }});
			  
			  var numClientSource = $("#numClientSource").val();
			  //客户来源
			  if(numClientSource != ""){
				  $("input[name='numClientSource']").each(function(){  
					    $(this).attr("disabled",true);  
					});
			  }
	  });

		function globalAlert(name, msg, level) {
			$.messager.alert(name, msg, level);
		}
  
	</script>
	
	

<script type="text/javascript">

$(function(){
	se=window.setInterval(second,1000);
});
	
</script>
			<form id="tbCallLedgerForm" method="post"   action=""   modelAttribute="tbClients,tbLedger"   data-options="novalidate:true">
			<div id="tt" class="easyui-tabs" >
			<div title="台帐详情" style="padding:10px">
				<div>
					<span class="div_margin_left span_title" >来电信息    </span>  
					<span  style="font-size: 12px;color: gray;margin-left: 8px;">通话时长</span>
					<input id="showtime" name="vacLedgerTalkDur" style="width: 100px;" value="${tbLedger.vacLedgerTalkDur }" >
					<span id="message" style="color:#ff0000;width:200px;" id="message" ></span>
					<br/>
					<span style="color: red" id="e"> ${error }</span>
				</div>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="display: none;" >
						<span class="div_margin_left">台帐id</span>
						<input name="tbLedger.id"  value="${tbLedger.id }"  >
						<span class="div_margin_left">客户id</span>
						<input name="tbClients.id"  value="${tbClients.id }" >
						<input id ="numClientSource"  value="${tbClients.numClientSource }">
				</div>
				<div>
					<span class="div_margin_left">   流 水 号 </span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerSerial"  value="${tbLedger.vacLedgerSerial }"  readonly="readonly"  >
					<span class="div_margin_left"> 来电号码</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerNumber"  id="vacLedgerNumber" value="${tbLedger.vacLedgerNumber }"  readonly="readonly" />
					<span class="div_margin_left"> 来电归属</span>
					<input type="hidden"  name="numLedgerProvinceId" value="${tbLedger.numLedgerProvinceId}">
					<input type="hidden"  name="numLedgerCityId" value="${tbLedger.numLedgerCityId}">
					<input type="hidden"  name="vacLedgerProvinceName" value="${tbLedger.vacLedgerProvinceName}">
					<input type="hidden"  name="vacLedgerCityName" value="${tbLedger.vacLedgerCityName}">
					<input class="easyui-textbox textbox_input"    value="${tbLedger.vacLedgerProvinceName} -  ${tbLedger.vacLedgerCityName} "  readonly="readonly" >
				</div>
				<br/>
				<div>
					<span class="div_margin_left">来电队列</span>
					<input  class="easyui-textbox textbox_input"  name="numLedgerCallQueue"   value="${tbLedger.numLedgerCallQueue }" readonly="readonly" >
					<span class="div_margin_left"> 话务员工号</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerAgentCode"   value="${tbLedger.vacLedgerAgentCode }"  readonly="readonly" >
					<span class="div_margin_left"> 话务员姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerAgentName"  value="${tbLedger.vacLedgerAgentName }"  readonly="readonly" >
				</div>
				<br/>
				<p/>
				<span class="div_margin_left span_title" >客户信息</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div >
					<span class="div_margin_left  div_color_red" >* </span><span >客户姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacClientName"  value="${tbClients.vacClientName}"  required="required" validType="length[0,20]"   >		
					<span class="div_margin_left  div_color_red" >* </span><span >性别</span>
					<c:forEach items="${genderList}" var="m" >       
					<span  class="redio_span" >               
			            <input  class="redio_input"  type="radio" id="numClientGender${m.value }" name="numClientGender" value="${m.value }" <c:if test="${tbClients.numClientGender == m.value}">checked="checked"</c:if>  >${m.label }       
			        </span>
			        </c:forEach>
			        <span class="div_margin_left  " >备注</span>
			        <input class="easyui-textbox textbox_input"  name="vacClientMemo"  value="${tbClients.vacClientMemo}"  disabled="disabled"  >	 
				</div>
				<br/>
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >客户来源</span>
			        <c:forEach items="${sourceList}" var="m" >       
					<span  class="redio_span" >               
			            <input  class="redio_input"  type="radio" id="numClientSource${m.value }" name="numClientSource" value="${m.value }" <c:if test="${tbClients.numClientSource == m.value}">checked="checked"</c:if>  >${m.label }       
			        </span>
			        </c:forEach> 
				</div>
				<br/>
				<div >			        
					<span class="div_margin_left  div_color_red" >* </span><span >咨询人类别</span>
					<c:forEach items="${typeList}" var="m" >
					<span  class="redio_span" >               
			            <input  class="redio_input" type="radio" id="numClientType${m.value }" name="numClientType" value="${m.value }" <c:if test="${tbClients.numClientType == m.value}">checked="checked"</c:if>  >
			            ${m.label }    
			            </span>   
			        </c:forEach>
				</div>
				<br/>
				<div>
					<span class="div_margin_left " >法律类型</span>
					<input id="numSpecialtyId"  name="numSpecialtyId" value="${tbLedger.numSpecialtyId }" style="width:270px;height:26px;">  
					<span class="div_margin_left  div_color_red" >* </span><span >业务类型</span>
					<input id="vacLedgerBusinessType"  name="vacLedgerBusinessType" value="${tbLedger.vacLedgerBusinessType }" style="width:270px;height:26px;" required="required">  
				</div>
				<br/>
				<div>
					<span class="div_margin_left " >常住地址</span>
					<input class="easyui-combobox combobox_input"  id="numClientProvinceId" name="numClientProvinceId"   value= "${tbClients.numClientProvinceId }"  >
					<input class="easyui-combobox combobox_input" id="numClientCityId" name="numClientCityId"   value= "${tbClients.numClientCityId }"  >
					<input class="easyui-textbox textbox_input"  id="vacClientAddress"  name="vacClientAddress"   value="${tbClients.vacClientAddress}"   style="width:300px;height:26px;"  validType="length[0,50]"   >
				</div> 
				<br/>
				<div>
					<span class="div_margin_left " >事发地址</span>
					<input class="easyui-combobox combobox_input"  id="numLedgerIncidentPorvinceId" name="numLedgerIncidentPorvinceId"   value= "${tbLedger.numLedgerIncidentPorvinceId }"  />
					<input class="easyui-combobox combobox_input" id="numLedgerIncidentCityId" name="numLedgerIncidentCityId"   value= "${tbLedger.numLedgerIncidentCityId }" >
					<input class="easyui-textbox textbox_input"  name="vacLedgerIncidentAddress"  value="${tbLedger.vacLedgerIncidentAddress }"  style="width:300px;height:26px;"   validType="length[0,50]"   >
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >咨询内容</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="vertical-align: top; display: inline-flex;">
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >客户自述</span>
					<input class="easyui-textbox " name="vacLedgerClientAccount" data-options="multiline:true" value="${tbLedger.vacLedgerClientAccount }" style="width:300px;height:100px"  required="required" validType="length[0,2000]"   >
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >处理意见</span>	
					<input class="easyui-textbox " name="vacLedgerLawyerSuggestion"  data-options="multiline:true" value="${tbLedger.vacLedgerLawyerSuggestion }"  style="width:300px;height:100px" required="required" validType="length[0,2000]"  >
				</div>	
				<br/>	
				<br/>
				<br/>
				<span class="div_margin_left span_title" >处理情况</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>		
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >处理情况</span>
					<c:forEach items="${handleList}" var="m" >  
					<span  class="redio_span" >                        
			            <input class="redio_input"  type="radio" id="numLedgerHandle${m.value }" name="numLedgerHandle" value="${m.value }" <c:if test="${tbLedger.numLedgerHandle == m.value}">checked="checked"</c:if>  />${m.label }       
			       </span >
			        </c:forEach> 
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >生成工单必填</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div >
					<span class="div_margin_left  " ><span >证件类型（生成工单必填）</span>
					<c:forEach items="${papersTypeList}" var="m" >      
						<span  class="redio_span" >                  
			            <input  class="redio_input"   type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbClients.zjType == m.value}">checked="checked"</c:if>  />${m.label }       
			     </span>
			        </c:forEach> 
			     </div>
			     <br/>
				<div>
					<span class="div_margin_left" ><span >证件号码（生成工单必填）</span>
					<input class="easyui-textbox textbox_input"   id="zjCode"  name="zjCode"  value="${tbClients.zjCode}"  validType="length[0,20]"  >
				</div>
				<br/>
				<br/>
				<div class="div_margin_left"  >
					<permission:hasPermission action="tb/call/saveCallLedger">
					<a href="javascript:saveCallLedger(<%=CommonField.LEDGER_STATUS_SUCCESS %>);" id="saveCallLedger"  iconCls="icon-save"  class="easyui-linkbutton a_button">保存台帐</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</permission:hasPermission>
					<permission:hasPermission action="tb/call/call-order">
					<a href="javascript:saveCallOrder(<%=CommonField.LEDGER_STATUS_FILL %>);" id="saveCallOrder"  iconCls="icon-save" class="easyui-linkbutton a_button">生成工单</a>&nbsp;&nbsp;&nbsp;&nbsp;
					</permission:hasPermission>
				</div>
				<br/>
				<br/>
			</div>
		</div>
	</form>
	
				