package com.xupu.common.po;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "o_redis")
public class Redis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    /**
     * 拥有用户 id
     */
    @Expose
    private Long userId;
    /**
     * key 值
     */
    @Expose
    private String mark;
    @Expose
    private String json;

    public Redis() {

    }
    public Redis(Long userId, String mark, String json) {
        this.userId = userId;
        this.mark = mark;
        this.json = json;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getJson() {

        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
