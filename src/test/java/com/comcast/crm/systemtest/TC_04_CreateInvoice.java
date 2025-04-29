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
import com.comcast.crm.objectrepositoryutility.CreatingNewInvoicePage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrgPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewProductPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.InvoiceInfoPage;
import com.comcast.crm.objectrepositoryutility.InvoicePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;
import com.comcast.crm.objectrepositoryutility.ProductInfoPage;
import com.comcast.crm.objectrepositoryutility.ProductsPage;

public class TC_04_CreateInvoice {
	@Test
	public void run() throws IOException {
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
		String subject = e.getDataFromExcelFile("org", 19, 2) + j.getRandomNumber();
		String orgname = e.getDataFromExcelFile("org", 16, 3) + j.getRandomNumber();
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
		op.getCreateNewOrgBtn().click();
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname);
		OrganizationsInfoPage of = new OrganizationsInfoPage(driver);
		String actOrgName = of.getHeadertext().getText();
		if (actOrgName.contains(orgname)) {
			System.out.println(orgname + " is created and verified");
		} else {
			System.out.println(orgname + " is not created and verified");
		}

		// create product
		h.getProductslink().click();
		ProductsPage p = new ProductsPage(driver);
		p.getCreateNewProductBtn().click();
		CreatingNewProductPage cnpp = new CreatingNewProductPage(driver);
		cnpp.createProduct(productname);
		ProductInfoPage pf = new ProductInfoPage(driver);
		String actProductName =pf.getHeadertext().getText();
		if (actProductName.contains(productname)) {
			System.out.println(productname + " is created and verified");
		} else {
			System.out.println(productname + " is not created and verified");
		}
		
		//create invoice
		h.navigatetoInvoicePage();
		InvoicePage ip = new InvoicePage(driver);
		ip.getCreateNewInvoiceBtn().click();
		CreatingNewInvoicePage cnip = new CreatingNewInvoicePage(driver);
		cnip.getSubjectEdt().sendKeys(subject);
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();
		String Sparent = driver.getWindowHandle();
		wd.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname, Keys.ENTER);
		driver.findElement(By.xpath("//a[.='" + orgname + "']")).click();
		wd.switchToAlertandAccept(driver);
		// switch to parent
		wd.switchToParentWindow(driver, Sparent);
		cnip.getBillingedt().sendKeys(bill);
		cnip.getShippingedt().sendKeys(ship);
		
		WebElement itemname = driver.findElement(By.xpath("//input[@name='productName1']/following-sibling::img"));
		wd.scrolldowntoaParticularElement(driver, itemname);
		itemname.click();
		wd.switchToTabOnURL(driver, "Products&action");
		driver.findElement(By.id("search_txt")).sendKeys(productname, Keys.ENTER);
		driver.findElement(By.xpath("//a[.='" + productname + "']")).click();
		// switch to parent
		wd.switchToParentWindow(driver, Sparent);
		cnip.getQtyEdt().sendKeys(quantity);
		cnip.getSavebtn().click();
		InvoiceInfoPage iip = new InvoiceInfoPage(driver);
		String actInvoiceHeaderinfo = iip.getHeadertext().getText();
		System.out.println(actInvoiceHeaderinfo);
		if (actInvoiceHeaderinfo.contains(subject)) {
			System.out.println(subject + " is created and verified");
		} else {
			System.out.println(subject + " is not created and verified");
		}
		driver.quit();

	}
 
}
