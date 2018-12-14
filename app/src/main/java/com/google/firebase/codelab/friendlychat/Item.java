package com.google.firebase.codelab.friendlychat;
public class Item {
    public String title;
    public String description;
    public String date;
    public String link;

    public Item(String title,String description,String date,String link){
        this.title = title;
        this.description = description;
        this.date = date;
        this.link = link;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getDate(){
        return date;
    }
    public String getLink(){
        return link;
    }
}