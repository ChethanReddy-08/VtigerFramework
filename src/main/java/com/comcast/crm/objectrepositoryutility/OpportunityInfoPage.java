package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpportunityInfoPage
{

	@FindBy(className = "dvHeaderText")
	private WebElement headertext;
	
	WebDriver driver;
	public OpportunityInfoPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public WebElement getHeadertext() {
		return headertext;
	}

}
