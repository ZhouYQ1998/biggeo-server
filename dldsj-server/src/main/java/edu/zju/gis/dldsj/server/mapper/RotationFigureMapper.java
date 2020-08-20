package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.entity.RotationFigure;

import java.util.List;

/**
 * @author Jinghaoyu
 * @version 1.0 2020/08/20
 *
 * 轮播图
 */

public interface RotationFigureMapper {
    List<RotationFigure> findAl();

    void save(RotationFigure rotationFigure);

    RotationFigure findById(Integer id);

    void delete(Integer[] id);

    void update(RotationFigure rotationFigure);
}
