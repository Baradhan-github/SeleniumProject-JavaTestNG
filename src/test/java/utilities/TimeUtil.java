package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	/*
	 * time utils
	 */
	
	//time stamp helper
	public static String getCurrentTimeStamp(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	public String getCurrentDate() {
		return getCurrentTimeStamp("dd/MM/yyyy");
	}
	
	public static String getCurrentTime() {
		return getCurrentTimeStamp("hh:mm:ss:ssss");
	}
	
	public String customDate(String input, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(input);
			return sdf.format(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	

}
