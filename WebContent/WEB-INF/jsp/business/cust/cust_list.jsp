<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en"  >
	<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	<style type="text/css">
		#fenyeNumber{display:none;}
	</style>
	</head>
<body>
		
<div class="container-fluid" id="main-container">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="cust/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="form-field-1" type="text" name="searchStr" value="${pd. searchStr  }" placeholder="客户名称/客户类别/联系人" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<c:if test="${QX.cha == 1 }">
						<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
						<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>
				</tr>
			</table>
		
			<!-- <div style="overflow-x:scroll;min-height:400px;"> -->
			<table id="table_report" class="table table-striped table-bordered table-hover" style="min-width:1100px;" >
				
				<thead>
					<tr>
						<th class="center" width="30px">操作</th>
						<th class="center" width="30px">序号</th>
						<th class="center" width="200px">客户名称</th>
						<th class="center" width="150px">客户类别</th>
						<th class="center" width="80px">联系人</th>
						<th class="center" width="100px">联系方式</th>
						<th class="center" width="320px">地址</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td style="vertical-align: middle;" class="center" >
									<div class='visible-phone visible-desktop visible-tablet btn-group'>
									<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
									</c:if>
									<c:if test="${QX.edit == 1 || QX.del == 1 }">
  									<c:if test="${icount > 6}">		
										<c:if test="${vs.index < icount-3}">
										<div class="inline position-relative">
										
										</c:if>
										<c:if test="${vs.index >= icount-3}">
										<div class="dropup">
										</c:if>		
									</c:if>	
  									<c:if test="${icount <=6}">		
										<div class="inline position-relative">		
									</c:if>
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu  dropdown-icon-only dropdown-ligh dropdown-caret ">
											<c:if test="${QX.edit == 1 }">
											<li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.CUST_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i> </span> </a></li>
											</c:if>
											<c:if test="${QX.del == 1 }">
											<li><a style="cursor:pointer;" title="删除" onclick="del('${var.CUST_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i> </span> </a></li>
											</c:if>
										</ul>
										</div>
										</c:if>
									</div>
								</td>
								<td class='center' style="vertical-align: middle;">${vs.index+1}</td>
								<td style="vertical-align: middle;">${var.CUST_NAME}</td>
								<td style="vertical-align: middle;">${var.CUST_TYPE_NAME}</td>
								<td style="vertical-align: middle;">${var.CUST_CONTECT}</td>
								<td style="vertical-align: middle;">${var.CUST_PHONE}</td>
								<td style="vertical-align: middle;">${var.CUST_ADDR}</td>
							</tr>
						
						</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"><div class="pagination" style="float:left;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
		
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
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认框 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		//删除
		function del(Id){
			var url = "<%=basePath%>cust/confirm.do?CUST_ID="+Id+"&tm="+new Date().getTime();
			$.get(url,function(data){
				if(data==1){
					bootbox.confirm("确定要删除吗?", function(result) {
						if(result) {
							top.jzts();
							var url = "<%=basePath%>cust/delete.do?CUST_ID="+Id+"&tm="+new Date().getTime();
							$.get(url,function(data){
								nextPage('${page.currentPage}');
							});
						}
					});
				}else{
					bootbox.alert("该客户已被使用,不能删除!");
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>cust/goEdit.do?CUST_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 400;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		//导出excel
		function toExcel(){
			document.Form.action='<%=basePath%>cust/excel.do';
			document.Form.submit();
			document.Form.action='<%=basePath%>cust/list.do';
		}
		</script>
		
	</body>
</html>

