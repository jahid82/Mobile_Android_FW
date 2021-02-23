package utility;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import configuration.Constants;
import configuration.TestSupport;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.*;
import java.net.InetAddress;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ReportGenerator {
	
	//private Common common = new Common();
    private static ExtentReports extent;
    private static ExtentTest logger;

    public static boolean initializeReport(String resultsDirectory, String testName) {

        boolean reportInitialized;

        try {

            String reportFilePath = resultsDirectory + "\\" + testName + ".html";
            String currentDirectory = System.getProperty("user.dir");
            File configurationFile = new File(currentDirectory + "\\src\\test\\java\\utility\\ExtentConfiguration.xml");

            extent = new ExtentReports(reportFilePath, true);
            extent.loadConfig(configurationFile);
            InetAddress address;

            String machineName = "Unknown";

            try {
                address = InetAddress.getLocalHost();
                machineName = address.getHostName();
            } catch (Exception e) {
                machineName = "Hostname could not be resolved";
            } finally {
                extent.addSystemInfo("Host Name", machineName);
            }

            String currentTime = TestSupport.getCurrentDateTime(false, true);
            logger = extent.startTest(testName + " - " + currentTime);
            reportInitialized = true;

        } catch (Exception e) {
            reportInitialized = false;
            printErrorMessage("initializeReport", e.getMessage());
        }

        return reportInitialized;
    }

    public static void testStatus(String testDetails, String testStatus, String screenshot) {

        try {

            if (testStatus.equalsIgnoreCase("Passed") && !screenshot.equalsIgnoreCase("")) {
                logger.log(LogStatus.PASS, testDetails + logger.addScreenCapture(screenshot));
            } else if (testStatus.equalsIgnoreCase("Passed") && screenshot.equalsIgnoreCase("")) {
                logger.log(LogStatus.PASS, testDetails);
            } else if (testStatus.equalsIgnoreCase("Failed") && !screenshot.equalsIgnoreCase("")) {
                logger.log(LogStatus.FAIL, testDetails + logger.addScreenCapture(screenshot));
            } else if (testStatus.equalsIgnoreCase("Failed") && screenshot.equalsIgnoreCase("")) {
                logger.log(LogStatus.FAIL, testDetails);
            } else if (testStatus.equalsIgnoreCase("Skipped")) {
                logger.log(LogStatus.SKIP, testDetails);
            } else if (testStatus.equalsIgnoreCase("Info")) {
                logger.log(LogStatus.INFO, testDetails);
            } else {
                logger.log(LogStatus.UNKNOWN, testDetails);
            }

            extent.endTest(logger);

        } catch (Exception e) {
            printErrorMessage("testStatus", e.getMessage());
        }
    }
   
   
 
   //Regiter log in extent report
    public static void logentry_Info(String logText,String logValue)
    {
        try
        {
           logger.log(LogStatus.INFO,  logText +" " +logValue);
           
           extent.endTest(logger);
        }
        catch (Exception e)
        {
            System.out.println("Unable to register log info");
        }
    }

    public static void finalizeReport() {

        try {
            extent.flush();
            extent.close();
        } catch (Exception e) {
            printErrorMessage("finalizeReport", e.getMessage());
        }
        
        
    }


	public static void printErrorMessage(String methodName, String errorMessage) {
        System.err.println("\n============================================================");
        System.err.println("----- Method Name: " + methodName);
        System.err.println("----- " + errorMessage);
        System.err.println("============================================================\n");
    }
	

	
	

}
