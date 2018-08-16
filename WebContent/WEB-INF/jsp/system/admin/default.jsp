<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

	
</head>
<body >
	<div class="container-fluid" id="main-container" >
		
		<div id="page-content" class="clearfix">

			<div class="page-header position-relative">
				<h1>
					首页 <small><i class="icon-double-angle-right"></i> </small>
				</h1>
				<br/>
				<img title="框架图解" src="static/img/mbfw.PNG" width="1000px" height="800px">
			
			</div><!-- /.main-content -->
		</div>
	</div>
	
	<div style="position:fixed; top:100%; left:100%;" >	
		<!--  回到顶部-->
		<a href="#" id="btn-scroll-up"  class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
	</div>
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<%-- 回到顶部功能的js插件  --%>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<%-- 回到顶部功能的js插件  --%>
	<script src="static/js/ace-elements.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>	<%-- 下拉框 --%>
	<script type="text/javascript" src="static/js/bootstrap-datepicker.js"></script><%-- 日期框 --%>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>			<%-- 确认窗口 --%>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>			<%-- 提示框--%>
	
	<script type="text/javascript">
	
		$(top.hangge());

	</script>

</body>
</html>
