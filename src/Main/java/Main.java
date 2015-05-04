package Main.java;

import java.io.IOException;

/**
 * Created by stageusr2015 on 04/05/2015.
 */
public class Main {
    public static void main(String[] args) {
        try {
            char c = (char) System.in.read();
            while(c != 0) {
                System.out.println("read: " + c);
                c = (char) System.in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
