<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/bootstrap-3.3.5/css/bootstrap.min.css">
		<meta charset="utf-8" />
		<title>act</title>
		<script type="text/javascript">
		$(function(){
			$("#inputForm").submit(function(){
				var radio= $(".radio_validate:checked").val(); 
				var actName=$("#actName").val();
				
				if(typeof(radio) == "undefined"){
					alert("请选择动作类型");
					return false;
				}
				if(actName==''||actName==null){
					alert("请选择动作url");
					return false;
				}
				return true;
			})
		})

		</script>
	</head>

	<body>
					<div class="page-content">
						<div class="page-header">
							<h1>
									菜单动作编辑
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
								<form:form id="inputForm" modelAttribute="sysAct" action="${ctx}/sys/act/save" class="form-horizontal">
									<form:hidden path="id"/>
									<form:hidden path="isDeleted"/>
									<form:hidden path="menuId"/>
									<form:hidden path="creator"/>
									<form:hidden path="modifier"/>
									<form:hidden path="gmtCreated"/>
									<form:hidden path="gmtModified"/>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >所属菜单:</label>

										<div class="col-sm-9">
											${menu.name}
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >动作:</label>

										<div class="col-sm-3">
											<form:input path="actName" class="form-control input-sm"/>
										</div>
									</div>

									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >动作类型:</label>

										<div class="col-sm-9">
										<form:radiobuttons path="type" items="${fns:getDictList('act_type')}"  class="radio_validate"  itemLabel="label" itemValue="value"/>
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >描述:</label>

										<div class="col-sm-3">
										<form:input path="remarks" class="form-control input-sm"/>
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
    <script src="${ctxStatic}/bootstrap-3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
