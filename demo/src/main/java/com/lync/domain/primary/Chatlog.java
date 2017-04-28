package com.lync.domain.primary;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by breeze on 2017/2/22.
 */
@Entity
@Table(name = "t_chatlog")
public class Chatlog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String confId; //会话id
    @Column
    private Date time; //会话时间
    @Column
    private String from; //发送人

    @Column
    private String to;  //消息接收者(包括发消息者)
    @Column
    private String data; //消息
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfId() {
        return confId;
    }

    public void setConfId(String confId) {
        this.confId = confId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
//
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
