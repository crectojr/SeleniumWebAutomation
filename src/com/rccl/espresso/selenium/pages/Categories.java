package com.rccl.espresso.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Categories extends AbstractPage {
	
	public static final By CATEGORIES_TABLE = By.id("catAvailGroupBasic");
	public static final By ADD_CATEGORIES = By.className("addCategoryButton");
	public static final By PROCEED_TO_GROUP_DETAILS = By.id("saveCategoryInventoryButton");
	
	public Categories(WebDriver driver, String pageTitle) {
		super(driver, "Categories");
	}

	@Override
	protected void collectMandatoryPageContents(List<By> mandatoryFieldsHolder) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<By> getBreadCrumbs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setAvailableCategories(){
		WebElement categoriesTable = this.driver.findElement(CATEGORIES_TABLE);		
		List<WebElement> stateroomsCount = categoriesTable.findElements(By.className("stateroomCount")); 
		String stateroomInfo ="";
		int availableStateroom=0;
		int minimumStateroom = 8;
		int totalAddedStateroom=0;
		int stateroomToAdd =0;
		int rowIndex = 0;
	    for (WebElement stateroomCount : stateroomsCount) { 
	    	WebElement stateroom = stateroomCount.findElement(By.className("categoryInfo"));
	    	
	    	stateroomInfo = stateroom.getText();
	    	if (!stateroomInfo.equals("CLS")){
	    		availableStateroom= Integer.valueOf(stateroomInfo.replaceAll("\\D+",""));
	    		//WebElement testelem = stateroomCount.findElement(By.className("categoryInfo")).findElement(By.tagName("hidden"));
	    		//testelem.getText();
	    		if (minimumStateroom>totalAddedStateroom){
	    			stateroomToAdd = availableStateroom;
	    			if ((totalAddedStateroom+stateroomToAdd)> minimumStateroom){
	    				stateroomToAdd = minimumStateroom - totalAddedStateroom;
	    			}
	    			//add stateroomtoadd to input box
	    			WebElement categoryInputField = this.driver.findElement(By.id("entry_"+rowIndex));
	    			categoryInputField.sendKeys(String.valueOf(stateroomToAdd));
	    			
	    			totalAddedStateroom =totalAddedStateroom + stateroomToAdd;
	    			if (totalAddedStateroom==minimumStateroom){
	    				break;
	    			}
	    			
	    		}
	    		
	    	}
	    	rowIndex++;
	    }
	 
	}

	public void addCategories() {

		this.clickButtonOrLink(ADD_CATEGORIES);
	}

	public void proceedToGroupDetails() {
		this.clickButtonOrLink(PROCEED_TO_GROUP_DETAILS);
		
	}

}
