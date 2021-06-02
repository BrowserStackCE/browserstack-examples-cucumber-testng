package browserstack.utils;

import java.text.SimpleDateFormat;

/**
 * Logger
 * 
 * @author Pavel Yampolsky
 *
 */

import java.util.Calendar;

public class Log {
	public static void INFO(String log) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		int getLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
		String getMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(
				sdf.format(cal.getTime()) + " [INFO] " + "[" + getMethodName + ":" + getLineNumber + "]" + " - " + log);


	}

	public static void ERROR(String log) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		int getLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
		String getMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()) + " [ERROR] " + "[" + getMethodName + ":" + getLineNumber + "]"
				+ " - " + log);


	}
}
