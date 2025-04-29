package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreatingNewCampaignsPage 
{
	@FindBy(name = "campaignname")
	private WebElement campaignnameEdt;
	
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement savebtn;

	public WebElement getcampaignnameEdt() {
		return  campaignnameEdt;
	}
	
	public WebElement getsavebtn() {
		return  savebtn;
	}
	
	WebDriver driver;
	public CreatingNewCampaignsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void CreateCampaign(String campaignName)
	{
		campaignnameEdt.sendKeys(campaignName);
		savebtn.click();
	}


}
