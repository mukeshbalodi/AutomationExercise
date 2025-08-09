package org.AutomationExercise.e_com.pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
		
	WebDriver driver;
	
	private By searchField = By.id("search_product");
	private By searchBtn = By.id("submit_search");
	private By addToCartBtn = By.xpath("//a[@data-product-id='33']");
	private By continueShoppingBtn = By.xpath("//button[@class='btn btn-success close-modal btn-block']");
	private By cartIcon = By.xpath("//a[normalize-space()='Cart']");
	
	public ProductPage(WebDriver driver) {
		this.driver=driver;
	}
  
	public void searchField(String productSearch) {
		driver.findElement(searchField).sendKeys(productSearch);;
	}
	
	public void clickSearchButton() {
		driver.findElement(searchBtn).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public void clickOnAddToCartBtn() {
		driver.findElement(addToCartBtn).click();
	}
	
	public void clickContinueShoppingBtn() {
		driver.findElement(continueShoppingBtn).click();
	}
	
	public void clickOncartIcon() {
		driver.findElement(cartIcon).click();
	}
	
}
