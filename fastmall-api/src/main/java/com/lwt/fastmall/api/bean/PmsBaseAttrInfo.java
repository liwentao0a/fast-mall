package com.lwt.fastmall.api.bean;


import com.lwt.fastmall.api.group.PmsBaseAttrInfoGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

public class PmsBaseAttrInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null(groups = {PmsBaseAttrInfoGroup.SaveBaseAttrInfo.class})
  private Long id;
  @NotBlank(groups = {PmsBaseAttrInfoGroup.SaveBaseAttrInfo.class})
  private String attrName;
  @NotNull(groups = {PmsBaseAttrInfoGroup.SaveBaseAttrInfo.class})
  private Long catalog3Id;
  @Null(groups = {PmsBaseAttrInfoGroup.SaveBaseAttrInfo.class})
  private String isEnabled;

  @Transient
  private List<PmsBaseAttrValue> baseAttrValues;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }


  public Long getCatalog3Id() {
    return catalog3Id;
  }

  public void setCatalog3Id(Long catalog3Id) {
    this.catalog3Id = catalog3Id;
  }


  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }

  public List<PmsBaseAttrValue> getBaseAttrValues() {
    return baseAttrValues;
  }

  public void setBaseAttrValues(List<PmsBaseAttrValue> baseAttrValues) {
    this.baseAttrValues = baseAttrValues;
  }
}
