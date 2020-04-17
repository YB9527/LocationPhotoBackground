package com.xupu.usermanager.po;

import com.google.gson.annotations.Expose;
import com.xupu.zjd.po.ZJD;

import javax.persistence.*;
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
    private String registDate;
    /**
     * 角色级别
     */
    @Expose
    @Enumerated(EnumType.ORDINAL)
    private Level level;

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

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
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
                "account='" + account + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", registDate='" + registDate + '\'' +
                ", level=" + level +
                ", email='" + email + '\'' +
                '}';
    }
}
