package Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager extends  TestListenerAdapter{
	public ExtentReports extent;
	public   ExtentTest test;
	public ExtentHtmlReporter Reporter;

	public void onStart(ITestContext testContext) {

		//For Create File Name With Time Stamp

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repName = "Test-Reports-"+timeStamp+".html";

		// To Give Path Of The HTML File
		Reporter = new ExtentHtmlReporter(System.getProperty("user.dir")+ "/Reports/"+repName);
		
		//Path of the extentconfig.xml file 
		Reporter.loadXMLConfig(System.getProperty("user.dir")+ "/extentconfig.xml");

		// Set SystemInfo

		extent = new ExtentReports();
		extent.attachReporter(Reporter);
		extent.setSystemInfo("Host", "LocalHost");
		extent.setSystemInfo("Os", "Windows11");
		extent.setSystemInfo("Tester", "Krishna");
		extent.setSystemInfo("Environment", "QA");

		//To Give Title For Project
		Reporter.config().setDocumentTitle("Project Name");
		Reporter.config().setReportName("Report Name");
		Reporter.config().setTheme(Theme.STANDARD);
	}

	//Create Label For PassTests
	public void onTestSuccess(ITestResult tr) {
		test = extent.createTest(tr.getName());
		test.log(Status.PASS, MarkupHelper.createLabel(tr.getName(),ExtentColor.GREEN));
	}

	//Create Label For FaiTests
	public void onTestFailure(ITestResult tr) {
		test=extent.createTest(tr.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
	}

	//Create A Label For SkipedTests
	public void onTestSkipped(ITestResult tr) {
		test=extent.createTest(tr.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(),ExtentColor.ORANGE));
	}
	//To End The Extent
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
}

