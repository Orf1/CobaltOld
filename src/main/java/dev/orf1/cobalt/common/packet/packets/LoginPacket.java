package dev.orf1.cobalt.common.packet.packets;

import dev.orf1.cobalt.common.packet.Packet;

public class LoginPacket extends Packet {
    private String type = "login";
    private String email;
    private String password;

    public LoginPacket(String email, String password) {
        this.email = email;
        this.password = password;
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
}
