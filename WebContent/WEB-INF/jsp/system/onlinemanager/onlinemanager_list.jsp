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
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
	</head> 
<body>
<div class="container-fluid" id="main-container">
	<div id="page-content" class="clearfix">					
  		<div class="row-fluid">

		<div class="row-fluid">
			<table>
				<tr bgcolor="#E0E0E0">
					<td>在线人数：</td>
					<td style="width:39px;" id="onlineCount">0</td>
				</tr>
			</table>
	
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class='center' style="width:50px;">序号</th>
						<th>用户名</th>
						<th class="center" style="width: 100px;">操作</th>
					</tr>
				</thead>
				
				<c:if test="${QX.cha == 1 }">
					<tbody id="userlist"></tbody>
				</c:if>
				
				<c:if test="${QX.cha == 0 }">
					<tbody>
						<tr>
							<td colspan="10" class="center">您无权查看</td>
						</tr>
					</tbody>
				</c:if>
			</table>
		</div>
 
 		<div class="page-header position-relative">
			<table style="width:100%;">
				<tr>
					<td style="vertical-align:top;">
					</td>
				</tr>
			</table>
		</div>
		<!-- PAGE CONTENT ENDS HERE -->
		</div><!--/row-->
	</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		$(top.hangge());
		</script>
		
		<c:if test="${QX.cha == 1 }">
		<script type="text/javascript">
		$(function(){
			top.getUserlist();
			$("#onlineCount").html(top.getUserCount());
			$("#onlineCount").tips({
				side:3,
	            msg:'正在计算,请稍等',
	            bg:'#AE81FF',
	            time:5
	        });
			setInterval("updateUsercount()",5000);
		});
		function updateUsercount(){
			$("#onlineCount").html(top.getUserCount());
			$("#userlist").html('');
			 $.each(top.getUserlist(), function(i, user){
				 $("#userlist").append(
					'<tr>'+	 
						'<td class="center">'+(i+1)+'</td>'+
						'<td><a>'+user+'</a></td>'+
						'<td class="center">'+
							'<c:if test="${QX.del == 1 }">'+
							'<button class="btn btn-mini btn-danger" onclick="goOutTUser(\''+user+'\')">强制下线</button>'+
							'</c:if>'+
						'</td>'+
					'</tr>'
				 );
			 });
		}
		
		//强制某用户下线
		function goOutTUser(theuser){
			bootbox.confirm("确定要强制用户["+theuser+"]下线吗?", function(result) {
				if(result) {
					top.goOutUser(theuser);
					updateUsercount();
				}
			});
		}
		</script>
		</c:if>
	</body>
</html>

