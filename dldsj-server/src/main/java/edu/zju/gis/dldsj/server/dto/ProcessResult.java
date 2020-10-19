package edu.zju.gis.dldsj.server.dto;

/**
 * @author : Shaojian
 * @date : 20201016
 */

public class ProcessResult {
    private int exitCode;

    private String output;

    public ProcessResult(int exitCode, String output) {
        this.exitCode = exitCode;
        this.output = output;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getOutput() {
        return output;
    }
}
