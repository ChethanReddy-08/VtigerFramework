package practice;

import static org.testng.Assert.fail;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners(practice.ListenerImpClass.class)
public class TestClass extends BaseClass
{
	@Test(priority = 1)
	public void test1()
	{
		Reporter.log("executed test1",true);
		Assert.fail();
		
		  SoftAssert s = new SoftAssert(); s.assertEquals(true, false); s.fail();
		  System.out.println("jihkfhxhfjhdfxhgcggckjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		  s.assertAll();
		 
	
	}
	
	@Test(priority = 2)
	public void test2()
	{	
		Reporter.log("test2 got failed",true);
	}
	
	@Test(priority = 3)
	public void test3()
	{
		Reporter.log("Skipped test3",true);
	}

	
}
