package dev.orf1.cobalt.server;

import io.netty.channel.ChannelHandlerContext;

public class User {
    private ChannelHandlerContext handler;
    private String email;
    private String password;
    private String username;
    private int id;
    private String token;

    public ChannelHandlerContext getHandler() {
        return handler;
    }

    public void setHandler(ChannelHandlerContext handler) {
        this.handler = handler;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
