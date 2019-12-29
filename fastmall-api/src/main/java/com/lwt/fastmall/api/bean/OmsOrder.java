package com.lwt.fastmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

public class OmsOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long couponId;
  private String orderSn;
  private java.sql.Timestamp createTime;
  private String userUsername;
  private BigDecimal totalAmount;
  private BigDecimal payAmount;
  private BigDecimal freightAmount;
  private BigDecimal promotionAmount;
  private BigDecimal integrationAmount;
  private BigDecimal couponAmount;
  private BigDecimal discountAmount;
  private Long payType;
  private Long sourceType;
  private Long status;
  private Long orderType;
  private String deliveryCompany;
  private String deliverySn;
  private Long autoConfirmDay;
  private Long integration;
  private Long growth;
  private String promotionInfo;
  private Long billType;
  private String billHeader;
  private String billContent;
  private String billReceiverPhone;
  private String billReceiverEmail;
  private String receiverName;
  private String receiverPhone;
  private String receiverPostCode;
  private String receiverProvince;
  private String receiverCity;
  private String receiverRegion;
  private String receiverDetailAddress;
  private String note;
  private Long confirmStatus;
  private Long deleteStatus;
  private Long useIntegration;
  private java.sql.Timestamp paymentTime;
  private java.sql.Timestamp deliveryTime;
  private java.sql.Timestamp receiveTime;
  private java.sql.Timestamp commentTime;
  private java.sql.Timestamp modifyTime;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public Long getCouponId() {
    return couponId;
  }

  public void setCouponId(Long couponId) {
    this.couponId = couponId;
  }


  public String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(String orderSn) {
    this.orderSn = orderSn;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public String getUserUsername() {
    return userUsername;
  }

  public void setUserUsername(String userUsername) {
    this.userUsername = userUsername;
  }


  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }


  public BigDecimal getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(BigDecimal payAmount) {
    this.payAmount = payAmount;
  }


  public BigDecimal getFreightAmount() {
    return freightAmount;
  }

  public void setFreightAmount(BigDecimal freightAmount) {
    this.freightAmount = freightAmount;
  }


  public BigDecimal getPromotionAmount() {
    return promotionAmount;
  }

  public void setPromotionAmount(BigDecimal promotionAmount) {
    this.promotionAmount = promotionAmount;
  }


  public BigDecimal getIntegrationAmount() {
    return integrationAmount;
  }

  public void setIntegrationAmount(BigDecimal integrationAmount) {
    this.integrationAmount = integrationAmount;
  }


  public BigDecimal getCouponAmount() {
    return couponAmount;
  }

  public void setCouponAmount(BigDecimal couponAmount) {
    this.couponAmount = couponAmount;
  }


  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }


  public Long getPayType() {
    return payType;
  }

  public void setPayType(Long payType) {
    this.payType = payType;
  }


  public Long getSourceType() {
    return sourceType;
  }

  public void setSourceType(Long sourceType) {
    this.sourceType = sourceType;
  }


  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }


  public Long getOrderType() {
    return orderType;
  }

  public void setOrderType(Long orderType) {
    this.orderType = orderType;
  }


  public String getDeliveryCompany() {
    return deliveryCompany;
  }

  public void setDeliveryCompany(String deliveryCompany) {
    this.deliveryCompany = deliveryCompany;
  }


  public String getDeliverySn() {
    return deliverySn;
  }

  public void setDeliverySn(String deliverySn) {
    this.deliverySn = deliverySn;
  }


  public Long getAutoConfirmDay() {
    return autoConfirmDay;
  }

  public void setAutoConfirmDay(Long autoConfirmDay) {
    this.autoConfirmDay = autoConfirmDay;
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


  public String getPromotionInfo() {
    return promotionInfo;
  }

  public void setPromotionInfo(String promotionInfo) {
    this.promotionInfo = promotionInfo;
  }


  public Long getBillType() {
    return billType;
  }

  public void setBillType(Long billType) {
    this.billType = billType;
  }


  public String getBillHeader() {
    return billHeader;
  }

  public void setBillHeader(String billHeader) {
    this.billHeader = billHeader;
  }


  public String getBillContent() {
    return billContent;
  }

  public void setBillContent(String billContent) {
    this.billContent = billContent;
  }


  public String getBillReceiverPhone() {
    return billReceiverPhone;
  }

  public void setBillReceiverPhone(String billReceiverPhone) {
    this.billReceiverPhone = billReceiverPhone;
  }


  public String getBillReceiverEmail() {
    return billReceiverEmail;
  }

  public void setBillReceiverEmail(String billReceiverEmail) {
    this.billReceiverEmail = billReceiverEmail;
  }


  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }


  public String getReceiverPhone() {
    return receiverPhone;
  }

  public void setReceiverPhone(String receiverPhone) {
    this.receiverPhone = receiverPhone;
  }


  public String getReceiverPostCode() {
    return receiverPostCode;
  }

  public void setReceiverPostCode(String receiverPostCode) {
    this.receiverPostCode = receiverPostCode;
  }


  public String getReceiverProvince() {
    return receiverProvince;
  }

  public void setReceiverProvince(String receiverProvince) {
    this.receiverProvince = receiverProvince;
  }


  public String getReceiverCity() {
    return receiverCity;
  }

  public void setReceiverCity(String receiverCity) {
    this.receiverCity = receiverCity;
  }


  public String getReceiverRegion() {
    return receiverRegion;
  }

  public void setReceiverRegion(String receiverRegion) {
    this.receiverRegion = receiverRegion;
  }


  public String getReceiverDetailAddress() {
    return receiverDetailAddress;
  }

  public void setReceiverDetailAddress(String receiverDetailAddress) {
    this.receiverDetailAddress = receiverDetailAddress;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }


  public Long getConfirmStatus() {
    return confirmStatus;
  }

  public void setConfirmStatus(Long confirmStatus) {
    this.confirmStatus = confirmStatus;
  }


  public Long getDeleteStatus() {
    return deleteStatus;
  }

  public void setDeleteStatus(Long deleteStatus) {
    this.deleteStatus = deleteStatus;
  }


  public Long getUseIntegration() {
    return useIntegration;
  }

  public void setUseIntegration(Long useIntegration) {
    this.useIntegration = useIntegration;
  }


  public java.sql.Timestamp getPaymentTime() {
    return paymentTime;
  }

  public void setPaymentTime(java.sql.Timestamp paymentTime) {
    this.paymentTime = paymentTime;
  }


  public java.sql.Timestamp getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(java.sql.Timestamp deliveryTime) {
    this.deliveryTime = deliveryTime;
  }


  public java.sql.Timestamp getReceiveTime() {
    return receiveTime;
  }

  public void setReceiveTime(java.sql.Timestamp receiveTime) {
    this.receiveTime = receiveTime;
  }


  public java.sql.Timestamp getCommentTime() {
    return commentTime;
  }

  public void setCommentTime(java.sql.Timestamp commentTime) {
    this.commentTime = commentTime;
  }


  public java.sql.Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(java.sql.Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

}
