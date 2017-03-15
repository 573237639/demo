<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<script type="text/javascript">
	//点击生成工单
	 function saveOrder(){
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
			//工单类型
			var zjFlag = false;
			var zjRadio = $("input[name='vacOrderType']");
			for(var i = 0;i < zjRadio.length;i++){
				   if(zjRadio[i].checked){
					   zjFlag = true;
					   break;
				   }
				}
			if(!zjFlag){
				$.messager.alert("消息提醒","请选择工单类型!","warning");
				return;
			}
			//任务类型
			var handleFlag = false;
			var handleRadio = $("input[name='vacOrderTaskType']");
			for(var i = 0;i < handleRadio.length;i++){
			   if(handleRadio[i].checked){
				   handleFlag = true;
				   break;
			   }
			}
			if(!handleFlag){
				$.messager.alert("消息提醒","请选择任务类型!","warning");
				return;
			}
			var validate = $("#orderadd-form").form("validate");
			if(!validate){
// 				　 $.messager.alert("消息提醒","请检查你输入的数据!","warning");
				　 return;
			} else{
				 $("#orderadd-save").linkbutton('disable');
					$.messager.progress();	// 显示进度条
					$('#orderadd-form').form('submit', {    
						url:"${ctx}/tb/ledger/orderaddSave", // 
					    success:function(data){
					    	$("#orderadd-save").linkbutton('enable');
					    	var dataObj=eval("("+data+")");
					    	$.messager.progress("close");	// 关闭进度条
				        	if(dataObj.code == 0){
					        	$.messager.alert('提示','数据保存成功!');
					        	$("#orderadd-datagrid").datagrid('load');
					        	 $('#orderadd-detail').panel('refresh');
					        }else{
					        	$.messager.alert('提示','工单保存失败!原因：'+dataObj.message);
					        } 
					    }  
					});
			}
	 }
	
		var orderDateList = ${orderDateList};
		function getOrderDate(){
			var orderDate= $("input[name='vacOrderType']:checked").val();
	  		for(var data in orderDateList){
	  			if(orderDate == orderDateList[data].value){
	  				$("#dateOrderTerm").textbox('setValue', orderDateList[data].label);
	  				 break;
	  			}
	  		}
		}
	
	   /* 初始化下载表格信息 */
	  $(function() {  
		  // 下拉框选择控件，下拉框的内容是动态查询数据库信息     常住地址
	  		$('#numClientProvinceId').combobox({ 
			      editable:false, //不可编辑状态
			      cache: false,
		          panelHeight: '300px',//自动高度适合
		          valueField:'id', //值字段
		          textField:'vacProvinceName', //显示的字段
		          url:'${ctx}/sms/province/findAllProvince',
		          value:'${tbClients.numClientProvinceId}',
		          onChange: function(pid){
				      $("#numClientCityId").combobox("setValue",'');
				      var clientPid = $("#numClientProvinceId").combobox('getValue');
				      if(clientPid == ''){
				    	  clientPid = 0;
				      }
				      $.ajax({
				        type: "GET",
				        url:'${ctx}/sms/city/findCityByProvId/'+$("#numClientProvinceId").combobox('getValue'),
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
			  
			  //事发地址
		  		$('#numOrderIncidentProvinceId').combobox({ 
				      editable:false, //不可编辑状态
				      cache: false,
			          panelHeight: '300px',//自动高度适合
			          valueField:'id', //值字段
			          textField:'vacProvinceName', //显示的字段
			          url:'${ctx}/sms/province/findAllProvince',
			          value:'${tbOrders.numOrderIncidentProvinceId}',
			          onChange: function(pid){
					      $("#numOrderIncidentCityId").combobox("setValue",'');
					      var orderIncPid = $("#numOrderIncidentProvinceId").combobox('getValue');
					      if(orderIncPid == ''){
					    	  orderIncPid = 0;
					      }
					      $.ajax({
					        type: "GET",
					        url:'${ctx}/sms/city/findCityByProvId/'+$("#numOrderIncidentProvinceId").combobox('getValue'),
					        cache: false,
					        dataType : "json",
					        success: function(data){
					        	$("#numOrderIncidentCityId").combobox("loadData",data);
					         }
					       }); 	
					  }
			  		}); 
		  		var orderIncCId = $('#numOrderIncidentCityId').val();
				var orderIncProId =  $('#numOrderIncidentProvinceId').val();
				if (orderIncCId != '') {
					$('#numOrderIncidentCityId')
							.combobox(
									{
										editable : false, //不可编辑状态
										cache : false,
										panelHeight : 'auto',//自动高度适合
										valueField : 'id', //值字段
										textField : 'vacCityName', //显示的字段
										url : '${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderIncidentProvinceId}',
										value : '${tbOrders.numOrderIncidentCityId}'
									});
				} else {
					if(orderIncProId != ''){
						$.ajax({
							type : "GET",
							url : '${ctx}/sms/city/findCityByProvId/'
									+ orderIncProId,
							cache : false,
							dataType : "json",
							success : function(data) {
								$("#numOrderIncidentCityId").combobox(
										"loadData", data);
							}
						});
					}
					$('#numOrderIncidentCityId').combobox({
						editable : false, //不可编辑状态
						cache : false,
						panelHeight : 'auto',//自动高度适合
						valueField : 'id', //值字段
						textField : 'vacCityName'//显示的字段
					});
				}
				  //常住地址tbOrders.numOrderContactProvinceId tbOrders.numOrderContactCityId
			  		$('#numOrderContactProvinceId').combobox({ 
					      editable:false, //不可编辑状态
					      cache: false,
				          panelHeight: '300px',//自动高度适合
				          valueField:'id', //值字段
				          textField:'vacProvinceName', //显示的字段
				          url:'${ctx}/sms/province/findAllProvince',
				          value:'${tbOrders.numOrderContactProvinceId}',
				          onChange: function(pid){
						      $("#numOrderContactCityId").combobox("setValue",'');
						      var orderContactPid = $("#numOrderContactProvinceId").combobox('getValue');
						      if(orderContactPid == ''){
						    	  orderContactPid = 0;
						      }
						      $.ajax({
						        type: "GET",
						        url:'${ctx}/sms/city/findCityByProvId/'+$("#numOrderContactProvinceId").combobox('getValue'),
						        cache: false,
						        dataType : "json",
						        success: function(data){
						        	$("#numOrderContactCityId").combobox("loadData",data);
						         }
						       }); 	
						  }
				  		});
			  		var orderContactCId = $('#numOrderContactCityId').val();
					var orderConProId = $('#numOrderContactProvinceId').val();
					if (orderContactCId != '') {
						$('#numOrderContactCityId')
								.combobox(
										{
											editable : false, //不可编辑状态
											cache : false,
											panelHeight : 'auto',//自动高度适合
											valueField : 'id', //值字段
											textField : 'vacCityName', //显示的字段
											url : '${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderContactProvinceId}',
											value : '${tbOrders.numOrderContactCityId}'
										});
					} else {
						if(orderConProId != ''){
							$.ajax({
								type : "GET",
								url : '${ctx}/sms/city/findCityByProvId/'
										+ orderConProId,
								cache : false,
								dataType : "json",
								success : function(data) {
									$("#numOrderContactCityId").combobox(
											"loadData", data);
								}
							});
						}
						$('#numOrderContactCityId').combobox({
							editable : false, //不可编辑状态
							cache : false,
							panelHeight : 'auto',//自动高度适合
							valueField : 'id', //值字段
							textField : 'vacCityName'//显示的字段
						});
					}
					  //接收区域 tbOrders.numOrderReceiveProvinceId  tbOrders.numOrderReceiveCityId
			  		$('#numOrderReceiveProvinceId').combobox({ 
					      editable:false, //不可编辑状态
					      cache: false,
				          panelHeight: '300px',//自动高度适合
				          valueField:'id', //值字段
				          textField:'vacProvinceName', //显示的字段
				          url:'${ctx}/sms/province/findAllProvince',
				          value:'${tbOrders.numOrderReceiveProvinceId}',
				          onChange: function(pid){
						      $("#numOrderReceiveCityId").combobox("setValue",'');
						      var orderReceivePid = $("#numOrderReceiveProvinceId").combobox('getValue');
						      if(orderReceivePid == ''){
						    	  orderReceivePid = 0;
						      }
						      $.ajax({
						        type: "GET",
						        url:'${ctx}/sms/city/findCityByProvId/'+$("#numOrderReceiveProvinceId").combobox('getValue'),
						        cache: false,
						        dataType : "json",
						        success: function(data){
						        	$("#numOrderReceiveCityId").combobox("loadData",data);
						         }
						       }); 	
						  }
				  		}); 
			  		var orderReceiveCId = $('#numOrderReceiveCityId').val();
			  		var orderRecProId = $('#numOrderReceiveProvinceId').val();
					if (orderReceiveCId != '') {
						$('#numOrderReceiveCityId')
								.combobox(
										{
											editable : false, //不可编辑状态
											cache : false,
											panelHeight : 'auto',//自动高度适合
											valueField : 'id', //值字段
											textField : 'vacCityName', //显示的字段
											url : '${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderReceiveProvinceId}',
											value : '${tbOrders.numOrderReceiveCityId}'
										});
					} else {
						if(orderRecProId != ''){
							$.ajax({
								type : "GET",
								url : '${ctx}/sms/city/findCityByProvId/'
										+ orderRecProId,
								cache : false,
								dataType : "json",
								success : function(data) {
									$("#numOrderReceiveCityId").combobox(
											"loadData", data);
								}
							});
						}
						$('#numOrderReceiveCityId').combobox({
							editable : false, //不可编辑状态
							cache : false,
							panelHeight : 'auto',//自动高度适合
							valueField : 'id', //值字段
							textField : 'vacCityName'//显示的字段
						});
					}
					
					
				var vacOrderBusinessType = '${tbOrders.vacOrderBusinessType}' ;
				  if(vacOrderBusinessType != ''){
			      $.ajax({
				        type: "POST",
				        url:'${ctx}/sys/specialty/getById/'+vacOrderBusinessType,
				        cache: false,
				        dataType : "json",
				        success: function(data){
				 			  $('#vacOrderBusinessType').combotree('setValue', 
				 					{
				 						id: vacOrderBusinessType,
				 						text: data.vacName
				 					}
				 			  );
				         }
				       }); 
			  }
				  
			  $('#vacOrderBusinessType').combotree({    
				    url: '${ctx}/sys/specialty/findAllBusinessType',    
				    editable:false,
				    panelHeight:300
				}); 
				  
			  $("#vacOrderBusinessType").combotree({
				    editable:false,
				  	onBeforeSelect:function(node){
					  var  leaf = $(this).tree('isLeaf',node.target);
					  if(!leaf){
						  //如果不是叶子,清除选中
						  $(this).treegrid("unselect");
					  }
				  }});
			  
	  });
	  
	    
</script>
				<br/>
				<form id="orderadd-form" method="post" action="" modelAttribute="tbClients,tbOrders">
				<p/>
				<div >
					<span class="div_margin_left span_title" >工单信息    </span>  
				</div>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="display: none;" >
						<span class="div_margin_left">工单id</span>
						<input name="tbOrders.id"  value="${tbOrders.id }"  >
						<span class="div_margin_left">客户id</span>
						<input name="tbClients.id"  value="${tbClients.id }" >
						<input name="numClientId" value="${tbClients.id }" >
						<input name="id" value="${tbOrders.id }" >
				</div>
				<div >
					<span class="div_margin_left">工单流水号</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderSerial"  value="${tbOrders.vacOrderSerial }" readonly="readonly">
					<span class="div_margin_left">来电号码</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderNumber"  id="vacOrderNumber" value="${tbOrders.vacOrderNumber }"    readonly="readonly"/>
				</div>
				<br/>
				<div>
					<span class="div_margin_left">制单员工号</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderAgentCode"   value="${tbOrders.vacOrderAgentCode }"   readonly="readonly">
					<span class="div_margin_left">制单员姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderAgentName"  value="${tbOrders.vacOrderAgentName }"   readonly="readonly">
				</div>
				<br/>
				<c:if test="${tbOrders.fileName != ''}">
				<div>
					<span class="div_margin_left">台帐录音</span>
					<object id="czplayer" width="300" height="63" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" 
							codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=7.0" 
							align="top" border="0" type="application/x-oleobject">
					     <param name="URL" value="${tbOrders.fileName}">
					</object>
				</div>
				<br/>
				</c:if>
				<p/>
				<span class="div_margin_left span_title" >客户信息</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >客户姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacClientName"  value="${tbClients.vacClientName}"  required="required"  validType="length[0,20]" >
					<span class="div_margin_left  div_color_red" >* </span><span >性别</span>
					 <c:forEach items="${genderList}" var="m" >     
					 <span  class="redio_span" >                    
			            <input class="redio_input"  type="radio" id="numClientGender${m.value }" name="numClientGender" value="${m.value }" <c:if test="${tbClients.numClientGender == m.value}">checked="checked"</c:if>   />${m.label }       
			       	</span>
			        </c:forEach> 
				</div>
				<br/>
				<div >	
					<span class="div_margin_left  div_color_red" >* </span><span >咨询人类别</span>
					<c:forEach items="${typeList}" var="m" >                        
			            <span  class="redio_span" >                    
			            <input class="redio_input"  type="radio" id="numClientType${m.value }" name="numClientType" value="${m.value }" <c:if test="${tbClients.numClientType == m.value}">checked="checked"</c:if>   />${m.label }       
			               	</span>
			        </c:forEach> 
				</div>
				<br/>
				<div >		
					  <span class="div_margin_left " >证件类型</span>
					  <c:forEach items="${papersTypeList}" var="m" >                        
			             <span  class="redio_span" >                    
			            <input class="redio_input"  type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbClients.zjType == m.value}">checked="checked"</c:if>  />${m.label }       
			        	</span>
			        </c:forEach> 
					<span class="div_margin_left " >证件号码</span>
					<input class="easyui-textbox textbox_input"  id="zjCode"  name="zjCode"   value="${tbClients.zjCode}"    validType="length[0,20]"  >
				</div>
				<br/>
				<div >	
					<span class="div_margin_left  div_color_red" >* </span><span >业务类型</span>
					<input id="vacOrderBusinessType"  name="vacOrderBusinessType"  value="${tbOrders.vacOrderBusinessType }" style="width:270px;height:26px;" required="required">
				</div>
				<br/>
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >工单标题</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderTitle"  value="${tbOrders.vacOrderTitle }"  required="required" validType="length[0,20]" />
					<span class="div_margin_left  div_color_red" >* </span><span >工单类型</span>
					<c:forEach items="${orderTypeList}" var="m" >                        
			            <span  class="redio_span" >                    
			            <input class="redio_input"  type="radio" id="orderType${m.value }" name="vacOrderType" value="${m.value }" <c:if test="${tbOrders.vacOrderType == m.value}">checked="checked"</c:if>  onChange="getOrderDate()" />${m.label }       
			       		</span>
			        </c:forEach>
					<span class="div_margin_left  div_color_red" >* </span><span >工单期限</span>
					<input class="easyui-textbox textbox_input"  name="dateOrderTerm" id="dateOrderTerm" value="${tbOrders.dateOrderTerm }"   readonly="readonly" />	
				</div>
				<br/>
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >任务类型</span>			
					<c:forEach items="${taskTypeList}" var="m" >                        
            		<span  class="redio_span" >                    
			            <input class="redio_input"  type="radio" id="taskType${m.value }" name="vacOrderTaskType" value="${m.value }" <c:if test="${tbOrders.vacOrderTaskType == m.value}">checked="checked"</c:if>  />${m.label }       
                  </span>
                    </c:forEach>
				</div>
				<br/>
				<div>
						<span class="div_margin_left " >联系地址</span>
						<input class="easyui-combobox combobox_input"  name="numClientProvinceId" id="numClientProvinceId" value="${tbClients.numClientProvinceId }">
						<input class="easyui-combobox combobox_input"  name="numClientCityId" id="numClientCityId"  value= "${tbClients.numClientCityId }">
						<input class="easyui-textbox "   id="vacClientAddress"  name="vacClientAddress"   value="${tbClients.vacClientAddress}"  style="width:300px;height:26px;"  validType="length[0,50]" >	
				</div>
				<br/>	
				<div>
						<span class="div_margin_left " >常住地址</span>
						<input class="easyui-combobox combobox_input"  name="numOrderContactProvinceId" id="numOrderContactProvinceId" value="${tbOrders.numOrderContactProvinceId }">
						<input class="easyui-combobox combobox_input"  name="numOrderContactCityId" id="numOrderContactCityId"  value= "${tbOrders.numOrderContactCityId }">
						<input class="easyui-textbox "  id="vacOrderContactAddress"  name="vacOrderContactAddress"   value="${tbOrders.vacOrderContactAddress}"  style="width:300px;height:26px;"  validType="length[0,50]" >	
				</div>	
				<br/>
				<div>
					    <span class="div_margin_left " >事发地址</span>
						<input class="easyui-combobox combobox_input"  id="numOrderIncidentProvinceId" name="numOrderIncidentProvinceId"  value="${tbOrders.numOrderIncidentProvinceId }">
						<input class="easyui-combobox combobox_input"  id="numOrderIncidentCityId"  name="numOrderIncidentCityId"   value= "${tbOrders.numOrderIncidentCityId }">
						<input class="easyui-textbox "  id="vacOrderIncidentAddress"  name="vacOrderIncidentAddress"   value="${tbOrders.vacOrderIncidentAddress}"  style="width:300px;height:26px;"  validType="length[0,50]" >	
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >咨询内容</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="vertical-align: top; display: inline-flex;">
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >事项说明</span>
					<input class="easyui-textbox " name="vacOrderAccount" data-options="multiline:true" value="${tbOrders.vacOrderAccount }" style="width:300px;height:100px" required="required" validType="length[0,2000]" >
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >处理意见</span>	
					<input class="easyui-textbox " name="vacOrderSuggestion"  data-options="multiline:true" value="${tbOrders.vacOrderSuggestion }" style="width:300px;height:100px" required="required" validType="length[0,2000]" >
				</div>	
				<br/>	
				<br/>
				<br/>
				<span class="div_margin_left span_title" >接收区域</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>				
				<div>
					<span class="div_margin_left " >接收区域</span>
					<input class="easyui-combobox combobox_input"  name="numOrderReceiveProvinceId" id="numOrderReceiveProvinceId"  value="${tbOrders.numOrderReceiveProvinceId }">
					<input class="easyui-combobox combobox_input"  name="numOrderReceiveCityId" id="numOrderReceiveCityId" value="${tbOrders.numOrderReceiveCityId }">
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >补充</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>						
				<div>							
					<span class="div_margin_left">客户其他联系电话</span>
					<input class="easyui-textbox textbox_input"   name="vacOrderOtherContacts"  value="${tbOrders.vacOrderOtherContacts }" validType="length[0,20]" />
					<span class="div_margin_left  " >服务律师姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderLawyerName"  value="${tbOrders.vacOrderLawyerName }"  validType="length[0,30]"  />	
				</div>
				<br/>
				<div>	
					<span class="div_margin_left  textbox_title_2" >产生原因</span>
					<input class="easyui-textbox "  name="vacOrderCreateReason"  value="${tbOrders.vacOrderCreateReason }"  
					data-options="multiline:true"  style="width:300px;height:100px"  validType="length[0,50]"  >		
				</div>
				<br/>
				<br/>
				<div class="div_margin_left"  >
				<permission:hasPermission action="tb/ledger/orderaddSave">
					<a href="javascript:saveOrder();" id="orderadd-save"  data-options="iconCls:'icon-save',disabled:false"     class="easyui-linkbutton a_button">保 存 工 单</a>&nbsp;&nbsp;&nbsp;&nbsp;
				</permission:hasPermission>
				</div>	
							<br/>
				<br/>		
					</form>
				