<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	
<jsp:include page="i-header.jsp" />
	
	<s:action name="report-all"></s:action>

	<div id="dashboard-page" class="content clear-fix">
		<div class="main-title container"><h1>Dashboard</h1></div>
		
		<div class="container c-95">
			<table>
				<caption>
					<ul class="tool">
						<li><a href="index" class="add"><span>Reload<i></i></span></a></li>
					</ul>
				</caption>
				<display:table name="request.reportList" requestURI="index" id="resulttable" pagesize="15"  defaultorder="descending" class="list">
    			<display:column property="insertDate" title="Date" sortable="false" format="{0,date,yyyy/MM/dd HH:mm:ss}" />
    			<display:column  title="Report Name" sortable="false"  >
			    <c:choose>
		   			<c:when test="${resulttable.reportStatus eq 2}"><a href="report-download?reportId=${resulttable.reportId}" >${resulttable.reportName}</a></c:when>
		    		<c:otherwise>${resulttable.reportName}</c:otherwise>
		    	</c:choose>
			    </display:column>
			    <display:column title="Different Percent " >
			    	>= ${resulttable.diffPct} %
			    </display:column>
			    <display:column title="Different Value " >
			    	>= ${resulttable.diffValue}
			    </display:column>
			    <display:column title="Different Ratio " >
			    	>= ${resulttable.diffRatio} %
			    </display:column>
			    <display:column title="Check Days " >
			    	<c:choose>
		   				<c:when test="${resulttable.maxCount eq 0}">All Data</c:when>
		   				<c:otherwise>${resulttable.maxCount} Days</c:otherwise>
		    		</c:choose>
			    </display:column>
			    <display:column  title="BBG Status" sortable="false"  >
			    <c:choose>
		   			<c:when test="${resulttable.bbgStatus eq 1}"><span class="status complete">Upload Complete</span></c:when>
		   			<c:when test="${resulttable.bbgStatus eq 2}"><span class="status error">Upload Failed</span></c:when>
		    		<c:otherwise><span class="status waiting">Waiting to Upload</span></c:otherwise>
		    	</c:choose>
			    </display:column>
			    
			    <display:column  title="EDM Status" sortable="false"  >
			    <c:choose>
		   			<c:when test="${resulttable.edmStatus eq 1}"><span class="status complete">Upload Complete</span></c:when>
		   			<c:when test="${resulttable.edmStatus eq 2}"><span class="status error">Upload Failed</span></c:when>
		    		<c:otherwise><span class="status waiting">Waiting to Upload</span></c:otherwise>
		    	</c:choose>
			    </display:column>
			    
			    <display:column  title="Report Status" sortable="false"  >
			    <c:choose>
		   			<c:when test="${resulttable.reportStatus eq 1}">
		   				<span class="status waiting">Verification Data</span></c:when>
		   			<c:when test="${resulttable.reportStatus eq 2}">
		   				<span class="status complete">Complete</span></c:when>
		   			<c:when test="${resulttable.reportStatus eq 3}">
		   				<span class="status error">Generate Failed</span></c:when>
		    		<c:otherwise><span class="status waiting">Waiting Upload Data</span></c:otherwise>
		    	</c:choose>
			    </display:column>
			    
			    <display:column >
				    <ul class="tool">
						<li><a href="report-del?id=${resulttable.reportId}" class="delete"><span>Delete<i></i></span></a></li>
				    </ul>
			    </display:column>
			  </display:table>
			</table>
		</div>
	</div> 
<jsp:include page="i-footer.jsp" />
