package com.xupu.common.po;

import com.google.gson.annotations.Expose;
import com.xupu.zjd.po.ZJD;

import javax.persistence.*;

/**
 * 任务
 */

@Entity
@Table(name = "o_task")
public class Task  {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "objectid")//columnDefinition="表注释"
    private Long id;

    /**
     * 任务状态 0：完成，1、未开始， 2：进行中，3：异常状态
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
    /**
     * 绑定的宅基地
     */
    @OneToOne(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    private ZJD zjd;


    public Task(){
        this.status =1;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ZJD getZjd() {
        return zjd;
    }

    public void setZjd(ZJD zjd) {
        this.zjd = zjd;
    }

    /**
     * 任务阶段
     */
   // private List<String> step;
    //private Map<String,Integer> step2;
    //private

   /* public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }*/

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
