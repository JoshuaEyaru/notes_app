package com.example;

public class Note{
    private long id;
    private String content;
    private String priority;

    public Note(long id, String content, String priority) {
        this.id = id;
        this.content = content;
        this.priority = priority;
    }
    
    // override the toString() method to provide a readable output

    @Override
    public  String toString(){
        return "Note{id" + id +", content='" + content + "', priority='" + priority + "'}";
    }
}