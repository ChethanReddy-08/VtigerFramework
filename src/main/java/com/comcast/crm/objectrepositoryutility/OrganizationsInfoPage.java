package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizationsInfoPage
{
    @FindBy(className = "dvHeaderText")
	private WebElement headertext;
	
	@FindBy(id = "dtlview_Industry")
	private WebElement industrytext;
	
	@FindBy(id = "dtlview_Type")
	private WebElement typetext;
	
	@FindBy(id = "dtlview_Organization Name")
	private WebElement orgtext;
	
	@FindBy(id = "dtlview_Phone")
	private WebElement phonetext;
	 
	public WebElement getOrgtext() {
		return orgtext;
	}
	public WebElement getIndustrytext() {
		return industrytext;
	}
	
	public WebElement getTypetext() {
		return typetext;
	}
	
	public WebElement getphonetext() {
		return phonetext;
	}
	
	WebDriver driver;
	public OrganizationsInfoPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getHeadertext() {
		return headertext;
	}
	

}
