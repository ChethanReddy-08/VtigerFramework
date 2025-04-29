package com.comcast.crm.systemtest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreatingNewProductPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.ProductsPage;

public class TC_06_CreateProduct 
{
	@Test
	public void run() throws IOException {
		// read common data from property file
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
		String productname = e.getDataFromExcelFile("products", 1, 1)+j.getRandomNumber();
			
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
		h.getProductslink().click();
		ProductsPage p = new ProductsPage(driver);
		p.getCreateNewProductBtn().click();
		CreatingNewProductPage cnp = new CreatingNewProductPage(driver);
		cnp.getProductNameEdt().sendKeys(productname);
		cnp.getSavebtn().click();

		String headerinfo = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if (headerinfo.contains(productname)) {
			System.out.println(productname + " is verified");
		} else {
			System.out.println(productname + " is not verified");

		}


		driver.quit();
	}


}
