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

 	<div class="span12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
				<h4 class="lighter">物流测试</h4>
			</div>
			<div class="widget-body">
			 <div class="widget-main">
			  	<b>快递100  快递查询  物流查询  国际货运    <a href="https://www.kuaidi100.com/">https://www.kuaidi100.com/</a></b><br/><br/>
			  	
			  	方式一    接口查询<br/>
			  	<table border="1" style="margin-left:200px;">
			 	<tr>
				 	<td>物流快递公司：</td>
				  	<td>
				  		<select id="type">
				  			<option value="" disabled="disabled" selected="selected">请选择物流快递公司</option>
				  			<option value="ems">EMS</option>
				  			<option value="shunfeng">顺丰速运</option>
				  			<option value="yuantong">圆通速递</option>
				  			<option value="zhongtong">中通快递</option>
				  			<option value="shentong">申通快递</option>
				  			<option value="yunda">韵达快递</option>
				  			<option value="huitongkuaidi">百世快递</option>
				  			<option value="tiantian">天天快递</option>
				  			<option value="0" disabled="disabled">更多物流快递公司详见官网</option>
				  		</select>
				  	</td>
			 	</tr>
			 	<tr>
				 	<td>单号：</td>
				 	<td><input type="text" id="postid" value="" title="单号" placeholder="请输入单号"/></td>
			 	</tr>
			 	<tr>
				 	<td colspan="2" align="center">
				 	<a class="btn btn-small btn-success" onclick="query();">查询</a>
			 		<a class="btn btn-small btn-info" onclick="reload();">重置</a><br/>
				 	</td>
			 	</tr>
			 	</table>
			 	<br/>
			 	
			  	方式二    快速集成<br/>
				<iframe name="kuaidi100" src="https://www.kuaidi100.com/frame/app/index2.html" width="800" height="400" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
				<br/><br/><br/>
				获取更多物流查询接口?<a href="https://www.baidu.com/s?wd=%E7%89%A9%E6%B5%81api%E6%8E%A5%E5%8F%A3&rsv_spt=1&rsv_iqid=0xfe815b160001a835&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=92495750_hao_pg&rsv_enter=1&oq=js%25E4%25BD%25BF%25E7%2594%25A8json&rsv_t=6141T1A%2FxaessGvroZV7qcv4E8N7zUUURDWCoidcnTpLdizk%2BzMpL%2BAb7VHTtUhqeSTgrH1K&rsv_pq=92918d0200016d63&inputT=460684&rsv_sug3=90&rsv_sug1=90&rsv_sug7=100&bs=js%E4%BD%BF%E7%94%A8json">点击查询</a>
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
		
		function query(){
			$.ajax({
				type : "POST",
				url :  'tool/queryTransport.do',
				data : {
					type : $("#type").val(),
					postid : $("#postid").val(),
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					$(top.hangge());
					if(data.message=="ok"){
						var array = data.data;
						var str = "";
						for(var i=0; i<array.length-1; i++){		//遍历array 数组时，i为索引
							str +=array[i].time + " " + array[i].context+ "<br/>";
						}
						bootbox.alert(str);
					}else{
						bootbox.alert("没有查询到结果,请确保物流快递公司、单号正确后再次查询！");
					}
				}
			})
		}
		
		function reload() {
			//top.jzts();
			$("#type").val('');
			$("#postid").val('');
			//self.location.reload();
		}
		</script>
	</body>
</html>