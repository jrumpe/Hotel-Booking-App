package com.example.guzman.app1;

public class MyHotelsData {
    String names, price, desc, images;

    public MyHotelsData(){

    }

    public MyHotelsData(String names, String price, String desc, String images) {
        this.names = names;
        this.price = price;
        this.desc = desc;
        this.images = images;
    }

    public String getNames() {

        return names;
    }

  /*    below are setters and getters methods                     */
  public void setNames(String names) {

        this.names = names;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}

