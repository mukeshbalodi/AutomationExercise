package org.AutomationExercise.e_com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class homePage {
WebDriver driver;
	
	private By ProductsBtn = By.xpath("//a[@href='/products']");
	
	
	public homePage(WebDriver driver) {
		this.driver=driver;
	}
  
	public void productsBtnClick() {
		driver.findElement(ProductsBtn).click();
	}
	
	
}
