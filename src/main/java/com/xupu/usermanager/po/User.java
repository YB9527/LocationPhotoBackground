package com.xupu.usermanager.po;

import com.google.gson.annotations.Expose;
import com.xupu.project.po.Project;
import com.xupu.xzqy.po.XZDM;
import com.xupu.zjd.po.ZJD;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "userTab")
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
     * 用户级别
     */
    @Expose
    @Enumerated(EnumType.ORDINAL)
    private Level level;
    /**
     * 管理的行政区域
     */
   /* @Expose
    @ElementCollection
    private List<String> djzqdms;*/
    /**
     * 用户邮箱，用于找回密码
     */
    @Expose
    private String email;


    /**
     * 一个人管理的行政区域
     */
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Expose
    private List<XZDM> xzdms;


    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<ZJD> zjds;
    /**
     * 一个人可以做多个项目
      */
    @ManyToMany(mappedBy = "users")
    private List<Project> projects;



    public  User(){

    }

    public User(String account, String nickName, String password, String email) {
        this.account = account;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    public List<Project> getProjects() {
        if(projects == null){
            projects = new ArrayList<>();
        }
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }




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

    public List<ZJD> getZjds() {
        return zjds;
    }

    public void setZjds(List<ZJD> zjds) {
        this.zjds = zjds;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", registDate=" + registDate +
                ", level=" + level +
                ", email='" + email + '\'' +
                ", zjds=" + zjds +
                '}';
    }
}
