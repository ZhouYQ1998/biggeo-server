package edu.zju.gis.dldsj.server.common;

import lombok.Getter;

/**
 * @auther Hu
 * @date 2019/1/2
 **/
public enum Seprator {

    COMMA(","), TAB("\t"), SINGLE_LINE("\r\n");

    @Getter
    private String value;

    Seprator(String value) {
        this.value = value;
    }

}
