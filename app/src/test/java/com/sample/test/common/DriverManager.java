package com.sample.test.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.sample.driver.WebCommonDriver;

public class DriverManager {
    private final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    private WebCommonDriver driver;

    public WebCommonDriver getWebCommonDriver() {
        return driver;
    }

    void create() {
        DriverLauncher driverLauncher = new DriverLauncher();
        WebCommonDriver driver = driverLauncher.launch();
        if (driver == null) {
            Assert.fail("yata web driver 생성 실패. class=" + DriverManager.class);
        }

        this.driver = driver;
        logger.info("======================== chrome driver created ========================");
    }

    void quit() {
        if (driver == null) return;
        driver.quit();
        logger.info("======================== chrome driver quited ========================driver={}", driver);
    }
}
