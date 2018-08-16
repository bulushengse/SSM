package com.zhoubc.controller.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.zhoubc.controller.BaseController;
import com.zhoubc.service.system.UserService;
import com.zhoubc.util.AppUtil;
import com.zhoubc.util.Const;
import com.zhoubc.util.PageData;
import com.zhoubc.util.Tools;
import com.zhoubc.util.HttpUtil;
import com.zhoubc.util.mail.SimpleMailSender;


/**
 * 类名称：ToolController.java
 * 
 * @zbc  创建时间：2018年4月28日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/tool")
public class ToolController extends BaseController {
	
	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 去接口测试页面
	 */
	@RequestMapping(value = "/interfaceTest")
	public ModelAndView interfaceTest() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/interfaceTest");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 接口内部请求
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/severTest")
	@ResponseBody
	public Object severTest() {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "success", rTime = "";
		StringBuilder builder = new StringBuilder();
		try {
			long startTime = System.currentTimeMillis(); // 请求起始时间_毫秒
			URL url = new URL(pd.getString("serverUrl"));	
			HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(pd.getString("requestMethod")); // 请求类型 POST or GET
			connection.connect();
			//int state = connection.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			long endTime = System.currentTimeMillis(); // 请求结束时间_毫秒
			String temp = "";
			while ((temp = in.readLine()) != null) {
				builder.append(temp);
			}
			rTime = String.valueOf(endTime - startTime);
		} catch (Exception e) {
			errInfo = "error";
		} 
		map.put("errInfo", errInfo); 			// 状态信息
		map.put("result", builder.toString()); 	// 返回结果
		map.put("rTime", rTime); 				// 服务器请求时间 毫秒
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 去打印测试页面
	 */
	@RequestMapping(value = "/printTest")
	public ModelAndView printTest() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/printTest");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 打印预览页面
	 */
	@RequestMapping(value = "/printPage")
	public ModelAndView printPage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/printPage");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 去发送邮件测试页面
	 */
	@RequestMapping(value = "/SendEmailTest")
	public ModelAndView SendEmailTest() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/emailTest");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 发送电子邮件
	 */
	@RequestMapping(value = "/sendEmail")
	@ResponseBody
	public Object sendEmail() {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "ok"; 	// 发送状态
		int count = 0; 		// 统计发送成功条数
		int zcount = 0; 	// 理论条数

		String strEMAIL = Tools.readTxtFile(Const.EMAIL); // 读取邮件配置

		List<PageData> pdList = new ArrayList<PageData>();

		String toEMAIL = pd.getString("EMAIL"); 	// 对方邮箱
		String TITLE = pd.getString("TITLE"); 		// 标题
		String CONTENT = pd.getString("CONTENT"); 	// 内容
		String TYPE = pd.getString("TYPE"); 		// 类型
		String isAll = pd.getString("isAll"); 		// 是否发送给全体成员 yes or no

		if (null != strEMAIL && !"".equals(strEMAIL)) {
			String strEM[] = strEMAIL.split(",mbfw,");
			if (strEM.length == 4) {
				if ("yes".endsWith(isAll)) {
					try {
						List<PageData> userList = userService.listAllUser(pd);
						zcount = userList.size();
						try {
							for (int i = 0; i < userList.size(); i++) {
								if (Tools.checkEmail(userList.get(i).getString("EMAIL"))) { // 邮箱格式不对就跳过
									SimpleMailSender.sendEmail(strEM[0], strEM[1], strEM[2], strEM[3], userList.get(i).getString("EMAIL"), TITLE, CONTENT, TYPE);// 调用发送邮件函数
									count++;
								} else {
									continue;
								}
							}
							msg = "ok";
						} catch (Exception e) {
							msg = "error";
						}
					} catch (Exception e) {
						msg = "error";
					}
				} else {
					toEMAIL = toEMAIL.replaceAll("；", ";");
					toEMAIL = toEMAIL.replaceAll(" ", "");
					String[] arrTITLE = toEMAIL.split(";");
					zcount = arrTITLE.length;
					try {
						for (int i = 0; i < arrTITLE.length; i++) {
							if (Tools.checkEmail(arrTITLE[i])) { // 邮箱格式不对就跳过
								SimpleMailSender.sendEmail(strEM[0], strEM[1], strEM[2], strEM[3], arrTITLE[i], TITLE, CONTENT, TYPE);// 调用发送邮件函数
								count++;
							} else {
								continue;
							}
						}
						msg = "ok";
					} catch (Exception e) {
						msg = "error";
					}
				}
			} else {
				msg = "error";
			}
		} else {
			msg = "error";
		}
		pd.put("msg", msg);
		pd.put("count", count); // 成功数
		pd.put("ecount", zcount - count); // 失败数
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 去发送短信测试页面
	 */
	@RequestMapping(value = "/SendSMSTest")
	public ModelAndView SendSMSTest() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/tools/SMSTest");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 发送短信
	 */
	@RequestMapping(value = "/SendSMS")
	@ResponseBody
	public Object SendSMS() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg="success";
		try {
			int sdkappId =Integer.parseInt(pd.getString("sdkappId"));
			SmsSingleSender sender = new SmsSingleSender(sdkappId,pd.getString("sig"));
			ArrayList<String> params = new ArrayList<String>();
			params.add(pd.getString("param1"));
			params.add(pd.getString("param2"));
			params.add(pd.getString("param3"));
			params.add(pd.getString("param4"));
			int contentId = Integer.parseInt(pd.getString("contentId"));
			
			String[] str = pd.getString("mobile").split(";");
			for(int i = 0; i < str.length; i++) {
				SmsSingleSenderResult  result = sender.sendWithParam(pd.getString("countryCode"), str[i], contentId, params, "", "", "");		
			}
		}catch(Exception e) {
			msg = "error";
		}
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 去图表统计页面
	 * @return
	 */
	@RequestMapping(value = "/reportAndView")
	public String reportAndView() {
		return "system/tools/reportAndView";
	}
	
	
	/**
	 * 去物流测试页面
	 * @return
	 */
	@RequestMapping(value = "/transportTest")
	public String transportTest() {
		return "system/tools/transportTest";
	}
	
	/**
	 * 查询物流信息
	 * @return
	 */
	@RequestMapping(value = "/queryTransport",produces = {"application/text;charset=UTF-8"})
	@ResponseBody
	public Object queryTransport() {
		PageData pd = new PageData();
		pd = this.getPageData();
		String resp = HttpUtil.doPost("http://www.kuaidi100.com/query", pd).toString();
		System.out.println(resp);
		return resp;
	}
	
	/**
	 * 去定位测试页面
	 * @return
	 */
	@RequestMapping(value = "/locationTest")
	public String locationTest() {
		return "system/tools/locationTest";
	}
}
