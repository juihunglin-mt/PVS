<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	
<jsp:include page="i-header.jsp" />
	
	<s:action name="report-all"></s:action>

	<div id="dashboard-page" class="content clear-fix">
		<div class="main-title container"><h1>Dashboard</h1></div>
		
		<div class="container c-95">
			<font color="red">Failed</font>
		</div>
	</div> 
<jsp:include page="i-footer.jsp" />
