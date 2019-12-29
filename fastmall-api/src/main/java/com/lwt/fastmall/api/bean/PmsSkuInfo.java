package com.lwt.fastmall.api.bean;


import com.lwt.fastmall.api.group.PmsSkuInfoGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;

public class PmsSkuInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null(groups = {PmsSkuInfoGroup.SaveSku.class})
  private Long id;
  @NotNull(groups = {PmsSkuInfoGroup.SaveSku.class})
  private Long productId;
  @NotNull(groups = {PmsSkuInfoGroup.SaveSku.class})
  private BigDecimal price;
  @NotBlank(groups = {PmsSkuInfoGroup.SaveSku.class})
  private String skuName;
  @NotNull(groups = {PmsSkuInfoGroup.SaveSku.class})
  private String skuDesc;
  @NotNull(groups = {PmsSkuInfoGroup.SaveSku.class})
  private BigDecimal weight;
  private Long tmId;
  @NotNull(groups = {PmsSkuInfoGroup.SaveSku.class})
  private Long catalog3Id;
  @NotBlank(groups = {PmsSkuInfoGroup.SaveSku.class})
  private String skuDefaultImg;

  @Transient
  private List<PmsSkuImage> skuImages;
  @Transient
  private List<PmsSkuAttrValue> skuAttrValues;
  @Transient
  private List<PmsSkuSaleAttrValue> skuSaleAttrValues;

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


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public String getSkuName() {
    return skuName;
  }

  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }


  public String getSkuDesc() {
    return skuDesc;
  }

  public void setSkuDesc(String skuDesc) {
    this.skuDesc = skuDesc;
  }


  public BigDecimal getWeight() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }


  public Long getTmId() {
    return tmId;
  }

  public void setTmId(Long tmId) {
    this.tmId = tmId;
  }


  public Long getCatalog3Id() {
    return catalog3Id;
  }

  public void setCatalog3Id(Long catalog3Id) {
    this.catalog3Id = catalog3Id;
  }


  public String getSkuDefaultImg() {
    return skuDefaultImg;
  }

  public void setSkuDefaultImg(String skuDefaultImg) {
    this.skuDefaultImg = skuDefaultImg;
  }

  public List<PmsSkuImage> getSkuImages() {
    return skuImages;
  }

  public void setSkuImages(List<PmsSkuImage> skuImages) {
    this.skuImages = skuImages;
  }

  public List<PmsSkuAttrValue> getSkuAttrValues() {
    return skuAttrValues;
  }

  public void setSkuAttrValues(List<PmsSkuAttrValue> skuAttrValues) {
    this.skuAttrValues = skuAttrValues;
  }

  public List<PmsSkuSaleAttrValue> getSkuSaleAttrValues() {
    return skuSaleAttrValues;
  }

  public void setSkuSaleAttrValues(List<PmsSkuSaleAttrValue> skuSaleAttrValues) {
    this.skuSaleAttrValues = skuSaleAttrValues;
  }
}
