package configuration;

import static executionEngine.TestMain.OR;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.offset.PointOption;
import utility.ReportGenerator;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ActionKeywords {

	public static AndroidDriver<AndroidElement> driver;
	public static Logger Log = LogManager.getLogger(ActionKeywords.class);
	AppiumDriverLocalService service;

	public static void openApp(String object, String Value) throws Exception {

		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\java\\utility\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String DeviceName = (String) prop.get("DeviceName");
		String PlatformName = (String) prop.get("PlatformName");
		String AUTOMATION_NAME = (String) prop.get("AUTOMATION_NAME");
		String udid = (String) prop.get("UDID");
		String PACKAGE = (String) prop.get("PACKAGE");
		String ACTIVITY = (String) prop.get("ACTIVITY");

		DesiredCapabilities cap = new DesiredCapabilities();
		Log.info("ObjectProperty : " + object + " " + Value);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);
		System.out.println("--------- Connecting to the Android device ----------");
		cap.setCapability(MobileCapabilityType.UDID, udid);// Samsung 9
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, PlatformName);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AUTOMATION_NAME);
		cap.setCapability("autoAcceptAlerts", true);
		// Disabling screen keyboard
		cap.setCapability("unicodeKeyboard", true);

		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PACKAGE);

		// new national APP_ACTIVITY-
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ACTIVITY);

		System.out.println("------ Initializing Appium Settings -------");

		try {

			System.out.println("************* Opening Application ************");

			driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);

			TestSupport.test_timeout(10);

		} catch (Exception e) {
			Log.error("############## Failed to Open App ##############");
			Constants.TestCaseStatus = false;
			// e.printStackTrace();
		}

		return;

	}

	// check which context you have

	public static void print_context(String Object, String Value) throws InterruptedException {

		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			Thread.sleep(5000);
			System.out.println("Context----->>" + contextName);
		}

	}

	// method- for changing web view to Native view
	public static boolean switch_NativeContext(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(1000);
			driver.context("NATIVE_APP");
			System.out.println("-------Switched to native view--------");
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.error("### Failed to switch in native view ###");
		}

		return stepSuccessful;
	}

	public static boolean switch_WebContext(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(1000);
			driver.context("WEBVIEW_chrome");
			System.out.println("-------Switched to WEBVIEW--------");

		} catch (Exception e) {
			Log.error("### Failed to switch in WEBVIEW ###");
		}

		return stepSuccessful;
	}

	// method- use to click on android elements
	public static boolean click(String Object, String Value) throws InterruptedException {

		boolean stepSuccessful = false;
		try {
			Thread.sleep(1000);
			Log.info("----------- Clicking on " + Object + " button/link ------------");
			AndroidElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.elementToBeClickable(ele)).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.error("############ Failed to click :" + Object + "############");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	public static boolean click_Afterwait(String Object, String Value) throws InterruptedException {

		boolean stepSuccessful = false;
		try {
			Thread.sleep(10000);
			Log.info("----------- Clicking on " + Object + " button/link ------------");
			AndroidElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions.elementToBeClickable(ele)).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			Log.error("############ Failed to click :" + Object + "############");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// method- use to click on android elements
	public static boolean click_Web(String Object, String Value) throws InterruptedException {

		boolean stepSuccessful = false;
		try {

			Log.info("----------- Clicking on " + Object + " button/link ------------");
			WebElement Wele = driver.findElement(By.xpath(OR.getProperty(Object)));
			Wele.click();
		} catch (Exception e) {
			Log.error("############ Failed to click :" + Object + "############");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	public static boolean click_Web_JS(String Object, String Value) throws InterruptedException {

		boolean stepSuccessful = false;
		try {
			Thread.sleep(2000);

			Log.info("----------- Clicking on " + Object + " button/link ------------");
			WebElement Wele = driver.findElement(By.xpath(OR.getProperty(Object)));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", Wele);

			Wele.click();
		} catch (Exception e) {
			Log.error("############ Failed to click :" + Object + "############");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// method- use to click on android elements
	public static boolean click_ifDisplayed(String Object, String Value) throws InterruptedException {

		boolean stepSuccessful = false;
		try {
			Thread.sleep(2000);
			Log.info("----------- Clicking if " + Object + " is visible ------------");
			AndroidElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(ele)).click();
		} catch (Exception e) {
			Log.error("----- Elemnt not present :" + Object + "------");
			Constants.TestCaseStatus = true;
		}
		return stepSuccessful;
	}

	// method- use to tap on elements
	public static boolean Single_tap(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		try {

			WebElement element = driver.findElement(By.xpath(OR.getProperty(Object)));
			System.out.println("tap on");
			TouchActions action = new TouchActions(driver);
			action.singleTap(element);
			action.perform();

			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("##### Failed to tap ######");
		}
		Thread.sleep(3000);
		return stepSuccessful;
	}

	// method- use to tap on elements
	public static boolean Drag_2car_cardPage(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		try {
			TouchAction action = new TouchAction(driver);
			action.press(PointOption.point(890, 1940)).perform().moveTo(PointOption.point(220, 1940)).release()
					.perform();

		} catch (Exception e) {
			Log.error("##### Failed to tap ######");

		}
		return stepSuccessful;
	}

	// method- use to tap on elements
	public static boolean Drag_2car_nearMePage(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = true;
		try {
			TouchAction action = new TouchAction(driver);
			action.press(PointOption.point(990, 380)).perform().moveTo(PointOption.point(990, 1175)).release()
					.perform();

		} catch (Exception e) {
			Log.error("##### Failed to tap ######");
		}

		return stepSuccessful;
	}

	// method- added additional time to wait before click
	public static boolean clickAndWait(String Object, String Value) throws InterruptedException {

		boolean stepSuccessful = false;
		try {

			Thread.sleep(5000);
			Log.info("----------- Clicked on " + Object + " button/link ------------");
			AndroidElement ele = driver.findElement(By.xpath("//*[@resource-id='continue' and @text='CONTINUE']"));
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(ele)).click();
		} catch (Exception e) {
			Log.error("############ Failed to click :" + Object + "############");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// method-scroll and click on link
	public static boolean scrollAndClick(String Object, String Value) {

		boolean stepSuccessful = false;
		try {
			Thread.sleep(3000);
			Log.info("------------ Scrolling down and click on " + Object + "------------------");

			MobileElement element = driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
							+ Object + "\").instance(0))");
			element.click();
			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("########### Failed to scroll and click ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;

		}
		return stepSuccessful;
	}

	public static boolean swapDown(String Object, String Value) {
		boolean stepSuccessful = true;
		try {
			Thread.sleep(2000);
			Log.info("---------- Swap down " + Object + "-------------");
			new TouchAction(driver).press(PointOption.point(850, 1975)).waitAction().moveTo(PointOption.point(850, 320))
					.release().perform();
		} catch (Exception e) {
			Log.error("########### Failed to swap Down ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = true;
		}
		return stepSuccessful;
	}

	public static boolean swapDown_half(String Object, String Value) {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(2000);
			Log.info("---------- Swap down " + Object + "-------------");
			new TouchAction(driver).press(PointOption.point(850, 1975)).waitAction()
					.moveTo(PointOption.point(850, 1020)).release().perform();
		} catch (Exception e) {
			Log.error("########### Failed to swap Down ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = true;
		}
		return stepSuccessful;
	}

	public static boolean swapDown_Quater(String Object, String Value) {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(2000);
			Log.info("---------- Swap down " + Object + "-------------");
			new TouchAction(driver).press(PointOption.point(850, 1975)).waitAction()
					.moveTo(PointOption.point(850, 1320)).release().perform();
		} catch (Exception e) {
			Log.error("########### Failed to swap Down ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = true;
		}
		return stepSuccessful;
	}

	public static boolean swapDown_half_LeftSide(String Object, String Value) {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(2000);
			Log.info("---------- Swap down " + Object + "-------------");
			new TouchAction(driver).press(PointOption.point(150, 1975)).waitAction()
					.moveTo(PointOption.point(150, 1020)).release().perform();
		} catch (Exception e) {
			Log.error("########### Failed to swap Down ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = true;
		}
		return stepSuccessful;
	}

	public static boolean swapDown_leftSide(String Object, String Value) {
		boolean stepSuccessful = true;
		try {
			Thread.sleep(2000);
			Log.info("---------- Swap down " + Object + "-------------");
			new TouchAction(driver).press(PointOption.point(150, 1975)).waitAction().moveTo(PointOption.point(150, 320))
					.release().perform();
		} catch (Exception e) {
			Log.error("########### Failed to swap Down ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = true;
		}
		return stepSuccessful;
	}

	// Scroll down to a particular word
	public void scrollToText(String Object, String Value) throws InterruptedException {
		Thread.sleep(1000);
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + Value + "\"));");
	}

	// Scroll down to a particular word
	public void scrollToText_DriverStatus(String Object, String Value) throws InterruptedException {
		Thread.sleep(1000);
		WebElement WE = driver.findElement(By.xpath("//*[@id='driverLicenseStatus']")); // Create a web element of the
																						// item you want to scroll to

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", WE);
	}

	// method for swap left of the screen
	public static boolean Swap_left(String Object, String Value) {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(2000);
			Log.info("---------- Swap to left  " + Object + "-------------");
			new TouchAction(driver).press(PointOption.point(920, 1240)).waitAction()
					.moveTo(PointOption.point(100, 1240)).release().perform();

		} catch (Exception e) {
			Log.error("########### Failed to swap left ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;

		}
		return stepSuccessful;
	}

	// method- special method for swap sub menu of discount page
	public static boolean swapleftdicountMainMenu(String Object, String Value) {
		boolean stepSuccessful = false;
		try {
			Log.info("---------- Swap to left  " + Object + "-------------");

			new TouchAction(driver).press(PointOption.point(850, 250)).waitAction().moveTo(PointOption.point(70, 250))
					.release().perform();

			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("########### Failed to swap left ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;

		}
		return stepSuccessful;
	}

	// method- special method for swap sub menu of discount page
	public static boolean swapleftdicountSubMenu(String Object, String Value) {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(1000);
			Log.info("---------- Swap to left  " + Object + "-------------");

			new TouchAction(driver).press(PointOption.point(1020, 420)).waitAction().moveTo(PointOption.point(50, 420))
					.release().perform();

			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("########### Failed to swap left ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;

		}
		return stepSuccessful;
	}

	// in this method, extra wait time added during typing. Only used for type used
	// id during login
	public static boolean TypeUserID(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + " ---------");
			TestSupport.test_timeout(180);
			// Thread.sleep(12000);
			AndroidElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			ele.sendKeys(Value);

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// not in use
	public static boolean wait(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- waiting for the element " + Object + " ---------");
			ActionKeywords.driver.manage().timeouts().implicitlyWait(Integer.parseInt(Value), TimeUnit.SECONDS);

		} catch (Exception e) {
			Log.error("############ Failed to wait ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// Type
	public static boolean sendKeys(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + " ---------");
			TestSupport.test_timeout(10);
			AndroidElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.visibilityOf(ele)).sendKeys(Value);

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	//
	public static boolean sendKeys_web(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + " ---------");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath(OR.getProperty(Object)));
			Wele.sendKeys(Value);

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// send zip Code
	public static boolean sendKeys_web_Zip_MI(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + " ---------");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_txtQuoteZip5']"));
			Wele.sendKeys("48093");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// send zip Code
	public static boolean sendKeys_web_Zip_TN(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + " ---------");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_txtQuoteZip5']"));
			Wele.sendKeys("48093");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}
	// send First Name
	public static boolean sendKeys_FirstName(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + "FirstName");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath("//*[@id='firstName']"));
			Wele.sendKeys("Mable");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// send Last Name
	public static boolean sendKeys_LastName(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + "LastName");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath("//*[@id='lastName']"));
			Wele.sendKeys("Seed");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// send Email
	public static boolean sendKeys_Email(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + "email");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath("//*[@id='email']"));
			Wele.sendKeys("Jack@gmail.com");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// send Address
	public static boolean sendKeys_address_MI(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + "address");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath("//*[@id='address-line1']"));
			Wele.sendKeys("31036 Wellston dr, warren, Michigan, USA");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys address ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}
	
	public static boolean sendKeys_address_TN(String Object, String Value) throws Exception {
	boolean stepSuccessful = false;
	try {
		Log.info("-------- Sending keys " + Object + "address");
		TestSupport.test_timeout(10);
		WebElement Wele = driver.findElement(By.xpath("//*[@id='address-line1']"));
		Wele.sendKeys("622 Harmon Rd, Mountain City, TN, USA");

	} catch (Exception e) {
		Log.error("############ Failed in Send Keys address ############# " + Object);
		// e.printStackTrace();
		Constants.TestCaseStatus = false;
	}
	return stepSuccessful;
}


	// send Email
	public static boolean sendKeys_Vin(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + "Vin");
			TestSupport.test_timeout(10);
			WebElement Wele = driver.findElement(By.xpath("//*[@id='vin']"));
			Wele.sendKeys("5FRYD4H2XGB057790");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys Vin ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// send Email
	public static boolean sendKeys_DOB(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Log.info("-------- Sending keys " + Object + "email");
			TestSupport.test_timeout(10);
			WebElement Wele1 = driver.findElement(By.xpath("//*[@id='birthMonth']"));
			Wele1.sendKeys("02");

			WebElement Wele2 = driver.findElement(By.xpath("//*[@id='birthDay']"));
			Wele2.sendKeys("02");

			WebElement Wele3 = driver.findElement(By.xpath("//*[@id='birthYear']"));
			Wele3.sendKeys("1980");

		} catch (Exception e) {
			Log.error("############ Failed in Send Keys ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// send Email
	public static boolean Select_ContactNo(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {

			WebElement Wele1 = driver.findElement(By.xpath("//*[@id='phone']"));
			Wele1.sendKeys("586-111-1111");

		} catch (Exception e) {
			Log.error("############ Failed in Send Contact Details ############# " + Object);
			// e.printStackTrace();
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// Click Vehicle Description-1
	public static void Tap_Select_Description_P1(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Select_Description ------------");
			Thread.sleep(5000);
			TouchAction touchAction = new TouchAction(driver);
			// Select Description
			touchAction.tap(PointOption.point(275, 1505)).perform();
			Thread.sleep(1000);
			// Select private passenger
			touchAction.tap(PointOption.point(400, 1130)).perform();
			Thread.sleep(1000);
			// previous title
			touchAction.tap(PointOption.point(200, 2115)).perform();
			Thread.sleep(4000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Select_Descriptionn ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Vehicle Description-2
	public static void Tap_Select_Description_P2(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Select_Description ------------");

			TouchAction touchAction = new TouchAction(driver);
			Thread.sleep(3000);
			// Where car parked?
			touchAction.tap(PointOption.point(190, 680)).perform();
			Thread.sleep(3000);
			// Next Button
			touchAction.tap(PointOption.point(740, 1980)).perform();
			Thread.sleep(3000);
			// Next 2nd button
			touchAction.tap(PointOption.point(748, 2122)).perform();
			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Select_Descriptionn ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Vehicle Description-2
	public static void Tap_Driver_Details(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Select_Description ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(300, 1315)).perform();
			Thread.sleep(2000);
			touchAction.tap(PointOption.point(740, 1990)).perform();
			Thread.sleep(7000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Select_Descriptionn ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Continue
	public static void Tap_Google_Address(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Google_Address ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(300, 1270)).perform();
			Thread.sleep(2000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Google_Addressn ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Continue
	public static void Tap_Green_ContinueButton(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Continue Button ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(600, 1140)).perform();
			Thread.sleep(5000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Continue Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Continue
	public static void Tap_Green_NextButton(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(750, 1990)).perform();
			Thread.sleep(5000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_ContinueButton(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Continue Button ------------");
			Thread.sleep(8000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(550, 2080)).perform();

			// roadside assistant
			Thread.sleep(4000);
			touchAction.tap(PointOption.point(550, 2100)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Continue Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Driverstatis
	public static void Tap_DriverStatus(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Continue Button ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(320, 1030)).perform();

			// roadside assistant
			Thread.sleep(4000);
			touchAction.tap(PointOption.point(200, 980)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Continue Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Continue
	public static void Tap_Green_NextButton_01(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(750, 1935)).perform();
			Thread.sleep(9000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Continue
	public static void Tap_Green_NextButton_03(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(750, 1990)).perform();
			Thread.sleep(12000);

			// lets review about your vehicle
			touchAction.tap(PointOption.point(750, 1470)).perform();
			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_Green_NextButton_04(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			// lets review about your vehicle
			touchAction.tap(PointOption.point(750, 1975)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(740, 2120)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(750, 1250)).perform();
			Thread.sleep(5000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_Green_NextButton_05(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			// Next
			touchAction.tap(PointOption.point(750, 1975)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(480, 1375)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(740, 1820)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(740, 1900)).perform();
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_Green_NextButton_06(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			// Vehicle used for-1
			touchAction.tap(PointOption.point(750, 1975)).perform();
			Thread.sleep(5000);
			// Vehicle used for-2
			touchAction.tap(PointOption.point(480, 1375)).perform();
			Thread.sleep(5000);
			// Annual Millage-1
			touchAction.tap(PointOption.point(740, 1820)).perform();
			Thread.sleep(5000);
			// Annual Millage-2
			touchAction.tap(PointOption.point(450, 1225)).perform();
			Thread.sleep(5000);
			// Next
			touchAction.tap(PointOption.point(730, 1980)).perform();
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_Green_NextButton_07(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			// Next
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(730, 1980)).perform();
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_Green_NextButton_08(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			// Next
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(66, 1510)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(730, 1980)).perform();
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}
	
	public static void Tap_Green_NextButton_09(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			// Next
			Thread.sleep(8000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(750, 1480)).perform();
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}


	public static void Tap_AdditionalDriver(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			// Next
			touchAction.tap(PointOption.point(270, 1670)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(315, 985)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(260, 1970)).perform();
			Thread.sleep(5000);
			touchAction.tap(PointOption.point(350, 1225)).perform();
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_ProviderDetails(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(3000);

			TouchAction touchAction = new TouchAction(driver);
			// lets review about your vehicle
			// Provide details
			touchAction.tap(PointOption.point(300, 1145)).perform();
			Thread.sleep(5000);

			// Select Employment Status
			touchAction.tap(PointOption.point(215, 2090)).perform();
			Thread.sleep(3000);
			// Employment
			touchAction.tap(PointOption.point(230, 880)).perform();
			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Driver Status
	public static void Tap_DriverInfo(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Tap_Next Button ------------");
			Thread.sleep(3000);

			TouchAction touchAction = new TouchAction(driver);
			// Driver status
			touchAction.tap(PointOption.point(265, 135)).perform();
			Thread.sleep(3000);

			// Select Driver Status Active
			touchAction.tap(PointOption.point(575, 975)).perform();
			Thread.sleep(3000);

			// Prior Auto Insurance_No
			touchAction.tap(PointOption.point(400, 870)).perform();
			Thread.sleep(3000);

			// Reason No Prior Auto Insurance
			touchAction.tap(PointOption.point(400, 1150)).perform();
			Thread.sleep(3000);

			// Reason No Prior Auto Insurance
			touchAction.tap(PointOption.point(330, 1315)).perform();
			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Next Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Click Continue
	public static void Tap_AddVehicle(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on AddVehicle ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(350, 1200)).perform();
			Thread.sleep(8000);

		} catch (Exception e) {
			Log.error("############ unable to tap on AddVehicle ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_FirstInsurance_Yes(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on FirstInsurance_Yes ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(200, 1800)).perform();
			Thread.sleep(3000);
			touchAction.tap(PointOption.point(350, 1200)).perform();
			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("############ unable to tap on FirstInsurance_Yes ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_FirstInsurance_Yes_TN(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on FirstInsurance_Yes ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(190, 1185)).perform();
			Thread.sleep(3000);

		} catch (Exception e) {
			Log.error("############ unable to tap on FirstInsurance_Yes ############");
			Constants.TestCaseStatus = false;
		}
	}
	public static void Tap_Change_Zip(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on ZipCode ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(321, 588)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on ZipCode ############");
			Constants.TestCaseStatus = false;
		}
	}

	// tap
	public static void tap(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  " + Object + " element ------------");

			AndroidElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			TouchActions action = new TouchActions(driver);
			action.singleTap(ele);
			action.perform();

		} catch (Exception e) {
			Log.error("############ tap on :" + Object + "############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Agree_CheckBox(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Agree checkBox element ------------");
			Thread.sleep(5000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(122, 1918)).perform();
			// touchAction.tap(PointOption.point(118,1916)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Agree element ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Select_PreFix(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Agree Select_PreFix ------------");
			Thread.sleep(5000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(122, 1918)).perform();
			// touchAction.tap(PointOption.point(118,1916)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Select_PreFix ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_OK_InvalidLogin(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on OK( Invalid Login) ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(815, 1352)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Invalid Login ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_OK_VerifyID(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Invalid Login ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(1131, 1633)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Invalid Login ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_AAADrive(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Change Zip Code Menu ------------");

			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(250,785)).perform();//v3
			touchAction.tap(PointOption.point(1000, 150)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Change Zip Code Menut ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Continue_as_a_Guest(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Continue_as_a_Guestt ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(250,785)).perform();//v3
			touchAction.tap(PointOption.point(510, 1935)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Continue_as_a_Guest ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Currently_Insured_No(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Currently_Insured_No ------------");

			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(250,785)).perform();//v3
			touchAction.tap(PointOption.point(300, 685)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Currently_Insured_No ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Currently_Insured_No_2(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Currently_Insured_No ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(250,785)).perform();//v3
			touchAction.tap(PointOption.point(295, 700)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Currently_Insured_No ############");
			Constants.TestCaseStatus = false;
		}
	}

	// Find Vin
	public static void tap_Find_Vin(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Currently_Insured_No ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(250,785)).perform();//v3
			touchAction.tap(PointOption.point(250, 1420)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Currently_Insured_No ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_ZipCode(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on ZipCode ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(250,785)).perform();//v3
			touchAction.tap(PointOption.point(125, 990)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on ZipCode ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_find(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Currently_Insured ------------");

			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(250,785)).perform();//v3
			touchAction.tap(PointOption.point(250, 1410)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Currently_Insured ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_ChangeZipCode(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Change Zip Code Menu ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(295, 2276)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Change Zip Code Menut ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Close_Button(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Change Zip Code Menu ------------");
			Thread.sleep(12000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(550, 1950)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on tap_Close_Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Cross_ViewCard(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Dashboard Tile ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(980, 214)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ tap on Dashboard Tile  ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_2CarLogo(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Dashboard Tile ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(220, 1770)).perform();
			Thread.sleep(2000);

		} catch (Exception e) {
			Log.error("############ tap on Dashboard Tile  ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_OK_DebugInfo(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Debug Info ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(779, 1364)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ tap on OK Debug Info  ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_OK_LogOut(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Debug Info ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(1100, 1600)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ tap on OK Debug Info  ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_More_BTN(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on More Button ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(1225, 215)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on More Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Male(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Male Button ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(200, 1810)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Male Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Married(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Married Button ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(380, 2100)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Married Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_AAADrive_Hamburger(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on AAADrive_Hamburger Button------------");
			Thread.sleep(12000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(1004, 154)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on AAADrive_Hamburger ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_NearMe_ShowList(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on AAADrive_Hamburger Button------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(525, 2118)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on NearMe_ShowList ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_FlatTire(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  tap_FlatTire ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(760, 815)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on tap_FlatTire ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_SpareTire_No(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  tap_SpareTire ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(125, 1032)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on tap_SpareTire ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Auto_Quote(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Auto_Quote ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(200, 850)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on Auto_Quote ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_CrossOnRoadSide(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Change Zip Code Menu ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(1022, 126)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on checkBox element ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_BackButton(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Back Button ------------");

			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(100,120)).perform();
			touchAction.tap(PointOption.point(122, 122)).perform();
			Thread.sleep(2000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Back Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Arrow_BackButton(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Back Button ------------");

			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(100,120)).perform();
			touchAction.tap(PointOption.point(274, 137)).perform();
			Thread.sleep(2000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Back Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_NearMe_Dropdown(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Near Me Drop Down Menu ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(997, 351)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on checkBox element ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_CrossIcon(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Cross Icon ------------");
			Thread.sleep(2000);
			TouchAction touchAction = new TouchAction(driver);
			// touchAction.tap(PointOption.point(994,144)).perform();//V3
			touchAction.tap(PointOption.point(990, 210)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Back Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_a_TravelTile(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Cross Icon ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(250, 1500)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on fav_button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void fav_button(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Cross Icon ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(330, 1257)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on fav_button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_advertisement(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  advertisement ------------");
			Thread.sleep(3000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(502, 769)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on advertisement ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tab_DealOnline(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on View DealOnline ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(550, 1085)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on DealOnline  ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_Cross_SacnCode(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Cross Icon ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(941, 207)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Back Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_ApplyForAcreditCard(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Cross Icon ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(550, 1510)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Back Button ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_Allow_Push_Notifications(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Allow_Push_Notifications ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(1270, 644)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Allow_Push_Notifications ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_Setting_Enable_FaceID(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Setting_Enable ------------");

			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(1000, 1770)).perform();
			Thread.sleep(1000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Setting_Enable ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_AAADrive(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  AAADrive ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(270, 780)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on tap on  AAADrive  ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_AAADrive_Allow(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  AAADrive Allow ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(550, 1245)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on tap on  AAADrive Allow  ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void Tap_About_AAADrive(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  Setting_Enable ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(230, 1565)).perform();
			Thread.sleep(5000);

		} catch (Exception e) {
			Log.error("############ unable to tap on About_AAADrive ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_View_dealOnline(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on View_dealOnline ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(540, 650)).perform();
			Thread.sleep(2000);

		} catch (Exception e) {
			Log.error("############ unable to tap on View_dealOnlinee ############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void tap_PushNotification(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on Push Notification ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(992, 462)).perform();
			Thread.sleep(2000);

		} catch (Exception e) {
			Log.error("############ unable to tap on Push Notification ############");
			Constants.TestCaseStatus = false;
		}
	}

	// double click on the elements
	public static boolean doubleClick(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		try {
			Log.info("----------- Clicking " + Object + " button/link ------------");
			AndroidElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			ele.click();
			ele.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			Log.error("############ Failed to click :" + Object + "############");
			Constants.TestCaseStatus = false;
		}

		return stepSuccessful;
	}

	public static void Tap_ViewBack(String Object, String Value) throws InterruptedException {

		try {
			Log.info("----------- tap on  ViewBack ------------");
			Thread.sleep(1000);
			TouchAction touchAction = new TouchAction(driver);
			touchAction.tap(PointOption.point(203, 1218)).perform();

		} catch (Exception e) {
			Log.error("############ unable to tap on tap on  ViewBack  ############");
			Constants.TestCaseStatus = false;
		}
	}

	// methos- use to return back on previous page

	public static boolean back_android(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		Log.info("------------- Back Android Action ----------------");
		try {
			Thread.sleep(3000);
			driver.navigate().back();
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("######### Failed to click android back ###########");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// Type zip code, this method will be merged with send key
	public static void click_changeZip(String Object, String Value) throws Exception {

		try {
			Log.info("---------Clicking changezipcodebtn Btn---------------");
			driver.findElementByXPath(".//*[@class='android.view.View' and @text='Change ZIP code']").click();

		} catch (Exception e) {
			Log.error("changecode Btn - Click Failed");
			Constants.TestCaseStatus = false;
		}

	}

	// method- for long press
	public static boolean longPress(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		try {
			WebElement ele = driver.findElement(By.xpath(OR.getProperty(Object)));
			// TouchAction touchAction=new TouchAction(driver);
			TouchAction action = new TouchAction(driver)
					.longPress(longPressOptions().withElement(element(ele)).withDuration(Duration.ofMillis(10000)))
					.release().perform();
			Thread.sleep(5000);
			Log.info("----------- longPress " + Object + " element ------------");
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("############ Failed to longPress  :" + Object + "############");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// validate text
	public static boolean getText(String Object, String Value) throws Exception {

		boolean stepSuccessful = false;

		try {
			Thread.sleep(3000);
			Log.info("----------- Getting Text -----------");

			String text = null;
			text = driver.findElementByXPath(OR.getProperty(Object)).getText().trim();
			Log.info("Actual Text: " + text);
			if (text.equals(Value)) {
				Log.info("************ Given Value Matched - Expected : " + Value + " Actual : " + text
						+ "************");
			} else {
				// Constants.TestCaseStatus = false;
				Log.error("############# Given Value doesnt match - Expected : " + Value + " Actual : " + text
						+ "##############");
				Constants.TestCaseStatus = false;
			}
		}

		catch (Exception e) {
			Log.error("########### Failed to get Text ##########" + Object);
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	public static void verifyerrmsg(String Object, String Value) throws InterruptedException {
		Log.info("Verifying Error message");
		Log.info("ObjectProperty : " + Object + "Value - " + Value);
		Thread.sleep(3000);
		try {
			if (driver.findElementByXPath("//android.view.View[@index='0']").isDisplayed()) {

				Log.info("Login failed pop up displayed");
				driver.findElementByXPath("//android.widget.Button[@index='2']").click();
				// System.out.println("PASS");
			}
		} catch (Exception e) {
			Log.error("Error message not displayed");
			Constants.TestCaseStatus = false;

		}
		// Thread.sleep(3000);

	}

	public static boolean clicksubmit(String Object, String Value) throws InterruptedException {

		boolean stepSuccessful = false;
		Log.info("Clicking Submit button");
		Log.info("ObjectProperty : " + Object + "Value - " + Value);
		Thread.sleep(3000);
		try {
			driver.findElementByClassName("android.widget.Button").click();
		} catch (Exception e) {
			Log.error("Failed to click :" + Object);
			Constants.TestCaseStatus = false;

		}
		Thread.sleep(3000);
		return stepSuccessful;
	}

	public static boolean clickMonitor(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			Thread.sleep(3000);
			Log.info("ObjectProperty : " + Object + "Value - " + Value);
			java.util.List<AndroidElement> elementsTextContainsTO = driver
					.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"Monitor Your Driving\")");

			for (AndroidElement element : elementsTextContainsTO) {
				Log.info("Found clickable object Monitor your Driving");
				element.click();

			}
		} catch (Exception e) {
			Log.info("Not found Link Monitor Your Driving");
		}
		return stepSuccessful;

	}

	public static boolean clickAAADrive(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {

			Log.info("ObjectProperty : " + Object + "Value - " + Value);
			AndroidElement element = (AndroidElement) driver
					.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"AAADrive\")");

			element.click();

		} catch (Exception e) {
			Log.info("Unable to Click on AAA Drive");
		}
		return stepSuccessful;

	}

	public static boolean verifylogo(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		AndroidElement mdriver;
		Log.info("----------- Finding" + Object + " option ----------");

		try {
			Thread.sleep(1000);
			mdriver = driver.findElementById("com.aaa.android.discounts:id/toolbar_right_logo");
			if (mdriver.isDisplayed()) {
				Log.info("****** AAA logo is available********* ");
			}
		} catch (Exception e) {
			Log.error("######### Missing AAA logo on Membership toolbar ######### ");
			Constants.TestCaseStatus = true;
		}
		Thread.sleep(6000);
		return stepSuccessful;
	}

	public static boolean verifyNewlogo(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		AndroidElement mdriver;
		Log.info("----------- Finding" + Object + " option ----------");

		try {
			mdriver = driver.findElementByXPath(".//*[@class='android.widget.Image' and @text='acgdeal_ic_aaa_logo']");
			if (mdriver.isDisplayed()) {
				Log.info("****** AAA logo is available********* ");
			}
		} catch (Exception e) {
			Log.error("######### Missing AAA logo on Membership toolbar ######### ");
			Constants.TestCaseStatus = true;
		}
		Thread.sleep(6000);
		return stepSuccessful;
	}

	public static boolean verifyIfPresent(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		Log.info("----------Verifying if " + Object + "is present---------");
		// Log.info("ObjectProperty : "+Object+"Value - "+Value);
		Thread.sleep(3000);
		try {
			if (driver.findElementById(OR.getProperty(Object)).isDisplayed()) {
				Log.info(Object + " is Present");
			}
		} catch (Exception e) {
			Log.error("######## Failed to verify" + Object + "#########");
			Constants.TestCaseStatus = false;
		}

		return stepSuccessful;
	}

	public static boolean verifySettingstoolbar(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		AndroidElement msettings;
		Log.info("Finding " + Object + " option");
		try {
			msettings = driver.findElementByAndroidUIAutomator("text(\"Settings\")");

		} catch (Exception e) {
			Log.error("Missing " + Object + "toolbar ");
			Constants.TestCaseStatus = false;
		}
		Thread.sleep(5000);
		return stepSuccessful;
	}

	public static boolean clickPushNotifications(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			driver.findElementByAndroidUIAutomator("text(\"Push Notifications\")").click();
		} catch (Exception e) {
			Log.error("Click failed for " + Object);
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	public static boolean toggleOnOff(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			driver.findElementById(OR.getProperty(Object)).click();
			Log.info("Toggle OnOff clicked for" + Object);
		} catch (Exception e) {
			Log.error("Toggle OnOff failed for " + Object);
			Constants.TestCaseStatus = false;
		}
		Thread.sleep(5000);
		return stepSuccessful;
	}

	public static boolean clickViewcard(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		Log.info("Click on Inside ViewCard ");
		try {
			java.util.List<AndroidElement> elementsTextContainsTO = driver
					.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"View\")");

			for (AndroidElement element : elementsTextContainsTO) {
				element.click();
				break;
				// }
			}
		} catch (Exception e) {
			Log.error("View card - Click Failed");
		}
		Thread.sleep(5000);
		return stepSuccessful;

	}

	public static boolean AAADrive_GetStarted(String Object, String Value) throws InterruptedException {
		boolean stepSuccessful = false;
		Log.info("Click on Inside ViewCard ");
		try {
			java.util.List<AndroidElement> elementsTextContainsTO = driver
					.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"Started\")");

			for (AndroidElement element : elementsTextContainsTO) {
				element.click();
				break;
				// }
			}
		} catch (Exception e) {
			Log.error("View card - Click Failed");
		}
		Thread.sleep(5000);
		return stepSuccessful;

	}

	// method- use to check build number
	public static boolean tap_fivetimes(String Object, String Value) throws Exception {
		boolean stepSuccessful = false;
		try {
			driver.findElementByXPath(OR.getProperty(Object)).click();
			driver.findElementByXPath(OR.getProperty(Object)).click();
			driver.findElementByXPath(OR.getProperty(Object)).click();
			driver.findElementByXPath(OR.getProperty(Object)).click();
			driver.findElementByXPath(OR.getProperty(Object)).click();
			Thread.sleep(3000);

		}

		catch (Exception e) {
			Log.error("########### Failed to get Text" + Object + "#############");
			Constants.TestCaseStatus = false;
		}
		return stepSuccessful;
	}

	// method- take screen shot
	public static String takeScreenShot(String reportDirectory) throws IOException {

		String filePath = "";

		try {

			String dateTimeStamp = TestSupport.getCurrentDateTime(true, true);
			dateTimeStamp = dateTimeStamp.replace("/", "-");
			dateTimeStamp = dateTimeStamp.replace(":", "-");
//	        filePath =  Constants.getCurrentDirectoryPath() + "\\" + dataValue + " " + dateTimeStamp + ".png";
			filePath = reportDirectory + "\\" + "Screenshots" + "\\" + dateTimeStamp + ".png";

			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(filePath));

		} catch (Exception e) {
			System.out.println("---> Could not take screenshot.");
		}

		return filePath;
	}

	public static void refreshPage(String Object, String Value) throws InterruptedException {

		Log.info("********* Refresh Page *************");
		try {

			driver.navigate().refresh();

		} catch (Exception e) {
			Log.error("########### Failed to Refresh Page ##############");
			Constants.TestCaseStatus = false;
		}
	}

	public static void closeApp(String Object, String Value) throws InterruptedException {

		Log.info("********* Closing Application *************");
		try {

			driver.closeApp();
			driver.quit();

		} catch (Exception e) {
			Log.error("########### Failed to close Application ##############");
			Constants.TestCaseStatus = false;
		}
	}

}