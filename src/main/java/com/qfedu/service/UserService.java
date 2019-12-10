/**
 * 用户信息service层
 *
 * @author 刘鹏尧
 * @date
 */
package com.qfedu.service;

import com.qfedu.dao.IUserDao;
import com.qfedu.pojo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private IUserDao userDao;


    /**
     * 登录
     *
     * @param user
     * @return
     */
     public User login(String userName){
         logger.info("login===========================================");
         return userDao.getUserByUsername(userName);
    }

}
