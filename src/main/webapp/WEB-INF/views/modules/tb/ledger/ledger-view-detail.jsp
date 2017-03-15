<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
//点击生成工单
function saveOrder(){
	 $("#saveOrder").linkbutton('disable');
	 var id = $("input[name='tbLedger.id']").val();
	 window.parent.window.newTab(id,"/tb/ledger/orderadd-index","生成工单"+id,"icon-edit");
	 $("#saveOrder").linkbutton('enable');
}

function deleteLedger(){
	 $("#deleteLedger").linkbutton('disable');
	 var id = $("input[name='tbLedger.id']").val();
	 var isDeleted = $("input[name='tbLedger.isDeleted']").val();
	 //设为无效的逻辑
	 $.messager.confirm('提示','确定将此台账设为有效？', function(r) {  
        if (r) {  
            $.post('${ctx}/tb/ledger/deleteLedger', {  
                id : id,
                isDeleted:isDeleted
            }, function(result) {  
           	 if(result.code == 1){
           		 $.messager.alert('提示',result.message);
           	 }else{
           		 $.messager.alert('提示','设为有效成功');
           		 //灰掉所有按钮，刷新台账无效记录
           	 }
            });  
        }else{
        	$("#deleteLedger").linkbutton('enable');
        }    
    });  
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
			  $('#numLedgerIncidentCityId').combobox({ 
					required:false,
					editable:false,//不可编辑，只能选择
					cache: false,
					panelHeight: 'auto',//自动高度适合
					valueField:'id', //值字段
					textField:'vacCityName' //显示的字段 
		      });
		}
		
		  var t = '${tbLedger.vacLedgerBusinessType}' ;
		  if(t != ""){
		      $.ajax({
			        type: "POST",
			        url:'${ctx}/sys/specialty/getById/${tbLedger.vacLedgerBusinessType}',
			        cache: false,
			        dataType : "json",
			        success: function(data){
			 			  $('#vacLedgerBusinessType').combotree('setValue', 
			 					{
			 						id: t,
			 						text: data.vacName
			 					}
			 			  );
			         }
			       }); 
		  }
		  $('#vacLedgerBusinessType').combotree({    
			  	url: '${ctx}/sys/specialty/findAllBusinessType',   
			    editable:true,
			    panelHeight:300,
			    required: true
			}); 
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
		  $("input[type='radio']").each(function(){  
			    $(this).attr("disabled",true);  
			});
		  
		  
});
</script>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<br/>
				<form id="ledgerview-form" method="post" action="" modelAttribute="tbClients,tbLedger">
				<div >
					<span class="div_margin_left span_title" >台账详情    </span> 
					<c:if test="${tbLedger.fileName != ''}">					
						<span style="font-size: 12px;color: gray;margin-left: 8px; class="div_margin_left">录音</span>
						<object id="czplayer" width="300" height="63"
							classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6"
							codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=7.0"
							align="top" border="0" type="application/x-oleobject">
							<param name="URL" value="${tbLedger.fileName}">
						</object>
					</c:if> 
				</div>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div >
						<input type="hidden"  name="tbLedger.id"  value="${tbLedger.id }"  >
						<input type="hidden"  name="tbClients.id"  value="${tbClients.id }" >
						<input type="hidden"  name="tbLedger.isDeleted"  value="${tbLedger.isDeleted }"  >
						
				</div>
				<div>
					<span class="div_margin_left">流 水 号</span>
					<input name="vacLedgerSerial"  value="${tbLedger.vacLedgerSerial }" class="easyui-textbox textbox_input"  disabled="disabled">
					<span class="div_margin_left">来电号码</span>
					<input name="vacLedgerNumber"  id="vacLedgerNumber"  value="${tbLedger.vacLedgerNumber }" class="easyui-textbox textbox_input"   disabled="disabled"/>
					<span class="div_margin_left">来电归属</span>
					<input type=hidden  name="numLedgerProvinceId" value="${tbLedger.numLedgerProvinceId}">
					<input type="hidden"  name="numLedgerCityId" value="${tbLedger.numLedgerCityId}">
					<input type="hidden"  name="vacLedgerProvinceName" value="${tbLedger.vacLedgerProvinceName}">
					<input type="hidden"  name="vacLedgerCityName" value="${tbLedger.vacLedgerCityName}">
					<input class="easyui-textbox textbox_input"    value="${tbLedger.vacLedgerProvinceName} -  ${tbLedger.vacLedgerCityName} "  disabled="disabled" >
				</div>
				<br/>
				<div>
					<span class="div_margin_left">来电队列</span>
					<input  class="easyui-textbox textbox_input"  name="numLedgerCallQueue"   value="${tbLedger.numLedgerCallQueue }" disabled="disabled" >
					<span class="div_margin_left"> 话务员工号</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerAgentCode"   value="${tbLedger.vacLedgerAgentCode }"  disabled="disabled" >
					<span class="div_margin_left"> 话务员姓名</span>
					<input class="easyui-textbox textbox_input"  name="vacLedgerAgentName"  value="${tbLedger.vacLedgerAgentName }"  disabled="disabled" >
				</div>
				<br/>
				<p/>
				<span class="div_margin_left span_title" >客户信息</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div >
					<span class="div_margin_left  div_color_red" >* </span><span >客户姓名</span>
					<input class="easyui-textbox textbox_input"  id="vacClientName" name="vacClientName"  value="${tbClients.vacClientName}"  disabled="disabled">		
					<span class="div_margin_left  div_color_red" >* </span><span >性别</span>
					<c:forEach items="${genderList}" var="m" >       
					<span  class="redio_span" >               
			            <input  class="redio_input"  type="radio"   id="numClientGender${m.value }" name="numClientGender" value="${m.value }" <c:if test="${tbClients.numClientGender == m.value}">checked="checked"</c:if>  >${m.label }       
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
					<input id="numSpecialtyId"  name="numSpecialtyId" value="${tbLedger.numSpecialtyId }" style="width:270px;height:26px;" required="required"  disabled="disabled"> 
					<span class="div_margin_left  div_color_red" >* </span><span >业务类型</span>
					<input id="vacLedgerBusinessType"  name="vacLedgerBusinessType" value="${tbLedger.vacLedgerBusinessType }" style="width:300px;height:26px;" disabled="disabled">  
				</div>
				<br/>
				<div>
					<span class="div_margin_left " >常住地址</span>
					<input class="easyui-combobox combobox_input"  id="numClientProvinceId" name="numClientProvinceId"   value= "${tbClients.numClientProvinceId }" disabled="disabled" >
					<input class="easyui-combobox combobox_input" id="numClientCityId" name="numClientCityId"   value= "${tbClients.numClientCityId }" disabled="disabled">
					<input class="easyui-textbox textbox_input"  id="vacClientAddress"  name="vacClientAddress"   value="${tbClients.vacClientAddress}"  style="width:300px;height:26px;"   disabled="disabled">
				</div> 
				<br/>
				<div>
					<span class="div_margin_left " >事发地址</span>
					<input class="easyui-combobox combobox_input"  id="numLedgerIncidentPorvinceId" name="numLedgerIncidentPorvinceId"   value= "${tbLedger.numLedgerIncidentPorvinceId }"  disabled="disabled"/>
					<input class="easyui-combobox combobox_input" id="numLedgerIncidentCityId" name="numLedgerIncidentCityId"   value= "${tbLedger.numLedgerIncidentCityId }" disabled="disabled" />
					<input class="easyui-textbox textbox_input"  name="vacLedgerIncidentAddress"  value="${tbLedger.vacLedgerIncidentAddress }"  style="width:300px;height:26px;"  disabled="disabled" />
				</div>
				<br/>
				<br/>
				<span class="div_margin_left span_title" >咨询内容</span>
				<p/>
				<div class="div_margin_left demo_line_02"></div>
				<p/>
				<div style="vertical-align: top; display: inline-flex;">
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >客户自述</span>
					<input class="easyui-textbox " name="vacLedgerClientAccount" data-options="multiline:true" value="${tbLedger.vacLedgerClientAccount }" style="width:300px;height:100px" readonly="readonly">
					<span class="div_margin_left textbox_title_1" >* </span><span  class="textbox_title_2" >处理意见</span>	
					<input class="easyui-textbox " name="vacLedgerLawyerSuggestion"  data-options="multiline:true" value="${tbLedger.vacLedgerLawyerSuggestion }"  style="width:300px;height:100px" readonly="readonly">
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
					<span class="div_margin_left " >证件类型</span>
					<c:forEach items="${papersTypeList}" var="m" >      
						<span  class="redio_span" >                  
			            <input  class="redio_input"   type="radio" id="zjType${m.value }" name="zjType" value="${m.value }" <c:if test="${tbClients.zjType == m.value}">checked="checked"</c:if>  />${m.label }       
			     </span>
			        </c:forEach> 
			     </div>
			     <br/>
				<div>
					<span class="div_margin_left " >证件号码</span>
					<input class="easyui-textbox textbox_input"   id="zjCode"  name="zjCode"  value="${tbClients.zjCode}"  disabled="disabled" >
				</div>
				<br/>
				<br/>
				<div class="div_margin_left"  >
				<c:choose>
						<c:when test="${tbLedger.isDeleted == true}">
						<permission:hasPermission action="tb/ledger/deleteLedger">
							<a href="javascript:deleteLedger();" id="deleteLedger"   data-options="iconCls:'icon-remove',disabled:false"  class="easyui-linkbutton a_button">设为有效</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</permission:hasPermission>
							<permission:hasPermission action="tb/ledger/saveLedger">
							<a href="javascript:saveLedger();"  data-options="iconCls:'icon-save',disabled:true"    class="easyui-linkbutton a_button">保存台帐</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</permission:hasPermission>
							<permission:hasPermission action="tb/ledger/orderadd-index">
							<a href="javascript:saveOrder();"  data-options="iconCls:'icon-save',disabled:true"  class="easyui-linkbutton a_button ">生成工单</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</permission:hasPermission>
						</c:when>
						<c:otherwise>		
							<permission:hasPermission action="tb/ledger/deleteLedger">			
							<a href="javascript:deleteLedger();"   data-options="iconCls:'icon-remove',disabled:true"  class="easyui-linkbutton a_button">设为无效</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</permission:hasPermission>
							<permission:hasPermission action="tb/ledger/saveLedger">
							<a href="javascript:saveLedger();" data-options="iconCls:'icon-save',disabled:true"    class="easyui-linkbutton a_button">保存台帐</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</permission:hasPermission>
							<permission:hasPermission action="tb/ledger/orderadd-index">
							<a href="javascript:saveOrder();" id="saveOrder"  data-options="iconCls:'icon-save',disabled:false"  class="easyui-linkbutton a_button ">生成工单</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</permission:hasPermission>
						</c:otherwise>
				</c:choose>
				</div>
				<br/>
				<br/>						
					</form>

					
