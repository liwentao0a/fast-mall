package com.lwt.fastmall.api.bean;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

public class PmsProductSaleAttr {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private Long saleAttrId;
  private String saleAttrName;

  @Transient
  private List<PmsProductSaleAttrValue> productSaleAttrValues;

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


  public Long getSaleAttrId() {
    return saleAttrId;
  }

  public void setSaleAttrId(Long saleAttrId) {
    this.saleAttrId = saleAttrId;
  }


  public String getSaleAttrName() {
    return saleAttrName;
  }

  public void setSaleAttrName(String saleAttrName) {
    this.saleAttrName = saleAttrName;
  }

  public List<PmsProductSaleAttrValue> getProductSaleAttrValues() {
    return productSaleAttrValues;
  }

  public void setProductSaleAttrValues(List<PmsProductSaleAttrValue> productSaleAttrValues) {
    this.productSaleAttrValues = productSaleAttrValues;
  }
}
