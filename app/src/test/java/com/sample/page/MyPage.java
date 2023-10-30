package com.sample.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.action.Action;
import com.sample.driver.Driver;
import com.sample.util.PropertyHandler;

public class MyPage {
  
  @FindBy(xpath = "//div[@id='commonMypage']//h1")
  private WebElement title;

  @FindBy(xpath = "//a[contains(text(), '회원정보변경')]")
  private WebElement memberEditBtn;

  private Driver driver = null;
  private Action action = null;

  private final String url = PropertyHandler.getProperties("url") + "app/mypage";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  public MyPage(Driver target_driver) {
    this.driver = target_driver;
    this.action = new Action(target_driver);

    PageFactory.initElements(this.action.getDriver(), this);
  }
  
  public MyPage Navigate() {
    this.action.goTo(url);
    return this;
  }

  public boolean isPresentPage() {
    if (this.action.isElement(title, 5)) {
      return this.action.getText(title).contains("My Page") && this.action.getCurrentUrl().contains(url);
    }
    logger.error("마이 페이지 출력되지 않음");
    return false;
  }

  public MyPage clickMemberBtn() {
    if (this.action.isElement(memberEditBtn, 5)) {
      this.action.waitClick(memberEditBtn, 5);
      logger.info("회원정보변경 버튼 클릭");
      return this;
    }
    logger.error("회원정보변경 버튼 출력되지 않음");
    return null;
  }

}
