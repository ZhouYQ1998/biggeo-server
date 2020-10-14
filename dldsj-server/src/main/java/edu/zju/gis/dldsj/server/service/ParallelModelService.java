package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.ParallelModel;
import edu.zju.gis.dldsj.server.entity.ParallelModelUsage;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;

import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-09-27
 */
public interface ParallelModelService {
    ParallelModelWithBLOBs select(String artifactId);

    int insert(ParallelModelWithBLOBs model);

    int delete(String artifactId);

    Page<ParallelModel> selectWithPage(Page<ParallelModel> page);

    Page<ParallelModel> selectByType(String type, Page<ParallelModel> page);

    Page<ParallelModel> selectByUser(String userId, Page<ParallelModel> page);

    Page<ParallelModel> search(String keywords, Page<ParallelModel> page);

    List<ParallelModelUsage> getAllUsages();

    String getCmd(String artifactId, String jobName, List<String> params, List<String> envSetting);

    String getCmd(ParallelModelWithBLOBs model, String jobName, List<String> params, List<String> envSetting);
}
