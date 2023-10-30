package com.sample.test.common;

import com.sample.driver.DRIVER_TYPE;
import com.sample.driver.WebCommonDriver;

import ch.qos.logback.classic.Level;

public class DriverLauncher {
      WebCommonDriver launch() {
        return new WebCommonDriver
          .Builder(DRIVER_TYPE.WEB_CHROME_DRIVER)
          .driverName("driver")
          .isRecordVideo(false)
          .maxWindowSize(true)
          .logLevel(Level.INFO)
          .build();
    }
}
