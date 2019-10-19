package com.superywd.aion.login.network;

import com.superywd.aion.login.network.handler.client.ClientChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 网络服务启动类
 * @author: saltman155
 * @date: 2019/3/20 18:00
 */
@Component
@PropertySource(value = {"file:./config/network/network.properties"})
public class ClientNetConnector {

    private static final Logger logger = LoggerFactory.getLogger(ClientNetConnector.class);

    @Value("${loginServer.port}")
    private Integer loginServerPort;
    @Value("${bossThreadCount}")
    private Integer bossThreadCount;
    @Value("${workThreadCount}")
    private Integer workThreadCount;

    @Resource
    private ClientChannelInitializer channelInitializer;

    public void start() throws InterruptedException {
        Executor workExecutor = Executors.newFixedThreadPool(workThreadCount);
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(bossThreadCount, workExecutor);
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(loginServerPort))
                    .childHandler(channelInitializer);
            ChannelFuture f = bootstrap.bind().sync();
            f.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}