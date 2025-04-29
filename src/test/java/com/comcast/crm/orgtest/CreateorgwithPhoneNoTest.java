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

public class CreateorgwithPhoneNoTest {
	@Test
	public void run() throws IOException {
		// create object
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
		String orgname = e.getDataFromExcelFile("org", 7, 2) + j.getRandomNumber();
		String phone = e.getDataFromExcelFile("org", 7, 3);

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

		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname, phone);

		// verify Phone no expected result
		OrganizationsInfoPage oip = new OrganizationsInfoPage(driver);
		String actPhone = oip.getphonetext().getText();
		if (phone.contains(actPhone)) {
			System.out.println(phone + " is verified");
		} else {
			System.out.println(phone + " is not verified");
		}

		// step 5: log out
		driver.quit();
	}

}
