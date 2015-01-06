<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%--<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userName = (String) session.getAttribute("userName");
if(userName==null) {userName = "";}
String userGroupClassId = (String) session.getAttribute("userGroupClass");
String userAdmin = session.getAttribute("userAdmin").toString();
request.setAttribute("userGroupClassId", userGroupClassId);
request.setAttribute("userAdmin", userAdmin);
String userId = (String) session.getAttribute("userId");

%>
--%>
<!doctype html>
<html lang="zh-tw">
<head>
	<meta charset="UTF-8">
	<title>Thomson Reuters PV System</title>

	<link rel="stylesheet" type="text/css" href="css/reset.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" href="css/ui-lightness/jquery-ui-1.10.3.custom.css">
	<script src="js/jquery-1.9.1.min.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/highcharts.js"></script>
	<script src="js/exporting.js"></script>
	<script src="js/script.js"></script>
	<script src="js/spin.js"></script>
	<script src="js/tab.js"></script>
	<script src="js/clock.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
	    	startTime();
	    });
	</script>
</head>
<body>
	<div class="header">
		<div class="logo"><a href="index"><img src="images/header_logo.png"></a></div>
		<div class="member"><a href="#">User</a> ｜ <a href="#">Logout</a> ｜ <font color="white" id="date"></font> ｜ <font color="white" id="time"></font></div>
		<div class="client"></div>
	</div>
	<s:include value="menu.jsp"></s:include>
