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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	
	<%--查看图片插件 --%>
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
    <script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
	<%--查看图片插件 --%>
	
	</head>
<body>
<div class="container-fluid" id="main-container">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="pictures/list.do" method="post" name="Form" id="Form">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="form-field-1" type="text" name="keyword" value="${pd.keyword}" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<c:if test="${QX.cha == 1 }">
						<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
						<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>
				</tr>
			</table>
			
			<!-- 检索  -->
			<table id="table_report" class="table table-striped table-bordered table-hover" style="min-width:1242px;">
				<thead>
					<tr>
						<th class="center" width="30px" onclick="selectAll()">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th width="30px">序号</th>
						<th width="130px">图片</th>
						<th width="130px">标题</th>
						<th width="110px">ID</th>
						<th width="130px">创建时间</th>
						<th width="50px">属于</th>
						<th>备注</th>
						<th class="center" width="65px">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center'>
									<label><input type='checkbox' name='ids' value="${var.PICTURES_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center'>${vs.index+1}</td>
									<td>
										<c:if test="${var.PATH == '#'}">
										<span>无图片</span>
										</c:if>
										<c:if test="${var.PATH != '#'}">
										<a href="<%=basePath%>uploadFiles/uploadImgs/${var.PATH}" title="${var.TITLE}" class="bwGal"><img src="<%=basePath%>uploadFiles/uploadImgs/${var.PATH}" alt="${var.TITLE}" width="100"></a>
										</c:if>
									</td>
									<td>${var.TITLE}</td>
									<td>${var.PICTURES_ID}</td>
									<td>${var.CREATETIME}</td>
									<td>${var.MASTER_ID}</td>
									<td>${var.BZ}</td>
								<td>
									<div class='visible-phone visible-desktop visible-tabletbtn-group'>
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
											<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<c:if test="${QX.edit == 1 }">
											<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.PICTURES_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><i class="icon-edit"></i></a>
										</c:if>
										<c:if test="${QX.del == 1 }">
											<a class='btn btn-mini btn-danger' title="删除" onclick="del('${var.TITLE}','${var.PICTURES_ID}','${var.PATH}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>
										</c:if>
									</div>
								</td>
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
					<td style="vertical-align:top;">
						<c:if test="${QX.add == 1 }">
						<a class="btn btn-small btn-success" onclick="add();">新增</a>
						</c:if>
						<c:if test="${QX.del == 1 }">
						<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
						</c:if>
					</td>
					<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
		<script type="text/javascript" src="static/js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认框 -->
		
		<script type="text/javascript">
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>pictures/goAdd.do';
			 diag.Width = 800;
			 diag.Height = 490;
			 diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('ccc').value == '1'){
					 nextPage('${page.currentPage}');
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(title,Id,PATH){
			bootbox.confirm("确定要删除["+title+"]吗?", function(result) {
				if(result){ 
					top.jzts();
					var url = "<%=basePath%>pictures/delete.do?PICTURES_ID="+Id+"&PATH="+PATH+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>pictures/goEdit.do?PICTURES_ID='+Id;
			 diag.Width = 600;
			 diag.Height = 465;
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
		
		//全选 （是/否）
		function selectAll(){
			 var checklist = document.getElementsByName ("ids");
			   if(document.getElementById("zcheckbox").checked){
			   for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			 }
		}

		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result){ 
						var str = '';
						for(var i=0;i < document.getElementsByName('ids').length;i++)
						{
							  if(document.getElementsByName('ids')[i].checked){
							  	if(str=='') str += document.getElementsByName('ids')[i].value;
							  	else str += ',' + document.getElementsByName('ids')[i].value;
							  }
						}
						if(str==''){
							bootbox.alert("您没有选择任何内容!"); 
							return;
						}else{
							if(msg == '确定要删除选中的数据吗?'){
								top.jzts();
								$.ajax({
									type: "POST",
									url: '<%=basePath%>pictures/deleteAll.do?tm='+new Date().getTime(),
							    	data: {DATA_IDS:str},
									dataType:'json',
									//beforeSend: validateData,
									cache: false,
									success: function(data){
										 $.each(data.list, function(i, list){
												nextPage('${page.currentPage}');
										 });
									}
								});
							}
						}
				}
			});
		}
		
		//导出excel
		function toExcel(){
			var keyword = $("#form-field-1").val();
			window.location.href='<%=basePath%>pictures/excel.do?keyword='+keyword;
		}
		</script>
	</body>
</html>

