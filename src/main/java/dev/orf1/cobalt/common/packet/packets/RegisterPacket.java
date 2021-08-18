package dev.orf1.cobalt.common.packet.packets;

import dev.orf1.cobalt.common.packet.Packet;

public class RegisterPacket extends Packet {
    private String type = "register";
    private String email;
    private String password;
    private String username;

    public RegisterPacket(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
