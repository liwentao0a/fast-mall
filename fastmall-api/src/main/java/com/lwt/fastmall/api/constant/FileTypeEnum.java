package com.lwt.fastmall.api.constant;

public enum FileTypeEnum {

    JPEG("FFD8FF"),
    PNG("89504E47"),
    GIF("47494638"),
    TIFF("49492A00"),
    RTF("7B5C727466"),
    DOC("D0CF11E0"),
    XLS("D0CF11E0"),
    MDB("5374616E64617264204A"),
    BMP("424D"),
    DWG("41433130"),
    PSD("38425053"),
    XML("3C3F786D6C"),
    HTML("68746D6C3E"),
    PDF("255044462D312E"),
    ZIP("504B0304"),
    RAR("52617221"),
    WAV("57415645"),
    AVI("41564920");

    private String value = "";

    private FileTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}