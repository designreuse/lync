package com.lync.domain.primary;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//jpa默认命名规范是属性名全小写。
@Entity
@Table(name = "t_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty(message = "用户名不能为空")
  private String username;
//  @NotEmpty(message = "密码不能为空")
  private String password;
  private String salt; //密码盐
  private int statu; //0 删除 1 正常
  private Date createTime;
  private Date modifyTime;
  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
          @JoinColumn(name = "role_id") })
  @JSONField(serialize = false)
  private List<Role> roleList;// 一个用户具有多个角色

  public User() {
    super();
  }

  public User(String username, String password) {
    super();
    this.username = username;
    this.password = password;
  }

  @Transient //@Transient标记出某个字段,这个字段就不会参与对数据库表的操作,是一个辅助字段
  public Set<String> getRolesName() {
    List<Role> roles = getRoleList();
    Set<String> set = new HashSet<String>();
    for (Role role : roles) {
      set.add(role.getRolename());
    }
    return set;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public int getStatu() {
    return statu;
  }

  public void setStatu(int statu) {
    this.statu = statu;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }
}
