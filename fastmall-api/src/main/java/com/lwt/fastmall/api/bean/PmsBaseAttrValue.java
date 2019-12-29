package com.lwt.fastmall.api.bean;


import com.lwt.fastmall.api.group.PmsBaseAttrValueGroup;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class PmsBaseAttrValue {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Null(groups ={PmsBaseAttrValueGroup.SaveBaseAttrValue.class})
  private Long id;
  @NotBlank(groups ={PmsBaseAttrValueGroup.SaveBaseAttrValue.class})
  private String valueName;
  @NotNull(groups ={PmsBaseAttrValueGroup.SaveBaseAttrValue.class})
  private Long attrId;
  @Null(groups ={PmsBaseAttrValueGroup.SaveBaseAttrValue.class})
  private String isEnabled;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getValueName() {
    return valueName;
  }

  public void setValueName(String valueName) {
    this.valueName = valueName;
  }


  public Long getAttrId() {
    return attrId;
  }

  public void setAttrId(Long attrId) {
    this.attrId = attrId;
  }


  public String getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(String isEnabled) {
    this.isEnabled = isEnabled;
  }

}
