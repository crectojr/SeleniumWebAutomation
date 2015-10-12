package com.rccl.espresso.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GroupSearch extends AbstractPage {

	public static final By GROUP_NAME = By.name("searchFormModel.groupName");
	public static final By GROUP_TYPE = By.name("searchFormModel.groupType");
	public static final By POLICY_TYPE = By.name("searchFormModel.policyType");
	public static final By SEARCH_BUTTON = By.id("submitId");
	public static final By BRAND_RADIO = By.name("searchFormModel.brand");
	public static final By SHIP_SELECTBOX = By.name("searchFormModel.ship");
	public static final By SAILING_DATE = By.id("sailingDate");
	
	// No pulmantur brands for Group
	public static enum Brand {
		AZAMARA("Azamara Club Cruises"),
		CELEBRITY("Celebrity Cruises"),
		ROYAL("Royal Caribbean International");
		private String brand;
		Brand(String brand) {
			this.brand = brand;
		}
		public String toString() {
			return this.brand;
		}
	}
	
	public static enum PolicyType {
		DEPOSIT("Deposit"),
		NO_DEPOSIT("No Deposit");
		private String policyType;
		PolicyType(String policyType) {
			this.policyType = policyType;
		}
		public String toString() {
			return this.policyType;
		}
	}
	
	public static enum GroupType {
		ASSOCIATION("A Association/Organization"),
		BUSINESS("A Business Or Meeting"),
		FRIENDS_FAMILY("A Friends And Family"),
		INCENTIVE("I Inventive"),
		PROMOTIONAL("P Promotionally Advertised"),
		STUDENT("S Student");
		private String groupType;
		GroupType(String groupType) {
			this.groupType = groupType;
		}
		public String toString() {
			return this.groupType;
		}
	}
	public GroupSearch(WebDriver driver) {
		super(driver, "Search");
	}

	@Override
	protected void collectMandatoryPageContents(List<By> mandatoryFieldsHolder) {
		mandatoryFieldsHolder.add(GROUP_NAME);
		mandatoryFieldsHolder.add(GROUP_TYPE);
		mandatoryFieldsHolder.add(POLICY_TYPE);
		mandatoryFieldsHolder.add(SEARCH_BUTTON);
		mandatoryFieldsHolder.add(BRAND_RADIO);
	}
	
	public void setGroupName(String groupName) {
		this.setInputBoxValue(GROUP_NAME, groupName);
	}

	@Override
	public List<By> getBreadCrumbs() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPolicyType(PolicyType policyType) {
		this.setSelectBox(POLICY_TYPE, policyType.toString());
	}

	public void setGroupName(GroupType groupType) {
		this.setSelectBox(GROUP_TYPE, groupType.toString());
		
	}

	public void search() {
		this.clickButtonOrLink(SEARCH_BUTTON);
	}

	public void setBrand(Brand brand) {
		this.setRadioButton(BRAND_RADIO, brand.toString());
	}

	public void setShip(int index) {
		this.setSelectBox(SHIP_SELECTBOX, index);
	}
	
	public void setShip(String shipName) {
		this.setSelectBox(SHIP_SELECTBOX, shipName);
	}
	
	private void clickDatePickerImg() {
		WebElement datePickerParent = getParent(SAILING_DATE);
		WebElement datePickerImg = datePickerParent.findElement(By.tagName("img"));
		performClickBuild(datePickerImg);
	}
	
	private List<WebElement> getDates() {
		clickDatePickerImg();
		WebElement datePicker = driver.findElement(By.id("ui-datepicker-div"));
		waitUntil(datePicker);
		return datePicker.findElements(By.tagName("td"));
	}
	
	public String getFirstAvailableDate() {
		List<WebElement> dates = getDates();
		for(WebElement date:dates) {
			String classes = date.getAttribute("class");
			if(!classes.contains("ui-state-disabled") && !classes.contains("unavailable")
					&& !classes.contains("ui-datepicker-today")) {
				performClickBuild(date);
				break;
			}
		}
		return driver.findElement(SAILING_DATE).getAttribute("value");
	}
	
	public String getFirstUnavailableDate() {
		List<WebElement> dates = getDates();
		for(WebElement date:dates) {
			String classes = date.getAttribute("class");
			if(classes.contains("unavailable") && !classes.contains("ui-datepicker-today")) {
				performClickBuild(date);
				break;
			}
		}
		return driver.findElement(SAILING_DATE).getAttribute("value");
	}
	
	public void setSailingDate(String sailingDate) {
		setInputBoxValue(SAILING_DATE, sailingDate);
	}
}
