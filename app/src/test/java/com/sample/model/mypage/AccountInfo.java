package com.sample.model.mypage;

public class AccountInfo {
  private String profileImg;
  private String id;
  private String password;
  private String name;
  private String nickName;
  private String email;
  private String phone;


  public AccountInfo() {

  }

  public String getProfileImg() {
    return this.profileImg;
  }

  public void setProfileImg(String profileImg) {
    this.profileImg = profileImg;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
  
}
