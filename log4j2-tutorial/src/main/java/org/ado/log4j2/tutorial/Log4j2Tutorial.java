package org.ado.log4j2.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2Tutorial {
	private static final Logger logger = LoggerFactory.getLogger(Log4j2Tutorial.class);

	public static void main(String[] args) {
		logger.info("使用log4j2日志记录器！");
	}
}
