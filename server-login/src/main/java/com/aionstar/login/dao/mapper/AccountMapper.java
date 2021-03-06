package com.aionstar.login.dao.mapper;

import com.aionstar.login.model.entity.Account;
import org.apache.ibatis.annotations.Param;

/**
 * @author: saltman155
 * @date: 2019/3/16 00:08
 */

public interface AccountMapper {

    /**
     * 根据用户id获取用户
     * @param id        用户账户id
     * @return          用户
     */
    Account selectById(@Param("id") Integer id);

    /**
     * 根据用户名称获取用户
     * @param account   用户账户
     * @return          用户
     */
    Account selectByAccount(@Param("account")String account);

    /**
     * 更新一个用户的最后登录服务器
     * @param id            账户id
     * @param lastServer    最后登录服务器
     */
    void updateLastServer(@Param("id")Integer id, @Param("lastServer")Byte lastServer);


}
