package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SalesOrderInfoPage 
{
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement headerinfo;
	
	WebDriver driver;
	public SalesOrderInfoPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getHeaderinfo() {
		return headerinfo;
	}


	
}
