package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.model.JsonAble;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-05
 */
@Getter
@Setter
public class Monitor implements JsonAble {
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String APPLICATION_TYPE = "applicationType";
    public static final String QUEUE = "queue";
    public static final String STATE = "state";
    public static final String FINAL_STATUS = "finalStatus";
    public static final String PROGRESS = "progress";
    public static final String TRACKING_UI = "trackingUI";
    public static final String TRACKING_URL = "trackingUrl";
    public static final String CLUSTER_ID = "clusterId";
    public static final String STARTED_TIME = "startedTime";
    public static final String FINISHED_TIME = "finishedTime";
    public static final String ELAPSED_TIME = "elapsedTime";
    public static final String AM_CONTAINER_LOGS = "amContainerLogs";
    public static final String AM_HOST_HTTP_ADDRESS = "amHostHttpAddress";
    public static final String ALLOCATED_MB = "allocatedMB";
    public static final String ALLOCATED_VCORES = "allocatedVCores";
    public static final String RUNNING_CONTAINERS = "runningContainers";
    private String id;
    private String user;
    private String name;
    private String applicationType;
    private String queue;
    private String state;
    private String finalStatus;
    private String progress;
    private String trackingUi;
    private String trackingUrl;
    private String clusterId;
    private String startedTime;
    private String finishedTime;
    private String elapsedTime;
    private String amContainerLogs;
    private String amHostHttpAddress;
    private String allocatedMb;
    private String allocatedVcores;
    private String runningContainers;

    public static Monitor fromJSONObject(JSONObject json) {
        Monitor monitor = new Monitor();
        monitor.setId(json.optString(ID));
        monitor.setUser(json.optString(USER));
        monitor.setName(json.optString(NAME));
        monitor.setApplicationType(json.optString(APPLICATION_TYPE));
        monitor.setQueue(json.optString(QUEUE));
        monitor.setState(json.optString(STATE));
        monitor.setFinalStatus(json.optString(FINAL_STATUS));
        monitor.setProgress(json.optString(PROGRESS));
        monitor.setTrackingUi(json.optString(TRACKING_UI));
        monitor.setTrackingUrl(json.optString(TRACKING_URL));
        monitor.setAmContainerLogs(json.optString(AM_CONTAINER_LOGS));
        monitor.setAmHostHttpAddress(json.optString(AM_HOST_HTTP_ADDRESS));
        monitor.setStartedTime(json.optString(STARTED_TIME));
        monitor.setFinishedTime(json.optString(FINISHED_TIME));
        monitor.setElapsedTime(json.optString(ELAPSED_TIME));
        monitor.setClusterId(json.optString(CLUSTER_ID));
        monitor.setAllocatedMb(json.optString(ALLOCATED_MB));
        monitor.setAllocatedVcores(json.optString(ALLOCATED_VCORES));
        monitor.setRunningContainers(json.optString(RUNNING_CONTAINERS));
        return monitor;
    }

    @Override
    public String id() {
        return id;
    }

    public enum State {
        UNACCEPTED, ACCEPTED, RUNNING, FINISHED, FAILED, KILLED
    }

    public enum FinalStatus {
        UNDEFINED, SUCCEEDED, FAILED, KILLED
    }
}
