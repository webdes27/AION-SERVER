package com.aionstar.login.network.client.serverpackets;

import com.aionstar.login.network.client.clientpackets.CM_AUTH_GG;
import com.aionstar.commons.network.packet.ServerPacket;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * 该数据包，是对客户端对服务端的密钥验证的响应包 {@link CM_AUTH_GG }的响应，
 * 这个封包发出后，表示客户端与登录服务器的互相校验已经完成
 * @author saltman155
 * @date 2019/10/20 18:16
 */

public class SM_AUTH_GG extends ServerPacket {

    private static final byte OPCODE = 0x0b;

    private final int sessionId;

    public SM_AUTH_GG(int sessionId) {
        super(OPCODE);
        this.sessionId = sessionId;
    }

    @Override
    protected void appendBody(ByteBuf buffer) {
        buffer.writeIntLE(sessionId);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeIntLE(0x00000000);
        buffer.writeByte(0x13);
    }
}
