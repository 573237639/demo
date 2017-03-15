<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="title" type="java.lang.String" required="false" description="当前页面title"%>
<title>${fns:getTitleName()}-${title}</title>

