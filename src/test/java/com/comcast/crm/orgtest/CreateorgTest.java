package com.comcast.crm.orgtest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrgPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateorgTest 
{
	@Test
	public void createorg() throws IOException
	{
		FileUtility f = new FileUtility();
		ExcelUtility e = new ExcelUtility();
		JavaUtility j = new JavaUtility();
		WebDriverUtility wd = new WebDriverUtility();
		// read common data from property file
		String browser = f.getDataFromPropertiesFile("browser");
		String url = f.getDataFromPropertiesFile("url");
		String username = f.getDataFromPropertiesFile("username");
		String password = f.getDataFromPropertiesFile("password");
				
		// read testscript data from excel file
		String orgname = e.getDataFromExcelFile("org", 1, 2)+j.getRandomNumber();
			
		WebDriver driver = null;
		if (browser.equals("chrome")) {
			driver = new ChromeDriver();

		} else if (browser.equals("firefox")) {
			driver = new FirefoxDriver();

		} else if (browser.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}
		
		//Step 1: login to app
		driver.get(url);
		wd.waitForPagetoLoad(driver);
		wd.maximizeWindow(driver);
		LoginPage lp = new LoginPage(driver);
		lp.logintoApp(username, password);
		
		//Step 2: navigate to org module
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		
		//step 3: click on creat org button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		//step 4: enter all the details and create new org
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname);

		OrganizationsInfoPage oip = new OrganizationsInfoPage(driver);
		//verify header msg expected result
		String headerinfo = oip.getHeadertext().getText();
		if (headerinfo.contains(orgname)) {
			System.out.println(orgname + " is created");
		} else {
			System.out.println(orgname + " is not created");
		}
		
		//verify header orgname expected result
		String actOrgName = oip.getOrgtext().getText();
		if (actOrgName.contains(orgname)) {
			System.out.println(orgname + " is verified");
		} else {
			System.out.println(orgname + " is not verified");
		}
		
		//step 5: log out
		driver.quit();
		
	}

}
