package com.xupu.xzqy.po;

import com.google.gson.annotations.Expose;
import com.xupu.project.po.Project;
import com.xupu.zjd.po.ZJD;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 行政区域
 */
@Entity
public class XZDM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    @Expose
    private String DJZQDM;
    @Expose
    private String DJZQMC;
    /**
     * 不参与数据的映射
     */
   /* @Expose
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name="zjd_id")//设置在article表中的关联字段(外键)
    */
    @Expose
    //@OneToMany(mappedBy = "xzdm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<ZJD> zjds;
    //@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=true)
   //private User user;
    @Expose
    private Long projectid;

    public Long getProjectId() {
        return   this.projectid =project.getId();
    }

    public void setProjectId(Long projectid) {
        if(this.project != null){
            this.projectid =project.getId();
        }else{
            this.projectid = null;
        }
    }

    //@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @Transient
    private Project project;


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public XZDM(){

    }

    @Override
    public String toString() {
        return "XZDM{" +
                "id=" + id +
                ", DJZQDM='" + DJZQDM + '\'' +
                ", DJZQMC='" + DJZQMC + '\'' +
                '}';
    }

    public List<ZJD> getZjds() {
        if(zjds == null){
            zjds = new ArrayList<>();
        }
        return zjds;
    }

    public void setZjds(List<ZJD> zjds) {
        this.zjds = zjds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDJZQDM() {
        return DJZQDM;
    }

    public void setDJZQDM(String DJZQDM) {
        this.DJZQDM = DJZQDM;
    }

    public String getDJZQMC() {
        return DJZQMC;
    }

    public void setDJZQMC(String DJZQMC) {
        this.DJZQMC = DJZQMC;
    }
}