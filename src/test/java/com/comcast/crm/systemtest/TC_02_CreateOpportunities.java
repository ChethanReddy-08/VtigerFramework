package com.comcast.crm.systemtest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OpporutinitesPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class TC_02_CreateOpportunities {
	@Test
	public void run() throws IOException, InterruptedException {
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
		String orgname = e.getDataFromExcelFile("org", 13, 2) + j.getRandomNumber();
		String opportunityName = e.getDataFromExcelFile("org", 13, 3) + j.getRandomNumber();
		String SalesStageOption = e.getDataFromExcelFile("org", 13, 4);

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
		LoginPage lp = new LoginPage(driver);
		lp.logintoApp(username, password);
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		//step 3: click on creat org button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		driver.findElement(By.name("accountname")).sendKeys(orgname);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//verify header msg expected result
		String headerinfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (headerinfo.contains(orgname)) {
			System.out.println(orgname + " is created");
		} else {
			System.out.println(orgname + " is not created");
		}
		
		h.getOpportunitieslink().click();
		OpporutinitesPage opt = new OpporutinitesPage(driver);
		opt.getCreateNewOpportunityBtn().click();
		driver.findElement(By.name("potentialname")).sendKeys(opportunityName);
		WebElement sales = driver.findElement(By.name("sales_stage"));
		wd.select(sales, SalesStageOption.trim());
		driver.findElement(By.xpath("//input[@id='related_to']/following-sibling::img")).click();
		
		//switchto child window
		String parent = driver.getWindowHandle();
		wd.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		//switch to parent
		wd.switchToParentWindow(driver, parent);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		String Oppheaderinfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (Oppheaderinfo.contains(opportunityName)) {
			System.out.println(opportunityName + " is created and verified");
		} else {
			System.out.println(opportunityName + " is not created and verified");

		}
		
		//goback to opp and verify
		h.getOpportunitieslink().click();
		WebElement Indropdown = driver.findElement(By.name("search_field"));
		wd.select(Indropdown, 1);
		driver.findElement(By.name("search_text")).sendKeys(opportunityName,Keys.ENTER);
		
		String actopp = driver.findElement(By.linkText(opportunityName)).getText();
		if (actopp.contains(opportunityName)) {
			System.out.println(opportunityName + " is verified in opp page");
		} else {
			System.out.println(opportunityName + " is not verified in opp page");

		}
		 

		driver.quit();
	}

}
