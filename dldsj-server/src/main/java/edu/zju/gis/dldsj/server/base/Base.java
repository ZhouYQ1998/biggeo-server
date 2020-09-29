package edu.zju.gis.dldsj.server.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zyq 2020/09/29
 */
@Getter
@Setter
@ToString(callSuper = true)
public abstract class Base<T> {
    private T id;
}

