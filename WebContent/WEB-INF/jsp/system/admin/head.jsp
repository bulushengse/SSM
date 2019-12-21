<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
		   		
			<a class="brand">
			<i class="icon-leaf" style="width:30px;height:26px;padding-top:6px;vertical-align:middle;"></i><span style="font-size:30px;vertical-align:middle;">${pd.SYSNAME}</span>
			</a>
			  
			  <ul class="nav ace-nav pull-right">
			  
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="javascript:;" data-toggle="dropdown">
							<img alt="" src="static/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info" style="padding:10px 0 0 0; ">
							</span>
							<i class="icon-caret-down" ></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a onclick="editUserH();" style="cursor:pointer;"><i class="icon-user"></i> 修改资料</a></li>
							<li id="systemset"><a onclick="editSys();" style="cursor:pointer;"><i class="icon-cog"></i> 系统设置</a></li>
							<li id="menuset"><a onclick="editmenu();" style="cursor:pointer;"><i class="icon-th"></i> 菜单管理</a></li>
							<li id="productCode"><a onclick="productCode();" style="cursor:pointer;"><i class="icon-cogs"></i> 代码生成</a></li> 
							<li id="webMonitor"><a onclick="webMonitor();" style="cursor:pointer;"><i class="icon-exclamation-sign"></i> web监控</a></li> 
							<li class="divider"></li>
							<li><a href="logout_logout"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
	
	
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/head.js"></script>
