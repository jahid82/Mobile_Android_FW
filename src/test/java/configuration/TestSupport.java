package configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class TestSupport {
	
	public static void test_timeout(int t)
	{
		ActionKeywords.driver.manage().timeouts().implicitlyWait(t, TimeUnit.SECONDS);
		
	}
	
	
public static String getCurrentDateTime(boolean bdate, boolean btime)
	{
		String datetimeStamp;
		if(bdate && btime) {
			datetimeStamp = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(Calendar.getInstance().getTime());
			return datetimeStamp;
		}else if(bdate) {
			datetimeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			return datetimeStamp;
			
		}else if(btime) {
			datetimeStamp = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
			return datetimeStamp;
		}
		else {
			return null;
		}
	}






}
