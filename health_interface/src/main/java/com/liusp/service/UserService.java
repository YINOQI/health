package com.liusp.service;

import com.liusp.pojo.User;
/**
 * 用户服务接口
 */
public interface UserService {
    public User findByUsername(String username);
}