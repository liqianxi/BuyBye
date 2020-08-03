package com.example.buybye.entities;

public class Catagory {
    private String name;
    private int Image;
    private int itemNum;
    public Catagory(String name, int Image){
        this.name = name;
        this.Image = Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return Image;
    }

    public int getItemNum() {
        return itemNum;
    }

    public String getName() {
        return name;
    }
}
