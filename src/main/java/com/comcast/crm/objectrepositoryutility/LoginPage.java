package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class LoginPage 
{
	@FindBy(name = "user_name")
	private WebElement usernameedt;
	
	@FindBy(name = "user_password")
	private WebElement passwordedt;
	
	@FindBy(id = "submitButton")
	private WebElement loginbtn;
	
	WebDriver driver;
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	public WebElement getUsername() {
		return usernameedt;
	}
	public WebElement getPassword() {
		return passwordedt;
	}
	public WebElement getLoginbtn() {
		return loginbtn;
	}
	WebDriverUtility wd = new WebDriverUtility();
	public void logintoApp(String url, String username , String password)
	{
		wd.waitForPagetoLoad(driver);
		wd.maximizeWindow(driver);
		driver.get(url);
		usernameedt.sendKeys(username);
		passwordedt.sendKeys(password);
		loginbtn.click();
	}
}
