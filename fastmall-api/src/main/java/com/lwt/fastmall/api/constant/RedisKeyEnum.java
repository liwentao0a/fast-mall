package com.lwt.fastmall.api.constant;

public enum RedisKeyEnum {

    FASTMALL_DB_UMS_USER("FASTMALL","DB","UMS_USER"),
    FASTMALL_DB_PMS_BASE_CATALOG1("FASTMALL","DB","PMS_BASE_CATALOG1"),
    FASTMALL_DB_PMS_BASE_CATALOG2("FASTMALL","DB","PMS_BASE_CATALOG2"),
    FASTMALL_DB_PMS_BASE_CATALOG3("FASTMALL","DB","PMS_BASE_CATALOG3"),
    FASTMALL_DB_PMS_BASE_ATTR_INFO("FASTMALL","DB","PMS_BASE_ATTR_INFO"),
    FASTMALL_DB_PMS_BASE_ATTR_VALUE("FASTMALL","DB","PMS_BASE_ATTR_VALUE"),
    FASTMALL_DB_PMS_BASE_SALE_ATTR("FASTMALL","DB","PMS_BASE_SALE_ATTR"),
    FASTMALL_DB_PMS_PRODUCT_INFO("FASTMALL","DB","PMS_PRODUCT_INFO"),
    FASTMALL_DB_PMS_SKU_INFO("FASTMALL","DB","PMS_SKU_INFO");

    private String prefix;
    private String module;
    private String type;

    RedisKeyEnum(String prefix, String module, String type) {
        this.prefix=prefix;
        this.module=module;
        this.type=type;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
