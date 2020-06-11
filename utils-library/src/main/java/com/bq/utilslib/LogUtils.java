package com.bq.utilslib;

import android.util.Log;

/** 
 * 控制APP软件日志的打印工具类
 * */

public class LogUtils {

	private static final int TAG_LEVEL_0 = 0;
	private static final int TAG_LEVEL_1 = 1;
	private static final int TAG_LEVEL_2 = 2;
	private static final int TAG_LEVEL_3 = 3;
	private static final int TAG_LEVEL_4 = 4;
	private static final int MAX_LENGTH = 3500;

	/** 通过配置是否输出调试日志，级别为错误的始终打印*/
	private static final boolean isDebug = true;
	private static final String tag = "LogUtils";
	
	public static void v(String log) {
		v(tag, log);
	}
	
	public static void v(String tag, String log) {
		if (isDebug) {
			printLagerLog(tag, log, TAG_LEVEL_0, null);
		}
	}
	
	public static void i(String log) {
		i(tag, log);
	}
	
	public static void i(String tag, String log) {
		if (isDebug) {
			printLagerLog(tag, log, TAG_LEVEL_1, null);
		}
	}
	
	public static void d(String log) {
		d(tag, log);
	}
	
	public static void d(String tag, String log) {
		if (isDebug) {
			printLagerLog(tag, log, TAG_LEVEL_2, null);
		}
	}
	
	public static void w(String log) {
		w(tag, log);
	}
	
	public static void w(String tag, String log) {
		if (isDebug) {
			printLagerLog(tag, log, TAG_LEVEL_3, null);
		}
	}
	
	public static void e(String log) {
		e(tag, log);
	}
	
	public static void e(String tag, String log) {
		printLagerLog(tag, log, TAG_LEVEL_4, null);
	}
	
	public static void w(String tag, String string, Exception e) {
		printLagerLog(tag, string, TAG_LEVEL_3, e);
	}

	public static void e(String tag, String string, Exception e) {
		printLagerLog(tag, string, TAG_LEVEL_4, e);
	}

	private static void printLagerLog(String tag, String log, int tagLevel, Exception e) {
		if (null != e) {
			Log.e(tag, log, e);
		}
		printLog(tag, log, tagLevel);
	}

	private static void printLog(String tag, String log, int tagLevel) {
		if (MAX_LENGTH < log.length()) {
			String showPart = log.substring(0, MAX_LENGTH);
			printPartLog(tag, showPart, tagLevel);
			String parts = log.substring(MAX_LENGTH, log.length());
			if (parts.length() > MAX_LENGTH) {
				printLog(tag, parts, tagLevel);
			} else {
				printPartLog(tag, parts, tagLevel);
			}
		} else {
			printPartLog(tag, log, tagLevel);
		}
	}

	private static void printPartLog(String tag, String partLog, int tagLvele) {
		switch (tagLvele) {
			case TAG_LEVEL_0:
				Log.v(tag, partLog);
				break;
			case TAG_LEVEL_1:
				Log.i(tag, partLog);
				break;
			case TAG_LEVEL_2:
				Log.d(tag, partLog);
				break;
			case TAG_LEVEL_3:
				Log.w(tag, partLog);
				break;
			case TAG_LEVEL_4:
				Log.e(tag, partLog);
				break;
			default:
				break;
		}
	}
	
}
