package com.pvaddoc.tests.flightreservation;

import com.pvaddoc.pages.flightreservation.*;
import com.pvaddoc.tests.BaseTest;
import com.pvaddoc.tests.flightreservation.model.FlightReservationTestData;
import com.pvaddoc.util.Config;
import com.pvaddoc.util.Constants;
import com.pvaddoc.util.JsonUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FlightReservationTest extends BaseTest {

    private String noOfPassengers;
    private String expectedPrice;
    private FlightReservationTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath) {
        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }

    @Test
    public void userRegistrationTest() {
         RegistrationPage registrationPage = new RegistrationPage(driver);
         registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
         Assert.assertTrue(registrationPage.isAt());
         registrationPage.enterUserDetails(testData.firstName(), testData.lastName());
         registrationPage.enterUserCredentials(testData.email(), testData.password());
         registrationPage.enterAddress(testData.street(), testData.city(), testData.zip());
         registrationPage.register();
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest() {
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmationPage.isAt());
        Assert.assertEquals(registrationConfirmationPage.getFirstName(), testData.firstName());
        registrationConfirmationPage.goToFlightSearch();
    }
    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightsSearchTest() {
        FlightSearchPage flightSearchPage = new FlightSearchPage(driver);
        Assert.assertTrue(flightSearchPage.isAt());
        flightSearchPage.selectPassengers(testData.passengersCount());
        flightSearchPage.searchFlightsButton();
    }
    @Test(dependsOnMethods = "flightsSearchTest")
    public void flightSelectionTest() {
        FlightSelectionPage flightSelectionPage = new FlightSelectionPage(driver);
        Assert.assertTrue(flightSelectionPage.isAt());
        flightSelectionPage.selectFlights();
        flightSelectionPage.confirmFlightButton();

    }
    @Test(dependsOnMethods = "flightSelectionTest")
    public void flightReservationConfirmationTest() {
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        Assert.assertTrue(flightConfirmationPage.isAt());
        Assert.assertEquals(flightConfirmationPage.getPrice(), testData.expectedPrice());
    }







}
