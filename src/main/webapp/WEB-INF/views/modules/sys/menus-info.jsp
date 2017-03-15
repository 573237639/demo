<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div>
	<h2 >当前菜单:${curMenu.name}</h2>	
	<table class="easyui-datagrid" data-options="fitColumns:true,singleSelect:true">   
	    <thead>   
	        <tr>   
	            <th data-options="field:'1',width:100">菜单类型</th>   
	            <th data-options="field:'2',width:100">菜单动作</th>   
	            <th data-options="field:'3',width:100">说明</th>   
	            <th data-options="field:'4',width:100">父菜单</th>   
	            <th data-options="field:'5',width:100">操作</th>   
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
					<a href="javascript:menuInfoClick('${ctx}/sys/menus/menuinfo?id=${curMenu.parentMenu.id}','${curMenu.parentMenu.id}')">${curMenu.parentMenu.name}</a>
				</c:if>
				</td>
				<td>
					<c:if test="${curMenu.id ne 0}">
		              <a href="javascript:menuOptClick('${ctx}/sys/menus/formEdit?id=${curMenu.id}')">编辑</a>
			         </c:if> 
			         <c:if test="${curMenu.menuType eq 1}">
			              <a href="javascript:menuOptClick('${ctx}/sys/menus/formAdd?id=${curMenu.id}')">添加子菜单</a>
			         </c:if> 
			         <c:if test="${curMenu.menuType eq 2}">
		              <a href="javascript:menuOptClick('${ctx}/sys/act/form?menuId=${curMenu.id}')">添加动作</a>
		           </c:if> 
				</td>
			</tr>   
	    </tbody>   
	</table>  
	
	<c:if test="${not empty subMenus}">
		<h3 class="header smaller lighter blue">子菜单列表:</h3>
		<table class="easyui-datagrid" data-options="fitColumns:true,singleSelect:true">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'1',width:100">菜单名称</th>   
		            <th data-options="field:'2',width:100">菜单类型</th>   
		            <th data-options="field:'3',width:100">菜单动作</th>   
		            <th data-options="field:'4',width:100">说明</th>   
		            <th data-options="field:'5',width:100">操作</th>   
		        </tr>   
		    </thead>   
		    <tbody>   
		        <c:forEach items="${subMenus}" var="menu">
					<tr>
						<td><a href="javascript:menuInfoClick('${ctx}/sys/menus/menuinfo?id=${menu.id}','${menu.id}')">${menu.name}</a></td>
						<td>${menu.menuTypeString}</td>
						<td>
						<c:if test="${not empty menu.sysAct}">
							${menu.sysAct.actName}
						</c:if>
						</td>
						<td>${menu.remarks}</td>
						<td>
							<a href="javascript:menuDelClick('${ctx}/sys/menus/delete?id=${menu.id}')">删除</a>
							<a href="${ctx}/sys/menus/up?id=${menu.id}&pid=${menu.parentMenu.id}">上移</a>
							<a href="${ctx}/sys/menus/down?id=${menu.id}&pid=${menu.parentMenu.id}">下移</a>
						</td>
					</tr>
				</c:forEach>
		    </tbody>   
		</table> 
	</c:if>
	
	<c:if test="${not empty commonActs}">
		<h3 class="header smaller lighter blue">普通动作列表:</h3>
		<table class="easyui-datagrid" data-options="fitColumns:true,singleSelect:true">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'1',width:100">动作名称</th>   
		            <th data-options="field:'2',width:100">动作类型</th>   
		            <th data-options="field:'3',width:100">说明</th>   
		            <th data-options="field:'4',width:100">操作</th>   
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
	</c:if>
	
	<c:if test="${not empty authActs}">
		<h3 class="header smaller lighter blue">授权动作列表:</h3>
		<table class="easyui-datagrid" data-options="fitColumns:true,singleSelect:true">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'1',width:100">动作名称</th>   
		            <th data-options="field:'2',width:100">动作类型</th>   
		            <th data-options="field:'3',width:100">说明</th>   
		            <th data-options="field:'4',width:100">操作</th>   
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
						<a href="javascript:menuDelClick('${ctx}/sys/act/delete?id=${authAct.id}')">删除</a>
						</td>
					</tr>
				</c:forEach>
		    </tbody>   
		</table>
	</c:if>
</div>
