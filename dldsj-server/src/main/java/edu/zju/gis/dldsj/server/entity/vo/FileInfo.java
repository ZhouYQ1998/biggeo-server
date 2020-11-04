package edu.zju.gis.dldsj.server.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-02
 */
@Getter
@Setter
public class FileInfo {
    private String name;
    private String path;
    private Boolean isFile;
    private Long size;
    private Date modificationTime;

    public FileInfo() {
        this("", "/", false, 0L, new Date(System.currentTimeMillis()));
    }

    public FileInfo(String name, String path, Boolean isFile, Long size, Date modificationTime) {
        this.name = name;
        this.path = path;
        this.isFile = isFile;
        this.size = size;
        this.modificationTime = modificationTime;
    }
}
