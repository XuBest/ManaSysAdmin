package com.spring.entity;

import net.jntoo.db.Query;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Table(name = "events")
public class Events implements Serializable {
    @GeneratedValue(generator = "JDBC") // 自增的主键映射
    @Id
    @Column(name = "id",insertable=false)
    private Integer id;
    @Column(name = "eventName")
    private String eventName;
    @Column(name = "eventContent")
    private String eventContent;
    @Column(name = "maxJoin")
    private Integer maxJoin;
    @Column(name = "publishTime")
    private String publishTime;
    @Column(name = "endTime")
    private String endTime;
    @Column(name = "eventStatus")
    private String eventStatus;
    @Column(name = "counter")
    private Integer counter;
    @Column(name = "success")
    private Integer success;
    @Column(name = "reginum")
    private Integer reginum;
    @Column(name = "picture")
    private String picture;

    private static final long serialVersionUID = 1L;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Long getRegistrationinfoCount()
    {
        return Query.make("registrationinfo").where("eventid" , id).count();
    }//键值对应的工作吧

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName == null ? "" : eventName.trim(); }

    public String getEventContent() { return eventContent; }
    public void setEventContent(String eventContent) { this.eventContent = eventContent ; }

    public Integer getMaxJoin() { return maxJoin; }
    public void setMaxJoin(Integer maxJoin) { this.maxJoin = maxJoin == null ? 0 : maxJoin; }

    public String getPublishTime() { return publishTime; }
    public void setPublishTime(String publishTime) { this.publishTime = publishTime == null ? "" : publishTime.trim(); }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime == null ? "" : endTime.trim(); }

    public String getEventStatus() { return eventStatus; }
    public void setEventStatus(String eventStatus) { this.eventStatus = eventStatus == null ? "" : eventStatus.trim(); }

    public Integer getCounter() { return counter;}
    public void setCounter(Integer counter) { this.counter = counter == null? 0 : counter; }

    public Integer getSuccess() { return success; }
    public void setSuccess(Integer reginum) { this.success = success == null? 0 : reginum; }

    public Integer getReginum() { return reginum; }
    public void setReginum(Integer reginum) { this.reginum = reginum == null? 0 : reginum; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
