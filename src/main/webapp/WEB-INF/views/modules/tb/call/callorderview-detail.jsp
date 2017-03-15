<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	   /* 初始化下载表格信息 */
	  $(function() {  	
	  // 下拉框选择控件，下拉框的内容是动态查询数据库信息     常住地址
	  		$('#numClientProvinceId').combobox({ 
	          editable:false, //不可编辑状态
	          disabled:true,
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
			if(clientCId != ''){
		  $('#numClientCityId').combobox({ 
		      editable:false, //不可编辑状态
		      cache: false,
		      disabled:true,
		      panelHeight: 'auto',//自动高度适合
		      valueField:'id', //值字段
		      textField:'vacCityName',//显示的字段
			  url:'${ctx}/sms/city/findCityByProvId/${tbClients.numClientProvinceId}',
			  value:'${tbClients.numClientCityId}'	
	     });
		}
		  
		  //事发地址
	  		$('#numOrderIncidentProvinceId').combobox({ 
		          editable:false, //不可编辑状态
		          cache: false,
		          disabled:true,
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
			if(orderIncCId != ''){
			  $('#numOrderIncidentCityId').combobox({ 
					required:true,
					editable:false,//不可编辑，只能选择
					cache: false,
					disabled:true,
					panelHeight: 'auto',//自动高度适合
					valueField:'id', //值字段
					textField:'vacCityName', //显示的字段
					url:'${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderIncidentProvinceId}',
					value:'${tbOrders.numOrderIncidentCityId}'		  
		     });
			}
			  //事发地址tbOrders.numOrderContactProvinceId tbOrders.numOrderContactCityId
		  		$('#numOrderContactProvinceId').combobox({ 
			          editable:false, //不可编辑状态
			          cache: false,
			          disabled:true,
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
				if(orderContactCId != ''){
				  $('#numOrderContactCityId').combobox({ 
						required:true,
						editable:false,//不可编辑，只能选择
						cache: false,
						disabled:true,
						panelHeight: 'auto',//自动高度适合
						valueField:'id', //值字段
						textField:'vacCityName', //显示的字段
						url:'${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderContactProvinceId}',
						value:'${tbOrders.numOrderContactCityId}'		  
			     });
				}
				  //接收区域 tbOrders.numOrderReceiveProvinceId  tbOrders.numOrderReceiveCityId
		  		$('#numOrderReceiveProvinceId').combobox({ 
			          editable:false, //不可编辑状态
			          cache: false,
			          disabled:true,
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
				if(orderReceiveCId != ''){
				  $('#numOrderReceiveCityId').combobox({ 
						required:true,
						editable:false,//不可编辑，只能选择
						cache: false,
						disabled:true,
						panelHeight: 'auto',//自动高度适合
						valueField:'id', //值字段
						textField:'vacCityName', //显示的字段
						url:'${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderReceiveProvinceId}',
						value:'${tbOrders.numOrderReceiveCityId}'		  
			     });
				}
			  if('${tbOrders.vacOrderBusinessType}' != ""){
				  var t = '${tbOrders.vacOrderBusinessType}' ;
			      $.ajax({
				        type: "POST",
				        url:'${ctx}/sys/specialty/getById/${tbOrders.vacOrderBusinessType}',
				        cache: false,
				        dataType : "json",
				        success: function(data){
				 			  $('#vacOrderBusinessType').combotree('setValue', 
				 					{
				 						id: t,
				 						text: data.vacName
				 					}
				 			  );
				         }
				       }); 
			  }
			  $('#vacOrderBusinessType').combotree({    
				    url: '${ctx}/sys/specialty/findAllBusinessType',    
				    editable:false,
				    disabled:true,
				    panelHeight:300,
				    required: true
				}); 
	  });
</script>
				<div >
					<span style="align:left;font-size: 20px;">工单信息</span>
				</div>
				<form id="tbOrdersForm" method="post" action="" modelAttribute="tbClients,tbOrders" disabled>
				<div hidden="true">
								<span>工单id</span>
								<input type="hidden" name="tbOrders.id"  value="${tbOrders.id }" class="easyui-textbox" style="height:32px">
								<span>客户id</span>
								<input type="hidden" name="tbClients.id"  value="${tbClients.id }" class="easyui-textbox" style="height:32px">
				</div>
				<div>
					<span>工单流水号</span>
					<input name="vacOrderSerial"  value="${tbOrders.vacOrderSerial }" class="easyui-textbox" style="height:32px" disabled>
					<span>来电号码</span>
					<input name="vacOrderNumber"  value="${tbOrders.vacOrderNumber }" class="easyui-textbox"  style="height:32px;border:1px solid #ccc" disabled/>
					<span>制单员工号</span>
					<input name="vacOrderAgentCode"  class="easyui-textbox"  value="${tbOrders.vacOrderAgentCode }"  style="height:32px;border:1px solid #ccc" disabled>
					<span>制单员姓名</span>
					<input name="vacOrderAgentName" class="easyui-textbox"  value="${tbOrders.vacOrderAgentName }"  style="height:32px;border:1px solid #ccc" disabled>
					<span>台帐录音</span>
					</div>
				<div>
					<span style="color: red">* </span><span>客户姓名</span>
					<input  name="vacClientName" class="easyui-textbox"  value="${tbClients.vacClientName}" data-option="required:true"  style="height:32px;border:1px solid #ccc">
					<span style="color: red">* </span><span>性别</span>
					 <c:forEach items="${genderList}" var="m" >                        
			            <input type="radio" id="numClientGender${m.value }" name="numClientGender" value="${m.value }" <c:if test="${tbClients.numClientGender == m.value}">checked="checked"</c:if>  />${m.label }       
			        </c:forEach> 
					<span style="color: red">* </span><span>咨询人类别</span>
					<c:forEach items="${typeList}" var="m" >                        
			            <input type="radio" id="numClientType${m.value }" name="numClientType" value="${m.value }" <c:if test="${tbClients.numClientType == m.value}">checked="checked"</c:if>  />${m.label }       
			        </c:forEach>
				</div>
				<div>
					<span style="color: red">* </span><span>证件类型（生成工单必填）</span>
					<c:forEach items="${papersTypeList}" var="m" >                        
			            <input type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbClients.zjType == m.value}">checked="checked"</c:if>  />${m.label }       
			        </c:forEach> 
					<span style="color: red">* </span><span>证件号码</span>
					<input id="zjCode"  name="zjCode" class="easyui-textbox"   value="${tbClients.zjCode}"  style="width:30%;height:32px;border:1px solid #ccc">
					<span style="color: red">* </span><span>业务类型</span>
					<input id="vacOrderBusinessType" value="${tbOrders.vacOrderBusinessType }" style="width:300px;height:32px;">
					<span>其他联系电话</span>
					<input name="vacOrderOtherContacts"  value="${tbOrders.vacOrderOtherContacts }" class="easyui-textbox"  style="height:32px;border:1px solid #ccc"/>
				</div>
				<div>
					<span>工单标题</span>
					<input name="vacOrderTitle"  value="${tbOrders.vacOrderTitle }" class="easyui-textbox"  style="height:32px;border:1px solid #ccc"/>
					<span style="color: red">* </span><span>工单类型</span>
					 <c:forEach items="${orderTypeList}" var="m" >                        
			            <input type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbOrders.vacOrderType == m.value}">checked="checked"</c:if>  />${m.label }       
			        </c:forEach>
					<span style="color: red">* </span><span>工单期限</span>
					<input name="dateOrderTerm"  value="${tbOrders.dateOrderTerm }" class="easyui-textbox"  style="height:32px;border:1px solid #ccc" disabled/>			
										<span style="color: red">* </span><span>服务律师</span>
					<input name="vacOrderLawyerName"  value="${tbOrders.vacOrderLawyerName }" class="easyui-textbox"  style="height:32px;border:1px solid #ccc"/>	
										<span style="color: red">* </span><span>产生原因</span>
					<input name="vacOrderCreateReason"  value="${tbOrders.vacOrderCreateReason }" class="easyui-textbox"  style="height:32px;border:1px solid #ccc"/>		
				</div>
				
			<div>
								<span style="color: red">* </span><span>任务类型</span>
								 <c:forEach items="${taskTypeList}" var="m" >                        
			            		 <input type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbOrders.vacOrderTaskType == m.value}">checked="checked"</c:if>  />${m.label }       
			                     </c:forEach>			
			</div>	
							<div>
						<span style="color: red">* </span><span>联系地址</span>
						<input class="easyui-combobox" name="numOrderContactProvinceId" id="numOrderContactProvinceId" value="${tbOrders.numOrderContactProvinceId }" style="width:10%">
						<input class="easyui-combobox" name="numOrderContactCityId" id="numOrderContactCityId"  value= "${tbOrders.numOrderContactCityId }" style="width:10%">
						<input id="vacOrderContactAddress"  name="vacOrderContactAddress"  class="easyui-textbox"  value="${tbOrders.vacOrderContactAddress}"  style="width:300px;height:26px;" >
				
				</div>	
				
				<div>
						<span style="color: red">* </span><span>常住地址</span>
						<input class="easyui-combobox" name="numClientProvinceId" id="numClientProvinceId" value="${tbClients.numClientProvinceId }" style="width:10%">
						<input class="easyui-combobox" name="numClientCityId" id="numClientCityId"  value= "${tbClients.numClientProvinceId }" style="width:10%">
						<input id="vacClientAddress"  name="vacClientAddress"  class="easyui-textbox"  value="${tbClients.vacClientAddress}"  style="width:300px;height:26px;" >
				
						
				</div>

				<div>
					<span style="color: red">* </span><span>事发地址</span>
						<input class="easyui-combobox" id="numOrderIncidentProvinceId" name="numOrderIncidentProvinceId"  value="${tbOrders.numOrderIncidentProvinceId }" style="width:10%">
						<input class="easyui-combobox" id="numOrderIncidentCityId"  name="numOrderIncidentCityId"   value= "${tbOrders.numOrderIncidentCityId }" style="width:10%">
						<input id="vacOrderIncidentAddress"  name="vacOrderIncidentAddress"  class="easyui-textbox"  value="${tbOrders.vacOrderIncidentAddress}"  style="width:300px;height:26px;" >
				<br/>
					
				</div>
				<div>
					<span style="color: red">* </span><span>事项说明</span>
					<input class="easyui-textbox" name="vacOrderAccount" data-options="multiline:true" value="${tbOrders.vacOrderAccount }" style="width:300px;height:100px">
					<span style="color: red">* </span><span>处理意见</span>	
					<input class="easyui-textbox" name="vacOrderSuggestion"  data-options="multiline:true" value="${tbOrders.vacOrderSuggestion }" style="width:300px;height:100px">
				</div>		
				<div>
					<span style="color: red">* </span><span>接收区域</span>
					<input class="easyui-combobox" name="numOrderReceiveProvinceId" id="numOrderReceiveProvinceId"  value="${tbOrders.numOrderReceiveProvinceId }" style="width:10%" >
					<input class="easyui-combobox" name="numOrderReceiveCityId" id="numOrderReceiveCityId" value="${tbOrders.numOrderReceiveCityId }" style="width:10%">
				</div>
				<div >
					<span style="align:left" style="font-size: 20px;">审核信息</span>
				</div>
					</form>
				