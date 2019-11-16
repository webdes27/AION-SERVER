package com.saltman155.aion.login.dao;

import com.saltman155.aion.login.model.GameServerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author saltman155
 * @date 2019/11/16 16:28
 */

@Mapper
public interface GameServerMapper {

    /**
     * 获取所有的游戏服务器信息
     * @return 游戏服务器信息
     */
    List<GameServerInfo> getAllGameServer();

}
