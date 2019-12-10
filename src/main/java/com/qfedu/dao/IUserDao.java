/**
 * 用户管理dao层
 *
 * @author 刘鹏尧
 * @date
 */

package com.qfedu.dao;

import com.qfedu.pojo.User;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserDao {
    /**
     * 按照用户名查询用户信息
     *
     * @return
     */
    public User getUserByUsername(String userName);


}
