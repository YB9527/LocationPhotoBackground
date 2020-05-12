package com.xupu.project.po;

import com.google.gson.annotations.Expose;
import com.xupu.usermanager.po.User;
import com.xupu.xzqy.po.XZDM;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    @Expose
    @Column(nullable = false)
    private String name;
    @Expose
    @Column(nullable = false)
    private String projectType;
    @Expose
    private String notes;

    /**
     * 顶级行政代码， 导入的数据只能包含这个代码，
     */
    @Expose
    private String djzqdm;

    //@Expose
    //@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<XZDM> xzdms;
    /**
     * 一个人可以做多个项目
     */
    @ManyToMany
    @JoinTable(name = "project_user",joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Expose
    private  List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<XZDM> getXzdms() {
        if(xzdms == null){
            xzdms = new ArrayList<>();
        }
        return xzdms;
    }

    public void setXzdms(List<XZDM> xzdms) {
        this.xzdms = xzdms;
    }


    public String getDjzqdm() {
        return djzqdm;
    }

    public void setDjzqdm(String djzqdm) {
        this.djzqdm = djzqdm;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
