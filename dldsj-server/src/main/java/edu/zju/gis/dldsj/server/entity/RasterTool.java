package edu.zju.gis.dldsj.server.entity;


import edu.zju.gis.dldsj.server.model.JsonAble;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RasterTool implements JsonAble {
    private String artifactId;
    private String name;
    private String description;
    private String usage;
    private String frameworkType;
    private String date;
    private String versionId;
    private String keywords;
    private String groupId;
    private String authorId;
    private String email;
    private String picPath;
    private String packages;

    @Override
    public String id() {
        return artifactId;
    }
}
