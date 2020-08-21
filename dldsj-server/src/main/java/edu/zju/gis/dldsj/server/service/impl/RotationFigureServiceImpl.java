package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.entity.RotationFigure;
import edu.zju.gis.dldsj.server.mapper.RotationFigureMapper;
import edu.zju.gis.dldsj.server.service.RotationFigureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jinghaoyu
 * @version 1.0 2020/08/20
 *
 * 轮播图
 */

@Service
public class RotationFigureServiceImpl implements RotationFigureService {

    @Resource
    private RotationFigureMapper rotationFigureMapper;

    @Override
    public List<RotationFigure> findAll() {
        return rotationFigureMapper.findAl();
    }

//    @Override
//    public void save(RotationFigure rotationFigure) {
//
//        //判断是添加还是修改
//        if(rotationFigure.getId()!=null){
//            rotationFigureMapper.update(rotationFigure);
//        }
//        else {
//            rotationFigureMapper.save(rotationFigure);
//        }
//    }
//
//    @Override
//    public RotationFigure findById(Integer id) {
//        return rotationFigureMapper.findById(id);
//    }
//
//    @Override
//    public void delete(Integer[] id) {
//        rotationFigureMapper.delete(id);
//    }


}
