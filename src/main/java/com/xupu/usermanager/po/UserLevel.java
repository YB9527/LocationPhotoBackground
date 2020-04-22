package com.xupu.usermanager.po;

import com.google.gson.annotations.Expose;
import com.xupu.xzqy.po.XZDM;

import javax.persistence.*;
import java.util.List;

/**
 * 用户级别，包含了，管理的行政区域
 */
@Entity
public class UserLevel {
    /**
     * id
     */
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户级别 数字
     */
    private Integer levelNum;
    /**
     * 用户级别
     */
    @Expose
    @Enumerated(EnumType.ORDINAL)
    private Level level;
    /**
     * 管理的行政区域
     */
    @Expose
    @ElementCollection
    private List<String> djzqdms;

    @OneToMany(mappedBy = "userLevel",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private  List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevelNum() {
        return this.getLevel().getLevelNum();
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }


    public List<String> getDjzqdms() {
        return djzqdms;
    }

    public void setDjzqdms(List<String> djzqdms) {
        this.djzqdms = djzqdms;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
