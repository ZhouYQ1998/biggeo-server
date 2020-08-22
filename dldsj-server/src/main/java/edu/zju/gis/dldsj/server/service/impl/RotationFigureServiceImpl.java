package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.Page;
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
public class RotationFigureServiceImpl implements RotationFigureService {

    @Resource
    private RotationFigureMapper rotationFigureMapper;

    @Override
    public List<RotationFigure> findAll() {
        return rotationFigureMapper.findAll();
    }

    @Override
    public List<RotationFigure> getById(Integer id, int offset, int size) {
        return rotationFigureMapper.selectById(id, offset, size);
    }

    @Override
    public void deleteById(Integer id) {
        rotationFigureMapper.deleteById(id);
    }

    @Override
    public boolean ifFigureExist(Integer id) {
        int a = rotationFigureMapper.ifFigureExist(id);
        return a > 0 ? true : false;
    }

    @Override
    public Page<RotationFigure> findByPaging(Map param) {
        return rotationFigureMapper.findByPaging(param);
    }

    @Override
    public RotationFigure select(Integer pk) {
        return rotationFigureMapper.selectByPrimaryKey(pk);
    }

    @Override
    public int insert(RotationFigure rotationFigure) {
        return rotationFigureMapper.insertSelective(rotationFigure);
    }

    @Override
    public void update(RotationFigure rotationFigure) {
        rotationFigureMapper.updateByPrimaryKeySelective(rotationFigure);
    }

    @Override
    public void delete(Integer id) {
        rotationFigureMapper.deleteByPrimaryKey(id);
    }

    @Override
    public boolean isExist(Integer id) {
        return rotationFigureMapper.selectByPrimaryKey(id) != null;
    }

    @Override
    public List<RotationFigure> getByPage(int offset, int size) {
        return rotationFigureMapper.selectByPage(offset, size);
    }
}
