package com.xupu.admin.service;


public interface IAdminService {
    /**
     * 如果后台密码为null，，那么后台密码是 字符串 "123"
     *
     * @return
     */
    String findAdminPassword();
}
