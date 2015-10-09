package com.rccl.espresso.selenium.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.rccl.espresso.selenium.pages.AbstractPage;

public abstract class AbstractTestClass {

	protected WebDriver driver;

	@BeforeClass
	public void setUp() {
		// This should be configurable
		String username = "cmounited";
		String password = "cmounited";
		String webLink = "localhost:8080/espresso/reservations.do";
		
		String getLink = new StringBuilder("http://").append(username)
				.append(":").append(password).append("@").append(webLink)
				.toString();
		driver = new FirefoxDriver();
		driver.get(getLink);
	}
	
	
	@AfterClass
	public void tearDown() {
//		driver.close();
//		driver.quit();
	}
	
	protected void inspectPageElements(AbstractPage page) {
		// Check if Page has correct title
		checkPageTitle(page);
		
		// Check if mandatory fields exist
		checkMandatoryFields(page);
		
		// Check breadcrumbs
		checkBreadCrumbs(page);
		
		checkHeaderLinks();
	}

	private void checkHeaderLinks() {
		Assert.assertNotNull("Banner should exist in this page", driver.findElement(By.cssSelector("a.checkIgnoreHome")));
		
		WebElement ulContainer = driver.findElement(By.cssSelector("ul.miniLinks"));
		List<WebElement> spans = ulContainer.findElements(By.tagName("span"));
		Set<String> existingLinks = new HashSet<String>();
		for(WebElement span:spans) {
			String spanText = span.getText();
			if(StringUtils.isNotBlank(spanText)) {
				existingLinks.add(spanText.toUpperCase());
			}	
		}
		
		Assert.assertTrue("Logout link should exist in this page", existingLinks.contains("LOGOUT"));
		Assert.assertTrue("Home link should exist in this page", existingLinks.contains("HOME"));
		Assert.assertTrue("Manage Profile link should exist in this page", existingLinks.contains("MANAGE PROFILE"));
	}

	private void checkBreadCrumbs(AbstractPage page) {
		// TODO is this really needed?
		if(page.isCreate()) {
			List<By> breadCrumbs = page.getBreadCrumbs();
			if(breadCrumbs != null) {
				breadCrumbs.size();
			}
		}
	}


	protected void assertNotNull(String message, By selector) {
		Assert.assertNotNull(selector.toString() + " is expected in this page", 
				driver.findElement(selector));
	}
	
	protected void assertEquals(String expected, String value, String message) {
		Assert.assertEquals(message, expected.toUpperCase(), 
				value.toUpperCase());
	}
	
	private void checkMandatoryFields(AbstractPage page) {
		List<By> mandatoryFields = page.getMandatoryPageContents(); 
		for(By mandatoryField:mandatoryFields) {
			assertNotNull(mandatoryField.toString() + " is expected in this page", mandatoryField);
		}
	}

	private void checkPageTitle(AbstractPage page) {
		// Page title should exist
		String actualPageTitle = driver.getTitle();
		Assert.assertTrue(StringUtils.isNotBlank(actualPageTitle));
		
		String expectedPageTitle = page.getExpectedTitle();
		// Page title should be equal to expected page title
		assertEquals(expectedPageTitle, actualPageTitle, "Page title should be " + expectedPageTitle);
	}
}
