package com.example.fileexplorer.Model;

public class CellDTO {
    private String title;
    private String type;

    public CellDTO(String title, String type){
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

}
