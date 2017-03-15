<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" >
		<title>菜单</title>
		    <link rel="stylesheet" href="${ctxStatic}/bootstrap-3.3.5/css/bootstrap.min.css">
		<%@ include file="/WEB-INF/views/include/header.jsp"%>
		<script type="text/javascript">
			window.parent.initTree(${curMenu.id});
		</script>
	</head>

	<body>
					<div class="page-content">
						<div class="row">
									<div class="col-xs-12">
									<h2 >当前菜单:${curMenu.name}</h2>	
										<div class="table-responsive">
											<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>菜单类型</th>
														<th>菜单动作</th>
														<th>说明</th>
														<th>父菜单</th>
														<th>操作</th>
													</tr>
												</thead>

												<tbody>
													<tr>
														<td>${curMenu.menuTypeString}</td>
														<td>
														<c:if test="${not empty curMenu.sysAct}">
															${curMenu.sysAct.actName}
														</c:if>
														</td>
														<td>${curMenu.remarks}</td>
														<td>
														<c:if test="${not empty curMenu.parentMenu}">
															<a href="${ctx}/sys/menu/menuinfo?id=${curMenu.parentMenu.id}">${curMenu.parentMenu.name}</a>
														</c:if>
														</td>
														<td>
															<c:if test="${curMenu.id ne 0}">
												              <a href="${ctx}/sys/menu/formEdit?id=${curMenu.id}">编辑</a>
													         </c:if> 
													         <c:if test="${curMenu.menuType eq 1}">
													              <a href="${ctx}/sys/menu/formAdd?id=${curMenu.id}">添加子菜单</a>
													         </c:if> 
													         <c:if test="${curMenu.menuType eq 2}">
												              <a href="${ctx}/sys/act/form?menuId=${curMenu.id}">添加动作</a>
												           </c:if> 
														</td>
													</tr>
												</tbody>
											</table>
										</div><!-- /.table-responsive -->
										<c:if test="${not empty subMenus}">
										<h3 class="header smaller lighter blue">子菜单列表:</h3>
										<div class="table-responsive">
											<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>菜单名称</th>
														<th>菜单类型</th>
														<th>菜单动作</th>
														<th>说明</th>
														<th>操作</th>
													</tr>
												</thead>

												<tbody>
													<c:forEach items="${subMenus}" var="menu">
													<tr>
														<td><a href="${ctx}/sys/menu/menuinfo?id=${menu.id}">${menu.name}</a></td>
														<td>${menu.menuTypeString}</td>
														<td>
														<c:if test="${not empty menu.sysAct}">
														${menu.sysAct.actName}
														</c:if>
														</td>
														<td>${menu.remarks}</td>
														<td>
														<permission:hasPermission action="/sys/menu/delete">
														<a href="${ctx}/sys/menu/delete?id=${menu.id}">删除</a>
														</permission:hasPermission>
														<permission:hasPermission action="/sys/menu/up">
														<a href="${ctx}/sys/menu/up?id=${menu.id}&pid=${menu.parentMenu.id}">上移</a>
														</permission:hasPermission>
														<permission:hasPermission action="/sys/menu/down">
														<a href="${ctx}/sys/menu/down?id=${menu.id}&pid=${menu.parentMenu.id}">下移</a>
														</permission:hasPermission>
														</td>
													</tr>
													</c:forEach>
												</tbody>
											</table>
										</div><!-- /.table-responsive -->
										</c:if>
										<c:if test="${not empty commonActs}">
										<h3 class="header smaller lighter blue">普通动作列表:</h3>
										<div class="table-responsive">
											<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>动作名称</th>
														<th>动作类型</th>
														<th>说明</th>
														<th>操作</th>
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${commonActs}" var="commonAct">
													<tr>
														<td>${commonAct.actName}</td>
														<td>菜单动作</td>
														<td>
														<font color="${commonAct.id==curMenu.sysAct.id ? 'red' : ''}">${commonAct.remarks}</font>
														</td>
														<td>
														</td>
													</tr>
												</c:forEach>
												</tbody>
											</table>
										</div><!-- /.table-responsive -->
										</c:if>
										<c:if test="${not empty authActs}">
										<h3 class="header smaller lighter blue">授权动作列表:</h3>
										<div class="table-responsive">
											<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>动作名称</th>
														<th>动作类型</th>
														<th>说明</th>
														<th>操作</th>
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${authActs}" var="authAct">
													<tr>
														<td>${authAct.actName}</td>
														<td>授权动作</td>
														<td>${authAct.remarks}</td>
														<td>
														<permission:hasPermission action="/sys/act/form">
														<a href="${ctx}/sys/act/form?id=${authAct.id}&menuId=${authAct.menuId}">修改</a>
														</permission:hasPermission>
														<permission:hasPermission action="/sys/act/delete">
														<a href="${ctx}/sys/act/delete?id=${authAct.id}">删除</a>
														</permission:hasPermission>
														</td>
													</tr>
													</c:forEach>
												</tbody>
											</table>
										</div><!-- /.table-responsive -->
										</c:if>
									</div><!-- /span -->
								</div><!-- /row -->
					</div><!-- /.page-content -->
		
		<!-- inline scripts related to this page -->
<script src="${ctxStatic}/public/js/common.min.js"></script>
</body>
</html>
