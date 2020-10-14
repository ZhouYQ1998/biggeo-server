package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.model.JsonAble;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-05
 */
@Getter
@Setter
@NoArgsConstructor
public class ParallelModelUsage implements JsonAble {
    String name;

    @Override
    public String id() {
        return name;
    }
}
