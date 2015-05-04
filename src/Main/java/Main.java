package Main.java;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by stageusr2015 on 04/05/2015.
 */
public class Main {
    public static void main(String[] args) {
        try {
            String filename = "C:\\Users\\stageusr2015\\Desktop\\stage\\noname\\src\\Main\\java\\readme.txt";
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);

            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
