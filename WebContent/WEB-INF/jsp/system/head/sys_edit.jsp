<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript">
			var jsessionid = "<%=session.getId()%>";  //勿删，uploadify兼容火狐用到
		</script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/sys.js"></script>	
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
	</head>
<body>

<div id="zhongxin">
 <div class="span6">
	<div class="tabbable">
            <ul class="nav nav-tabs" id="myTab">     
              <li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 配置 NO1</a></li>
              <li><a data-toggle="tab" href="#profile2"><i class="green icon-cog bigger-110"></i>配置 NO2</a></li>
              <li><a data-toggle="tab" href="#profile3"><i class="green icon-cog bigger-110"></i>配置 NO3</a></li>
            </ul>
            <div class="tab-content">
			  <div id="home" class="tab-pane in active">
				<form action="head/saveSys.do" name="Form" id="Form" method="post">
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="width:70px;text-align: right;padding-top: 13px;">系统名称:</td>
						<td><input type="text" name="YSYNAME" id="YSYNAME" value="${pd.SYSNAME }" placeholder="这里输入系统名称" style="width:90%" title="系统名称"/></td>
					
						<td style="width:70px;text-align: right;padding-top: 13px;">每页条数:</td>
						<td><input type="number" name="COUNTPAGE" id="COUNTPAGE" value="${pd.COUNTPAGE }" placeholder="这里输入每页条数" style="width:90%" title="每页条数"/></td>
					</tr>
				</table>
				
				<table class="center" style="width:100%" >
					<tr>
						<td style="text-align: center;" colspan="100">
							<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
				</table>
				</form>
			</div>
			<div id="profile2" class="tab-pane"></div>
			
			<div id="profile3" class="tab-pane"></div>
			  
          </div>
	  </div>
  </div><!--/span-->
</div>
		
<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript">
		$(top.hangge());	
		</script>
</body>
</html>
