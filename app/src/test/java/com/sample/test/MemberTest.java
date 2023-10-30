package com.sample.test;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sample.helper.MaskingHelper;
import com.sample.model.mypage.AccountInfo;
import com.sample.page.LoginPage;
import com.sample.page.MemberPage;
import com.sample.page.MyPage;
import com.sample.test.common.TestTemplate;
import com.sample.util.PropertyHandler;

public class MemberTest extends TestTemplate {

  LoginPage loginPage;
  MyPage myPage;
  MemberPage memberPage;
  Boolean is_changed_pw = false;
  String changePwd = "test1234";

  @Override
  protected void doBeforeClass() {
    loginPage = new LoginPage(getWebCommonDriver());
    myPage = new MyPage(getWebCommonDriver());
    memberPage = new MemberPage(getWebCommonDriver());

    loginPage.login(PropertyHandler.getProperties("id"), PropertyHandler.getProperties("password"));
    memberPage.Navigate();
  }
  
  @Test(description="회원정보 페이지 이동")
  public void tid_001() {
    myPage.Navigate().clickMemberBtn();
    Assert.assertTrue(memberPage.isPresentPage(), "회원정보 페이지로 이동되지 않음");
  }

  @Test(description="기본회원정보 > 사진 등록 확인")
  public void tid_002() {
    memberPage.uploadProfile("changeProfileImg.jpeg");
    Assert.assertNotEquals(memberPage.getProfileImgName(), "b.png", "프로필 이미지 변경되지 않고, 기본 이미지로 출력");
  }

  @Test(description="기본회원정보 > 기본 이미지 변경 확인")
  public void tid_003() {
    this.tid_002();
    memberPage.changeDefaultProfile();
    Assert.assertEquals(memberPage.getProfileImgName(), "b.png", "프로필 기본이미지로 변경되지 않고, 기존 업로드 이미지로 출력");
  }

  @Test(description="기본회원정보 > 기본 정보 출력 확인")
  public void tid_004() {
    AccountInfo accountInfo = new AccountInfo();
    MaskingHelper maskingHelper = new MaskingHelper();
    accountInfo = memberPage.getDefaultAccountInfo();

    // POI 연결하여 TestData를 Excel로 부터 가져오도록 추후 개선
    String expectedId = PropertyHandler.getProperties("id");
    String expectedName = maskingHelper.nameMasking(PropertyHandler.getProperties("name"));
    String expectedEmail = maskingHelper.emailMasking(PropertyHandler.getProperties("email"));
    String expectedPhone = maskingHelper.phoneMasking(PropertyHandler.getProperties("phone"));

    this.getSoftAssert().assertEquals(accountInfo.getId(), expectedId, "아이디 미스매치");
    this.getSoftAssert().assertEquals(accountInfo.getPassword(), "********", "패스워드 미스매치");
    this.getSoftAssert().assertEquals(accountInfo.getName(), expectedName, "이름 미스매치");
    this.getSoftAssert().assertEquals(accountInfo.getEmail(), expectedEmail, "이메일 미스매치");
    this.getSoftAssert().assertEquals(accountInfo.getPhone(), expectedPhone, "휴대전화번호 미스매치");

    this.getSoftAssert().assertAll();
  }

  @Test(description="기본회원정보 > 비밀번호 변경 확인")
  public void tid_005() {
    memberPage.changePassword(PropertyHandler.getProperties("password"), changePwd);
    Assert.assertTrue(memberPage.getAlertMsg().contains("비밀번호가 변경되었습니다."), "비밀번호 변경 완료 알럿 출력되지 않음");

    memberPage.confirmAlert();
    is_changed_pw = true;
  }

  @Test(description = "기본회원정보 > 닉네임 변경 확인")
  public void tid_007() {
    Random rnd = new Random();
    String nickName = "테스트닉네임" + String.valueOf(rnd.nextInt(10));

    memberPage.changeNickName(nickName);

    Assert.assertEquals(nickName, memberPage.getNickName());
  }

  @Test(description = "가려진정보 보기 해제 확인")
  public void tid_021() {
    memberPage.clickHiddenInfoBtn();
    memberPage.changeVisibleInfoMode(PropertyHandler.getProperties("password"));
    
    AccountInfo accountInfo = new AccountInfo();
    accountInfo = memberPage.getDefaultAccountInfo();

    String expectedName = PropertyHandler.getProperties("name");
    String expectedEmail = PropertyHandler.getProperties("email");
    String expectedPhone = PropertyHandler.getProperties("phone");

    this.getSoftAssert().assertEquals(accountInfo.getName(), expectedName, "이름 미스매치");
    this.getSoftAssert().assertEquals(accountInfo.getEmail(), expectedEmail, "이메일 미스매치");
    this.getSoftAssert().assertEquals(accountInfo.getPhone(), expectedPhone, "휴대전화번호 미스매치");

    this.getSoftAssert().assertAll();
  }

  @Override
  protected void doAfterMethod() {
    memberPage.Navigate();
    
    if (!memberPage.getProfileImgName().contains("b.png")) {
      memberPage.changeDefaultProfile();
    }

    if (is_changed_pw) {
      memberPage.changePassword(changePwd, PropertyHandler.getProperties("password"));
      is_changed_pw = false;
    }
  }
}