package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.config.QueryConfig;
import edu.zju.gis.dldsj.server.mapper.ParallelModelMapper;
import edu.zju.gis.dldsj.server.entity.ParallelModel;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import edu.zju.gis.dldsj.server.service.ParallelModelService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/01
 */
@Service
public class ParallelModelServiceImpl  implements ParallelModelService {


    @Override
    public List<ParallelModel> getByIdList(Collection<String> idList) {
        return null;
    }

    @Override
    public String getCmd(String artifactId, String jobName, List<String> params, List<String> envSetting) {
        return null;
    }

    @Override
    public String getCmd(ParallelModelWithBLOBs model, String jobName, List<String> params, List<String> envSetting) {
        return null;
    }

    @Override
    public Page<ParallelModelWithBLOBs> search(BaseFilter params, Page page) {
        return null;
    }

    @Override
    public Page<ParallelModel> searchByClass(Collection<String> idList, Page page) {
        return null;
    }

    @Override
    public List<ParallelModel> searchByClass(List<String> classIds) {
        return null;
    }

    @Override
    public List<ParallelModel> searchPublicByClass(List<String> classIds) {
        return null;
    }

    @Override
    public List<ParallelModel> searchPublicByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<String> getIdList() {
        return null;
    }

    @Override
    public Page<ParallelModel> searchByUserId(String userId, Page page) {
        return null;
    }

    @Override
    public List<ParallelModel> searchByUserId(String userId) {
        return null;
    }

    @Override
    public Page<ParallelModel> selectPublicModel(Page page) {
        return null;
    }

    @Override
    public List<ParallelModel> selectByKeywords(String userId, String keys) {
        return null;
    }
}
