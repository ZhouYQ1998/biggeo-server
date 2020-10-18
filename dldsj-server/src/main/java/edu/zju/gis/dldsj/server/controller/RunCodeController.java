package edu.zju.gis.dldsj.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Shaojian
 * @date : 20201016
 */

@ConfigurationProperties(prefix = "run.script")
@Component
public class RunCodeConfig {
    private String cpp;
    private String c;
    private String python;
    private String java;

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public void setCpp(String cpp) {
        this.cpp = cpp;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setPython(String python) {
        this.python = python;
    }

    public String getCpp() {
        return cpp;
    }

    public String getC() {
        return c;
    }

    public String getPython() {
        return python;
    }

}