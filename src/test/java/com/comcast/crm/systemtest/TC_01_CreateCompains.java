package com.comcast.crm.systemtest;

import java.io.IOException;

import org.openqa.selenium.By;
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
import com.comcast.crm.objectrepositoryutility.CampaignInfoEdtitingPage;
import com.comcast.crm.objectrepositoryutility.CampaignsInfoPage;
import com.comcast.crm.objectrepositoryutility.CampaignsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewCampaignsPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class TC_01_CreateCompains 
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
		String campaignsName = e.getDataFromExcelFile("org", 10, 2)+j.getRandomNumber();
		String newcampaignsName = e.getDataFromExcelFile("org", 10, 2)+j.getRandomNumber();
			
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
		
		h.navigatetoCampaignsPage();
		CampaignsPage cp = new CampaignsPage(driver);
		cp.getCreateNewCampaignBtn().click();
		
		CreatingNewCampaignsPage cncp = new CreatingNewCampaignsPage(driver);
		cncp.CreateCampaign(campaignsName);
		
		CampaignsInfoPage cip = new CampaignsInfoPage(driver);
		
		String actHeaderInfo = cip.getHeadertext().getText();
		if (actHeaderInfo.contains(campaignsName)) {
			System.out.println(campaignsName + " is verified");
		} else {
			System.out.println(campaignsName + " is not verified");
		}
		
		//edit campains name and date
		cip.getEditBtn().click();
		CampaignInfoEdtitingPage cep = new CampaignInfoEdtitingPage(driver);
		cep.getCampaignnameEdt().clear();
		cep.getCampaignnameEdt().sendKeys(newcampaignsName);
		
		cep.getClosingdateEdt().clear();
		String closingDate = j.getRequiredDateYYYYDDMM(30);
		cep.getClosingdateEdt().sendKeys(closingDate);
		cep.getSaveBtn().click();
		
		//verify editedinfo and closing date
		String editedinfo = cip.getHeadertext().getText();
		if (editedinfo.contains(newcampaignsName)) {
			System.out.println(newcampaignsName + " is edited successfully");
		} else {
			System.out.println(newcampaignsName + " is not edited");
		}
		
		String actEndDate = cip.getendDatetxt().getText();
		System.out.println(actEndDate);
		if (actEndDate.trim().equals(closingDate)) {
			System.out.println(closingDate + " is verified");
		} else {
			System.out.println(closingDate + " is not verified");
		}
		driver.quit();
	}
	
	

}
