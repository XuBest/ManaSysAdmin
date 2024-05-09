package com.spring.entity;

import net.jntoo.db.Query;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "ticketinfo")
public class Ticketinfo implements Serializable {

    @GeneratedValue(generator = "JDBC") // 自增的主键映射
    @Id
    @Column(name = "id",insertable=false)
    private Integer id;

    @Column(name = "ticketid")
    private Integer ticketid;
    @Column(name = "ticketkey")
    private String ticketkey;
    @Column(name = "ticketname")
    private String ticketname;
    @Column(name = "price")
    private Double price;
    @Column(name = "ordernum")
    private String ordernum;
    @Column(name = "ordertime")
    private String ordertime;
    @Column(name = "ordername")
    private String ordername;
    @Column(name = "phonenum")
    private String phonenum;
    @Column(name = "ticketstatus")
    private String ticketstatus;
    @Column(name = "notice")
    private String notice;
    @Column(name = "adder")
    private String adder;
    @Column(name = "addtime")
    private String addtime;
    private String ifpay;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketid() { return ticketid; }
    public void setTicketid(Integer ticketid) {
        this.ticketid = ticketid == null ? 0 : ticketid;
    }
    public String getTicketkey() { return ticketkey; }
    public void setTicketkey(String ticketkey) {
        this.ticketkey = ticketkey == null ? "" : ticketkey.trim();
    }

    public String getTicketname() { return ticketname; }
    public void setTicketname(String ticketname) {
        this.ticketname = ticketname== null ? "" : ticketname.trim();
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) {
        this.price = price == null ? 0.0f : price;
    }


    public String getOrdernum() { return ordernum; }
    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum == null ? "" : ordernum.trim();
    }

    public String getOrdertime() { return ordertime; }
    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime == null ? "" : ordertime.trim();
    }

    public String getOrdername() { return ordername; }
    public void setOrdername(String ordername) {
        this.ordername = ordername == null ? "" : ordername.trim();
    }

    public String getPhonenum() { return phonenum; }
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum == null ? "" : phonenum.trim();
    }

    public String getTicketstatus() { return ticketstatus; }
    public void setTicketstatus(String ticketstatus) {
        this.ticketstatus = ticketstatus == null ? "" : ticketstatus.trim();
    }

    public String getNotice() { return notice; }
    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getAdder() { return adder; }
    public void setAdder(String adder) {
        this.adder = adder == null ? "" : adder.trim();
    }

    public String getIfpay() { return ifpay; }
    public void setIfpay(String ifpay) {
        this.ifpay = ifpay == null ? "" : ifpay.trim();
    }

    public String getAddtime() { return addtime; }
    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }

}
