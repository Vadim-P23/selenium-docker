package com.pvaddoc.tests.vendorportal;

import com.pvaddoc.pages.vendorportal.DashboardPage;
import com.pvaddoc.pages.vendorportal.LoginPage;
import com.pvaddoc.tests.BaseTest;
import com.pvaddoc.tests.vendorportal.model.VendorPortalTestData;
import com.pvaddoc.util.Config;
import com.pvaddoc.util.Constants;
import com.pvaddoc.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VendorPortalTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VendorPortalTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setPageObjects(String testDataPath){
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.testData = JsonUtil.getTestData(testDataPath, VendorPortalTestData.class);
    }

    @Test
    public void loginTest() {
        loginPage.goTo(Config.get(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAt());
        loginPage.login(testData.username(), testData.password());
    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest() {
        Assert.assertTrue(dashboardPage.isAt());

        Assert.assertEquals(dashboardPage.getMonthlyEarning(), testData.monthlyEarning());
        Assert.assertEquals(dashboardPage.getAnnualEarning(), testData.annualEarning());
        Assert.assertEquals(dashboardPage.getProfitMarginEarning(), testData.profitMargin());
        Assert.assertEquals(dashboardPage.getAvailableInventoryEarning(), testData.availableInventory());

        //order history search
        dashboardPage.searchOrderHistoryBy(testData.searchKeyword());
        Assert.assertEquals(dashboardPage.getSearchResultCount(), testData.searchResultsCount());
    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logout() {
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAt());
    }
}
