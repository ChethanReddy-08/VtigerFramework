package com.comcast.crm.contacttest;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.PropertyFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.ContactsInfoPage;
import com.comcast.crm.objectrepositoryutility.ContactsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewContactPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOpportunityPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrgPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateContactandOrgTest {
	@Test
	public void run() throws IOException, InterruptedException {
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
		String orgname = e.getDataFromExcelFile("contact", 7, 2)+j.getRandomNumber();
		String lastname = e.getDataFromExcelFile("contact", 7, 3)+j.getRandomNumber();

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
		
		CreatingNewOrgPage cop = new CreatingNewOrgPage(driver);
		cop.createOrg(orgname);
	
		Thread.sleep(2000);
		h.getContactlink().click();
		
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateNewContactBtn().click();
		
		CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
		ccp.getlastnameEdt().sendKeys(lastname);
		driver.findElement(By.xpath("//input[@name='account_id']/following-sibling::img")).click();	
		String pw = driver.getWindowHandle();
		wd.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		driver.switchTo().window(pw);
		ccp.getSavebtn().click(); 
		
		ContactsInfoPage cip = new ContactsInfoPage(driver);
		
		// verify header msg expected result
		String headerinfo = cip.getheadertext().getText();
		if (headerinfo.contains(lastname)) {
			System.out.println(lastname + " is verfied");
		} else {
			System.out.println(lastname + " is not verified");
		}

		// verify header orgname expected result
		String actOrgName = cip.getOrgnametext().getText();
		if (actOrgName.trim().equals(orgname)) {
			System.out.println(orgname + " is verified");
		} else {
			System.out.println(orgname + " is not verified");
		}

		// step 5: log out
		driver.quit();
	}

}
