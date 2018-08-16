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
<%@ include file="../../system/admin/top.jsp"%> 
<meta charset="utf-8" />
<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/demo.css"/>
<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
<style>
	/**基础css**/
	div{padding:0px; margin:0px;}
	#kehulebie{background-color: #ccf;width:295px;height:621px;}
	#kehu{position: absolute;top:10px; left:294px;}
	#tree{width:280px;height:608px;position: relative;left:1px;background-color:#fff;}
	/**根节点css**/
	.ztree li a.level0 {width:255px; height: 25px; text-align: center; vertical-align: middle; display:block; background-color: #88f; border:1px silver solid;}
	.ztree li a.level0 span { color: white; padding-top:3px; font-size:18px; font-weight: bold; word-spacing: 3px;}
	.ztree li span.button.switch.level0 {visibility:hidden; width: 1px;height: 15px; position: absolute; top: 10px; left: 5px;}
 	#tree_1_ico {display: none; }
 	#tree_1_add {display: none; }
 	#tree_1_edit {display: none; }
 	#tree_1_addCust {display: none; }
 	.ztree li ul.level0 {padding:0; background:none;}
 	/**节点图标css**/
 	.ztree li span.button.pIcon01_ico_open{margin-right:2px; background: url(plugins/zTree/3.5/img/zTreeStandard.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle; background-position: -110px -16px;}
	.ztree li span.button.pIcon01_ico_close{margin-right:2px; background: url(plugins/zTree/3.5/img/zTreeStandard.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle; background-position: -110px 0; }
 	.ztree li span.button.pIcon01_ico_docu{margin-right:2px; background: url(plugins/zTree/3.5/img/zTreeStandard.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle; background-position: -110px -16px; }
 	/**zTree菜单 css**/
 	div#treeMenu {position:fixed; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;z-index:99;}
	div#treeMenu ul li{margin: 1px 0;padding: 0 5px;cursor: pointer;list-style: none outside none;background-color: #DFDFDF;}
 	#query{padding:5px 0px; margin:3px 0px;}
</style>
</head>
<body>
			<!-- 客户类别信息 -->
			<div id="kehulebie" >
				<ul id="tree" class="ztree" ></ul>
				<br/><br/><br/>
			</div>
			<div id="treeMenu">
				<ul>
				<li id="m_add" onclick="addTreeNode();">新增</li>
				<li id="m_querybtn" onclick="showQuery();">检索</li>
				<li id="m_query">
					<input type="text" value="" id="query" class="empty" placeholder="请输入客户类别名"/>
				</li>
				<li id="m_reset" onclick="resetTree();">重置</li>
				</ul>
			</div>		
			<!-- 客户信息 -->
			<div id="kehu">
				<iframe name="kehuFrame" id="kehuFrame" frameborder="0" scrolling="no" src="cust/list.do" style="min-width:1200px;overflow-x:scroll"></iframe>
			</div>
		
		<br/><br/><br/>
		<div style="position:fixed; top:100%; left:100%;" >
			<!--  回到顶部-->
			<a href="#" id="btn-scroll-up"  class="btn btn-small btn-inverse">
				<i class="icon-double-angle-up icon-only"></i>
			</a>
		</div>
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-2.1.4.min.js'>\x3C/script>");</script>
	<script type="text/javascript" src="static/js/jquery-2.1.4.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认框 -->
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.all.min.js?VER=1"></script> 
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
	<script type="text/javascript">
	top.hangge();
	//客户信息iframe 自适应大小
	function changeFrameSize(){
	    var ifm= document.getElementById("kehuFrame"); 
	    ifm.height=document.documentElement.clientHeight+120;
	    ifm.width=document.documentElement.clientWidth-294;
	}
	$(window).resize(function(){
		 changeFrameSize();  
	});
	$("#kehuFrame").ready(function(){
		 changeFrameSize();  
	});
	//----------------------------------
	
	var zTree;
	var rootNode;
	var node;
	
	var setting = {
				data: {
					simpleData: {
						enable: true,
						idKey: "CUST_TYPE_ID",  
					    pIdKey: "FATHER_ID", 
					    rootPId: 0
					},
				    key: {  
			            name : "CUST_TYPE_NAME"   
			        }  
				},
				view: {  
					enable : true,  
					showLine : true,  
					showIcon : true,
					selectedMulti : false,            //不可多选
					fontCss: setFontCss,              //文字样式
					addHoverDom: addHoverDom,         //鼠标移动到节点，显示自定义控件
					removeHoverDom: removeHoverDom,   //鼠标移出节点，显示自定义控件
					dblClickExpand: false	 		 //双击节点时，是否自动展开节点的标识
				},
				edit: {  
					drag:{
						isCopy: false,           
						isMove: false				//禁止拖拽
					},
					enable: true,  
					editNameSelectAll: true,  		//节点编辑名称,input初次显示时,设置 txt内容是否为全选状态。  
					renameTitle:"编辑",
					showRemoveBtn: false,     		  //删除按钮
					showRenameBtn:setRenameBtn        //编辑按钮
				},
				callback : {  
					beforeClick: beforeClick,     
					onDblClick : zTreeOnDblClick,    //双击 查询客户信息  
					onRightClick: rightClick,		 //右键菜单触发后
					beforeRename: beforeRename,	    //点击编辑 触发前
					onRename: zTreeOnRename         //点击编辑 触发后
				}
			};
	
	var nodes = eval('${custType }')
	zTree = $.fn.zTree.init($("#tree"), setting, nodes);
	if(eval('${QX.cha  }')==1){
		rootNode = zTree.getNodeByParam("CUST_TYPE_ID", "0", null);
	    zTree.expandNode(rootNode, true);
	}else{
		$("#tree").append("<span style='position: relative; top:10px; left:30px;'>您无权查看</span>");
		zTree.setting.callback.onRightClick=null;
	}
	//双击节点   客户信息查询
	function zTreeOnDblClick(event, treeId, treeNode){
		if(treeNode.tId == "tree_1")return;
		$(".node_name").css("color","#000");			//节点字体黑色
		$("#tree_1_a .node_name").css("color","#fff");  //根节点字体白色
		$("#kehuFrame").attr({src: 'cust/list.do?CUST_TYPE_ID='+treeNode.CUST_TYPE_ID+'&searchStr='+treeNode.CUST_TYPE_NAME});
		$("#"+treeNode.tId+"_span").css("color","#00f");//双击节点字体蓝色
	}
	//全局字体样式
	function setFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000","font-weight":"bold","font-size":"18px"} : {color:"#333","font-weight":"bold","font-size":"18px"};
	}
	//根节点样式 
	function beforeClick(treeId, treeNode) {
		return treeNode.tId=='tree_1'?false:true;
	}
	//右键 菜单--------------------------------------------------------
	var treeMenu = $("#treeMenu");
	function rightClick(event, treeId, treeNode) {
		if(treeNode) return;
		showRMenu(event.clientX, event.clientY);
	}
	//显示ztree菜单
	function showRMenu(x, y) {
		$("#treeMenu ul").show();
		$("#m_add").show();
		$("#m_querybtn").show();
		$("#m_reset").show();
		$("#m_query").hide();
        treeMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
	//隐藏zTree菜单
	function hideRMenu() {
		if (treeMenu) treeMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event){
		if (!(event.target.id == "treeMenu" || $(event.target).parents("#treeMenu").length>0)) {
			treeMenu.css({"visibility" : "hidden"});
		}
	}
	//重置
	function resetTree(){
		hideRMenu();
		zTree.expandAll(false);
	    zTree.expandNode(rootNode, true);
	    //搜索后的重置
	    var treeObj = $.fn.zTree.getZTreeObj("tree");
	    for( var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = false;
			treeObj.updateNode(nodeList[i]);
		}
	}
	//新增类别
	function addTreeNode(){
		var fnode = zTree.getNodeByTId("tree_1");
		treeNode = zTree.addNodes(fnode, {"FATHER_ID":fnode.CUST_TYPE_ID, "CUST_TYPE_NAME":"请输入名称", "ICONSKIN":"pIcon01", "isNew":true});
		zTree.editName(treeNode[0]);
	}
	//搜索------------------------------------------------------------------------
	var key;
	function showQuery(){
		$("#m_add").hide();
		$("#m_querybtn").hide();
		$("#m_reset").hide();
		$("#m_query").show();
		key = $("#query");
	    key.bind("focus", focusKey)
		.bind("blur", blurKey)
		.bind("propertychange", searchNode)
		.bind("input", searchNode);
		$("#query").val("");
	}
	function focusKey(e) {
		if (key.hasClass("empty")) {
			key.removeClass("empty");
		}
	}
	function blurKey(e) {
		if (key.get(0).value === "") {
			key.addClass("empty");
		}
	}
	var lastValue = "", nodeList = [];
	//修改节点字体样式
	function updateNodes(highlight) {
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		for( var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;
			treeObj.updateNode(nodeList[i]);
		}
	}
	//搜索
	function searchNode(e) {
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			treeObj.expandAll(true);
			var value = $.trim(key.get(0).value);
			if (key.hasClass("empty")) {
				value = "";
			}
			updateNodes(false);
			if (value == "")return;
			nodeList = zTree.getNodesByParamFuzzy("CUST_TYPE_NAME", value);
			updateNodes(true);
	}
	//-------------------------------------------------------------------------
	//----------------------------------------------------------------------------
	
	//鼠标移动到节点,新增类别按钮和新增客户按钮---------------------------------------------
	function addHoverDom(treeId, treeNode){
		if ($("#" + treeNode.tId + "_add").length>0) return;        //按钮重复
		if ($("#" + treeNode.tId + "_addCust").length>0) return;	//按钮重复
		if ($("#" + treeNode.tId + "_del").length>0) return;	    //按钮重复
		if (treeNode.editNameFlag) return;                          //编辑状态
		if(eval('${QX.del  }') == 1){								//权限
			var delStr = "<span id='"+treeNode.tId+"_del' class='button icon_docu' title='删除' onfocus='this.blur();' style='background:url(plugins/zTree/3.5/img/zTreeStandard.png); background-position: -111px -64px; vertical-align: top;'> </span>";
			//删除类别
			if(!treeNode.isParent){
				var obja = $("#" + treeNode.tId + "_a");
				obja.append(delStr);
				var delbtn = $("#" + treeNode.tId + "_del");
				if (delbtn) delbtn.bind("click", function(){
					var url = "<%=basePath%>custtype/confirm.do?CUST_TYPE_ID="+treeNode.CUST_TYPE_ID+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data==1){
							bootbox.alert("该客户类别下有客户,不能删除!");
						}else{
							bootbox.confirm("确定要删除"+treeNode.CUST_TYPE_NAME+"吗?", function(result) {
								if(result) {
									var map = {CUST_TYPE_ID:treeNode.CUST_TYPE_ID};
									$.post('<%=basePath%>custtype/delete.do',
											map,function(msg) {  
										if (msg == "success") {  
											zTree.removeNode(treeNode);
										}else{	
											bootbox.alert("操作删除失败，请稍后重试。");
										}  
									});
								}
							});
						}
					});
				});
			}
		}
		if (eval('${QX.add  }') == 1){  						
			var addStr = "<span id='"+treeNode.tId+"_add' class='button icon_docu' title='新增' onfocus='this.blur();' style='background:url(plugins/zTree/3.5/img/zTreeStandard.png); background-position: -144px 0; vertical-align: top;'> </span>";
			var addCustStr = "<span id='"+treeNode.tId+"_addCust' class='button icon_docu' title='新增客户' onfocus='this.blur();' style='background:url(plugins/zTree/3.5/img/diy/cust.png); vertical-align: top;'> </span>";
			if(treeNode.level <= 2){
				var objSpan = $("#" + treeNode.tId + "_span");
				objSpan.after(addStr);
			}
			var obja = $("#" + treeNode.tId + "_a");
			obja.append(addCustStr);
			//新增类别 
			var addtypebtn = $("#" + treeNode.tId + "_add");
			if (addtypebtn) addtypebtn.bind("click", function(){
				treeNode = zTree.addNodes(treeNode, {"FATHER_ID":treeNode.CUST_TYPE_ID, "CUST_TYPE_NAME":"请输入名称", "ICONSKIN":"pIcon01", "isNew":true});
				zTree.editName(treeNode[0]);
			});
			//新增客户
			var addCustbtn = $("#" + treeNode.tId + "_addCust");
			if (addCustbtn) addCustbtn.bind("click", function(){
				top.jzts();
				var diag = new top.Dialog();
				diag.Drag=true;
				diag.Title ="新增客户";
				diag.URL = '<%=basePath%>cust/goAdd.do?CUST_TYPE_NAME='+treeNode.CUST_TYPE_NAME+'&CUST_TYPE_ID='+treeNode.CUST_TYPE_ID;
				diag.Width = 450;
				diag.Height = 400;
				diag.CancelEvent = function(){  //取消事件
					if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						//跳转查询
						zTreeOnDblClick(null, treeId, treeNode);
					}
					diag.close();
				};
				diag.show();
			})
		}

		
	}
	function removeHoverDom(treeId, treeNode){
		$("#" + treeNode.tId + "_add").unbind().remove();
		$("#" + treeNode.tId + "_addCust").unbind().remove();
		$("#" + treeNode.tId + "_del").unbind().remove();
	}
	//-------------------------------------------------------------------------
	
	//编辑---------------------------------------------------------------------
	//控制编辑按钮
	function setRenameBtn(treeId, treeNode){
		if(eval('${QX.edit  }') == 1){
			return true;
		}else{
			return false;
		}
	}
	//点击编辑  触发前
	var nodeName;
	function beforeRename(treeId, treeNode, newName, isCancel){
		nodeName = treeNode.CUST_TYPE_NAME;
		if(newName.length <= 0){							
			bootbox.alert("输入的名称不能为空值！");
			isCancel= true; 
			zTree.cancelEditName();//取消编辑
			return false;
		}else{
			return true;
		}
	}
	//点击编辑 触发后 
	function zTreeOnRename(event, treeId, treeNode, isCancel){
		if(treeNode.CUST_TYPE_NAME != '请输入名称' && treeNode.CUST_TYPE_NAME == nodeName) return;      //名称不变
		//新增类别 
		if(treeNode.isNew == true) {  
			var map = {CUST_TYPE_NAME:treeNode.CUST_TYPE_NAME,FATHER_ID:treeNode.FATHER_ID};
			$.post('<%=basePath%>custtype/save.do',
					map,function(msg) {  
				var arr = msg.split(",");
				if (arr[0] == "success") {
					treeNode.CUST_TYPE_ID = arr[1];
					zTree.updateNode(treeNode);
				}else{
					zTree.removeNode(treeNode);
					bootbox.alert("操作新增失败，请稍后重试。"); 
				}  
			});
		//修改类别
		}else{
			var map = {CUST_TYPE_ID:treeNode.CUST_TYPE_ID,CUST_TYPE_NAME:treeNode.CUST_TYPE_NAME,FATHER_ID:treeNode.FATHER_ID};
			$.post('<%=basePath%>custtype/edit.do',
					map,function(msg) {  
				if (msg == "success") {
				}else{
					isCancel= true;
					treeNode.CUST_TYPE_NAME = nodeName;
					zTree.updateNode(treeNode);
					bootbox.alert("操作编辑失败，请稍后重试。"); 
				}  
			});
		};                    
		
	}
	//---------------------------------------------------------------------------------

	</script>
</body>
</html>