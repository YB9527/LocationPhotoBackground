package com.xupu.usermanager.po;

import com.google.gson.annotations.Expose;
import com.xupu.zjd.po.ZJD;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 账号
     */
    @Expose
    private String account;
    /**
     * 昵称
     */
    @Expose
    private String nickName;
    /**
     * 密码
     */
    @Expose
    private String password;
    /**
     * 注册日期
     */
    @Expose
    private Date registDate;
    /**
     * 角色级别
     */
    @Expose
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=true)//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    private UserLevel userLevel;
    /**
     * 用户邮箱，用于找回密码
     */
    @Expose
    private String email;


    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<ZJD> zjds;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public List<ZJD> getZjds() {
        return zjds;
    }

    public void setZjds(List<ZJD> zjds) {
        this.zjds = zjds;
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", registDate=" + registDate +
                ", userLevel=" + userLevel +
                ", email='" + email + '\'' +
                ", zjds=" + zjds +
                '}';
    }
}
