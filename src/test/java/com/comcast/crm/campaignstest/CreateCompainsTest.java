package com.comcast.crm.campaignstest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.IOException;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.objectrepositoryutility.CampaignInfoEdtitingPage;
import com.comcast.crm.objectrepositoryutility.CampaignsInfoPage;
import com.comcast.crm.objectrepositoryutility.CampaignsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewCampaignsPage;
import com.comcast.crm.objectrepositoryutility.HomePage;

public class CreateCompainsTest extends BaseClass
{
	SoftAssert sa = new SoftAssert();
	@Test
	public void createCompainsTest() throws IOException
	{
		// read testscript data from excel file
		String campaignsName = elib.getDataFromExcelFile("org", 10, 2)+jlib.getRandomNumber();
		String newcampaignsName = elib.getDataFromExcelFile("org", 10, 2)+jlib.getRandomNumber();
			
		HomePage h = new HomePage(driver);
		
		h.navigatetoCampaignsPage();
		CampaignsPage cp = new CampaignsPage(driver);
		cp.getCreateNewCampaignBtn().click();
		
		CreatingNewCampaignsPage cncp = new CreatingNewCampaignsPage(driver);
		cncp.CreateCampaign(campaignsName);
		
		CampaignsInfoPage cip = new CampaignsInfoPage(driver);
		
		String actHeaderInfo = cip.getHeadertext().getText();
		boolean status = actHeaderInfo.contains(campaignsName);
		assertEquals(status, true);
		
		
		//edit campains name and date
		cip.getEditBtn().click();
		CampaignInfoEdtitingPage cep = new CampaignInfoEdtitingPage(driver);
		cep.getCampaignnameEdt().clear();
		cep.getCampaignnameEdt().sendKeys(newcampaignsName);
		
		cep.getClosingdateEdt().clear();
		String closingDate = jlib.getRequiredDateYYYYDDMM(30);
		cep.getClosingdateEdt().sendKeys(closingDate);
		cep.getSaveBtn().click();
		
		//verify editedinfo and closing date
		String editedinfo = cip.getHeadertext().getText();
		boolean edit = editedinfo.contains(newcampaignsName);
		assertEquals(edit, true);
		
		
		String actEndDate = cip.getendDatetxt().getText();
		sa.assertEquals(actEndDate.trim(), closingDate);
		sa.assertAll();
	}
	
	

}
