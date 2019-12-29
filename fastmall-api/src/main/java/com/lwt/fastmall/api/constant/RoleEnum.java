package com.lwt.fastmall.api.constant;

public enum RoleEnum {

    USER("USER",1),
    ADMIN("ADMIN",10);

    private String name;
    private int level;

    RoleEnum(String name, int level) {
        this.name=name;
        this.level=level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
