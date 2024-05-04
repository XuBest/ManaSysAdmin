package com.spring.entity;

import net.jntoo.db.Query;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "ticket")
public class Ticket implements Serializable {
    @GeneratedValue(generator = "JDBC") // 自增的主键映射
    @Id
    @Column(name = "id",insertable=false)
    private Integer id;
    @Column(name = "ticketkey")
    private String ticketkey;
    @Column(name = "ticketname")
    private String ticketname;
    @Column(name = "picture")
    private String picture;
    @Column(name = "price")
    private Double price;
    @Column(name = "counter")
    private Integer counter;
    @Column(name = "special")
    private String special;
    @Column(name = "overview")
    private String overview;
    @Column(name = "addtime")
    private String addtime;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTicketinfoCount()
    {
        return Query.make("ticketinfo").where("ticketid" , id).count();
    }//键值对应的工作吧

    public String getTicketkey() { return ticketkey; }
    public void setTicketkey(String ticketkey) {
        this.ticketkey = ticketkey == null ? "" : ticketkey.trim();
    }

    public String getTicketname() { return ticketname; }
    public void setTicketname(String ticketname) {
        this.ticketname = ticketname== null ? "" : ticketname.trim();
    }

    public String getPicture() { return picture; }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) {
        this.price = price == null ? 0.0f : price;
    }

    public Integer getCounter() { return counter; }
    public void setCounter(Integer counter) {
        this.counter = counter== null ? 0 : counter;
    }

    public String getSpecial() { return special; }
    public void setSpecial(String special) {
        this.special = special;
    }

    public String getOverview() { return overview; }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getAddtime() { return addtime; }
    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? "" : addtime.trim();
    }
}
