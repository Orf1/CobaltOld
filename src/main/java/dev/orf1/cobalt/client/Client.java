package dev.orf1.cobalt.client;

import dev.orf1.cobalt.common.packet.packets.LoginPacket;
import dev.orf1.cobalt.common.packet.packets.RegisterPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class Client {

    private EventLoopGroup group;
    private Channel channel;
    Bootstrap bootstrap;
    ChannelFuture channelFuture;

    public void start() throws Exception {
        try{
            System.out.println("Starting Netty initialization.");
            initialize();
        } catch (Exception exception) {
            System.out.println("Netty initialization failed.");
            return;
        }

        try{
            System.out.println("Attempting to connect to server.");
            connect("127.0.0.1", 9127);
        } catch (Exception exception) {
            System.out.println("Server offline, please try again later.");
            return;
        }

        try{
            System.out.println("Starting authentication stage.");
            authenticate();
        } catch (Exception exception) {
            System.out.println("Authentication stage failed.");
            return;
        }

        try{
            System.out.println("Starting main stage.");
            main();
        } catch (Exception exception) {
            System.out.println("Main stage failed.");
            return;
        }
        disconnect();
        System.out.println("Disconnected from server.");
    }

    public void authenticate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select an option:\n1 - Login\n2 - Register\n");
        String selection = scanner.nextLine();

        switch (selection) {
            case "1": {

                System.out.println("Please enter your email: ");
                String email = scanner.nextLine();
                System.out.println("Please enter your password: ");
                String password = scanner.nextLine();

                LoginPacket packet = new LoginPacket(email, password);
                send(packet.toJson());

                break;
            }
            case "2": {

                System.out.println("Please enter your email: ");
                String email = scanner.nextLine();
                System.out.println("Please enter your password: ");
                String password = scanner.nextLine();
                System.out.println("Please enter your username: ");
                String username = scanner.nextLine();
                RegisterPacket packet = new RegisterPacket(email, password, username);
                send(packet.toJson());

                break;
            }
            case "3":

                System.out.println("Developer mode activated!");

                break;

        }
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            send(input);
        }
    }

    public void initialize() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        p.addLast(new ClientHandler());
                    }
                });
    }

    public void connect(String host, int port) throws Exception{
        channelFuture= bootstrap.connect(host, port).sync();
        setChannel(channelFuture.sync().channel());
    }

    public void disconnect() throws Exception{
        getChannel().closeFuture().sync();
        group.shutdownGracefully();
    }

    public void send(String data) {
        getChannel().writeAndFlush(data);
        System.out.println("Sent: " + data);
    }

    public EventLoopGroup getGroup() {
        return group;
    }

    public void setGroup(EventLoopGroup group) {
        this.group = group;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
