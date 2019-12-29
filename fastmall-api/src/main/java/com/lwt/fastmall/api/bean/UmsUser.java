package com.lwt.fastmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UmsUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long roleLevel;
  private Long memberId;
  private String username;
  private String password;
  private String nickname;
  private String phone;
  private Long status;
  private java.sql.Timestamp createTime;
  private String icon;
  private Long gender;
  private java.sql.Date birthday;
  private String city;
  private String job;
  private String personalizedSignature;
  private Long sourceType;
  private Long integration;
  private Long growth;
  private Long luckeyCount;
  private Long historyIntegration;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getRoleLevel() {
    return roleLevel;
  }

  public void setRoleLevel(Long roleLevel) {
    this.roleLevel = roleLevel;
  }


  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }


  public Long getGender() {
    return gender;
  }

  public void setGender(Long gender) {
    this.gender = gender;
  }


  public java.sql.Date getBirthday() {
    return birthday;
  }

  public void setBirthday(java.sql.Date birthday) {
    this.birthday = birthday;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }


  public String getPersonalizedSignature() {
    return personalizedSignature;
  }

  public void setPersonalizedSignature(String personalizedSignature) {
    this.personalizedSignature = personalizedSignature;
  }


  public Long getSourceType() {
    return sourceType;
  }

  public void setSourceType(Long sourceType) {
    this.sourceType = sourceType;
  }


  public Long getIntegration() {
    return integration;
  }

  public void setIntegration(Long integration) {
    this.integration = integration;
  }


  public Long getGrowth() {
    return growth;
  }

  public void setGrowth(Long growth) {
    this.growth = growth;
  }


  public Long getLuckeyCount() {
    return luckeyCount;
  }

  public void setLuckeyCount(Long luckeyCount) {
    this.luckeyCount = luckeyCount;
  }


  public Long getHistoryIntegration() {
    return historyIntegration;
  }

  public void setHistoryIntegration(Long historyIntegration) {
    this.historyIntegration = historyIntegration;
  }

}
