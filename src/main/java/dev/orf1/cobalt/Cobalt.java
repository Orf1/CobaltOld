package dev.orf1.cobalt;

import dev.orf1.cobalt.client.Client;
import dev.orf1.cobalt.server.Server;

import java.util.Scanner;


public class Cobalt {
    public static void main(String[] args) {
        try {
            System.out.println("Cobalt v1.0.0 by Orf1");
            System.out.println("Please select an option:\n1 - Client\n2 - Server\n3 - Exit");
            String selection = new Scanner(System.in).nextLine();
            if (selection.equals("1")) {
                System.out.println("Starting Client.");
                new Client().start();
            } else if (selection.equals("2")) {
                System.out.println("Starting Server.");
                new Server().start();
            }
            System.out.println("Program Complete.");
        } catch (Exception exception) {
            System.out.println("Something went wrong!");
            exception.printStackTrace();
        }
    }

}
