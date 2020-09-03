package edu.zju.gis.dldsj.server.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Hu
 * @date 2019/4/27
 **/
@Getter
@Setter
public abstract class BaseFilter<T> {
    private T id;
}
