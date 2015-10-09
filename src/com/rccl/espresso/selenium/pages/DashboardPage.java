package com.rccl.espresso.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends AbstractPage {

	public static final By START_INDIVIDUAL_RESERVATION_BUTTON = By
			.className("startResLink");
	public static final By START_GROUP_BUTTON = By.className("groupLink");
	public static final By FIND_RESERVATION_INPUT = By.id("reservationid");
	
	public DashboardPage(WebDriver driver) {
		super(driver, "Dashboard");
	}

	public void clickGroupButton() {
		this.clickButtonOrLink(START_GROUP_BUTTON);
	}

	@Override
	public List<By> getBreadCrumbs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void collectMandatoryPageContents(List<By> mandatoryFieldsHolder) {
		mandatoryFieldsHolder.add(START_INDIVIDUAL_RESERVATION_BUTTON);
		mandatoryFieldsHolder.add(START_GROUP_BUTTON);
		mandatoryFieldsHolder.add(FIND_RESERVATION_INPUT);
	}

}
