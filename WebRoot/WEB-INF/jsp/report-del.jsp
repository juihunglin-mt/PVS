<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="i-header.jsp" />
   <s:action name="report-get" executeResult="false">
      <s:param name="reportId" value="#parameters.id" />
   </s:action>
   
<div class="error_msg"><s:actionerror/></div>
<div class="content clear-fix">
	<div class="main-title container"><h1><a href="index">Reports</a> > Delete</h1></div>
	<div class="container c-95">
		<table>
		<s:form action="report-delete" validate="true" id="doupdate" cssClass="list">
  			<tr>
  				<td>
  					<s:hidden name="reportId"  value="%{#request.report.reportId}" />
  		    <td><span class="showRed">Delete "<s:property value="#request.report.reportName"/>" ?</span>
    			<div class="button-wrapper"><button class="submit">Submit</button></div></td>
    		</tr>
		</s:form>	
		</table>
    </div>
</div>
<jsp:include page="i-footer.jsp" />