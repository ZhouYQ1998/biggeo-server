package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 在线分析平台模块需要的图层信息
 */
@Getter
@Setter
public class mapProject {
    private String layers;
    private String style;
}
