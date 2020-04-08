package com.xupu.cbd.po;

import com.xupu.common.FileTool;

import javax.persistence.*;
import java.util.List;

@Entity
public class AAAA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String name1;
    private String name2;



    @OneToMany(mappedBy = "aaaa",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    //级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
    //拥有mappedBy注解的实体类为关系被维护端
    //mappedBy="author"中的author是Article中的author属性
    private List<User> userIList;
    public Long getId() {
        return id;
    }

    public List<User> getUserIList() {
        return userIList;
    }

    public void setUserIList(List<User> userIList) {
        this.userIList = userIList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}