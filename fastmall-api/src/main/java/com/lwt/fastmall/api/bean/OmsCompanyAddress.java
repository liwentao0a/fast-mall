package com.lwt.fastmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OmsCompanyAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String addressName;
  private Long sendStatus;
  private Long receiveStatus;
  private String name;
  private String phone;
  private String province;
  private String city;
  private String region;
  private String detailAddress;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getAddressName() {
    return addressName;
  }

  public void setAddressName(String addressName) {
    this.addressName = addressName;
  }


  public Long getSendStatus() {
    return sendStatus;
  }

  public void setSendStatus(Long sendStatus) {
    this.sendStatus = sendStatus;
  }


  public Long getReceiveStatus() {
    return receiveStatus;
  }

  public void setReceiveStatus(Long receiveStatus) {
    this.receiveStatus = receiveStatus;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }


  public String getDetailAddress() {
    return detailAddress;
  }

  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }

}
