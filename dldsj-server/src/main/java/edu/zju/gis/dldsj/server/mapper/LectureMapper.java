package edu.zju.gis.dldsj.server.mapper;
import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Lecture;
import java.util.List;


/**
 * @author Jiarui
 * @date 2020/8/19 9:52 上午
 */

public interface LectureMapper extends BaseMapper<Lecture,String> {

    //type为排序的字段，typeOrder为升序或降序
    List<Lecture> selectByOrder(String type,String typeOrder);

}
