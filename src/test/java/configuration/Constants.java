package configuration;

public class Constants {
	
	
	public static final String PathTestPlan =System.getProperty("user.dir")+ "\\src\\test\\java\\testDataEngine\\TestPlan.xml";
	public static final String Path_OR =System.getProperty("user.dir")+ "\\src\\test\\java\\configuration\\OR.txt";
	
	public static final String FileTestData = "Keyword.xlsx";
	
	public static int TotalTestCase;
	public static boolean TestCaseStatus;
	
	
	 public static String getCurrentDirectoryPath() {
		 
	        String currentDirectory = System.getProperty("user.dir");
	        
	        return currentDirectory;
	  }
	 
	 public static String getXMLDirectoryPath(String _filename) {
		 
		 String curdir = getCurrentDirectoryPath();
		 
		 return curdir = curdir+"\\src\\test\\java\\testDataEngine\\"+_filename;
		 
	 }

}
