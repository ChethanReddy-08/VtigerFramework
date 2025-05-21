package com.comcast.crm.producttest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.PropertyFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreatingNewProductPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.ProductInfoPage;
import com.comcast.crm.objectrepositoryutility.ProductsPage;

public class CreateProduct extends BaseClass
{
	@Test
	public void createProduct() throws IOException {
				
		// read testscript data from excel file
		String productname = elib.getDataFromExcelFile("products", 1, 1)+jlib.getRandomNumber();
	
		HomePage h = new HomePage(driver);
		h.getProductslink().click();
		
		ProductsPage p = new ProductsPage(driver);
		p.getCreateNewProductBtn().click();
		
		CreatingNewProductPage cnp = new CreatingNewProductPage(driver);
		cnp.getProductNameEdt().sendKeys(productname);
		cnp.getSavebtn().click();

		ProductInfoPage pi = new ProductInfoPage(driver);
		String headerinfo = pi.getHeadertext().getText();
		
		assertEquals(headerinfo.contains(productname), true);
		
	}


}
