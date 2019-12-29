package com.lwt.fastmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UmsUserMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long growthPoint;
  private Long defaultStatus;
  private double freeFreightPoint;
  private Long commentGrowthPoint;
  private Long priviledgeFreeFreight;
  private Long priviledgeSignIn;
  private Long priviledgeComment;
  private Long priviledgePromotion;
  private Long priviledgeMemberPrice;
  private Long priviledgeBirthday;
  private String note;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Long getGrowthPoint() {
    return growthPoint;
  }

  public void setGrowthPoint(Long growthPoint) {
    this.growthPoint = growthPoint;
  }


  public Long getDefaultStatus() {
    return defaultStatus;
  }

  public void setDefaultStatus(Long defaultStatus) {
    this.defaultStatus = defaultStatus;
  }


  public double getFreeFreightPoint() {
    return freeFreightPoint;
  }

  public void setFreeFreightPoint(double freeFreightPoint) {
    this.freeFreightPoint = freeFreightPoint;
  }


  public Long getCommentGrowthPoint() {
    return commentGrowthPoint;
  }

  public void setCommentGrowthPoint(Long commentGrowthPoint) {
    this.commentGrowthPoint = commentGrowthPoint;
  }


  public Long getPriviledgeFreeFreight() {
    return priviledgeFreeFreight;
  }

  public void setPriviledgeFreeFreight(Long priviledgeFreeFreight) {
    this.priviledgeFreeFreight = priviledgeFreeFreight;
  }


  public Long getPriviledgeSignIn() {
    return priviledgeSignIn;
  }

  public void setPriviledgeSignIn(Long priviledgeSignIn) {
    this.priviledgeSignIn = priviledgeSignIn;
  }


  public Long getPriviledgeComment() {
    return priviledgeComment;
  }

  public void setPriviledgeComment(Long priviledgeComment) {
    this.priviledgeComment = priviledgeComment;
  }


  public Long getPriviledgePromotion() {
    return priviledgePromotion;
  }

  public void setPriviledgePromotion(Long priviledgePromotion) {
    this.priviledgePromotion = priviledgePromotion;
  }


  public Long getPriviledgeMemberPrice() {
    return priviledgeMemberPrice;
  }

  public void setPriviledgeMemberPrice(Long priviledgeMemberPrice) {
    this.priviledgeMemberPrice = priviledgeMemberPrice;
  }


  public Long getPriviledgeBirthday() {
    return priviledgeBirthday;
  }

  public void setPriviledgeBirthday(Long priviledgeBirthday) {
    this.priviledgeBirthday = priviledgeBirthday;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

}
