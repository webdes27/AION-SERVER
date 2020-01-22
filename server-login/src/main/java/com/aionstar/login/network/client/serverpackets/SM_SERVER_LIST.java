package com.aionstar.login.network.client.serverpackets;

import com.aionstar.login.MainServerManager;
import com.aionstar.login.SpringContext;
import com.aionstar.login.model.MainServerInfo;
import com.aionstar.login.model.entity.Account;
import com.aionstar.login.network.client.ClientChannelAttr;
import com.aionstar.login.utils.ChannelUtil;
import com.aionstar.commons.network.packet.ServerPacket;
import com.aionstar.login.network.client.clientpackets.CM_SERVER_LIST;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Map;

/**
 * 对客户端 ${@link CM_SERVER_LIST}
 * 封包的响应封包，返回登录服务器的各种信息
 * @author saltman155
 * @date 2019/11/16 18:07
 */

public class SM_SERVER_LIST extends ServerPacket {

    private static final byte OPCODE = 0x04;

    private Channel userConnection;

    private Map<Integer,Integer> characterCounts;

    public SM_SERVER_LIST(Channel userConnection, Map<Integer,Integer> characterCounts) {
        super(OPCODE);
        this.userConnection = userConnection;
        this.characterCounts = characterCounts;
    }

    @Override
    protected void appendBody(ByteBuf buffer) {
        Account user = userConnection.attr(ClientChannelAttr.ACCOUNT).get();
        Collection<MainServerInfo> servers =
                SpringContext.getContext().getBean(MainServerManager.class).getGameServers();
        int maxId = 0;
        //写入游戏服务器数量
        buffer.writeByte(servers.size());
        //写入最后一次登录服务器id
        buffer.writeByte(user.getLastServer());
        for(MainServerInfo server : servers){
            maxId = Math.max(server.getId(),maxId);
            //1个字节 写入服务器id
            buffer.writeByte(server.getId());
            //n个字节 ???
            buffer.writeBytes(server.getIpAddressForPlayerIp(ChannelUtil.getIp(userConnection)));
            //8个字节 写入服务器port
            buffer.writeIntLE(server.getClientPort());
            //1个字节 age limit
            buffer.writeByte(0x00);
            //1个字节 pvp=1
            buffer.writeByte(0x01);
            //2个字节 当前在线人数
            buffer.writeShortLE(server.getCurrentPlayers());
            //2个字节 最大在线人数
            buffer.writeShortLE(server.getMaxPlayers());
            //1个字节 是否在线
            buffer.writeByte(server.isOnline() ? 0x01 : 0x00);
            buffer.writeIntLE(0x00000000);
            //server.brackets ? 0x01 : 0x00
            buffer.writeByte(0x00);
        }
        buffer.writeShortLE(maxId + 1);
        buffer.writeByte(0x01);
        //写入每个服务器的当前账号注册角色数量
        for(int i = 1;i< maxId;i++){
            Integer count = characterCounts.get(i);
            buffer.writeByte(count == null ? 0 : count);
        }
    }
}
