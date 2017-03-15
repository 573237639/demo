<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<script type="text/javascript">
	
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
	  			  required:true,
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
						  required:true,
					      editable:false, //不可编辑状态
					      cache: false,
					      panelHeight: 'auto',//自动高度适合
					      valueField:'id', //值字段
					      textField:'vacCityName',//显示的字段
						  url:'${ctx}/sms/city/findCityByProvId/${tbClients.numClientProvinceId}',
						  value:'${tbClients.numClientCityId}'	
				     });
			}else{
				  $('#numClientCityId').combobox({ 
					  required:true,
				      editable:false, //不可编辑状态
				      cache: false,
				      panelHeight: 'auto',//自动高度适合
				      valueField:'id', //值字段
				      textField:'vacCityName'//显示的字段
			     });
			}
			  
			  //事发地址
		  		$('#numOrderIncidentProvinceId').combobox({ 
		  			  required:true,
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
				if(orderIncCId != ''){
				  $('#numOrderIncidentCityId').combobox({ 
					  required:true,
				      editable:false, //不可编辑状态
				      cache: false,
						panelHeight: 'auto',//自动高度适合
						valueField:'id', //值字段
						textField:'vacCityName', //显示的字段
						url:'${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderIncidentProvinceId}',
						value:'${tbOrders.numOrderIncidentCityId}'		  
			     });
				}else{
					  $('#numOrderIncidentCityId').combobox({ 
						  required:true,
					      editable:false, //不可编辑状态
					      cache: false,
					      panelHeight: 'auto',//自动高度适合
					      valueField:'id', //值字段
					      textField:'vacCityName'//显示的字段
				     });
				}
				  //事发地址tbOrders.numOrderContactProvinceId tbOrders.numOrderContactCityId
			  		$('#numOrderContactProvinceId').combobox({ 
			  			  required:true,
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
					if(orderContactCId != ''){
					  $('#numOrderContactCityId').combobox({ 
						  required:true,
					      editable:false, //不可编辑状态
					      cache: false,
							panelHeight: 'auto',//自动高度适合
							valueField:'id', //值字段
							textField:'vacCityName', //显示的字段
							url:'${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderContactProvinceId}',
							value:'${tbOrders.numOrderContactCityId}'		  
				     });
					}else{
						  $('#numOrderContactCityId').combobox({ 
							  required:true,
						      editable:false, //不可编辑状态
						      cache: false,
						      panelHeight: 'auto',//自动高度适合
						      valueField:'id', //值字段
						      textField:'vacCityName'//显示的字段
					     });
					}
					  //接收区域 tbOrders.numOrderReceiveProvinceId  tbOrders.numOrderReceiveCityId
			  		$('#numOrderReceiveProvinceId').combobox({ 
			  			  required:true,
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
					if(orderReceiveCId != ''){
					  $('#numOrderReceiveCityId').combobox({ 
						  required:true,
					      editable:false, //不可编辑状态
					      cache: false,
							panelHeight: 'auto',//自动高度适合
							valueField:'id', //值字段
							textField:'vacCityName', //显示的字段
							url:'${ctx}/sms/city/findCityByProvId/${tbOrders.numOrderReceiveProvinceId}',
							value:'${tbOrders.numOrderReceiveCityId}'		  
				     });
					}else{
						  $('#numOrderReceiveCityId').combobox({ 
							  required:true,
						      editable:false, //不可编辑状态
						      cache: false,
						      panelHeight: 'auto',//自动高度适合
						      valueField:'id', //值字段
						      textField:'vacCityName'//显示的字段
					     });
					}
					
					var vacReexamineStatus = '${tbOrders.numOrderStatus}';
					if(vacReexamineStatus==1){
						$("#reexamine_div").show();
					}else{
						$("#reexamine_div").hide();
					}
				var vacOrderBusinessType = '${tbOrders.vacOrderBusinessType}' ;
				  if(vacOrderBusinessType != ''){
			      $.ajax({
				        type: "POST",
				        url:'${ctx}/sys/specialty/getById/${tbOrders.vacOrderBusinessType}',
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
			  
	  });
	  
	    
</script>
				<br/>
				<p/>
				<div >
					<span class="div_margin_left span_title" >来电信息</span>
				</div>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<form id="orderadd-form" method="post" action="" modelAttribute="tbClients,tbOrders">
				<div >
						<input type="hidden" name="tbOrders.id"  value="${tbOrders.id }"  >
						<input type="hidden" name="tbClients.id"  value="${tbClients.id }" >
						<input type="hidden" name="numClientId" value="${tbClients.id }" >
				</div>
				<div >
					<span class="div_margin_left">工单流水号</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderSerial"  value="${tbOrders.vacOrderSerial }" disabled="disabled">
					<span class="div_margin_left">来电号码</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderNumber"  id="vacOrderNumber" value="${tbOrders.vacOrderNumber }" disabled="disabled"/>
				</div>
				<br/>
				<div>
					<span class="div_margin_left">制单员工号</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderAgentCode"   value="${tbOrders.vacOrderAgentCode }" disabled="disabled">
					<span class="div_margin_left">制单员姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacOrderAgentName"  value="${tbOrders.vacOrderAgentName }" disabled="disabled">
					</div>
					<c:if test="${tbOrders.fileName != ''}">
					<br/>
					<div>
					<span class="div_margin_left">台帐录音</span>
					<object id="czplayer" width="300" height="63" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" 
							codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=7.0" 
							align="top" border="0" type="application/x-oleobject">
					     <param name="URL" value="${tbOrders.fileName}">
					</object>
					</div>
					</c:if>
				<br/>
				<p/>
				<span class="div_margin_left span_title" >客户信息</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >客户姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacClientName"  value="${tbClients.vacClientName}" disabled="disabled" validType="length[0,20]" >
					<span class="div_margin_left  div_color_red" >* </span><span >性别</span>
					 <c:forEach items="${genderList}" var="m" >     
					 <span  class="redio_span" disabled="disabled">                    
			            <input  class="redio_input"  type="radio" id="numClientGender${m.value }" name="numClientGender" value="${m.value }" disabled="disabled" <c:if test="${tbClients.numClientGender == m.value}">checked="checked"</c:if>   />${m.label }       
			       	</span>
			        </c:forEach> 
				</div>
				<br/>
				<div >	
					<span class="div_margin_left  div_color_red" >* </span><span >咨询人类别</span>
					<c:forEach items="${typeList}" var="m" >                        
			            <span  class="redio_span" >                    
			            <input class="redio_input" disabled="disabled" type="radio" id="numClientType${m.value }" name="numClientType" value="${m.value }" <c:if test="${tbClients.numClientType == m.value}">checked="checked"</c:if>   />${m.label }       
			               	</span>
			        </c:forEach> 
				</div>
				<br/>
				<div >		
						<span class="div_margin_left " >证件类型</span>
					  <c:forEach items="${papersTypeList}" var="m" >      
						<span  class="redio_span" >                  
			            <input  class="redio_input" disabled="disabled"  type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbClients.zjType == m.value}">checked="checked"</c:if>  />${m.label }       
			     </span>
			        </c:forEach> 
					<span class="div_margin_left " >证件号码</span>
					<input class="easyui-textbox textbox_input"   id="zjCode"  name="zjCode"  disabled="disabled"  value="${tbClients.zjCode}"  required="required"  validType="length[0,20]"  >
				</div>
				<br/>
				<div >	
					<span class="div_margin_left  div_color_red" >* </span><span >业务类型</span>
					<input id="vacOrderBusinessType"  name="vacOrderBusinessType"  value="${tbOrders.vacOrderBusinessType }" style="width:270px;height:26px;" disabled="disabled">  
				</div>
				<br/>
				<div >
					<span class="div_margin_left span_title" >工单信息    </span>
				</div>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >工单标题</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled" name="vacOrderTitle"  value="${tbOrders.vacOrderTitle }"  required="required" />
					<span class="div_margin_left  div_color_red" >* </span><span >工单类型</span>
					<c:forEach items="${orderTypeList}" var="m" >                        
			            <span  class="redio_span" >                    
			            <input class="redio_input"  type="radio" disabled="disabled" id="orderType${m.value }" name="vacOrderType" value="${m.value }" <c:if test="${tbOrders.vacOrderType == m.value}">checked="checked"</c:if>  onChange="getOrderDate()" />${m.label }       
			       		</span>
			        </c:forEach>
					<span class="div_margin_left  div_color_red" >* </span><span >工单期限</span>
					<input class="easyui-textbox textbox_input" name="dateOrderTerm" disabled="disabled"
							id="dateOrderTerm" value="${tbOrders.dateOrderTermView }" />	
				</div>
				<br/>
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >任务类型</span>			
					<c:forEach items="${taskTypeList}" var="m" >                        
            		<span  class="redio_span" >                    
			            <input class="redio_input"  type="radio" id="taskType${m.value }" name="vacOrderTaskType" value="${m.value }" disabled="disabled" <c:if test="${tbOrders.vacOrderTaskType == m.value}">checked="checked"</c:if>  />${m.label }       
                  </span>
                    </c:forEach>
				</div>
				<br/>	
				<div>
						<span class="div_margin_left " >联系地址</span>
						<input class="easyui-combobox combobox_input"  name="numOrderContactProvinceId" id="numOrderContactProvinceId" value="${tbOrders.numOrderContactProvinceId }" disabled="disabled">
						<input class="easyui-combobox combobox_input"  name="numOrderContactCityId" id="numOrderContactCityId"  value= "${tbOrders.numOrderContactCityId }" disabled="disabled">
						<input class="easyui-textbox "  id="vacOrderContactAddress"  name="vacOrderContactAddress"   value="${tbOrders.vacOrderContactAddress}"  style="width:300px;height:26px;"   validType="length[0,50]" disabled="disabled">	
				</div>	
				<br/>
				<div>
						<span class="div_margin_left " >常住地址</span>
						<input class="easyui-combobox combobox_input"  name="numClientProvinceId" id="numClientProvinceId" value="${tbClients.numClientProvinceId }" disabled="disabled">
						<input class="easyui-combobox combobox_input"  name="numClientCityId" id="numClientCityId"  value= "${tbClients.numClientProvinceId }" disabled="disabled">
						<input class="easyui-textbox "   id="vacClientAddress"  name="vacClientAddress"   value="${tbClients.vacClientAddress}"  style="width:300px;height:26px;" disabled="disabled">	
				</div>
				<br/>
				<div>
						<span class="div_margin_left " >事发地址</span>
						<input class="easyui-combobox combobox_input"  id="numOrderIncidentProvinceId" name="numOrderIncidentProvinceId"  value="${tbOrders.numOrderIncidentProvinceId }" disabled="disabled">
						<input class="easyui-combobox combobox_input"  id="numOrderIncidentCityId"  name="numOrderIncidentCityId"   value= "${tbOrders.numOrderIncidentCityId }" disabled="disabled">
						<input class="easyui-textbox "  id="vacOrderIncidentAddress"  name="vacOrderIncidentAddress"   value="${tbOrders.vacOrderIncidentAddress}"  style="width:300px;height:26px;"  validType="length[0,50]"  disabled="disabled">	
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >咨询内容</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="vertical-align: top; display: inline-flex;">
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >事项说明</span>
					<input class="easyui-textbox " name="vacOrderAccount" disabled="disabled" data-options="multiline:true" value="${tbOrders.vacOrderAccount }" style="width:300px;height:100px" required="required" validType="length[0,2000]" >
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >处理意见</span>	
					<input class="easyui-textbox " name="vacOrderSuggestion" disabled="disabled"  data-options="multiline:true" value="${tbOrders.vacOrderSuggestion }" style="width:300px;height:100px" required="required" validType="length[0,2000]" >
				</div>	
				<br/>	
				<br/>
				<br/>
				<span class="div_margin_left span_title" >接收区域</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>				
				<div>
					<span class="div_margin_left  div_color_red" >* </span><span >接收区域</span>
					<input class="easyui-combobox combobox_input" disabled="disabled"  name="numOrderReceiveProvinceId" id="numOrderReceiveProvinceId"  value="${tbOrders.numOrderReceiveProvinceId }"  required="required">
					<input class="easyui-combobox combobox_input" disabled="disabled"  name="numOrderReceiveCityId" id="numOrderReceiveCityId" value="${tbOrders.numOrderReceiveCityId }"  required="required">
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >补充</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>						
				<div>							
					<span class="div_margin_left">客户其他联系电话</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled"  name="vacOrderOtherContacts"  value="${tbOrders.vacOrderOtherContacts }" validType="length[0,20]" />
					<span class="div_margin_left  " >服务律师姓名</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled" name="vacOrderLawyerName"  value="${tbOrders.vacOrderLawyerName }"  validType="length[0,20]" />	
				</div>
				<br/>
				<div>	<span
			class="div_margin_left  textbox_title_2">产生原因</span> <input class="easyui-textbox "
			name="vacOrderCreateReason" value="${tbOrders.vacOrderCreateReason }"   
			data-options="multiline:true"  disabled="disabled" style="width:300px;height:100px"  validType="length[0,50]" 
			style="width:300px;height:26px;" >	
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >分配信息</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div>							
					<span class="div_margin_left">分配人</span>
					<input class="easyui-textbox textbox_input"   disabled="disabled" name="vacAllotUserName"  value="${tbOrders.vacAllotUserName }" validType="length[0,20]" />
					<span class="div_margin_left  " >分配时间</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled" name="dateAllotTimeString"  value="${tbOrders.dateAllotTimeString }"  />	
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >审核信息</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div>							
					<span class="div_margin_left textbox_title_1" >* </span><span>初审结果</span>
					<input class="easyui-combobox combobox_input" name="numOrderStatus" value="${tbOrders.numOrderStatus}"
					 disabled="disabled"	data-options="valueField:'value',width:150,panelHeight:'auto',textField:'label',url:'${ctx}/sys/dict/findDictTypeBy/status_type'" />
					<span class="div_margin_left">初审人</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled"  name="modifierName"  value="${tbOrders.modifierName }"  validType="length[0,20]" />
					<span class="div_margin_left  " >初审时间</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled" name="dateAllotTimeString"  value="${tbOrders.dateAllotTimeString }"  />
				</div>
				<br />
					<div>
					<span class="div_margin_left  textbox_title_2">初审不通过原因</span> <input
						class="easyui-textbox textbox_input" name="noPassReason"
						value="${tbOrders.noPassReason }"  disabled="disabled" data-options="multiline:true"  style="width:300px;height:100px"  validType="length[0,50]" />
					</div>
					<br/>
					<div id="reexamine_div">							
					<span class="div_margin_left textbox_title_1" >* </span><span>复审结果</span>
					<input class="easyui-textbox textbox_input" name="vacReexamineTaskstate" value="${tbOrders.vacReexamineTaskstate}" disabled="disabled"/>
					<span class="div_margin_left">复审情况</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled"  name="vacSituation"  value="${tbOrders.vacSituation }"/>
					<span class="div_margin_left  " >复审时间</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled" name="vacReexamineOpinion"  value="${tbOrders.vacCompleteTime }"  />
					<span class="div_margin_left  " >处理结果</span>
					<input class="easyui-textbox textbox_input"  disabled="disabled" name=vacCompleteTime  value="${tbOrders.vacReexamineOpinion }"  />
				</div>
				<br/>
				<br/>
					</form>
				