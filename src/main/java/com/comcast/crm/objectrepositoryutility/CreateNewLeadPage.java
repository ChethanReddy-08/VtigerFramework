package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewLeadPage {
	@FindBy(name = "lastname")
	private WebElement lastnameEdt;
	
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement savebtn;
	
	@FindBy(name = "company")
	private WebElement companyEdt;
	
	WebDriver driver;
	public CreateNewLeadPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public WebElement getlastnameEdt() {
		return lastnameEdt;
	}
	public WebElement getSavebtn() {
		return savebtn;
	}
	public WebElement getcompanyEdt() {
		return companyEdt;
	} 
	
	public void createLead(String lastname, String company)
	{
		getlastnameEdt().sendKeys(lastname);
		getcompanyEdt().sendKeys(company);
		savebtn.click();
	}
	

}
