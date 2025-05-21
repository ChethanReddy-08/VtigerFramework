package com.comcast.crm.contacttest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.PropertyFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.ContactsInfoPage;
import com.comcast.crm.objectrepositoryutility.ContactsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewContactPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrgPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateContactTest extends BaseClass{
	
	SoftAssert sa = new SoftAssert();
	@Test(groups = {"smokeTest"})
	public void createContactTest() throws IOException {

		// read testscript data from excel file
		String lastname = elib.getDataFromExcelFile("contact", 1, 3) + jlib.getRandomNumber();
		
		HomePage h = new HomePage(driver);
		h.getContactlink().click();
		
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateNewContactBtn().click();
		
		CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
		ccp.createContact(lastname);

		ContactsInfoPage cip = new ContactsInfoPage(driver);
		// verify Phone no expected result
		String actlastname = cip.getLastnametext().getText();
		sa.assertEquals(actlastname, lastname);
		sa.assertAll();
		
	}

	@Test(groups = {"regressionTest"})
	public void createContactAndOrgTest() throws IOException, InterruptedException {

		// read testscript data from excel file
		String orgname = elib.getDataFromExcelFile("contact", 7, 2)+jlib.getRandomNumber();
		String lastname = elib.getDataFromExcelFile("contact", 7, 3)+jlib.getRandomNumber();

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
		wdlib.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		driver.switchTo().window(pw);
		ccp.getSavebtn().click(); 
		
		ContactsInfoPage cip = new ContactsInfoPage(driver);
		
		// verify header msg expected result
		String headerinfo = cip.getheadertext().getText();
		boolean status = headerinfo.contains(lastname);
		assertEquals(status, true);
		
		// verify header orgname expected result
		String actOrgName = cip.getOrgnametext().getText();
		sa.assertEquals(actOrgName.trim(), orgname);
		sa.assertAll();
	}
	
	@Test(groups = {"regressionTest"})
	public void createContactwithSupportDateTest() throws IOException 
	{
		// read testscript data from excel file
		String lastname = elib.getDataFromExcelFile("contact", 1, 3)+jlib.getRandomNumber();
		
			HomePage h = new HomePage(driver);
			h.getContactlink().click();
			
			ContactsPage cp = new ContactsPage(driver);
			cp.getCreateNewContactBtn().click();
			
			CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
			
			String startDate = jlib.getSystemDateYYYYDDMM();
			String endDate=jlib.getRequiredDateYYYYDDMM(30);
			
			ccp.createContactwithSupporteDate(lastname, startDate, endDate);
			
			ContactsInfoPage cip = new ContactsInfoPage(driver);
			
			String actlastname = cip.getLastnametext().getText();
			sa.assertEquals(actlastname, lastname);
			
			String actStartDate = cip.getStartDatetext().getText();
			sa.assertEquals(actStartDate, startDate);
			
			String actEndDate = cip.getEndDatetext().getText();
			sa.assertEquals(actEndDate, endDate);
			sa.assertAll();

}

}
