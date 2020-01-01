package com.saltman155.aion.login.network.client.serverpackets.response;

import com.saltman155.aion.login.network.client.clientpackets.response.CM_AUTH_GG_RESPONSE;
import com.saltman155.aion.login.network.client.ServerPacket;

import java.nio.ByteBuffer;

/**
 * 该数据包，是对客户端对服务端的密钥验证的响应包 {@link CM_AUTH_GG_RESPONSE }的响应，
 * 这个封包发出后，表示客户端与登录服务器的互相校验已经完成
 * @author saltman155
 * @date 2019/10/20 18:16
 */

public class SM_AUTH_GG_RESPONSE extends ServerPacket {

    private static final byte OPCODE = 0x0b;

    private final int sessionId;

    public SM_AUTH_GG_RESPONSE(int sessionId) {
        super(OPCODE);
        this.sessionId = sessionId;
    }

    @Override
    protected void appendBody(ByteBuffer buf) {
        buf.putInt(sessionId);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.putInt(0x00000000);
        buf.put((byte)0x13);
    }
}