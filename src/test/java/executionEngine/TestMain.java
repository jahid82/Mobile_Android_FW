/**
 * 
 */
package executionEngine;


import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import configuration.ActionKeywords;
import configuration.Constants;
import configuration.TestSupport;

import utility.ReportGenerator;


public class TestMain {
	
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	
	public static Method method[];//Reflection Class Object
	public static Properties OR;
	
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sObject;
	public static String sObjectValue;
	public static String testStatus;

	
	public static Logger Log=LogManager.getLogger(TestMain.class);
	
	public TestMain() throws NoSuchMethodException, SecurityException {
		actionKeywords = new ActionKeywords();		
		//This will load all method in class 'ActionKeywords'
		method = actionKeywords.getClass().getMethods();		
	}

	public static void main(String[] args) throws Exception {
		
		
		TestMain startExecution = new TestMain();
		System.setProperty("currentDate", new SimpleDateFormat("MM_dd_yyyy_HH_MM_SS").format(new Date()));
    	//This is to start the Log4j logging in the test case [DOMConfigurator.configure("log4j.xml");]
		
		//Creating file system object for Object Repository text/property file
		FileInputStream fs = new FileInputStream(Constants.Path_OR);

		//Creating an Object of properties
		OR= new Properties(System.getProperties());
		//Loading all the properties from Object Repository property file in to OR object
		OR.load(fs);		
		startExecution.execute_TestCase();
			
	}
	
	private void execute_TestCase() throws Exception {
		
		Document DocTestPlan;
		DocTestPlan = utility.XMLutils.readXmlFile(Constants.PathTestPlan);
		String PathComponentFile;
		//= Constants.getTestExecutionDirectoryPath();
		
		NodeList ListTestSuite = utility.XMLutils.getTestSuite(DocTestPlan);
		
	
		//Get directory to save extent report file
		String sdatetime = TestSupport.getCurrentDateTime(true, true);
		String reportDirectory = Constants.getCurrentDirectoryPath()+"//AutomationResults//TestReports_"+sdatetime+"//";
		
		
		//**Important**- To save report in a particular drive comment out above line and uncomment bolow line and set your path.
		//String reportDirectory ="Copy the path"+"//AutomationResults//TestReports_"+sdatetime+"//"
				
				
		for(int testsuite = 0; testsuite < ListTestSuite.getLength(); testsuite++){
			
			Node nTestSuite = ListTestSuite.item(testsuite);
			Element eTestSuite = (Element) nTestSuite;
			String componentFileName = eTestSuite.getAttribute("filename");
			Log.info("Component Name :" + componentFileName);
			PathComponentFile = Constants.getXMLDirectoryPath(componentFileName);
			Log.info("Path for Component file : "+PathComponentFile);
			//NodeList ListTestcases = eTestSuite.getElementsByTagName("TestCase");
			String executeComponent = eTestSuite.getAttribute("execute");
			if(executeComponent.equals("No")){
				Log.warn("---------> Skipping Test Component to run <--------");
				continue;
			}
			
			
				
				Document DocComponent;
				
				DocComponent = utility.XMLutils.readXmlFile(PathComponentFile);
				
				NodeList nListTestCase = utility.XMLutils.getTestCaseDefined(DocComponent);
		        
		        Log.info("Component : "+ componentFileName +" | Total TestCases :"+ nListTestCase.getLength());
		        
		        for (int testcase = 0; testcase < nListTestCase.getLength(); testcase++) {
		        	Node nTestCase= nListTestCase.item(testcase);
		        	Element eTestCase = (Element) nTestCase;
		        	
		        			        		
		        		Constants.TestCaseStatus = true;
		        		 
		        		ReportGenerator.initializeReport(reportDirectory, componentFileName);
		        	
		        		NodeList nListTestSteps = eTestCase.getElementsByTagName("Step");
		        		
		        		System.out.println("//==================//==================//==================//============");
		        		Log.info(" Test Case Id : "+ eTestCase.getAttribute("id")+" | Total Test Steps : " +nListTestSteps.getLength());
		        	
		        		for(int teststeps = 0; teststeps < nListTestSteps.getLength(); teststeps++){
		        			if(!Constants.TestCaseStatus){
		        			Log.warn("---------> Test Case Failed , Skipping remaining test steps");
		        				break;
		        			}
		        			Node nTestSteps = nListTestSteps.item(teststeps);
		        			Element eTestSteps = (Element) nTestSteps;
		        			sActionKeyword = eTestSteps.getElementsByTagName("Action").item(0).getTextContent();
		        			sObject = eTestSteps.getElementsByTagName("Object").item(0).getTextContent();
		        			sObjectValue = eTestSteps.getElementsByTagName("ObjectValue").item(0).getTextContent();
		        			
		        			//String testStatus;
		        			testStatus="Passed";
		        		
		        			//Log.info("ElementType:"+sElementType);
		        		    Log.info("Step: "+(teststeps)+" Action Keyword : "+sActionKeyword+" | Object : "+sObject+" | Object Value : "+sObjectValue);
		        			//String newobject = "(\"Membership\")";
		        			
		        			execute_Actions();
		        			if(!Constants.TestCaseStatus){
		        				testStatus = "Failed";
		        			}
		        			String testDetails = "Step: "+(teststeps+2)+" Action Keyword : "+sActionKeyword+" | Object : "+sObject+" | Object Value : "+sObjectValue;
		        			String screenshot;
		        			screenshot = ActionKeywords.takeScreenShot(reportDirectory);
							ReportGenerator.testStatus(testDetails, testStatus, screenshot);
		        					        			
		        		}
		        		//Log.TestResult();
		        		//Log.endTestCase(sTestCaseID);
		        		ReportGenerator.finalizeReport();
		        	}
		        }
			}	
	
	
	private static void execute_Actions() throws Exception {		
		Boolean methodFound = false ;		
		for(int i = 0; i < method.length; i++) {
			if(method[i].getName().equals(sActionKeyword)){
						
				method[i].invoke(actionKeywords,sObject,sObjectValue);
				//method[i].invoke(actionKeywords,sObject,sObjectValue,sElementType);
				methodFound = true;
				break;	
			}					
		}		
		if (!methodFound)
		{			
			Log.error("!--- Method,"+sActionKeyword+"-Not defined in ActionKeyword.java file.");
		}		
	}
	
	

}


