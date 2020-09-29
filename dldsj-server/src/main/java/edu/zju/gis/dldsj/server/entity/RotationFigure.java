package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.base.Base;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * @author Jinghaoyu
 * @version 1.0 2020/08/20
 *
 * 轮播图视图实体类
 */
@Getter
@Setter
public class RotationFigure extends Base<Integer> {
    private String imgPath;
    private String pagePath;
    private Date saveTime;
    private String pageType;
}
