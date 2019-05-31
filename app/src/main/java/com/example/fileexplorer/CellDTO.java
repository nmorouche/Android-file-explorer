package com.example.fileexplorer;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
