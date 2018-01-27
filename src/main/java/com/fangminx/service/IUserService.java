package com.fangminx.service;

import com.fangminx.entity.User;

/**
 * 用户服务
 */
public interface IUserService {
    User findUserByName(String userName);
}
