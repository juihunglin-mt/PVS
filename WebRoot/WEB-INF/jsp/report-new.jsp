<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="i-header.jsp" />

<div class="error_msg"><s:actionerror/></div>
<div class="content clear-fix">
	<div class="main-title container"><h1><a href="index">Reports</a> > Add</h1></div>
	<div class="container c-95">
   		<table>
    		<s:form action="report-save" validate="true" id="doupdate" cssClass="list" enctype="multipart/form-data" theme="simple">
				<tr>
					<th>Field</th>
					<th>Value</th>	
				</tr>
				<tr>
					<td>Different Percent >= </td>
					<td><s:textfield name="diffPct"/> % (Default 1 %)</td>
				</tr>
				<tr>
					<td>Different Value >= </td>
					<td><s:textfield name="diffValue"/> (Default 0.01)</td>
				</tr>
				<tr>
					<td>Different Ratio >= </td>
					<td><s:textfield name="diffRatio"/> % (Default 0.01 %)</td>
				</tr>
				<tr>
					<td>Total Check Days </td>
					<td><s:textfield name="maxCount"/> Days (Default 0 is all)</td>
				</tr>
	  		    <tr>
					<td>BBG File</td>
					<td><s:file name="bbgFile" label="Select BBG File"/></td>
				</tr>
				<tr>
					<td>EDM File</td>
					<td><s:file name="edmFile" label="Select EDM File"/></td>
				</tr>
	  			<tr>
		  		    <td colspan="2" align="right"><div class="button-wrapper"><button class="submit">Submit</button></div></td>
		  		</tr>
	  		</s:form>
    	</table>
    </div>
</div>
<jsp:include page="i-footer.jsp" />