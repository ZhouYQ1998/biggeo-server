package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.entity.RotationFigure;

import java.util.List;

/**
 * @author Jinghaoyu
 * @version 1.0 2020/08/20
 *
 * 轮播图
 */

public interface RotationFigureService {
    List<RotationFigure> findAll();

//    void save(RotationFigure rotationFigure);
//
//    RotationFigure findById(Integer id);
//
//    void delete(Integer[] id);
}
