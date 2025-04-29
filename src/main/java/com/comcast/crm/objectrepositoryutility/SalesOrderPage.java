package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SalesOrderPage 
{
	@FindBy(xpath = "//img[@title='Create Sales Order...']")
	private WebElement CreateNewSalesOrderBtn;

	public WebElement getCreateNewSalesOrderBtn() {
		return CreateNewSalesOrderBtn;
	}
	
	WebDriver driver;
	public SalesOrderPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

}
