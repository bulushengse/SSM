$(function() {
	if (typeof ($.cookie('menusf')) == "undefined") {
		$("#menusf").attr("checked", true);
		$("#sidebar").attr("class", "menu-min");
	} else {
		$("#sidebar").attr("class", "");
	}
});

$(function() {
	if (window.history && window.history.pushState){
		$(window).on('popstate', function(){
			window.history.pushState('forward', '后台通用管理系统','main/index');
			window.history.forward(1);
		});
		window.history.pushState('forward', '后台通用管理系统','main/index'); //在IE中必须得有这两行
		window.history.forward(1);
	}
});
	
		function cmainFrame(){
			var hmain = document.getElementById("mainFrame");
			var bheight = document.documentElement.clientHeight;
			hmain .style.width = '100%';
			hmain .style.height = (bheight  - 51) + 'px';
			var bkbgjz = document.getElementById("bkbgjz");
			bkbgjz .style.height = (bheight  - 41) + 'px';
			
		}
		
		cmainFrame();
		window.onresize=function(){  
			cmainFrame();
		};
		jzts();
		
		//页面长时间未操作自动退出------------------------------------
		var lastTime = new Date().getTime();
		var currentTime = new Date().getTime();
		var timeOut = 15 * 60 * 1000; //设置超时时间：15分

		$(function(){
		    /* 鼠标移动事件 */
		    $(document).mouseover(function(){
		        lastTime = new Date().getTime(); //更新操作时间
		    });
		});

		function testTime(){
		    currentTime = new Date().getTime(); 	//更新当前时间
		    if(currentTime-lastTime >= timeOut){ 	//判断是否超时
		    	alert("您长时间未操作，请求超时！请重新登录...");
		    	location.href="login_toLogin";
		        //console.log("超时");
		    }
		}
		/* 定时器  间隔1分检测是否长时间未操作页面  */
		window.setInterval(testTime, 60000);
		
		