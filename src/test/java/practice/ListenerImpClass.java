package practice;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerImpClass implements ITestListener, ISuiteListener
{

	@Override
	public void onStart(ISuite suite) {
		System.out.println("onStart from ISuiteListener");
		System.out.println(suite.getName());
		
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("onFinish from ISuiteListener");
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("onTestStart");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("onTestSuccess");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("onTestFailure");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("onTestSkipped");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage");	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		System.out.println("onTestFailedWithTimeout");
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("onStart from ITestListener");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("onFinish from ITestListener");
	}
	

}
