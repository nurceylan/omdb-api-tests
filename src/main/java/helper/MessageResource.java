package helper;

import java.util.ResourceBundle;

public class MessageResource {
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages");

    public static String getMessage(String name){
        return bundle.getString(name);
    }

}
