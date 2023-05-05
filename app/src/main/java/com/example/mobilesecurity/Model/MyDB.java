package com.example.mobilesecurity.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDB {

//    private ArrayList<Message> allDeleted;
    private HashMap<String,ArrayList<Message>> allDeleted;

    public MyDB() { };

    public HashMap<String,ArrayList<Message>> getMessages() {
        if(allDeleted == null){
            allDeleted = new HashMap<String,ArrayList<Message>>();
        }
        return allDeleted;
    }

    public MyDB setMessages(HashMap<String,ArrayList<Message>> allDeleted) {
        this.allDeleted = allDeleted;
        return this;
    }
}
