package practice;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseClass 
{
	@BeforeSuite
	public void configBS()
	{
		System.out.println("Before suite");
	}

	@BeforeTest
	public void configBT()
	{
		System.out.println("Before test");

	}

	@BeforeClass
	public void configBC()
	{
		System.out.println("Before class");

	}

	@BeforeMethod
	public void configBM()
	{
		System.out.println("Before method");

	}
	@AfterMethod
	public void configAM()
	{
		System.out.println("After Method");
	}

	@AfterClass
	public void configAC()
	{
		System.out.println("After Class");

	}

	@AfterTest
	public void configAT()
	{
		System.out.println("After Test");

	}

	@AfterSuite
	public void configAS()
	{
		System.out.println("After Suite");

	}
}
