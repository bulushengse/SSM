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

		
<div id="page-content">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
	<div>
	<div id="breadcrumbs">
		<table class="center" style="min-width:800px;">
			<tr height="35">
				<c:if test="${QX.add == 1 }">
				<td style="width:69px;"><a href="javascript:addRole();" class="btn btn-small btn-success">新增</a></td>
				</c:if>
					<c:choose>
					<c:when test="${not empty roleList}">
					<c:forEach items="${roleList}" var="role" varStatus="vs">
						<c:if test="${role.ROLE_ID != '1' }">
							<td style="width:100px;" class="center" <c:choose><c:when test="${pd.ROLE_ID == role.ROLE_ID}">bgcolor="#FFC926" onMouseOut="javascript:this.bgColor='#FFC926';"</c:when><c:otherwise>bgcolor="#E5E5E5" onMouseOut="javascript:this.bgColor='#E5E5E5';"</c:otherwise></c:choose>  onMouseMove="javascript:this.bgColor='#FFC926';" >
								<a href="role/listRoles.do?ROLE_ID=${role.ROLE_ID }" style="text-decoration:none; display:block;"><li class=" icon-group"></li>&nbsp;<font color="#666666">${role.ROLE_NAME }</font></a>
							</td>
							<td style="width:5px;"></td>
						</c:if>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan="100">没有相关数据</td>
						</tr>
					</c:otherwise>
					</c:choose>
				<td></td>
			</tr>
		</table>
	</div>	
		<table>
			<tr height="7px;"><td colspan="100"></td></tr>
			<tr>
			<td><font color="#808080">本组：</font></td>
			<td>
			<c:if test="${QX.edit == 1 }">
			<a class="btn btn-mini btn-info" onclick="editRole('${pd.ROLE_ID }');">修改组名称<i class="icon-arrow-right  icon-on-right"></i></a>
			</c:if>
				<c:choose>
					<c:when test="${pd.ROLE_ID == '99'}">
					</c:when>
					<c:otherwise>
					<c:if test="${QX.edit == 1 }">
					<a class="btn btn-mini btn-purple" onclick="editRights('${pd.ROLE_ID }');"><i class="icon-pencil"></i>组菜单权限</a>
					</c:if>
					</c:otherwise>
				</c:choose>
				<c:choose> 
					<c:when test="${pd.ROLE_ID == '2'}"><%--超级管理员组没有删除 --%>
					</c:when>
					<c:otherwise>
					 <c:if test="${QX.del == 1 }">
					 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${pd.ROLE_ID }','z','${pd.ROLE_NAME }');"><i class='icon-trash'></i></a>
					 </c:if>
					</c:otherwise>
				</c:choose>
			</td>
			</tr>
			<tr height="7px;"><td colspan="100"></td></tr>
		</table>
		
		
	</div>
	<table id="table_report" class="table table-striped table-bordered table-hover" style="min-width:800px;">
		<thead>
		<tr>
			<th class="center" width="50px">序号</th>
			<th class="center" >角色</th>
			<c:if test="${QX.edit == 1 }">
			<th class="center" width="30px">增</th>
			<th class="center" width="30px">删</th>
			<th class="center" width="30px">改</th>
			<th class="center" width="30px">查</th>
			</c:if>
			<th style="width:155px;" class="center">操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty roleList_z}">
				<c:if test="${QX.cha == 1 }">
				<c:forEach items="${roleList_z}" var="var" varStatus="vs">
				<tr>
				<td class='center' style="width:30px;">${vs.index+1}</td>
				<td id="ROLE_NAMETd${var.ROLE_ID }">${var.ROLE_NAME }</td>
				<c:if test="${QX.edit == 1 }">
				<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','add_qx');" class="btn btn-warning btn-mini" title="分配新增权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
				<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','del_qx');" class="btn btn-warning btn-mini" title="分配删除权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
				<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','edit_qx');" class="btn btn-warning btn-mini" title="分配修改权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
				<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','cha_qx');" class="btn btn-warning btn-mini" title="分配查看权限"><i class="icon-wrench icon-2x icon-only"></i></a></td>
				</c:if>
				<td style="width:200px;">
				
				<c:if test="${QX.edit != 1 && QX.del != 1 }">
				<div style="width:100%;" class="center">
				<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
				</div>
				</c:if>
				
				<c:if test="${QX.edit == 1 }">
				<a class="btn btn-mini btn-purple" onclick="editRights('${var.ROLE_ID }');"><i class="icon-pencil"></i>菜单权限</a>
				<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${var.ROLE_ID }');"><i class='icon-edit'></i></a>
				</c:if>
				<c:choose> 
					<c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}">
					</c:when>
					<c:otherwise>
					 <c:if test="${QX.del == 1 }">
					 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');"><i class='icon-trash'></i></a>
					 </c:if>
					</c:otherwise>
				</c:choose>
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
				<tr>
				<td colspan="100" class="center" >没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
			
		<div class="page-header position-relative">
		<c:if test="${QX.add == 1 }">
		<table style="min-width:800px;">
			<tr>
				<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="addRole2('${pd.ROLE_ID }');">新增</a></td>
			</tr>
		</table>
		</c:if>
		</div>
	</div>
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
	<div style="position:fixed; top:100%; left:100%;" >	
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
	</div>	
		<!-- 引入 -->
		<script src="static/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		
		<script type="text/javascript">
		
		top.hangge();
		
		//新增组
		function addRole(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增组";
			 diag.URL = '<%=basePath%>role/toAdd.do?parent_id=0';
			 diag.Width = 222;
			 diag.Height = 90;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					top.jzts();
					setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//新增角色
		function addRole2(pid){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增角色";
			 diag.URL = '<%=basePath%>role/toAdd.do?parent_id='+pid;
			 diag.Width = 222;
			 diag.Height = 90;
			 diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					top.jzts();
					setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function editRole(ROLE_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>role/toEdit.do?ROLE_ID='+ROLE_ID;
			 diag.Width = 222;
			 diag.Height = 90;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					top.jzts();
					setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delRole(ROLE_ID,msg,ROLE_NAME){
			bootbox.confirm("确定要删除["+ROLE_NAME+"]吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>role/delete.do?ROLE_ID="+ROLE_ID+"&guid="+new Date().getTime();
					top.jzts();
					$.get(url,function(data){
						if("success" == data.result){
							if(msg == 'c'){
								top.jzts();
								document.location.reload();
							}else{
								top.jzts();
								window.location.href="role/listRoles.do";
							}
							
						}else if("false" == data.result){
							top.hangge();
							bootbox.dialog("删除失败，请先删除此部门下的职位!", 
									[
									  {
										"label" : "关闭",
										"class" : "btn-small btn-success",
										"callback": function() {
											//Example.show("great success");
											}
										}]
								);
						}else if("false2" == data.result){
							top.hangge();
							bootbox.dialog("删除失败，请先删除此职位下的用户!", 
									[
									  {
										"label" : "关闭",
										"class" : "btn-small btn-success",
										"callback": function() {
											//Example.show("great success");
											}
										}]
								);
						}
					});
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		//菜单权限
		function editRights(ROLE_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag = true;
			 diag.Title = "菜单权限";
			 diag.URL = '<%=basePath%>role/auth.do?ROLE_ID='+ROLE_ID;
			 diag.Width = 280;
			 diag.Height = 370;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//按钮权限
		function roleButton(ROLE_ID,msg){
			top.jzts();
			if(msg == 'add_qx'){
				var Title = "授权新增权限";
			}else if(msg == 'del_qx'){
				Title = "授权删除权限";
			}else if(msg == 'edit_qx'){
				Title = "授权修改权限";
			}else if(msg == 'cha_qx'){
				Title = "授权查看权限";
			}
			
			 var diag = new top.Dialog();
			 diag.Drag = true;
			 diag.Title = Title;
			 diag.URL = '<%=basePath%>role/button.do?ROLE_ID='+ROLE_ID+'&msg='+msg;
			 diag.Width = 220;
			 diag.Height = 350;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		</script>
	</body>
</html>

