package org.AutomationExercise.e_com.TestCases;

import java.time.Duration;

import org.AutomationExercise.e_com.Utils.windowScroll;
import org.AutomationExercise.e_com.pageObjects.ProductPage;
import org.AutomationExercise.e_com.pageObjects.homePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class sanity extends org.AutomationExercise.e_com.Base.BaseClassLocal {
	
	homePage hpage;
	ProductPage pdpage;
	windowScroll scroll;
	
	@Test(priority = 1)
	    public void searchProduct() {
			hpage=  new homePage(getDriver());
			pdpage=   new ProductPage(getDriver());
			scroll = new windowScroll();
	        getDriver().get("https://www.automationexercise.com/");
	        getDriver().manage().window().maximize();
	        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        hpage.productsBtnClick();
	        getTest().log(Status.INFO, "Clicked on Products element");
	        pdpage.searchField("Jeans");
	        getTest().log(Status.INFO, "word Jeans is entered into the Product Search Field");
	        pdpage.clickSearchButton();
	        getTest().log(Status.INFO, "Click on Search Button");
	        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        scroll.ScrollWindow(0, 500);
	        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	       
	        try {
	        	WebElement ele= getDriver().findElement(By.xpath("//p[normalize-space()='Soft Stretch Jeans']"));
	            Assert.assertTrue(ele.isDisplayed());
	            getTest().log(Status.PASS, "Element displayed : " + ele.getText());
	        } catch (AssertionError e) {
	            getTest().log(Status.FAIL, "Test fail Product not found  ");
	        }
	    }
    
	@Test(priority=2,dependsOnMethods = "searchProduct")
	public void verifyCart() {
		hpage=  new homePage(getDriver());
		pdpage=   new ProductPage(getDriver());
		scroll = new windowScroll();
		pdpage.clickOnAddToCartBtn();
	    getTest().log(Status.INFO, "Clicked on Add To Cart Button");
	    getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		pdpage.clickContinueShoppingBtn();
		 getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		getTest().log(Status.INFO, "Clicked on Continue Shopping Button");
		scroll.ScrollWindow(0, -500);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		pdpage.clickOncartIcon();
		getTest().log(Status.INFO, "Clicked on Cart Icon");
		
		  try {
	        	WebElement ele= getDriver().findElement(By.xpath("//a[normalize-space()='Soft Stretch Jeans']"));
	            Assert.assertEquals(ele.getText(),"Soft Stretch Jeans");
	            WebElement ele2 = getDriver().findElement(By.xpath("//p[@class='cart_total_price']"));
	            Assert.assertEquals(ele2.getText(),"Rs. 799");
	            getTest().log(Status.PASS, "Product Name and Price verified: " + ele.getText()+" "+ ele2.getText());
	        } catch (AssertionError e) {
	            getTest().log(Status.FAIL, "Test fail Product not found  ");
	        }
	}
  
}
