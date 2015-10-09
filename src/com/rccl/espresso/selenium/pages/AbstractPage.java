package com.rccl.espresso.selenium.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {
	
	// Do not include page assertions on this class and it's subclasses
	// Driver to be used for other tasks
	protected WebDriver driver;
	
	protected String pageTitle;
	
	protected boolean isCreate;
	
	private List<By> mandatoryFields;
	
	protected Actions action;
	
	public void setIsCreateMode(boolean isCreateMode) {
		this.isCreate = isCreateMode;
	}
	
	// By default, test is create mode
	public AbstractPage(WebDriver driver, String pageTitle) {
		this.collectMandatoryPageContents();
		this.driver = driver;
		this.pageTitle = pageTitle;
		this.isCreate = true;
		this.action = new Actions(driver);
	}
	
	protected abstract void collectMandatoryPageContents(List<By> mandatoryFieldsHolder);
	
	private void collectMandatoryPageContents(){
		List<By> mandatoryFieldsHolder = new ArrayList<By>();
		collectMandatoryPageContents(mandatoryFieldsHolder);
		mandatoryFields = Collections.unmodifiableList(mandatoryFieldsHolder);
	};
	
	public List<By> getMandatoryPageContents() {
		return mandatoryFields;
	}
	
	public abstract List<By> getBreadCrumbs();

	public String getExpectedTitle() {
		return this.pageTitle;
	}

	public boolean isCreate() {
		return isCreate;
	}
	
	public void setInputBoxValue(By inputBoxQuery, String inputValue) {
		WebElement inputBoxElement = driver.findElement(inputBoxQuery);
		inputBoxElement.sendKeys(inputValue);
	}
	
	public void clickButtonOrLink(By buttonOrLinkQuery) {
		WebElement buttonOrLinkElement = driver.findElement(buttonOrLinkQuery);
		performClickBuild(buttonOrLinkElement);
	}
	
	public void setSelectBox(By selectboxQuery) {
		setSelectBox(selectboxQuery, "SELECT");
	}
	
	public void setSelectBox(By selectboxQuery, String optionValue) {
		List<WebElement> options = getSelectboxOptions(selectboxQuery);
		
		optionValue = StringUtils.isNotBlank(optionValue) ? optionValue:"SELECT";
		for(WebElement option:options) {
			String text = option.findElement(By.cssSelector("div.text")).getText();
			if(StringUtils.equalsIgnoreCase(optionValue, text)) {
				performClickBuild(option);
				break;
			}
		}
	}
	
	public void setSelectBox(By selectboxQuery, int index) {
		List<WebElement> options = getSelectboxOptions(selectboxQuery);
		int optionsLength = options.size();
		for(int optionPosition = 0; optionPosition < optionsLength; optionPosition++) {
			if(optionPosition == index) {
				WebElement option = options.get(optionPosition);
				performClickBuild(option);
				break;
			}
		}
	}

	private List<WebElement> getSelectboxOptions(By selectboxQuery) {
		WebElement parent = getParent(selectboxQuery);
		WebElement arrowButton = parent.findElement(By
				.cssSelector("div.arrow_btn"));
		
		String optionsId = parent.findElement(By.tagName("ul")).getAttribute("id");
		performClickBuild(arrowButton);
		
		List<WebElement> options = driver.findElement(By.id(optionsId)).findElements(By.tagName("li"));
		return options;
	}
	
	public void setRadioButton(By radioButtonQuery, int index) {
		List<WebElement> radios = driver.findElements(radioButtonQuery);
		int radiosLength = radios.size();
		for(int radioIndex = 0; radioIndex < radiosLength; radioIndex++) {
			if(index == radioIndex) {
				WebElement radio = radios.get(index);
				WebElement label = getRadioLabel(radio);
				performClickBuild(label);
				break;
			}
		}
	}
	
	public void setRadioButton(By radioButtonQuery, String radioValue) {
		List<WebElement> radios = driver.findElements(radioButtonQuery);
		for(WebElement radio:radios) {
			WebElement label = getRadioLabel(radio);
			String labelText = label.findElement(By.tagName("span")).getText();
			if(StringUtils.equalsIgnoreCase(radioValue, labelText)) {
				performClickBuild(label);
				break;
			}
		}
	}

	private WebElement getRadioLabel(WebElement radio) {
		WebElement parent = getParent(radio);
		WebElement label = parent.findElement(By.tagName("label"));
		return label;
	}

	protected void performClickBuild(WebElement label) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(label));
		action.moveToElement(label).click().build().perform();
	}
	
	public WebElement getParent(By query) {
		WebElement formObject = driver.findElement(query);
		return getParent(formObject);
	}
	
	public WebElement getParent(WebElement formObject) {
		WebElement parent = (WebElement) ((JavascriptExecutor) driver)
				.executeScript("return arguments[0].parentNode",
						formObject);
		return parent;
	}
	
	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
