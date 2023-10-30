package com.sample.page;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.action.Action;
import com.sample.driver.Driver;
import com.sample.util.PropertyHandler;

public class MainPage {

  @FindBy(className = "txt_tit_main")
  private WebElement title;

  @FindBy(xpath = "//div[@id='topCommonPc']/header/div[3]")
  private WebElement gnb;

  private Driver driver = null;
  private Action action = null;

  private final String url = PropertyHandler.getProperties("url") + "app/";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public MainPage(Driver target_driver) {
    this.driver = target_driver;
    this.action = new Action(target_driver);

    PageFactory.initElements(this.action.getDriver(), this);
  }

  public MainPage Navigate() {
    this.action.goTo(url);
    return this;
  }

  public boolean isPresentPage() {
    if (this.action.isElement(title, 5)) {
      return this.action.getText(title).contains("실시간 랭킹") && this.action.getCurrentUrl().contains(url);
    }
    logger.error("메인 페이지 출력되지 않음");
    return false;
  }

  public MainPage clickGNBMenu(String menuName) {
    if (this.action.isElement(gnb, 5)) {
      List<WebElement> gnbMenus = this.action.getFirstChildren(gnb);
  
      if (gnbMenus.size() > 0) {
        WebElement target = gnbMenus.stream().filter(menu -> menu.getText().contains(menuName)).findAny().get();
        if (target != null) {
          target.click();
          logger.info("GNB > [{}] 메뉴 클릭", menuName);
          return this;
        }
      }
      logger.error("GNB 메뉴가 존재하지 않음");
      return null;
    }
    logger.error("GNB가 출력되지 않음");
    return null;
  }
  
}
