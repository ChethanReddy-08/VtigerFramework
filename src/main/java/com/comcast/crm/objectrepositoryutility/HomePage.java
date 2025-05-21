package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class HomePage 
{
	@FindBy(linkText = "Organizations")
	private WebElement orgLink;
	
	@FindBy(linkText = "Contacts")
	private WebElement contactlink;
	
	@FindBy(linkText = "Campaigns")
	private WebElement compaignlink;
	
	@FindBy(linkText = "More")
	private WebElement morelink;
	
	@FindBy(linkText = "Opportunities")
	private WebElement opportunitieslink;
	
	@FindBy(linkText = "Products")
	private WebElement productslink; 
	
	@FindBy(linkText = "Leads")
	private WebElement leadslink;
	
	@FindBy(linkText = "Vendors")
	private WebElement vendorslink;
	
	@FindBy(xpath = "(//a[.='Sales Order'])[1]")
	private WebElement salesOrderlink;
	
	@FindBy(linkText = "Invoice")
	private WebElement invoicelink;
	
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement savebtn;
	
	@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
	private WebElement adminImg;
	
	@FindBy(linkText = "Sign Out")
	private WebElement signoutlink;
	
	
	public WebElement getSavebtn() {
		return savebtn;
	}
	WebDriver driver;
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getOrgLink() {
		return orgLink;
	}
	public WebElement getContactlink() {
		return contactlink;
	}
	public void navigatetoCampaignsPage() 
	{
		Actions act = new Actions(driver);
		act.moveToElement(morelink).perform();
		compaignlink.click();
	}
	
	public void navigatetoSalesOrderPage() 
	{
		Actions act = new Actions(driver);
		act.moveToElement(morelink).perform();
		salesOrderlink.click();
	}
	
	public void navigatetoVendorsPage() 
	{
		Actions act = new Actions(driver);
		act.moveToElement(morelink).perform();
		vendorslink.click();
	}
	
	
	public void navigatetoInvoicePage() 
	{
		Actions act = new Actions(driver);
		act.moveToElement(morelink).perform();
		invoicelink.click();
	}
	
	public void logout()
	{
		Actions act = new Actions(driver);
		WebDriverUtility wdlib = new WebDriverUtility();
		wdlib.waitForElementToBeClickable(driver, adminImg);
		act.moveToElement(adminImg).perform();
		signoutlink.click();
	}
	
	public WebElement getMorelink() {
		return morelink;
	}
	public WebElement getOpportunitieslink() {
		return opportunitieslink;
	}
	public WebElement getProductslink() {
		return productslink;
	}

	public WebElement getLeadslink() {
		return leadslink;
	}

	public WebElement getVendorslink() {
		return vendorslink;
	}
}
