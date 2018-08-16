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
<body>
<div class="container-fluid" id="main-container">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

 	<div class="span12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
				<h4 class="lighter">发送短信测试</h4>
			</div>
			<div class="widget-body">
			 <div class="widget-main">
			 	<b>腾讯云短信平台</b><br/>
			 	<span class="text-warning orange">短信内容,例如通知类短信模版"工单派送：工单号｛1｝，客户名称｛2｝，预计到达时间｛3｝，备注｛4｝"</span><br/><br/>
			 	<table>
			 	<tr>
				 	<td>sdkappid：</td>
				  	<td><input type="text" id="sdkappId" value="" title="sdkappid(腾讯云申请码)" placeholder="腾讯云申请码"/></td>
			 	</tr>
			 	<tr>
				 	<td>sig：</td>
				 	<td><input type="text" id="sig" value="" title="sig(App凭证)" placeholder="腾讯云App凭证"/></td>
			 	</tr>
			 	<tr>
				 	<td>短信模版ID：</td>
				 	<td><input type="text" id="contentId" value="" title="短信模版ID" placeholder="腾讯云短信模版ID"/></td>
			 	</tr>
			 	<tr>
			 		<td>国家码：</td>
			 		<td><input type="text" id="countryCode" value="" title="国家码" placeholder="详细上网查，国内86"/></td>
			 	</tr>
			 	<tr>
			 		<td>手机号：</td>
			 		<td><input type="text" id="mobile" value="" title="手机号" placeholder="多个手机号请用(;)分号隔开"/></td>
			 	</tr>
			 	<tr>
				 	<td>参数1：</td>
				 	<td><input type="text" id="param1" value="" title="参数1" placeholder="这里输入参数1"/></td>
			 	</tr>
			 	<tr>
				 	<td>参数2：</td>
				 	<td><input type="text" id="param2" value="" title="参数2" placeholder="这里输入参数2"/></td>
			 	</tr>
			 	<tr>
				 	<td>参数3：</td>
				 	<td><input type="text" id="param3" value="" title="参数3" placeholder="这里输入参数3"/></td>
			 	</tr>
			 	<tr>
				 	<td>参数4：</td>
				 	<td><input type="text" id="param4" value="" title="参数4" placeholder="这里输入参数4"/></td>
				</tr>
			 	</table>
			 	<br/>
			 	<a class="btn btn-small btn-success" onclick="query();">查询</a>
			 	<a class="btn btn-small btn-info" onclick="reload();">重置</a>
			 	<br/><br/><br/>
			 	获取更多短信平台?<a href="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=92495750_hao_pg&wd=%E7%9F%AD%E4%BF%A1%E5%B9%B3%E5%8F%B0&oq=%25E7%2599%25BE%25E5%25BA%25A6%25E4%25BA%2591&rsv_pq=da0fdabc00012682&rsv_t=f46eWvgWjn3zanLXvbHGA3RhbJGL%2F54IlwZLPuebyHQaBN5cmqfXuBxSmyCYo9JKuMagWyFk&rqlang=cn&rsv_enter=1&inputT=28338&rsv_sug3=74&rsv_sug1=104&rsv_sug7=100&bs=%E7%99%BE%E5%BA%A6%E4%BA%91">点击查询</a>
				<hr>
			 </div><!--/widget-main-->
			</div><!--/widget-body-->
		</div>
	</div>
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认框 -->
		<script type="text/javascript">
		$(top.hangge());
		
		//发送短信
		function sendSMS(){
			$.ajax({
				type : "POST",
				url :  'tool/SendSMS.do',
				data : {
					sdkappId : $("#sdkappId").val(),
					sig : $("#sig").val(),
					contentId : $("#contentId").val(),
					countryCode : $("#countryCode").val(),
					mobile : $("#mobile").val(),
					param1 : $("#param1").val(),
					param2 : $("#param2").val(),
					param3 : $("#param3").val(),
					param4 : $("#param4").val()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					$(top.hangge());
					if("success" == data.msg){
						bootbox.alert("发送成功！");
					}else{
						bootbox.alert("发送失败！");
					}
				}
			})
		}
		
		//重置
		function reload() {
			top.jzts();
			$("#phone").val('');
			$("#content").val('');
			self.location.reload();
		}
		</script>
	</body>
</html>

