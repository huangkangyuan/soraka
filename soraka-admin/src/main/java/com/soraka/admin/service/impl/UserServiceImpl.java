package com.soraka.admin.service.impl;

import com.soraka.admin.dao.UserDAO;
import com.soraka.admin.model.domain.UserDO;
import com.soraka.admin.model.dto.Page;
import com.soraka.admin.model.dto.QueryParam;
import com.soraka.admin.service.RoleService;
import com.soraka.admin.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yongjie.teng
 * @date 2018/8/16
 * @package com.soraka.admin.service.impl
 */
@Transactional(rollbackFor = {RuntimeException.class})
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoleService roleService;

    /**
     * 根据主键获取用户信息
     *
     * @param id 主键
     * @return {@link UserDO}
     */
    @Override
    public UserDO get(Long id) {
        UserDO userDO = userDAO.get(id);
        if (userDO != null) {
            userDO.setRoles(roleService.findByUserId(userDO.getId()));
        }
        return userDO;
    }

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return {@link UserDO}
     */
    @Override
    public UserDO getByUsername(String username) {
        UserDO userDO = userDAO.getByUsername(username);
        if (userDO != null) {
            userDO.setRoles(roleService.findByUserId(userDO.getId()));
        }
        return userDO;
    }

    /**
     * 根据手机号获取用户
     *
     * @param mobilephone 手机号
     * @return {@link UserDO}
     */
    @Override
    public UserDO getByMobilephone(String mobilephone) {
        UserDO userDO = userDAO.getByMobilephone(mobilephone);
        if (userDO != null) {
            userDO.setRoles(roleService.findByUserId(userDO.getId()));
        }
        return userDO;
    }

    /**
     * 根据邮箱获取用户
     *
     * @param email 邮箱
     * @return {@link UserDO}
     */
    @Override
    public UserDO getByEmail(String email) {
        UserDO userDO = userDAO.getByEmail(email);
        if (userDO != null) {
            userDO.setRoles(roleService.findByUserId(userDO.getId()));
        }
        return userDO;
    }

    /**
     * 查询用户列表页
     *
     * @param queryParam 查询参数
     * @return {@link Page}
     */
    @Override
    public Page findPage(@NotNull QueryParam queryParam) {
        Page page = new Page();
        List<UserDO> users;
        int total = userDAO.count(queryParam);
        page.setTotal(total);
        if (total > 0) {
            users = userDAO.find(queryParam);
            page.setRows(users);
        } else {
            page.setRows(new ArrayList<>());
        }
        return page;
    }

    /**
     * 生成随机盐值
     *
     * @return 盐值
     */
    @Override
    public String randomSalt() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    /**
     * 生成用户密码加密串
     *
     * @param username 用户名
     * @param password 密码
     * @param salt 盐值
     * @return 加密密码串
     */
    @Override
    public String encryptPassword(String username, String password, String salt) {
        return DigestUtils.md5Hex(username + password + salt);
    }
}
