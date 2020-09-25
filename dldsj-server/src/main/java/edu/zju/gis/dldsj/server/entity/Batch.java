package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zyq 2020/09/23
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Batch<T> {
    private T t;
    private String message;
}

