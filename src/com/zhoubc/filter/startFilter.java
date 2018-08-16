package com.zhoubc.filter;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.java_websocket.WebSocketImpl;

import com.zhoubc.controller.BaseController;
import com.zhoubc.plugin.websocket.OnlineChatServer;
import com.zhoubc.util.Const;
import com.zhoubc.util.Tools;

/**
 * 类名称：startFilter.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
public class startFilter extends BaseController implements Filter {

	/**
	 * 初始化
	 */
	@Override
	public void init(FilterConfig fc) throws ServletException {
		this.startWebsocketOnline();
	}

	/**
	 * 启动在线管理服务
	 */
	public void startWebsocketOnline() {
		WebSocketImpl.DEBUG = false;
		OnlineChatServer s = null;
		try {
			String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);// 读取WEBSOCKET配置,获取端口配置
			if (null != strWEBSOCKET && !"".equals(strWEBSOCKET)) {
				String strIW[] = strWEBSOCKET.split(",gzgtx,");
				if (strIW.length == 4) {
					s = new OnlineChatServer(Integer.parseInt(strIW[3]));
					s.start();
				}
			}
			//System.out.println( "websocket服务器启动,端口" + s.getPort() );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

}
