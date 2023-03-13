package com.example.myapplication.web;

import java.io.Closeable;
import java.io.IOException;

public class WebUtil {
    private WebUtil() {
    }

    /**
     * a way to close instances of Closeable.
     * @param able
     */
    public static void closeAll(Closeable... able) {
        for (Closeable closeable : able) {
            if(closeable != null){
                try{
                    closeable.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}