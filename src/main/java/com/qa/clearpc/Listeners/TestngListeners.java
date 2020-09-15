package com.qa.clearpc.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.SeleniumUtility;
import com.relevantcodes.extentreports.LogStatus;

public class TestngListeners extends BaseClass implements ITestListener
{

	SeleniumUtility seleniumUtility;
	
	public void onTestStart(ITestResult result) {
		System.out.println("Test Execution started" +result.getMethod().getMethodName());
		
	}

	public void onTestSuccess(ITestResult result) {
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			try {
				extentTest.log(LogStatus.PASS, "TESTCASE IS PASS  "+result.getMethod().getMethodName());
				seleniumUtility = new SeleniumUtility();
				seleniumUtility.getScreenshot(result.getMethod().getMethodName());
			} catch (Exception e) {
				log.error("failed to take screenshot on successful testcase execution", e);
				e.printStackTrace();
			}
		}
	
	}

	public void onTestFailure(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE)
		{
			try {
				extentTest.log(LogStatus.FAIL, "TESTCASE IS FAILED  "+result.getMethod().getMethodName());
				extentTest.log(LogStatus.FAIL, "TESTCASE IS FAILED  "+result.getThrowable());
				seleniumUtility = new SeleniumUtility();
				String screenshotPath=seleniumUtility.getScreenshot(result.getMethod().getMethodName());
				extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath));
			} catch (Exception e) {
				System.out.println("Failed to attach screenshot to extent report  " +e.getMessage());
				log.error("failed to attach screenshot to Extent report" ,e);
				e.printStackTrace();
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		if(result.getStatus()==ITestResult.SKIP)
		{
			extentTest.log(LogStatus.SKIP, "TESTCASE IS SKIPPED" +result.getMethod().getMethodName());
			extentTest.log(LogStatus.SKIP, "TESTCASE IS SKIPPED" +result.getThrowable());
			
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
