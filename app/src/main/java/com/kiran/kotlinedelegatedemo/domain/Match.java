package com.kiran.kotlinedelegatedemo.domain;

public class Match implements ListItem{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
