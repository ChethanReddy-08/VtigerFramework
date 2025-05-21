package com.comcast.crm.leadtest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.lang.ref.SoftReference;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.objectrepositoryutility.CreateNewLeadPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LeadsPage;

public class CreateLead extends BaseClass {
	@Test
	public void createLead() throws IOException {
		// read testscript data from excel file
		String lastname = elib.getDataFromExcelFile("leads", 1, 1)+jlib.getRandomNumber();
		String company = elib.getDataFromExcelFile("leads", 1, 2)+jlib.getRandomNumber();

		HomePage h = new HomePage(driver);
		h.getLeadslink().click();
		
		LeadsPage l = new LeadsPage(driver);
		l.getCreateNewLeadBtn().click();
		
		CreateNewLeadPage cnl = new CreateNewLeadPage(driver);
		cnl.createLead(lastname, company);
		
		String headerinfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		assertEquals(headerinfo.contains(lastname), true);
		
		String actCompany=driver.findElement(By.id("dtlview_Company")).getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actCompany, company);
		sa.assertAll();
	}

}
