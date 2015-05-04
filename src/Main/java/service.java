package Main.java;

import java.io.Serializable;

/**
 * Created by stageusr2015 on 04/05/2015.
 */
public class Service implements Serializable {
    private String serialize;

    @Override
    public String toString() {
        return "Service{" +
                "serialize='" + serialize + '\'' +
                '}';
    }

    public Service(String serialize) {
        this.serialize = serialize;
    }
}
