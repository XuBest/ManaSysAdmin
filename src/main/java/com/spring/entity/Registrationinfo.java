package com.spring.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "registrationinfo")
public class Registrationinfo implements Serializable {
    @GeneratedValue(generator = "JDBC") // 自增的主键映射
    @Id
    @Column(name = "id",insertable=false)
    private Integer id;
    @Column(name = "eventid")
    private Integer eventid;

    @Column(name = "eventName")
    private String eventName;

    @Column(name = "endTime")
    private String endTime;
    @Column(name = "eventStatus")
    private String eventStatus;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "regiStatus")
    private String regiStatus;
    @Column(name = "notes")
    private String notes;
    @Column(name = "reserver")
    private String reserver;

    @Column(name = "regiTime")
    private String regiTime;
    @Column(name = "startTime")
    private String startTime;
    @Column(name = "adder")
    private String adder;

    private static final long serialVersionUID = 1L;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getEventid() { return eventid; }
    public void setEventid(Integer eventid) { this.eventid = eventid == null ? 0 : eventid; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName == null ? "" : eventName.trim(); }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime == null ? "" : endTime.trim(); }

    public String getEventStatus() { return eventStatus; }
    public void setEventStatus(String eventStatus) { this.eventStatus = eventStatus == null ? "正在报名中" : eventStatus.trim(); }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone == null ? "" : telephone.trim(); }

    public String getRegiStatus() { return regiStatus; }
    public void setRegiStatus(String regiStatus) { this.regiStatus = regiStatus == null ? "待审核" : regiStatus.trim(); }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes ; }

    public String getReserver() { return reserver; }
    public void setReserver(String reserver) { this.reserver = reserver == null ? "" : reserver.trim(); }

    public String getRegiTime() { return regiTime; }
    public void setRegiTime(String regiTime) { this.regiTime = regiTime == null ? "" :regiTime.trim(); }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getAdder() { return adder; }
    public void setAdder(String adder) { this.adder = adder == null ? "" : adder.trim(); }
}
