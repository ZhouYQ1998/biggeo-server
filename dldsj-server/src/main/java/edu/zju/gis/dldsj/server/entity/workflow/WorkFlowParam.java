package edu.zju.gis.dldsj.server.entity.workflow;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/9/24
 */
@Getter
@Setter
@ToString(callSuper = true)
public class WorkFlowParam {
    String id;
    String name;
    String description;
    List<Connection> connections;
    List<NodeInfo> nodes;
    String style;

    public String getConnectionsAsJson() {
        Gson gson = new Gson();
        return gson.toJson(this.connections);
    }

    public String getNodesAsJson() {
        Gson gson = new Gson();
        return gson.toJson(this.nodes);
    }
}
