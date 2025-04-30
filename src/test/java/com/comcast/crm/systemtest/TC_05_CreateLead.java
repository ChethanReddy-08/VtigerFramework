package com.comcast.crm.systemtest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.PropertyFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreateNewLeadPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LeadsPage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class TC_05_CreateLead {
	@Test
	public void run() throws IOException {
		PropertyFileUtility f = new PropertyFileUtility();
		ExcelUtility e = new ExcelUtility();
		JavaUtility j = new JavaUtility();
		WebDriverUtility wd = new WebDriverUtility();
		// read common data from property file
		String browser = f.getDataFromPropertiesFile("browser");
		String url = f.getDataFromPropertiesFile("url");
		String username = f.getDataFromPropertiesFile("username");
		String password = f.getDataFromPropertiesFile("password");
				
		// read testscript data from excel file
		String lastname = e.getDataFromExcelFile("leads", 1, 1)+j.getRandomNumber();
		String company = e.getDataFromExcelFile("leads", 1, 2)+j.getRandomNumber();
			
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
		HomePage h = new HomePage(driver);
		h.getLeadslink().click();
		LeadsPage l = new LeadsPage(driver);
		l.getCreateNewLeadBtn().click();
		CreateNewLeadPage cnl = new CreateNewLeadPage(driver);
		cnl.createLead(lastname, company);
		
		String headerinfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (headerinfo.contains(lastname)) {
			System.out.println(lastname + " is verified");
		} else {
			System.out.println(lastname + " is not verified");

		}
		String actCompany=driver.findElement(By.id("dtlview_Company")).getText();
		if (company.equals(actCompany)) {
			System.out.println(company + " is verified");
		} else {
			System.out.println(company + " is not verified");

		}

		driver.quit();
	}

}
