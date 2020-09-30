package edu.zju.gis.dldsj.server.entity.workflow;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiarui
 * @date 2020/9/24
 */

@Getter
@Setter
public class NodeInfo {
    String id;
    String nodeName;
    String modelId;
    String taskName;
    List<Param> params;

    public String getParamAsJson() {
        Gson gson = new Gson();
        return gson.toJson(params);
    }

    public List<String> getParamValueList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            result.add(params.get(i).value);
        }
        return result;
    }
}
