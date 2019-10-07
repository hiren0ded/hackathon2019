package com.example.demo;

public class TableClass {

    String Title;
    String SubTitle;

    TableClass(String Title, String SubTitle){
        this.Title = Title;
        this.SubTitle = SubTitle;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
