package edu.zju.gis.dldsj.server.service;


import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.ParallelModel;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import org.json.JSONArray;

import java.util.Collection;
import java.util.List;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/01
 */
public interface ParallelModelService extends BaseService<ParallelModelWithBLOBs, String> {
    List<ParallelModel> getByIdList(Collection<String> idList);

    default String getCmd(String artifactId, String jobName, List<String> params, List<String> envSetting) {
        ParallelModelWithBLOBs model = select(artifactId);
        return getCmd(model, jobName, params, envSetting);
    }

    String getCmd(ParallelModelWithBLOBs model, String jobName, List<String> params, List<String> envSetting);

    Page<ParallelModelWithBLOBs> search(BaseFilter params, Page page);

    Page<ParallelModel> searchByClass(Collection<String> idList, Page page);

    List<ParallelModel> searchByClass(List<String> classIds);

    List<ParallelModel> searchPublicByClass(List<String> classIds);

    List<ParallelModel> searchPublicByKeyword(String keyword);

    List<String> getIdList();

    Page<ParallelModel> searchByUserId(String userId, Page page);

    List<ParallelModel> searchByUserId(String userId);

    Page<ParallelModel> selectPublicModel(Page page);

    List<ParallelModel> selectByKeywords(String userId, String keys);
}
