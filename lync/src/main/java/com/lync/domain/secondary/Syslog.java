package com.lync.domain.secondary;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by breeze on 2017/2/22.
 */
@Entity
@Table(name = "syslog")
public class Syslog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
