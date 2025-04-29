package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactsPage {
	@FindBy(xpath = "//img[@title='Create Contact...']")
	private WebElement CreateNewContactBtn;

	public WebElement getCreateNewContactBtn() {
		return  CreateNewContactBtn;
	}
	
	WebDriver driver;
	public ContactsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
