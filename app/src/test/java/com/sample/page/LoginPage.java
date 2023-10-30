package com.sample.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.action.Action;
import com.sample.driver.Driver;
import com.sample.util.PropertyHandler;

public class LoginPage {
  
  @FindBy(xpath="//div[@class = 'container login']")
  private WebElement loginContainer;

  @FindBy(xpath="//div[@id='headerCommonLayout']//h2")
  private WebElement title;
  
  @FindBy(name="id")
  private WebElement id;

  @FindBy(name="pw")
  private WebElement password;

  @FindBy(xpath="//form[@name='loginform']//button[contains(@class, 'login-button__item ')]")
  private WebElement loginBtn;
  
  private Driver driver = null;
  private Action action = null;

  private final String url = PropertyHandler.getProperties("url") + "auth/login";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  

  public LoginPage(Driver target_driver) {
    this.driver = target_driver;
    this.action = new Action(target_driver);

    PageFactory.initElements(this.action.getDriver(), this);
  }

  public LoginPage Navigate() {
    this.action.goTo(url);
    return this;
  }

  public boolean isPresentPage() { 
    return this.action.isElement(loginContainer, 5) && this.action.getCurrentUrl().contains(url);
  }

  public LoginPage inputId(String text) {
    if (this.action.isElement(id, 5)) {
      this.action.type(id, text);
      logger.info("input id {}", text);
      return this;
    }
    logger.error("아이디 입력 박스가 출력되지 않음");
    return null;
  }

  public LoginPage inputPassword(String text) {
    if (this.action.isElement(password, 5)) {
      this.action.type(password, text);
      logger.info("input password {}", text);
      return this;
    }
    logger.error("패스워드 입력 박스가 출력되지 않음");
    return null;
  }

  public LoginPage clickLoginBtn() {
    if (this.action.isElement(loginBtn, 5)) {
      this.action.waitClick(loginBtn, 5);
      logger.info("로그인 버튼 클릭");
      return this;
    }
    logger.error("로그인 버튼이 출력되지 않음");
    return null;
  }

  public LoginPage login(String id, String pw) {
    MainPage mainPage = new MainPage(this.driver);

    this.Navigate();

    if (this.isPresentPage()) {
      this.inputId(id);
      this.inputPassword(pw);
      this.clickLoginBtn();

      if (mainPage.isPresentPage()) {
        return this;
      }
      logger.error("로그인 후 메인페이지로 이동되지 않음");
      return null;
    }
    logger.error("로그인 페이지가 출력되지 않음");
    return null;
  }
  
}
