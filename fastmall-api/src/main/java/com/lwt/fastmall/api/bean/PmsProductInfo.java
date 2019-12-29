package com.lwt.fastmall.api.bean;


import com.lwt.fastmall.api.group.PmsProductInfoGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

public class PmsProductInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null(groups = {PmsProductInfoGroup.SaveProduct.class})
  private Long id;
  @NotBlank(groups = {PmsProductInfoGroup.SaveProduct.class})
  private String productName;
  @NotBlank(groups = {PmsProductInfoGroup.SaveProduct.class})
  private String description;
  @NotNull(groups = {PmsProductInfoGroup.SaveProduct.class})
  private Long catalog3Id;
  private Long tmId;

  @Transient
  private List<PmsProductImage> productImages;
  @Transient
  private List<PmsProductSaleAttr> productSaleAttrs;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public Long getCatalog3Id() {
    return catalog3Id;
  }

  public void setCatalog3Id(Long catalog3Id) {
    this.catalog3Id = catalog3Id;
  }


  public Long getTmId() {
    return tmId;
  }

  public void setTmId(Long tmId) {
    this.tmId = tmId;
  }

  public List<PmsProductImage> getProductImages() {
    return productImages;
  }

  public void setProductImages(List<PmsProductImage> productImages) {
    this.productImages = productImages;
  }

  public List<PmsProductSaleAttr> getProductSaleAttrs() {
    return productSaleAttrs;
  }

  public void setProductSaleAttrs(List<PmsProductSaleAttr> productSaleAttrs) {
    this.productSaleAttrs = productSaleAttrs;
  }
}
