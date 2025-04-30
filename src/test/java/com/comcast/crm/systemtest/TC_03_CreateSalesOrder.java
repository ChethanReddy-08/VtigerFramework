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
import com.comcast.crm.generic.fileutility.PropertyFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreatingNewOpportunityPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrgPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewProductPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewSalesOrderPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OpportunityInfoPage;
import com.comcast.crm.objectrepositoryutility.OpporutinitesPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;
import com.comcast.crm.objectrepositoryutility.ProductInfoPage;
import com.comcast.crm.objectrepositoryutility.ProductsPage;
import com.comcast.crm.objectrepositoryutility.SalesOrderInfoPage;
import com.comcast.crm.objectrepositoryutility.SalesOrderPage;

public class TC_03_CreateSalesOrder 
{
	@Test
	public void run() throws IOException
	{
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
		String subject = e.getDataFromExcelFile("org", 16, 2) + j.getRandomNumber();
		String orgname = e.getDataFromExcelFile("org", 16, 3) + j.getRandomNumber();
		String opportunityName = e.getDataFromExcelFile("org", 16, 4) + j.getRandomNumber();
		String bill = e.getDataFromExcelFile("org", 16, 5);
		String ship = e.getDataFromExcelFile("org", 16, 6);
		String productname = e.getDataFromExcelFile("org", 16, 7) + j.getRandomNumber();
		String quantity = e.getDataFromExcelFile("org", 16, 8);


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
		
		//create org
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		OrganizationsPage op = new OrganizationsPage(driver);
		CreatingNewOrgPage cp = new CreatingNewOrgPage(driver);
		OrganizationsInfoPage oi = new OrganizationsInfoPage(driver);
		op.getCreateNewOrgBtn().click();
		cp.createOrg(orgname);
		String actOrgName = oi.getHeadertext().getText();
		if (actOrgName.contains(orgname)) {
			System.out.println(orgname + " is created and verified");
		} else {
			System.out.println(orgname + " is not created and verified");
		}
		
		//create opportunity
		h.getOpportunitieslink().click();
		OpporutinitesPage opt = new OpporutinitesPage(driver);
		opt.getCreateNewOpportunityBtn().click();
		CreatingNewOpportunityPage cnp = new CreatingNewOpportunityPage(driver);
		cnp.getOpportunityNameEdt().sendKeys(opportunityName);
		cnp.getSelectOrgbtn().click();
		//switchto child window
		String oppparent = driver.getWindowHandle();
		wd.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		//switch to parent
		wd.switchToParentWindow(driver, oppparent);
		cnp.getSavebtn().click();
		OpportunityInfoPage of = new OpportunityInfoPage(driver);
		String Oppheaderinfo = of.getHeadertext().getText();
		if (Oppheaderinfo.contains(opportunityName)) {
			System.out.println(opportunityName + " is created and verified");
		} else {
	 		System.out.println(opportunityName + " is not created and verified");
		}
		
		//create product
		h.getProductslink().click();
		ProductsPage p = new ProductsPage(driver);
		p.getCreateNewProductBtn().click();
		CreatingNewProductPage cnpp = new CreatingNewProductPage(driver);
		cnpp.createProduct(productname);
		ProductInfoPage pf = new ProductInfoPage(driver);
		String actProductName = pf.getHeadertext().getText();
		if (actProductName.contains(productname)) {
			System.out.println(productname + " is created and verified");
		} else {
			System.out.println(productname + " is not created and verified");
		}
		
		//create sales order
		h.navigatetoSalesOrderPage();
		SalesOrderPage sp = new SalesOrderPage(driver);
		sp.getCreateNewSalesOrderBtn().click();
		CreatingNewSalesOrderPage cs = new CreatingNewSalesOrderPage(driver);
		cs.getSubjectEdt().sendKeys(subject);
		
		driver.findElement(By.xpath("//input[@name='potential_name']/following-sibling::img")).click();
		String Sparent = driver.getWindowHandle();
		wd.switchToTabOnURL(driver, "Potentials&action");
		driver.findElement(By.id("search_txt")).sendKeys(opportunityName,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ opportunityName +"']")).click();
		//switch to parent
		wd.switchToParentWindow(driver, Sparent);
		
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();
		wd.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		wd.switchToAlertandAccept(driver);
		//switch to parent
		wd.switchToParentWindow(driver, Sparent);
		WebElement billing = cs.getBillingedt();
		
		//scroll down to bill
		wd.scrolldowntoaParticularElement(driver, billing);
		billing.sendKeys(bill);
		cs.getShippingedt().sendKeys(ship);
		//scroll down to item
		WebElement item = driver.findElement(By.name("productName1"));
		wd.scrolldowntoaParticularElement(driver, item);
		
		driver.findElement(By.xpath("//input[@name='productName1']/following-sibling::img")).click();
		wd.switchToTabOnURL(driver, "Products&action");
		driver.findElement(By.id("search_txt")).sendKeys(productname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ productname +"']")).click();
		//switch to parent
		wd.switchToParentWindow(driver, Sparent);
		cs.getQtyEdt().sendKeys(quantity);
		cs.getSavebtn();
	
		SalesOrderInfoPage soi = new SalesOrderInfoPage(driver);
		String actSalesHeaderinfo = soi.getHeaderinfo().getText();
		if (actSalesHeaderinfo.contains(subject)) {
			System.out.println(subject + " is created and verified");
		} else {
			System.out.println(subject + " is not created and verified");
		}
				
		driver.close();
		
	}
	
}
