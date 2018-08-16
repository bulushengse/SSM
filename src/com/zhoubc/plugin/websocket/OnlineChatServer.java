package com.zhoubc.plugin.websocket;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import net.sf.json.JSONObject;

/**
 * 类名称：OnlineChatServer.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
public class OnlineChatServer extends WebSocketServer {

	public OnlineChatServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public OnlineChatServer(InetSocketAddress address) {
		super(address);
	}

	/**
	 * 触发连接事件
	 */
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		// this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
		// System.out.println("===" + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	/**
	 * 触发关闭事件
	 */
	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		userLeave(conn);
	}

	/**
	 * 客户端发送消息到服务器时触发事件
	 */
	@Override
	public void onMessage(WebSocket conn, String message) {
		message = message.toString();
		if (null != message && message.startsWith("[join]")) {
			this.userjoin(message.replaceFirst("\\[join\\]", ""), conn);
			//this.userjoin2(message.replaceFirst("\\[join\\]", ""), conn);
		}
		if (null != message && message.startsWith("[goOut]")) {
			this.goOut(message.replaceFirst("\\[goOut\\]", ""));
		} else if (null != message && message.startsWith("[leave]")) {
			this.userLeave(conn);
		} else if (null != message && message.startsWith("[count]")) {
			this.getUserCount(conn);
		} else if (null != message && message.startsWith("[getUserlist]")) {
			this.getUserList(conn);
		}
	}

	public void onFragment(WebSocket conn, Framedata fragment) {}

	/**
	 * 触发异常事件
	 */
	@Override
	public void onError(WebSocket conn, Exception ex) {
		// ex.printStackTrace();
		if (conn != null) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	/**
	 * 用户加入处理1   踢出其它终端登录用户（当前选择的处理方法）
	 * 
	 * @param user
	 */
	public void userjoin(String user, WebSocket conn) {
		if (null == OnlineChatServerPool.getWebSocketByUser(user)) { // 判断用户是否在其它终端登录
			OnlineChatServerPool.addUser(user, conn); // 向连接池添加当前的连接对象
		} else {
			goOut(user);
			OnlineChatServerPool.addUser(user, conn); // 向连接池添加当前的连接对象
		}
	}
	
	/**
	 * 用户加入处理2   踢出当前登录用户
	 * 
	 * @param user
	 */
	public void userjoin2(String user, WebSocket conn) {
		if (null == OnlineChatServerPool.getWebSocketByUser(user)) { // 判断用户是否在其它终端登录
			OnlineChatServerPool.addUser(user, conn); // 向连接池添加当前的连接对象
		} else {
			goOut(conn, "goOut");
			OnlineChatServerPool.addUser(user, conn); // 向连接池添加当前的连接对象
		}
	}

	/**
	 * 强制某用户下线
	 * 
	 * @param user
	 */
	public void goOut(String user) {
		this.goOut(OnlineChatServerPool.getWebSocketByUser(user), "thegoout2");
	}

	/**
	 * 强制用户下线
	 * 
	 * @param conn
	 */
	public void goOut(WebSocket conn, String type) {
		JSONObject result = new JSONObject();
		result.element("type", type);
		result.element("msg", "goOut");
		OnlineChatServerPool.sendMessageToUser(conn, result.toString());
	}

	/**
	 * 用户下线处理
	 * 
	 * @param user
	 */
	public void userLeave(WebSocket conn) {
		OnlineChatServerPool.removeUser(conn); // 在连接池中移除连接
	}
	
	/**
	 * 获取在线总数
	 * 
	 * @param user
	 */
	public void getUserCount(WebSocket conn) {
		JSONObject result = new JSONObject();
		result.element("type", "count");
		result.element("msg", OnlineChatServerPool.getUserCount());
		OnlineChatServerPool.sendMessageToUser(conn, result.toString());
	}
	
	/**
	 * 获取在线用户列表
	 * 
	 * @param user
	 */
	public void getUserList(WebSocket conn) {
		JSONObject result = new JSONObject();
		result.element("type", "userlist");
		result.element("list", OnlineChatServerPool.getOnlineUser());
		OnlineChatServerPool.sendMessageToUser(conn, result.toString());
	}

}
