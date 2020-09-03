package edu.zju.gis.dldsj.server.service;

import com.github.pagehelper.Page;
import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.RotationFigure;

import java.util.List;
import java.util.Map;

/**
 * @author Jinghaoyu
 * @version 1.0 2020/08/20
 *
 * 轮播图
 */

public interface RotationFigureService extends BaseService<RotationFigure, Integer> {
    List<RotationFigure> findAll();

    Page<RotationFigure> findByPaging(Map param);
}
