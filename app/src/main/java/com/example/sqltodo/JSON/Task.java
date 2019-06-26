package com.example.sqltodo.JSON;

import java.util.Date;

public class Task {

    String name;
    String desc;
    String status,startDate, endDate;
    Integer prefTime, priority, taskid, estDuration, actDuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPrefTime() {
        return prefTime;
    }

    public void setPrefTime(Integer prefTime) {
        this.prefTime = prefTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getEstDuration() {
        return estDuration;
    }

    public void setEstDuration(Integer estDuration) {
        this.estDuration = estDuration;
    }

    public Integer getActDuration() {
        return actDuration;
    }

    public void setActDuration(Integer actDuration) {
        this.actDuration = actDuration;
    }
}
