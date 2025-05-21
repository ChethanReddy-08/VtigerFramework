package com.comcast.crm.orgtest;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrgPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OrganizationsInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

public class CreateOrganizationTest extends BaseClass
{
	SoftAssert sa = new SoftAssert();
	@Test
	public void createOrgTest() throws IOException
	{
		UtilityClassObject.getTest().log(Status.INFO, "read data from Excel");
		String orgname = elib.getDataFromExcelFile("org", 1, 2)+jlib.getRandomNumber();
		
		UtilityClassObject.getTest().log(Status.INFO, "navigate to org module");
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		
		UtilityClassObject.getTest().log(Status.INFO, "click on org link");
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		UtilityClassObject.getTest().log(Status.INFO, "enter all the details and create new org");
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname);

		OrganizationsInfoPage oip = new OrganizationsInfoPage(driver);
		
		//verify header msg expected result
		String headerinfo = oip.getHeadertext().getText();
		boolean status = headerinfo.contains(orgname);
		assertTrue(status);
		
		//verify header orgname expected result
		String actOrgName = oip.getOrgtext().getText();
		sa.assertEquals(actOrgName,orgname);
		sa.assertAll();
			
	}
	@Test
	public void createOrgWithIndustryTest() throws EncryptedDocumentException, IOException 
	{
		// read testscript data from excel file
		String orgname = elib.getDataFromExcelFile("org", 4, 2) + jlib.getRandomNumber();
		String industry = elib.getDataFromExcelFile("org", 4, 3);
		String type = elib.getDataFromExcelFile("org", 4, 4);
		
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();
		driver.findElement(By.linkText("Organizations")).click();

		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname, industry, type);

		OrganizationsInfoPage oip = new OrganizationsInfoPage(driver);
		
		String actindustry = oip.getIndustrytext().getText();
		sa.assertEquals(actindustry, industry);
		
		String actType = oip.getTypetext().getText();
		sa.assertEquals(actType, type);
		sa.assertAll();
	}
	@Test
	public void createOrgWithPhoneNoTest() throws IOException {
		// read testscript data from excel file
		String orgname = elib.getDataFromExcelFile("org", 7, 2) + jlib.getRandomNumber();
		String phone = elib.getDataFromExcelFile("org", 7, 3);
		
		HomePage h = new HomePage(driver);
		h.getOrgLink().click();

		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		
		CreatingNewOrgPage cnp = new CreatingNewOrgPage(driver);
		cnp.createOrg(orgname, phone);

		// verify Phone no expected result
		OrganizationsInfoPage oip = new OrganizationsInfoPage(driver);
		String actPhone = oip.getphonetext().getText();
		sa.assertEquals(actPhone, phone);

	}

}
