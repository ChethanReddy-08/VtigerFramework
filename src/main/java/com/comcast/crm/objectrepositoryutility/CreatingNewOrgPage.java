package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreatingNewOrgPage 
{
	@FindBy(name = "accountname")
	private WebElement orgnameEdt;
	
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement savebtn;
	
	@FindBy(name = "industry")
	private WebElement industryDP;
	
	@FindBy(name = "phone")
	private WebElement PhonenoEdt;
	
	@FindBy(name = "accounttype")
	private WebElement typeDB;
	
	WebDriver driver;
	public CreatingNewOrgPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public WebElement getPhonenoEdt() {
		return PhonenoEdt;
	}
	public WebElement getOrgnameEdt() {
		return orgnameEdt;
	}
	public WebElement getSavebtn() {
		return savebtn;
	}
	public WebElement getIndustryDP() {
		return industryDP;
	} 
	public WebElement getTypeEdt() {
		return typeDB;
	}
	
	public void createOrg(String orgName)
	{
		orgnameEdt.sendKeys(orgName);
		savebtn.click();
	}
	public void createOrg(String orgName, String industry, String type)
	{
		orgnameEdt.sendKeys(orgName);
		Select s = new Select(industryDP);
		s.selectByContainsVisibleText(industry);
		Select s1 = new Select(typeDB);
		s1.selectByContainsVisibleText(type);
		savebtn.click();
	}
	public void createOrg(String orgName, String Phoneno)
	{
		orgnameEdt.sendKeys(orgName);
		PhonenoEdt.sendKeys(Phoneno);
		savebtn.click();
	}
	

}
