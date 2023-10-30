package com.sample.test.common;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.sample.driver.WebCommonDriver;

public abstract class TestTemplate {
    private final Logger logger = LoggerFactory.getLogger(TestTemplate.class);
    private SoftAssert softAssert;
    private String testName;
    private final DriverManager driverManager = new DriverManager();

    public WebCommonDriver getWebCommonDriver() {
        return driverManager.getWebCommonDriver();
    }

    public SoftAssert getSoftAssert() {
        return softAssert;
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        logger.info("-------------------------- test suite start --------------------------");
        // allureEnvironmentWriter(
        //         ImmutableMap.<String, String>builder()
        //                 .put("ENV", PropertyHandler.getProperties("env"))
        //                 .put("URL", PropertyHandler.getProperties("url"))
        //                 .build(), System.getProperty("user.dir") + "/build/allure-results/");
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        logger.info("-------------------------- test class start --------------------------");
        driverManager.create();
        doBeforeClass();
    }

    protected abstract void doBeforeClass();

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestResult result){
        softAssert = new SoftAssert();
        testName = result.getMethod().getMethodName();
        logger.info("======================== test start {} ========================", testName);
        doBeforeMethod();
    }

    protected void doBeforeMethod(){}

    @AfterMethod
    public void afterMethod(){
        doAfterMethod();
        softAssert = null;
        logger.info("======================== test end {} ========================", testName);
    }

    protected void doAfterMethod(){};

    @AfterTest
    public void afterTest(){
        logger.debug("afterTest");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        logger.debug("afterClass");

        logger.debug("devtools session disconnect");
        DevTools devtools = ((ChromeDriver) driverManager.getWebCommonDriver().get()).getDevTools();
        devtools.disconnectSession();

        doAfterClass();
        driverManager.quit();
        logger.debug("driverManager.quit()");
        logger.info("————————————— test class end —————————————");
    }

    protected void doAfterClass(){}

    @AfterGroups(alwaysRun = true)
    public void afterGroups(){
        logger.debug("afterGroups");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(){
//        logger.debug("afterSuite");
        logger.info("————————————— after suit end —————————————");
    }

  
}
