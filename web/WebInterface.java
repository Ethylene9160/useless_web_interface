package com.example.myapplication.web;

public interface WebInterface {
    public static String SPLIT_INSIDE = "#";

    /**
     * When implying this interface, this method will be called while the receiver received a message.
     * @param message message you'll receive
     */
    void webAction(String message);
}
