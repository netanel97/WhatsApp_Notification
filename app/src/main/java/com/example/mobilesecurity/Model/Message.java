package com.example.mobilesecurity.Model;

public class Message {

    private String contact_name;
    private String message;
    private String time;

    public Message(String contact_name, String message, String time) {
        this.contact_name = contact_name;
        this.message = message;
        this.time = time;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "contact_name='" + contact_name + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
