package com.comcast.crm.opportunitytest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.PropertyFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OpportunityInfoPage;
import com.comcast.crm.objectrepositoryutility.OpporutinitesPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateOpportunities extends BaseClass{
	@Test
	public void createOpportunityWithOrg() throws IOException, InterruptedException {
		// read testscript data from excel file
		String orgname = elib.getDataFromExcelFile("org", 13, 2) + jlib.getRandomNumber();
		String opportunityName = elib.getDataFromExcelFile("org", 13, 3) + jlib.getRandomNumber();
		String SalesStageOption = elib.getDataFromExcelFile("org", 13, 4);

		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		//step 3: click on creat org button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		driver.findElement(By.name("accountname")).sendKeys(orgname);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//verify header msg expected result
		OrganizationsInfoPage oip = new OrganizationsInfoPage(driver);
		String headerinfo = oip.getHeadertext().getText();
		assertEquals(headerinfo.contains(orgname), true);
		
		
		h.getOpportunitieslink().click();
		OpporutinitesPage opt = new OpporutinitesPage(driver);
		opt.getCreateNewOpportunityBtn().click();
		driver.findElement(By.name("potentialname")).sendKeys(opportunityName);
		WebElement sales = driver.findElement(By.name("sales_stage"));
		wdlib.select(sales, SalesStageOption.trim());
		driver.findElement(By.xpath("//input[@id='related_to']/following-sibling::img")).click();
		
		//switchto child window
		String parent = driver.getWindowHandle();
		wdlib.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		//switch to parent
		wdlib.switchToParentWindow(driver, parent);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		OpportunityInfoPage p = new OpportunityInfoPage(driver);
		String Oppheaderinfo = p.getHeadertext().getText();
		assertEquals(Oppheaderinfo.contains(opportunityName), true);
		
		//goback to opp and verify
		h.getOpportunitieslink().click();
		WebElement Indropdown = driver.findElement(By.name("search_field"));
		wdlib.select(Indropdown, 1);
		driver.findElement(By.name("search_text")).sendKeys(opportunityName,Keys.ENTER);
		
		String actopp = driver.findElement(By.linkText(opportunityName)).getText();
		assertEquals(actopp.contains(opportunityName), true);
		
		 
	}

}
