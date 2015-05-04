package Main.java;

import java.io.*;

/**
 * Created by stageusr2015 on 04/05/2015.
 */
public class Main {
    private final static boolean APPEND_MODE = true;
    private final static String BASEDIR = "C:\\Users\\stageusr2015\\Desktop\\stage\\noname\\src\\Main\\";

    public static void main(String[] args) {
        String filename = BASEDIR + "serialized\\service.ser";
        Service store = new Service("test serialization");
        /* write object to file */
        FileOutputStream fos = null;
        ObjectOutputStream os = null;

        try {
            boolean fileExists = (new File(filename).exists());
            fos = new FileOutputStream(filename, APPEND_MODE);
            if(fileExists){
                /* use this only if the file does not exists */
                os = new AppendableObjectOutputStream(fos);
            }
            else {
                os = new ObjectOutputStream(fos);
            }
            System.out.println("serialized: " + store);
            os.writeObject(store);
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }
        finally { closeResource(fos, os); }

        /* restore object from file */
        FileInputStream fis = null;
        ObjectInputStream is = null;
        try {
            fis = new FileInputStream(filename);
            is = new ObjectInputStream(fis);
            while (true) {
                try {
                    Service deserialize = (Service) is.readObject();
                    System.out.println("deserialized: " + deserialize);
                } catch(EOFException e){
                    break;
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        finally { closeResource(fis, is); }
    }

    private static void closeResource(Closeable... res){
        for(Closeable r : res) {
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
