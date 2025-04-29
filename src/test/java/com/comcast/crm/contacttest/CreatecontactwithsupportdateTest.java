package com.comcast.crm.contacttest;

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
import com.comcast.crm.objectrepositoryutility.ContactsInfoPage;
import com.comcast.crm.objectrepositoryutility.ContactsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewContactPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class CreatecontactwithsupportdateTest 
{
	@Test
	public void run() throws IOException 
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
		String lastname = e.getDataFromExcelFile("contact", 1, 3)+j.getRandomNumber();
		
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
			
			driver.get(url);
			wd.waitForPagetoLoad(driver);
			wd.maximizeWindow(driver);
			
			LoginPage l = new LoginPage(driver);
			l.logintoApp(username, password);

			HomePage h = new HomePage(driver);
			h.getContactlink().click();
			
			ContactsPage cp = new ContactsPage(driver);
			cp.getCreateNewContactBtn().click();
			
			CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
			
			String startDate = j.getSystemDateYYYYDDMM();
			String endDate=j.getRequiredDateYYYYDDMM(30);
			
			ccp.createContactwithSupporteDate(lastname, startDate, endDate);
			
			ContactsInfoPage cip = new ContactsInfoPage(driver);
			

			String actlastname = cip.getLastnametext().getText();
			if (lastname.contains(actlastname)) {
				System.out.println(lastname + " is verified");
			} else {
				System.out.println(lastname + " is not verified");
			}
			
			String actStartDate = cip.getStartDatetext().getText();
			if (startDate.contains(actStartDate)) {
				System.out.println(startDate + " is verified");
			} else {
				System.out.println(startDate + " is not verified");
			}
			
			String actEndDate = cip.getEndDatetext().getText();
			if (endDate.contains(actEndDate)) {
				System.out.println(endDate + " is verified");
			} else {
				System.out.println(endDate + " is not verified");
			}

			driver.quit();

}
}
