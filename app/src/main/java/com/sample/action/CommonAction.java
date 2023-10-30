package com.sample.action;

import org.slf4j.Logger;
import com.sample.driver.DRIVER_TYPE;
import com.sample.driver.Driver;
import com.sample.driver.WebCommonDriver;
import com.sample.listener.Log;

public class CommonAction {
  protected Driver _driver;

  protected Logger logger = Log.getInstance().getLogger();
  
  public CommonAction(Driver driver) {
    this._driver = driver;
  }

  public Object getDriver() {
    if (this._driver.getDriverType() == DRIVER_TYPE.WEB_CHROME_DRIVER) {
      WebCommonDriver driver = (WebCommonDriver) this._driver;
      return driver.get();
    }
    return null;
  }
}