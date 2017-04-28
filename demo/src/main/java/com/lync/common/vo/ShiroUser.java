package com.lync.common.vo;

import com.lync.domain.primary.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by breeze on 2017/2/25.
 */
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -6350204712121429104L;
    private Object id;
    private String username;
    private List<Role> roleList;
    public ShiroUser(Object id,String username,List<Role> roleList){
        this.id = id;
        this.username=username;
//        ArrayList arrayList = new ArrayList();
//        for(Role r:roleList){
//            arrayList.add(r.getId());
//        }
        this.roleList=roleList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
}
