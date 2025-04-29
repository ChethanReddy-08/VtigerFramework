package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage {

	@FindBy(xpath = "//img[@title='Create Product...']")
	private WebElement CreateNewProductBtn;

	public WebElement getCreateNewProductBtn() {
		return  CreateNewProductBtn;
	}
	
	WebDriver driver;
	public ProductsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
