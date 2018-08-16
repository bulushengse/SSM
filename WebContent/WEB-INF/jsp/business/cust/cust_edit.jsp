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
		<link rel="stylesheet" href="static/css/chosen.css" /><!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	//保存
	function save(){
		if($("#CUST_NAME").val()==""){
			$("#CUST_NAME").tips({
				side:3,
	            msg:'请输入客户名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUST_NAME").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="cust/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CUST_ID" id="CUST_ID" value="${pd.CUST_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:80px;text-align: right;padding-top: 13px;"><span style="color:#f00;">*</span>客户类别:</td>
				<td>
					<input type="text" name="CUST_TYPE_NAME" id="CUST_TYPE_NAME" value="${pd.CUST_TYPE_NAME}" maxlength="20" placeholder="这里输入客户类别名称" title="客户类别" readonly/>
					<input type="hidden" name="CUST_TYPE_ID" id="CUST_TYPE_ID" value="${pd.CUST_TYPE_ID}"/>
				</td>
			</tr>
			<tr>
				<td style="width:80px;text-align: right;padding-top: 13px;"><span style="color:#f00;">*</span>客户名称:</td>
				<td><input type="text" name="CUST_NAME" id="CUST_NAME" value="${pd.CUST_NAME}" maxlength="64" placeholder="这里输入客户名称" title="客户名称"/></td>
			</tr>
			<tr>
				<td style="width:80px;text-align: right;padding-top: 13px;">地址:</td>
				<td><input type="text" name="CUST_ADDR" id="CUST_ADDR" value="${pd.CUST_ADDR}" maxlength="64" placeholder="这里输入地址" title="地址"/></td>
			</tr>
			<tr>
				<td style="width:80px;text-align: right;padding-top: 13px;">联系人:</td>
				<td><input type="text" name="CUST_CONTECT" id="CUST_CONTECT" value="${pd.CUST_CONTECT}" maxlength="10" placeholder="这里输入联系人" title="联系人"/></td>
			</tr>
			<tr>
				<td style="width:80px;text-align: right;padding-top: 13px;">联系方式:</td>
				<td><input type="text" name="CUST_PHONE" id="CUST_PHONE" value="${pd.CUST_PHONE}" maxlength="64" placeholder="这里输入联系人电话" title="联系人电话"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>