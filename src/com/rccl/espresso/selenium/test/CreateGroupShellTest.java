package com.rccl.espresso.selenium.test;

import org.testng.annotations.Test;

import com.rccl.espresso.selenium.pages.DashboardPage;
import com.rccl.espresso.selenium.pages.GroupSearch;
import com.rccl.espresso.selenium.pages.GroupSearch.Brand;
import com.rccl.espresso.selenium.pages.GroupSearch.GroupType;
import com.rccl.espresso.selenium.pages.GroupSearch.PolicyType;

public class CreateGroupShellTest extends AbstractTestClass {
	
	private String groupName;
	private String policyType;
	private String brand;
	private String ship;
	private String sailingDate;
	
	@Test(priority = 0)
	public void testTransitionToCreateGroupShell() {
		DashboardPage dashboard = new DashboardPage(driver);
		
		this.inspectPageElements(dashboard);
		
		// Transition to Create Group Shell
		dashboard.clickGroupButton();
	}
	
	@Test(priority = 1)
	public void testGroupSearchPage() {
		GroupSearch groupSearch = new GroupSearch(driver);
		
		this.inspectPageElements(groupSearch);
		
		groupSearch.setGroupName("Sample Group Name");
		groupSearch.setPolicyType(PolicyType.NO_DEPOSIT);
		groupSearch.setGroupName(GroupType.ASSOCIATION);
		groupSearch.setBrand(Brand.AZAMARA);
		groupSearch.setShip("AZAMARA JOURNEY");
		
		String sailingDate = groupSearch.getFirstAvailableDate();
		groupSearch.search();
	}

}
