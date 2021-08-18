package dev.orf1.cobalt.server;

import dev.orf1.cobalt.common.packet.packets.LoginPacket;
import dev.orf1.cobalt.common.packet.packets.RegisterPacket;
import io.netty.channel.ChannelHandlerContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserManager {

    private final List<User> USERS = new ArrayList<>();

    public boolean login(LoginPacket loginPacket, ChannelHandlerContext handler) {
        if (!isValidLogin(loginPacket.getEmail(), loginPacket.getPassword())) {
            return false;
        }
        for(User user : USERS) {
            if (user.getEmail().equals(loginPacket.getEmail()) && user.getPassword().equals(loginPacket.getPassword())) {
                user.setHandler(handler);
                user.setToken(generateToken());
                return true;
            }
        }
        return false;
    }

    public boolean register(RegisterPacket registerPacket, ChannelHandlerContext handler) {
        if (!isValidRegister(registerPacket.getEmail(), registerPacket.getPassword(), registerPacket.getUsername())) {
            return false;
        }
        User user = new User();
        user.setEmail(registerPacket.getEmail());
        user.setPassword(registerPacket.getPassword());
        user.setUsername(registerPacket.getUsername());
        user.setHandler(handler);
        user.setToken(generateToken());
        user.setId(USERS.size() + 1);
        USERS.add(user);

        System.out.println("Userlist: ");
        for (User usr : USERS) {
            System.out.println(usr.getEmail());
            System.out.println(usr.getPassword());
            System.out.println(usr.getUsername());
            System.out.println(usr.getHandler());
            System.out.println(usr.getId());
            System.out.println(usr.getToken());
        }

        return true;
    }

    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private boolean isValidLogin(String email, String password) {
        System.out.println("Checking to see if login request is valid.");
        if (!isValidEmail(email) || !isValidPassword(password)) {
            System.out.println("Email or Password is not valid.");
            return false;
        }
        for(User user : USERS) {
            System.out.println("Checking to see if request matches this user.");
            if (user.getEmail().equalsIgnoreCase(email)) {
                if(user.getPassword().equals(password)) {
                    System.out.println("The login is valid.");
                    return true;
                } else {
                    System.out.println("Password did not match: " + user.getPassword());
                }
            } else {
                System.out.println("Email did not match: " + user.getEmail());
            }
        }
        System.out.println("The login is not valid.");
        return false;
    }

    private boolean isValidRegister(String email, String password, String username) {
        System.out.println("Checking to see if register request is valid.");
        if (!isValidEmail(email) || !isValidPassword(password) || !isValidUsername(username)) {
            System.out.println("Email, Password, or Username is invalid.");
            return false;
        }

        for(User user : USERS) {
            if (user.getEmail().equalsIgnoreCase(email) || user.getUsername().equals(username)) {
                System.out.println("Email, Password, or Username is taken.");
                return false;
            }
        }
        System.out.println("Is register is valid.");
        return true;
    }

    private boolean isValidEmail(String email) {
        int EMAIL_MIN = 5;
        int EMAIL_MAX = 64;
        return email.length() >= EMAIL_MIN && email.length() <= EMAIL_MAX;
    }

    private boolean isValidPassword(String password) {
        int PASSWORD_MIN = 5;
        int PASSWORD_MAX = 32;
        return password.length() >= PASSWORD_MIN && password.length() <= PASSWORD_MAX;

    }

    private boolean isValidUsername(String username) {
        int USERNAME_MIN = 3;
        int USERNAME_MAX = 64;
        return username.length() >= USERNAME_MIN && username.length() <= USERNAME_MAX;
    }
}
