package com.comcast.crm.orgtest;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
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

public class CreateOrgWithIndustryTest {
	@Test
	public void run() throws EncryptedDocumentException, IOException 
	{
		//create object
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
		String orgname = e.getDataFromExcelFile("org", 4, 2) + j.getRandomNumber();
		String industry = e.getDataFromExcelFile("org", 4, 3);
		String type = e.getDataFromExcelFile("org", 4, 4);

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

		// Step 1: login to app
		driver.get(url);
		wd.waitForPagetoLoad(driver);
		wd.maximizeWindow(driver);
		LoginPage l = new LoginPage(driver);
		l.logintoApp(username, password);
		
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		driver.findElement(By.linkText("Organizations")).click();

		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname, industry, type);

		OrganizationsInfoPage oip = new OrganizationsInfoPage(driver);
		String actindustry = oip.getIndustrytext().getText();
		if (industry.contains(actindustry)) {
			System.out.println(industry + " is verified");
		} else {
			System.out.println(orgname + " is not verified");
		}
		
		String actType = oip.getTypetext().getText();
		if (type.contains(actType)) {
			System.out.println(type + " is verified");
		} else {
			System.out.println(type + " is not verified");
		}

		// step 5: log out
		driver.quit();
	}

}
