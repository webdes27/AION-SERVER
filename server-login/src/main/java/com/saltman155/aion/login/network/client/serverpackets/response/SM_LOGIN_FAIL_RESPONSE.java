package com.saltman155.aion.login.network.client.serverpackets.response;

import com.saltman155.aion.login.network.client.LoginAuthResponse;
import com.saltman155.aion.login.network.client.ServerPacket;

import java.nio.ByteBuffer;

/**
 * 这个数据封包用来表示服务端向客户端响应各种错误的状态。
 * @author saltman155
 * @date 2019/10/20 18:30
 */

public class SM_LOGIN_FAIL_RESPONSE extends ServerPacket {

    private static final byte OPCODE = 0x01;

    private LoginAuthResponse response;

    public SM_LOGIN_FAIL_RESPONSE(LoginAuthResponse response) {
        super(OPCODE);
        this.response = response;
    }

    @Override
    protected void appendBody(ByteBuffer buf) {
        buf.putInt(response.getId());
    }

}