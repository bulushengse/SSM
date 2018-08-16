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
	<style>
        * {margin: 0; padding: 0; border: 0;}
        body {
            position: absolute;
            width: 100%;
            height: 100%;
        }
        #geoPage, #markPage {
            position: relative;
        }
    </style>
<script type="text/javascript" src="https://3gimg.qq.com/lightmap/components/geolocation/geolocation.min.js "></script>

    </head>
<body>
<div class="container-fluid" id="main-container">

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

 	<div class="span12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
				<h4 class="lighter">定位测试</h4>
			</div>
			<div class="widget-body">
			 <div class="widget-main" >
			 	<b>腾讯地图    PC端根据IP查询地址（有较大误差），移动端根据GPS获取地址（高精度），更多接口详见官网<a href="http://lbs.qq.com/">http://lbs.qq.com/</a></b>
			 	<div style="height:500px;">
			  	<!--  通过 iframe 嵌入前端定位组件，此处没有隐藏定位组件，使用了定位组件的在定位中视觉特效  -->
    			<iframe id="geoPage" width="100%" height="5%" frameborder=0 scrolling="no"
    			src="https://apis.map.qq.com/tools/geolocation?key=IPNBZ-44T64-PZZUL-XBOGR-TUSSE-NSBO5&referer=查询IP位置&effect=zoom"></iframe>
   			 	<!-- 接收到位置信息后 通过 iframe 嵌入位置标注组件 -->
    			<iframe id="markPage" width="100%" height="70%" frameborder=0 scrolling="no" src=""></iframe>
				</div>
				获取更多地图接口?<a href="https://www.baidu.com/s?wd=%E5%9C%B0%E5%9B%BEapi&rsv_spt=1&rsv_iqid=0xfe815b160001a835&issp=1&f=3&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=92495750_hao_pg&rsv_enter=1&oq=%25E8%2585%25BE%25E8%25AE%25AF%25E5%259C%25B0%25E5%259B%25BEapi&rsv_t=fa23BsT8aiiz1sfe%2FrxadBJDx74%2BCghK6F8AH9THAWNvEsE1%2Fn9QZIm3imffYTIb5qmEKjZK&rsv_pq=f7fca02700034fcb&inputT=406&rsv_sug3=110&rsv_sug1=110&rsv_sug7=100&rsv_sug2=0&prefixsug=%25E5%259C%25B0%25E5%259B%25BEapi&rsp=0&rsv_sug4=2860">点击查询</a>
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
		
        var loc;
        var isMapInit = false;
        //监听定位组件的message事件
        window.addEventListener('message', function(event) {
            loc = event.data; // 接收位置信息
            //alert(JSON.stringify(loc))
            console.log('location', loc);
 
            if(loc  && loc.module == 'geolocation') { //定位成功,防止其他应用也会向该页面post信息，需判断module是否为'geolocation'
                var markUrl = 'https://apis.map.qq.com/tools/poimarker' +
                '?marker=coord:' + loc.lat + ',' + loc.lng +
                ';title:我的位置;addr:' + (loc.addr || loc.city) +
                '&key=IPNBZ-44T64-PZZUL-XBOGR-TUSSE-NSBO5&referer=查询IP位置';
                //给位置展示组件赋值
                document.getElementById('markPage').src = markUrl;
            } else { //定位组件在定位失败后，也会触发message, event.data为null
                //alert('定位失败');
            }
 
            /* 另一个使用方式
            if (!isMapInit && !loc) { //首次定位成功，创建地图
                isMapInit = true;
                createMap(event.data);
            } else if (event.data) { //地图已经创建，再收到新的位置信息后更新地图中心点
                updateMapCenter(event.data);
            }
            */
        }, false);
        //为防止定位组件在message事件监听前已经触发定位成功事件，在此处显示请求一次位置信息
        document.getElementById("geoPage").contentWindow.postMessage('getLocation', '*');
 
        //设置6s超时，防止定位组件长时间获取位置信息未响应
        setTimeout(function() {
            if(!loc) {
                //主动与前端定位组件通信（可选），获取粗糙的IP定位结果
            document.getElementById("geoPage")
                .contentWindow.postMessage('getLocation.robust', '*');
            }
        }, 6000); //6s为推荐值，业务调用方可根据自己的需求设置改时间，不建议太短
		</script>
	</body>
</html>