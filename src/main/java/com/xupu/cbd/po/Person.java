package com.xupu.cbd.po;

public class Person {
    private long id;
    private String name;
    private int age;
    private String hobby;

    @Override
    public String toString() {
        return "name:" + name + ";age=" + age + ";hobby:" + hobby;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
