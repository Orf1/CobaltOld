package dev.orf1.cobalt.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Server server = Server.INSTANCE;


    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        server.onConnect(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        server.onRead(ctx, msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        server.onException(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        server.onDisconnect(ctx);
    }
}
