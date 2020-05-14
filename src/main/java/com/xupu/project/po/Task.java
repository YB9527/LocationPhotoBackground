package com.xupu.project.po;

import com.google.gson.annotations.Expose;
import com.xupu.common.tools.DateTool;
import com.xupu.usermanager.po.User;

import javax.persistence.*;
import java.util.Date;

/**
 * 宅基地任务
 */
@Entity
@Table(name = "o_task")
public class Task  {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")//columnDefinition="表注释"
    private Long id;

    /**
     * 任务状态 0：完成，1、进行中,3：异常状态
     */
    @Expose
    private int status;
    /**
     * 任务创建日期
     */
    @Expose
    private String createdate;
    /**
     * 任务完成日期
     */
    @Expose
    private String finishdate;
    /**
     * 任务信息
     */
    @Expose
    private String message;




    //任务分配者
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @Expose
    private User user;

    public Task(){
        this.status =1;
        this.createdate = DateTool.dataFormat(new Date());
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(String finishdate) {
        this.finishdate = finishdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
