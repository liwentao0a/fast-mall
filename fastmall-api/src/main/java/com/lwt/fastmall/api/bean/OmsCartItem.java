package com.lwt.fastmall.api.bean;


import com.lwt.fastmall.api.group.OmsCartItemGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

public class OmsCartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null(groups = OmsCartItemGroup.SaveCartItem.class)
  private Long id;
  @NotNull(groups = OmsCartItemGroup.SaveCartItem.class)
  private Long productId;
  @NotNull(groups = OmsCartItemGroup.SaveCartItem.class)
  private Long productSkuId;
  @NotNull(groups = OmsCartItemGroup.SaveCartItem.class)
  private Long userId;
  @NotNull(groups = OmsCartItemGroup.SaveCartItem.class)
  private Long quantity;
  @NotNull(groups = OmsCartItemGroup.SaveCartItem.class)
  private BigDecimal price;
  private String sp1;
  private String sp2;
  private String sp3;
  @NotBlank(groups = OmsCartItemGroup.SaveCartItem.class)
  private String productPic;
  @NotBlank(groups = OmsCartItemGroup.SaveCartItem.class)
  private String productName;
  private String productSubTitle;
  private String productSkuCode;
  @NotBlank(groups = OmsCartItemGroup.SaveCartItem.class)
  private String userNickname;
  @Null(groups = OmsCartItemGroup.SaveCartItem.class)
  private java.sql.Timestamp createDate;
  @Null(groups = OmsCartItemGroup.SaveCartItem.class)
  private java.sql.Timestamp modifyDate;
  @Null(groups = OmsCartItemGroup.SaveCartItem.class)
  private Long deleteStatus;
  @NotNull(groups = OmsCartItemGroup.SaveCartItem.class)
  private Long productCategoryId;
  private String productBrand;
  private String productSn;
  private String productAttr;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }


  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public String getSp1() {
    return sp1;
  }

  public void setSp1(String sp1) {
    this.sp1 = sp1;
  }


  public String getSp2() {
    return sp2;
  }

  public void setSp2(String sp2) {
    this.sp2 = sp2;
  }


  public String getSp3() {
    return sp3;
  }

  public void setSp3(String sp3) {
    this.sp3 = sp3;
  }


  public String getProductPic() {
    return productPic;
  }

  public void setProductPic(String productPic) {
    this.productPic = productPic;
  }


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public String getProductSubTitle() {
    return productSubTitle;
  }

  public void setProductSubTitle(String productSubTitle) {
    this.productSubTitle = productSubTitle;
  }


  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }


  public String getUserNickname() {
    return userNickname;
  }

  public void setUserNickname(String userNickname) {
    this.userNickname = userNickname;
  }


  public java.sql.Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(java.sql.Timestamp createDate) {
    this.createDate = createDate;
  }


  public java.sql.Timestamp getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(java.sql.Timestamp modifyDate) {
    this.modifyDate = modifyDate;
  }


  public Long getDeleteStatus() {
    return deleteStatus;
  }

  public void setDeleteStatus(Long deleteStatus) {
    this.deleteStatus = deleteStatus;
  }


  public Long getProductCategoryId() {
    return productCategoryId;
  }

  public void setProductCategoryId(Long productCategoryId) {
    this.productCategoryId = productCategoryId;
  }


  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }


  public String getProductSn() {
    return productSn;
  }

  public void setProductSn(String productSn) {
    this.productSn = productSn;
  }


  public String getProductAttr() {
    return productAttr;
  }

  public void setProductAttr(String productAttr) {
    this.productAttr = productAttr;
  }

}
