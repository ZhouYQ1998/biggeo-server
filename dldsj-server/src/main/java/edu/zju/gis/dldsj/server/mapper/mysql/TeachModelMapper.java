package edu.zju.gis.dldsj.server.mapper.mysql;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.TeachModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Jiarui
 * @date 2020/10/12
 */
@Component
@Mapper
public interface TeachModelMapper extends BaseMapper<TeachModel,String> {

    @Insert({"insert into tb_teach_model(teachmodel_id,name,keywords,file_type,file_path,pic_path) values (#{teachmodelId},#{name},#{keywords},#{fileType},#{filePath},#{picPath})"})
    int insertwll(TeachModel teachmodel);
}
