package com.comcast.crm.basetest;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.comcast.crm.generic.databaseutility.DatabaseUtility;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.PropertyFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class BaseClass 
{
	public DatabaseUtility dblib = new DatabaseUtility();
	public PropertyFileUtility plib = new PropertyFileUtility();
	public ExcelUtility elib = new ExcelUtility();
	public JavaUtility jlib = new JavaUtility();
	public WebDriverUtility wdlib = new WebDriverUtility();
	public WebDriver driver = null;
	public static WebDriver sdriver = null;
	
	@BeforeSuite(groups = {"smokeTest","regressionTest"})
	public void configBS()
	{
		System.out.println("connect to DB , Report Config");
		dblib.getDBConnection();  
	}
	
	@Parameters("Browser")
	@BeforeClass(groups = {"smokeTest","regressionTest"})
	public void configBC() throws IOException
	{
		System.out.println("launch the browser");
		//String Browser = browser;
	   String Browser=plib.getDataFromPropertiesFile("browser");
		
		if (Browser.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (Browser.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (Browser.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}
		sdriver = driver;
		UtilityClassObject.setDriver(driver);
		
	}
	
	@BeforeMethod(groups = {"smokeTest","regressionTest"})
	public void configBM() throws IOException
	{
		System.out.println("Login");	
		LoginPage l = new LoginPage(driver);
		String url=plib.getDataFromPropertiesFile("url");
		String username=plib.getDataFromPropertiesFile("username");
		String password=plib.getDataFromPropertiesFile("password");
		l.logintoApp(url, username, password);
	}
	
	@AfterMethod
	public void configAM()
	{
		HomePage h = new HomePage(driver);
		h.logout();
		System.out.println("Logout");	
	} 
	
	@AfterClass 
	public void configAC()
	{
		driver.quit();
		System.out.println("closed the browser");
	}
	
	@AfterSuite
	public void configAS() throws SQLException
	{
		System.out.println("close DB, Report Backup");	
		dblib.closeDBConnection();
	}

}
