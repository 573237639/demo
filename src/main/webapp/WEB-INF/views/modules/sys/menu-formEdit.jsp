<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>菜单</title>
		<%@ include file="/WEB-INF/views/include/header.jsp"%>
		<meta http-equiv="X-UA-Compatible" content="IE=9" >
		    <link rel="stylesheet" href="${ctxStatic}/bootstrap-3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" type="text/css" />
		 <link rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins2/Blue/jbox.css">
		 <style type="text/css">
			.error{color:red;}
		</style>
		 <script type="text/javascript" src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.min.js"></script>

		<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		}); 
		
		function changeInput(v){
			var s = v.value;
			if(s==1){
				$("#space-id-4").hide();
				$("#form-id-group").hide();
			}else{
				$("#space-id-4").show();
				$("#form-id-group").show();
			}
		}
		
		//--------------------------------------------------------------
		var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};

		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("parentMenusTree"),
			nodes = zTree.getSelectedNodes(),
			v = "";
			idv = "";
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				idv = nodes[i].id;
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#parentMenuName");
			cityObj.attr("value", v);
			$("#parentId").attr("value", idv);
		}

		function showMenu() {
			var cityObj = $("#parentMenuName");
			var cityOffset = $("#parentMenuName").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		$(document).ready(function(){
			$.get('${ctx}/sys/menu/pMenu',{},function(data){  
			    $.fn.zTree.init($("#parentMenusTree"), setting, eval(data));
			  });  
		});
		</script>
	</head>

	<body>
					<div class="page-content">
						<div class="page-header">
							<h1>
								菜单管理
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
								<form:form id="inputForm" action="${ctx}/sys/menu/edit" modelAttribute="menu" class="form-horizontal">
									<input type="hidden" name="parentId" id="parentId" value="${menu.parentMenu.id}"/>
									<c:if test="${not empty menu.sysAct}">
									<input type="hidden" name="actId" value="${menu.sysAct.id}"/>
									</c:if>
									<form:hidden path="sort"/>
									<form:hidden path="id"/>
								 	<tags:message content="${message}"/> 
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >父菜单:</label>

										<div class="col-sm-3">
											<input name="parentMenuName" class="form-control input-sm" id="parentMenuName" readonly="readonly" value="${menu.parentMenu.name}"/>
										</div>
										&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >菜单名:</label>

										<div class="col-sm-3">
											<form:input path="name" class="form-control input-sm required"/>
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >菜单类型:</label>

										<div class="col-sm-3">
											<label><input type="radio" name="menuType" value="1" <c:if test="${menu.menuType eq 1}">checked="checked"</c:if> class="required" onclick="changeInput(this);">父级菜单</label>
											<label><input type="radio" name="menuType" value="2" class="required" <c:if test="${menu.menuType eq 2}">checked="checked"</c:if> onclick="changeInput(this);">叶子菜单</label>
											<%-- <form:radiobuttons path="menuType" items="${fns:getDictList('menu_type')}" itemLabel="label" itemValue="value" onclick="changeInput(this);"/> --%>
										</div>
									</div>
									
									<div class="space-4" id="space-id-4" style="${menu.menuType eq 1 ? 'display:none' : ''}"></div>
									<div class="form-group" id="form-id-group" style="${menu.menuType eq 1 ? 'display:none' : ''}">
										<label class="col-sm-3 control-label no-padding-right" >菜单动作:</label>

										<div class="col-sm-3">
											<form:hidden path="sysAct.id"/>
											<form:hidden path="sysAct.type"/>
											<form:hidden path="sysAct.menuId"/>
											<form:hidden path="sysAct.remarks"/>
											<form:hidden path="sysAct.isDeleted"/>
											<form:input path="sysAct.actName"  class="form-control input-sm col-xs-10 col-sm-5"/>
										</div>
									</div>
									
									<div class="space-4"></div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >菜单图标:</label>

										<div class="col-sm-3">
											<tags:iconselect id="icon" name="icon" value="${menu.icon}"></tags:iconselect>
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >描述:</label>

										<div class="col-sm-3">
											<form:input path="remarks" class="form-control input-sm col-xs-10 col-sm-5"/>
										</div>
									</div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="icon-ok bigger-110"></i>
												Submit
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="icon-undo bigger-110"></i>
												Reset
											</button>
										</div>
									</div>

								</form:form>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->


		<!-- inline scripts related to this page -->
		
		<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
			<ul id="parentMenusTree" class="ztree" style="margin-top:0; width:160px; background-color: gray;"></ul>
		</div>
		<script src="${ctxStatic}/public/js/common.min.js"></script>
</body>
</html>
