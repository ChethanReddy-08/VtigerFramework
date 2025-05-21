package com.comcast.crm.salesordertest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.comcast.crm.basetest.BaseClass;

import com.comcast.crm.objectrepositoryutility.CreatingNewOpportunityPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrgPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewProductPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewSalesOrderPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OpportunityInfoPage;
import com.comcast.crm.objectrepositoryutility.OpporutinitesPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;
import com.comcast.crm.objectrepositoryutility.ProductInfoPage;
import com.comcast.crm.objectrepositoryutility.ProductsPage;
import com.comcast.crm.objectrepositoryutility.SalesOrderInfoPage;
import com.comcast.crm.objectrepositoryutility.SalesOrderPage;

public class CreateSalesOrder extends BaseClass
{
	@Test
	public void createSalesOrderWithOrgAndOpportunityAndProduct() throws IOException
	{
		// read testscript data from excel file		
		String subject = elib.getDataFromExcelFile("org", 16, 2) + jlib.getRandomNumber();
		String orgname = elib.getDataFromExcelFile("org", 16, 3) + jlib.getRandomNumber();
		String opportunityName = elib.getDataFromExcelFile("org", 16, 4) + jlib.getRandomNumber();
		String bill = elib.getDataFromExcelFile("org", 16, 5);
		String ship = elib.getDataFromExcelFile("org", 16, 6);
		String productname = elib.getDataFromExcelFile("org", 16, 7) + jlib.getRandomNumber();
		String quantity = elib.getDataFromExcelFile("org", 16, 8);
		
		//create org
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		OrganizationsPage op = new OrganizationsPage(driver);
		CreatingNewOrgPage cp = new CreatingNewOrgPage(driver);
		OrganizationsInfoPage oi = new OrganizationsInfoPage(driver);
		op.getCreateNewOrgBtn().click();
		cp.createOrg(orgname);
		
		String actOrgName = oi.getHeadertext().getText();
		assertEquals(actOrgName.contains(orgname), true);
		
		//create opportunity
		h.getOpportunitieslink().click();
		OpporutinitesPage opt = new OpporutinitesPage(driver);
		opt.getCreateNewOpportunityBtn().click();
		CreatingNewOpportunityPage cnp = new CreatingNewOpportunityPage(driver);
		cnp.getOpportunityNameEdt().sendKeys(opportunityName);
		cnp.getSelectOrgbtn().click();
		//switchto child window
		String oppparent = driver.getWindowHandle();
		wdlib.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		//switch to parent
		wdlib.switchToParentWindow(driver, oppparent);
		cnp.getSavebtn().click();
		OpportunityInfoPage of = new OpportunityInfoPage(driver);
		String Oppheaderinfo = of.getHeadertext().getText();
		assertEquals(Oppheaderinfo.contains(opportunityName), true);
		
		//create product
		h.getProductslink().click();
		ProductsPage p = new ProductsPage(driver);
		p.getCreateNewProductBtn().click();
		CreatingNewProductPage cnpp = new CreatingNewProductPage(driver);
		cnpp.createProduct(productname);
		ProductInfoPage pf = new ProductInfoPage(driver);
		String actProductName = pf.getHeadertext().getText();
		assertEquals(actProductName.contains(productname), true);
		
		//create sales order
		h.navigatetoSalesOrderPage();
		SalesOrderPage sp = new SalesOrderPage(driver);
		sp.getCreateNewSalesOrderBtn().click();
		CreatingNewSalesOrderPage cs = new CreatingNewSalesOrderPage(driver);
		cs.getSubjectEdt().sendKeys(subject);
		
		driver.findElement(By.xpath("//input[@name='potential_name']/following-sibling::img")).click();
		String Sparent = driver.getWindowHandle();
		wdlib.switchToTabOnURL(driver, "Potentials&action");
		driver.findElement(By.id("search_txt")).sendKeys(opportunityName,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ opportunityName +"']")).click();
		//switch to parent
		wdlib.switchToParentWindow(driver, Sparent);
		
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();
		wdlib.switchToTabOnURL(driver, "Accounts&action");
		driver.findElement(By.id("search_txt")).sendKeys(orgname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ orgname +"']")).click();
		wdlib.switchToAlertandAccept(driver);
		//switch to parent
		wdlib.switchToParentWindow(driver, Sparent);
		WebElement billing = cs.getBillingedt();
		
		//scroll down to bill
		wdlib.scrolldowntoaParticularElement(driver, billing);
		billing.sendKeys(bill);
		cs.getShippingedt().sendKeys(ship);
		//scroll down to item
		WebElement item = driver.findElement(By.name("productName1"));
		wdlib.scrolldowntoaParticularElement(driver, item);
		
		driver.findElement(By.xpath("//input[@name='productName1']/following-sibling::img")).click();
		wdlib.switchToTabOnURL(driver, "Products&action");
		driver.findElement(By.id("search_txt")).sendKeys(productname,Keys.ENTER);
		driver.findElement(By.xpath("//a[.='"+ productname +"']")).click();
		//switch to parent
		wdlib.switchToParentWindow(driver, Sparent);
		cs.getQtyEdt().sendKeys(quantity);
		cs.getSavebtn().click();
		SalesOrderInfoPage soi = new SalesOrderInfoPage(driver);
		String actSalesHeaderinfo = soi.getHeaderinfo().getText();
		
		assertEquals(actSalesHeaderinfo.contains(subject), true);
		
		
	}
	
}
