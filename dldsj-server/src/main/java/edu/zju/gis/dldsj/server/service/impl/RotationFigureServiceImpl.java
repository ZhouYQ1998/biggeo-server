package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.Page;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.RotationFigure;
import edu.zju.gis.dldsj.server.mapper.RotationFigureMapper;
import edu.zju.gis.dldsj.server.service.RotationFigureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Jinghaoyu
 * @version 1.0 2020/08/20
 *
 * 轮播图
 */

@Service
public class RotationFigureServiceImpl extends BaseServiceImpl<RotationFigureMapper, RotationFigure, Integer> implements RotationFigureService {

    @Resource
    private RotationFigureMapper rotationFigureMapper;

    @Override
    public List<RotationFigure> findAll() {
        return rotationFigureMapper.findAll();
    }


    @Override
    public Page<RotationFigure> findByPaging(Map param) {
        return rotationFigureMapper.findByPaging(param);
    }

    @Override
    public List<RotationFigure> getByPage(int offset, int size) {
        return rotationFigureMapper.selectByPage(offset, size);
    }
}
