package com.sample.page;

import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.sample.action.Action;
import com.sample.driver.Driver;
import com.sample.model.mypage.AccountInfo;
import com.sample.util.PropertyHandler;

public class MemberPage {
  @FindBy(xpath="//header[contains(@class, 'first')]//h1[@class='tit']")
  private WebElement title;

  @FindBy(xpath = "//div[contains(@class,'login-button')]/button")
  private WebElement popUpLoginBtn;
  
  @FindBy(xpath="//a[text()='로그아웃']")
  private WebElement logoutBtn;

  @FindBy(id="change-profile-image-btn")
  private WebElement changeProfileImgBtn;

  @FindBy(id="change-default-image-btn")
  private WebElement changeDefaultImgBtn;

  @FindBy(id="change-profile-image-finish-btn")
  private WebElement confirmChangeProfileImgBtn;

  @FindBy(id="profile-image")
  private WebElement imgUpload;

  @FindBy(xpath="//tr[@id='profile-image-area']//div[@class='img']")
  private WebElement profileImg;
  
  @FindBy(xpath="//th[text()='아이디']/following-sibling::td[1]")
  private WebElement id;

  @FindBy(xpath="//tr[@id='password-area']/td[1]")
  private WebElement password;

  @FindBy(xpath="//th[text()='이름(실명)']/following-sibling::td[1]")
  private WebElement name;

  @FindBy(xpath="//tr[@id='nickName-area']/td[1]")
  private WebElement nickName;

  @FindBy(id = "change-nickName-btn")
  private WebElement nickNameEditBtn;

  @FindBy(xpath="//tr[@id='change-nickName-area']")
  private WebElement editNickNameContainer;

  @FindBy(xpath="//tr[@id='change-nickName-area']/td//li[@class='txt-black']/strong")
  private WebElement editNickNameCount;

  @FindBy(xpath = "//tr[@id='change-nickName-area']//input[@id='nickName']")
  private WebElement editNickName;

  @FindBy(id="change-nickName-finish-btn")
  private WebElement confirmNickName;

  @FindBy(xpath="//tr[@id='email-area']/td[1]")
  private WebElement email;

  @FindBy(xpath="//tr[@id='mobile-area']/td[1]")
  private WebElement phone;

  @FindBy(id="change-password-btn")
  private WebElement editPasswordBtn;

  @FindBy(id="change-password-area")
  private WebElement editPasswordContainer;

  @FindBy(id="password")
  private WebElement prePassword;

  @FindBy(id="newPassword")
  private WebElement newPassword;

  @FindBy(id="confirmPassword")
  private WebElement confirmPassword;

  @FindBy(id="change-password-finish-btn")
  private WebElement passwordConfirmBtn;

  @FindBy(xpath = "//button[text()='가려진정보 보기']")
  private WebElement hiddenInfoBtn;

  private Driver driver = null;
  private Action action = null;
  private AccountInfo accountInfo = null;

  private final String url = PropertyHandler.getProperties("url") + "member/mypage";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  public MemberPage(Driver target_driver) {
    this.driver = target_driver;
    this.action = new Action(target_driver);
    this.accountInfo = new AccountInfo();

    PageFactory.initElements(this.action.getDriver(), this);
  }
  
  public MemberPage Navigate() {
    this.action.goTo(url);
    return this;
  }

  public boolean isPresentPage() {
    if (this.action.isElement(title, 5)) {
      return this.action.getText(title).contains("기본 회원정보") && this.action.getCurrentUrl().contains(url);
    }
    logger.error("회원정보 페이지 출력되지 않음");
    return false;
  }

  public MemberPage clickLogoutBtn() {
    if (this.action.isElement(logoutBtn)) {
      this.action.waitClick(logoutBtn, 5);
      logger.info("로그아웃 버튼 클릭");
      return this;
    }
    logger.error("로그아웃 버튼 출력되지 않음");
    return null;
  }

  public MemberPage clickChangeProfileImgBtn() {
    if (this.action.isElement(changeProfileImgBtn)) {
      this.action.waitClick(changeProfileImgBtn, 5);
      logger.info("프로필 이미지 변경 버튼 클릭");
      return this;
    }
    logger.error("프로필 이미지 변경 버튼 출력되지 않음");
    return null;
  }

  public MemberPage clickChangeDefaultImgBtn() {
    if (this.action.isElement(changeDefaultImgBtn)) {
      this.action.waitClick(changeDefaultImgBtn, 5);
      logger.info("프로필 기본이미지로 변경 버튼 클릭");
      return this;
    }
    logger.error("프로필 기본이미지로 변경 버튼 출력되지 않음");
    return null;
  }

  public MemberPage clickConfirmProfileImgBtn() {
    if (this.action.isElement(confirmChangeProfileImgBtn)) {
      this.action.waitClick(confirmChangeProfileImgBtn, 5);
      logger.info("프로필 이미지 변경 완료 버튼 클릭");
      return this;
    }
    logger.error("프로필 이미지 변경 완료 버튼 출력되지 않음");
    return null;
  }

  public MemberPage confirmAlert() {
    if (this.action.alert.isAlert()) {
      this.action.alert.accept();
      logger.info("알럿창 확인 처리 완료");
      return this;
    }
    logger.error("알럿창 출력되지 않음");
    return null;
  }

  public String getAlertMsg() {
    if (this.action.alert.isAlert()) {
      logger.info("알럿창 메시지 가져오기 완료");
      return this.action.alert.getText();
    }
    logger.error("알럿창 출력되지 않음");
    return null;
  }

  public MemberPage uploadProfileImg(String img) {
    if (this.action.isElement(imgUpload, 5)) {
      URL resource = getClass().getClassLoader().getResource(String.format("image/%s", img));
      String filepath = resource.getFile();
      this.action.uploadFile(imgUpload, filepath);
      logger.info("이미지 파일 업로드 완료 : {}", img);
      return this;
    }
    logger.error("이미지 업로드 버튼 출력되지 않음");
    return null;
  }

  public MemberPage uploadProfile(String img) {
    return this.clickChangeProfileImgBtn().uploadProfileImg(img).clickConfirmProfileImgBtn().confirmAlert();
  }

  public String getProfileImgName() {
    if (this.action.isElement(profileImg, 5)) {
      String path = profileImg.getAttribute("style").trim();
      accountInfo.setProfileImg(path.substring(path.lastIndexOf("/") + 1).split("\"")[0]);
      logger.info("이미지 파일 이름 가져오기 : {}", accountInfo.getProfileImg());
      return accountInfo.getProfileImg();
    }
    logger.error("프로필 이미지 Element가 출력되지 않음");
    return null;
  }

  public MemberPage changeDefaultProfile() {
    return this.clickChangeProfileImgBtn().clickChangeDefaultImgBtn().clickConfirmProfileImgBtn().confirmAlert();
  }

  public String getId() {
    if (this.action.isElement(id, 5)) {
      accountInfo.setId(this.action.getText(id).trim());
      logger.info("아이디 가져오기 : {}", accountInfo.getId());
      return accountInfo.getId();
    }
    logger.error("아이디 Element가 출력되지 않음");
    return null;
  }

  public String getPassword() {
    if (this.action.isElement(password, 5)) {
      accountInfo.setPassword(this.action.getText(password).trim());
      logger.info("패스워드 가져오기 : {}", accountInfo.getPassword());
      return accountInfo.getPassword();
    }
    logger.error("패스워드 Element가 출력되지 않음");
    return null;
  }

  public String getName() {
    if (this.action.isElement(name, 5)) {
      accountInfo.setName(this.action.getText(name).trim().split(" ")[0]);
      logger.info("이름 가져오기 : {}", accountInfo.getName());
      return accountInfo.getName();
    }
    logger.error("이름 Element가 출력되지 않음");
    return null;
  }

  public String getNickName() {
    if (this.action.isElement(nickName, 5)) {
      accountInfo.setNickName(this.action.getText(nickName).trim());
      logger.info("닉네임 가져오기 : {}", accountInfo.getNickName());
      return accountInfo.getNickName();
    }
    logger.error("닉네임 Element가 출력되지 않음");
    return null;
  }

  public String getEmail() {
    if (this.action.isElement(email, 5)) {
      accountInfo.setEmail(this.action.getText(email).trim());
      logger.info("이메일 가져오기 : {}", accountInfo.getEmail());
      return accountInfo.getEmail();
    }
    logger.error("이메일 Element가 출력되지 않음");
    return null;
  }

  public String getPhone() {
    if (this.action.isElement(phone, 5)) {
      accountInfo.setPhone(this.action.getText(phone).trim().split(" ")[0]);
      logger.info("휴대전화번호 가져오기 : {}", accountInfo.getPhone());
      return accountInfo.getPhone();
    }
    logger.error("휴대전화번호 Element가 출력되지 않음");
    return null;
  }

  public AccountInfo getDefaultAccountInfo() {
    this.getProfileImgName();
    this.getId();
    this.getPassword();
    this.getName();
    this.getNickName();
    this.getEmail();
    this.getPhone();
    return accountInfo;
  }

  public MemberPage clickPasswordEditBtn() {
    if (this.action.isElement(editPasswordBtn, 5)) {
      this.action.waitClick(editPasswordBtn, 5);
      logger.info("비밀번호 변경 버튼 클릭");
      return this;
    }
    logger.error("비밀번호 변경 버튼 출력되지 않음");
    return null;
  }

  public boolean isPresentPasswordEditContainer() {
    return this.action.isDisplayed(editPasswordContainer);
  }

  public MemberPage inputPrePassword(String text) {
    if (this.action.isElement(prePassword, 5)) {
      this.action.type(prePassword, text);
      logger.info("input prePassword {}", text);
      return this;
    }
    logger.error("현재 비밀번호 입력 박스가 출력되지 않음");
    return null;
  }

  public MemberPage inputNewPassword(String text) {
    if (this.action.isElement(newPassword, 5)) {
      this.action.type(newPassword, text);
      logger.info("input prePassword {}", text);
      return this;
    }
    logger.error("신규 비밀번호 입력 박스가 출력되지 않음");
    return null;
  }

  public MemberPage inputConfrimPassword(String text) {
    if (this.action.isElement(confirmPassword, 5)) {
      this.action.type(confirmPassword, text);
      logger.info("input prePassword {}", text);
      return this;
    }
    logger.error("신규 비밀번호 재 입력 박스가 출력되지 않음");
    return null;
  }

  public MemberPage clickPasswordConfirmBtn() {
    if (this.action.isElement(passwordConfirmBtn, 5)) {
      this.action.waitClick(passwordConfirmBtn, 5);
      logger.info("비밀번호 변경 완료 버튼 클릭");
      return this;
    }
    logger.error("비밀번호 변경 완료 버튼 출력되지 않음");
    return null;
  }

  public MemberPage changePassword(String pre, String after) {
    if (this.clickPasswordEditBtn().isPresentPasswordEditContainer()) {
      this.inputPrePassword(pre)
          .inputNewPassword(after)
          .inputConfrimPassword(after)
          .clickPasswordConfirmBtn()
          .confirmAlert();
      logger.info("비밀번호 변경 완료 - pre : {}, after : {}", pre, after);
      return this;
    }
    logger.error("비밀번호 변경 실패");
    return null;
  }

  public MemberPage clickNickNameEditBtn() {
    if (this.action.isElement(nickNameEditBtn, 5)) {
      this.action.waitClick(nickNameEditBtn, 5);
      logger.info("닉네임 변경 버튼 클릭");
      return this;
    }
    logger.error("닉네임 변경 버튼 출력되지 않음");
    return null;
  }

  public boolean isPresentNickNameEditContainer() {
    return this.action.isDisplayed(editNickNameContainer);
  }

  public int getNickNameEditCount() {
    if (this.action.isElement(editNickName, 5)) {
      String result = this.action.getText(editNickNameCount).replaceAll("[^\\d]", "");
      return Integer.parseInt(result);
    }
    logger.error("닉네임 변경 횟수가 출력되지 않음");
    return 0;
  }

  public MemberPage inputNickName(String text) {
    if (this.action.isElement(editNickName, 5)) {
      this.action.type(editNickName, text);
      logger.info("input nickName {}", text);
      return this;
    }
    logger.error("닉네임 입력 박스가 출력되지 않음");
    return null;
  }

  public MemberPage clickNickNameConfirmBtn() {
    if (this.action.isElement(confirmNickName, 5)) {
      this.action.waitClick(confirmNickName, 5);
      logger.info("닉네임 변경 완료 버튼 클릭");
      return this;
    }
    logger.error("닉네임 변경 완료 버튼 출력되지 않음");
    return null;
  }

  public MemberPage changeNickName(String text) {
    if (this.clickNickNameEditBtn().isPresentNickNameEditContainer()) {
      if (this.getNickNameEditCount() > 0) {
        this.inputNickName(text).clickNickNameConfirmBtn();
        return this;
      }
      logger.error("닉네임 변경 횟수가 남아 있지 않음");
      return null;
    }
    logger.error("닉네임 변경 컨테이너가 출력되지 않음");
    return null;
  }

  public MemberPage clickHiddenInfoBtn() {
    if (this.action.isElement(hiddenInfoBtn, 5)) {
      this.action.waitClick(hiddenInfoBtn, 5);
      logger.info("가려진 정보 보기 버튼 클릭");
      return this;
    }
    logger.error("가려진 정보 보기 버튼 출력되지 않음");
    return null;
  }

  public MemberPage clickPopUpLoginBtn() {
    if (this.action.isElement(popUpLoginBtn, 5)) {
      this.action.waitClick(popUpLoginBtn, 5);
      this.action.popup.switchToPopUp();
      logger.info("비밀번호 입력 확인 버튼 클릭");
      return this;
    }
    logger.error("비밀번호 입력 확인 버튼 출력되지 않음");
    return null;
  }

  public MemberPage changeVisibleInfoMode(String pwd) {
    if (this.action.getDriver().getWindowHandles().size() > 1) {
      this.action.popup.switchToPopUp();
      this.inputPrePassword(pwd).clickPopUpLoginBtn();
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      this.action.popup.switchToMain();
      return this;
    }
    return null;
  }
}