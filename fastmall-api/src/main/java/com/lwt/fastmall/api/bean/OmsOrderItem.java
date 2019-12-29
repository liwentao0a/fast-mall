package com.lwt.fastmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

public class OmsOrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long orderId;
  private String orderSn;
  private Long productId;
  private String productPic;
  private String productName;
  private String productBrand;
  private String productSn;
  private BigDecimal productPrice;
  private Long productQuantity;
  private Long productSkuId;
  private String productSkuCode;
  private Long productCategoryId;
  private String sp1;
  private String sp2;
  private String sp3;
  private String promotionName;
  private BigDecimal promotionAmount;
  private BigDecimal couponAmount;
  private BigDecimal integrationAmount;
  private BigDecimal realAmount;
  private Long giftIntegration;
  private Long giftGrowth;
  private String productAttr;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }


  public String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(String orderSn) {
    this.orderSn = orderSn;
  }


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
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


  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }


  public Long getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(Long productQuantity) {
    this.productQuantity = productQuantity;
  }


  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }


  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }


  public Long getProductCategoryId() {
    return productCategoryId;
  }

  public void setProductCategoryId(Long productCategoryId) {
    this.productCategoryId = productCategoryId;
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


  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }


  public BigDecimal getPromotionAmount() {
    return promotionAmount;
  }

  public void setPromotionAmount(BigDecimal promotionAmount) {
    this.promotionAmount = promotionAmount;
  }


  public BigDecimal getCouponAmount() {
    return couponAmount;
  }

  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }


  public BigDecimal getIntegrationAmount() {
    return integrationAmount;
  }

  public void setIntegrationAmount(BigDecimal integrationAmount) {
    this.integrationAmount = integrationAmount;
  }


  public BigDecimal getRealAmount() {
    return realAmount;
  }

  public void setRealAmount(BigDecimal realAmount) {
    this.realAmount = realAmount;
  }


  public Long getGiftIntegration() {
    return giftIntegration;
  }

  public void setGiftIntegration(Long giftIntegration) {
    this.giftIntegration = giftIntegration;
  }


  public Long getGiftGrowth() {
    return giftGrowth;
  }

  public void setGiftGrowth(Long giftGrowth) {
    this.giftGrowth = giftGrowth;
  }


  public String getProductAttr() {
    return productAttr;
  }

  public void setProductAttr(String productAttr) {
    this.productAttr = productAttr;
  }

}
