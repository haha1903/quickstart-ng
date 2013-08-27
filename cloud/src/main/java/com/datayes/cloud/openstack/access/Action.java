package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 下午5:11
 * DataYes
 */
public class Action {
    private String action;
    private String message;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty("instance_uuid")
    private String instanceUuid;
    @JsonProperty("start_time")
    private Date startTime;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInstanceUuid() {
        return instanceUuid;
    }

    public void setInstanceUuid(String instanceUuid) {
        this.instanceUuid = instanceUuid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Action{" +
                "action='" + action + '\'' +
                ", message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                ", userId='" + userId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", instanceUuid='" + instanceUuid + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
