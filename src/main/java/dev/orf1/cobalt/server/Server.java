package dev.orf1.cobalt.server;

import com.google.gson.Gson;
import dev.orf1.cobalt.common.packet.Packet;
import dev.orf1.cobalt.common.packet.packets.LoginPacket;
import dev.orf1.cobalt.common.packet.packets.RegisterPacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    public static Server INSTANCE;
    private final UserManager userManager;


    public Server() {
        INSTANCE = this;
        userManager = new UserManager();
    }

    public void onRead(ChannelHandlerContext handler, String data) {
        System.out.println("Server received - " + data);
        String type = Packet.getType(data);

        if (type.equals("login")) {
            try {
                System.out.println("Attempting to login user: " + handler);
                if (userManager.login(new Gson().fromJson(data, LoginPacket.class), handler)) {
                    System.out.println("User logged in! " + handler);
                } else {
                    System.out.println("Failed to login user: " + handler);
                }
            } catch (Exception exception) {
                System.out.println("Failed to login user: " + handler);
            }
        } else if (type.equals("register")) {
            try {
                System.out.println("Attempting to register user: " + handler);
                if (userManager.register(new Gson().fromJson(data, RegisterPacket.class), handler)) {
                    System.out.println("User registered! " + handler);
                } else {
                    System.out.println("Failed to register user: " + handler);
                }
            } catch (Exception exception) {
                System.out.println("Failed to register user: " + handler);
            }
        } else {
            System.out.println("Invalid packet received from: " + handler);
        }
    }

    public void onConnect(ChannelHandlerContext handler) {
        System.out.println("User connected: " + handler);
    }

    public void onDisconnect(ChannelHandlerContext handler) {
        System.out.println("Channel closed: " + handler);
        handler.close();
    }

    public void onException(ChannelHandlerContext handler, Throwable cause) {
        System.out.println("User disconnected due to an exception: " + handler);
        cause.printStackTrace();
        handler.close();
    }

    public void start() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(new ServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(9127).sync();
            System.out.println("Cobalt server started.");
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
