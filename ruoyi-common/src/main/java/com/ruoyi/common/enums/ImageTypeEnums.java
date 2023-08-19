package com.ruoyi.common.enums;

import java.util.Objects;

public enum ImageTypeEnums {
    MAIN_PIC("z", "1"),
    DETAIL("xq", "2"),
    VIDEO("sp", "3"),
    SL_PIC("sl", "4"),
    ;


    private String code;

    private String value;

    ImageTypeEnums(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getImageTypeValue(String code) {
        for(ImageTypeEnums imageTypeEnums : ImageTypeEnums.values()) {
            if(Objects.equals(code, imageTypeEnums.code)) {
                return imageTypeEnums.value;
            }
        }
        return code;
    }

    public static String getImageTypeCode(String value) {
        for(ImageTypeEnums imageTypeEnums : ImageTypeEnums.values()) {
            if(Objects.equals(value, imageTypeEnums.value)) {
                return imageTypeEnums.code;
            }
        }
        return value;
    }
}
