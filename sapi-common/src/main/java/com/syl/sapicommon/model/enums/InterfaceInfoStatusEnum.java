package com.syl.sapicommon.model.enums;

public enum InterfaceInfoStatusEnum {
    //上线
    ONLINE("上线",1),
    //下线
    OFFLINE("下线",0);
    private String text;
    private int value;

    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
