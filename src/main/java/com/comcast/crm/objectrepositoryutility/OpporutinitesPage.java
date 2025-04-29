package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpporutinitesPage 
{
	@FindBy(xpath = "//img[@title='Create Opportunity...']")
	private WebElement CreateNewOpportunityBtn;

	public WebElement getCreateNewOpportunityBtn() {
		return CreateNewOpportunityBtn;
	}
	
	WebDriver driver;
	public OpporutinitesPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

}
