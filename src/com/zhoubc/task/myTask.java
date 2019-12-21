package com.zhoubc.task;

import com.zhoubc.util.Const;
import com.zhoubc.util.Logger;
import com.zhoubc.util.Tools;

public class myTask {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	private static final String STR1 = "===>";
	
	private static final String STR2 = "=============start=================";
	
	public void print() {
		logBefore(logger,"执行定时任务：test..");
		//业务逻辑代码。。。
		System.out.println("这里执行定时任务..什么也没用....");
			
		logAfter(logger);
	}
	
	
	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info(STR1+interfaceName+STR2);
	}
	
	public static void log(Logger logger, String str) {
		logger.info(STR1+str);
	}

	public static void logAfter(Logger logger) {
		logger.info("===>end");
		logger.info("");
	}
}
