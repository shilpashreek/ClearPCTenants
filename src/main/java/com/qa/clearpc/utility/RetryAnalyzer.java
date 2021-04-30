package com.qa.clearpc.utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	@Override
	public boolean retry(ITestResult arg0) {
		int counter = 0;
		int retryLimit = 2;
		if (counter < retryLimit) {
			counter++;
			return true;

		}
		return false;
	}

}
