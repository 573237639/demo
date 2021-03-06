<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/brower.js"></script>
<i id="${id}Icon" class="icon-${not empty value?value:' hide'}"></i>&nbsp;<label id="${id}IconLabel">${not empty value?value:'无'}</label>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}"/><a id="${id}Button" href="javascript:" class="btn">选择</a>&nbsp;&nbsp;
<script type="text/javascript">
	$("#${id}Button").click(function(){
		 $.jBox(
				"iframe:${ctx}/tag/iconselect?value="+$("#${id}").val(),
					{
						title:"选择图标", 
						width:700,
						height:180, 
			            buttons:{"确定":"ok", "清除":"clear", "关闭":true},
		           		submit:function(v, h, f){
			                if (v=="ok"){
			                	var icon = h.find("iframe")[0].contentWindow.$("#icon").val();
			                	$("#${id}Icon").attr("class", "icon-"+icon);
				                $("#${id}IconLabel").text(icon);
				                $("#${id}").val(icon);
			                }else if (v=="clear"){
				                $("#${id}Icon").attr("class", "icon- hide");
				                $("#${id}IconLabel").text("无");
				                $("#${id}").val("");
			                }
		          		}, 
		      			loaded:function(h){
		              	 $(".jbox-content", top.document).css("overflow-y","hidden");
			            }
					}
		); 
	});
</script>