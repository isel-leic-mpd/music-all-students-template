package org.isel.leirt.music_all;

public class Errors {
    public static void TODO(String method) {
        throw new RuntimeException(method + " method not Implemented!");
    }
    
    public static void TO_COMPLETE(String method) {
        throw new RuntimeException(method + " method uncompleted!");
    }
    
}
