package com.comcast.crm.invoicetest;

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

public class CreateInvoiceTest extends BaseClass{
	@Test
	public void createInvoiceWithOrgAndProduct() throws IOException {
		
		// read testscript data from excel file
		String subject = elib.getDataFromExcelFile("org", 19, 2) + jlib.getRandomNumber();
		String orgname = elib.getDataFromExcelFile("org", 16, 3) + jlib.getRandomNumber();
		String bill = elib.getDataFromExcelFile("org", 16, 5);
		String ship = elib.getDataFromExcelFile("org", 16, 6);
		String productname = elib.getDataFromExcelFile("org", 16, 7) + jlib.getRandomNumber();
		String quantity = elib.getDataFromExcelFile("org", 16, 8);


		//create org
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname);
		OrganizationsInfoPage of = new OrganizationsInfoPage(driver);
		String actOrgName = of.getHeadertext().getText();
		boolean org = actOrgName.contains(orgname);
		assertEquals(org, true);

		// create product
		h.getProductslink().click();
		ProductsPage p = new ProductsPage(driver);
		p.getCreateNewProductBtn().click();
		CreatingNewProductPage cnpp = new CreatingNewProductPage(driver);
		cnpp.createProduct(productname);
		ProductInfoPage pf = new ProductInfoPage(driver);
		String actProductName =pf.getHeadertext().getText();
		boolean status = actProductName.contains(productname);
		assertEquals(status, true);
		
		//create invoice
		h.navigatetoInvoicePage();
		InvoicePage ip = new InvoicePage(driver);
		ip.getCreateNewInvoiceBtn().click();
		CreatingNewInvoicePage cnip = new CreatingNewInvoicePage(driver);
		cnip.getSubjectEdt().sendKeys(subject);
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();
		String Sparent = driver.getWindowHandle();
		wdlib.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname, Keys.ENTER);
		driver.findElement(By.xpath("//a[.='" + orgname + "']")).click();
		wdlib.switchToAlertandAccept(driver);
		// switch to parent
		wdlib.switchToParentWindow(driver, Sparent);
		cnip.getBillingedt().sendKeys(bill);
		cnip.getShippingedt().sendKeys(ship);
		
		WebElement itemname = driver.findElement(By.xpath("//input[@name='productName1']/following-sibling::img"));
		wdlib.scrolldowntoaParticularElement(driver, itemname);
		itemname.click();
		wdlib.switchToTabOnURL(driver, "Products&action");
		driver.findElement(By.id("search_txt")).sendKeys(productname, Keys.ENTER);
		driver.findElement(By.xpath("//a[.='" + productname + "']")).click();
		// switch to parent
		wdlib.switchToParentWindow(driver, Sparent);
		cnip.getQtyEdt().sendKeys(quantity);
		cnip.getSavebtn().click();
		InvoiceInfoPage iip = new InvoiceInfoPage(driver);
		String actInvoiceHeaderinfo = iip.getHeadertext().getText();
		boolean info = actInvoiceHeaderinfo.contains(subject);
		assertEquals(info, false);

	}
 
}
