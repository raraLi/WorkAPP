package com.example.workapp.db;

public class TypeBean {
    int id;
    String typename;
    int imageId;
    int simageId;
    int kind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSImagId() {
        return simageId;
    }

    public void setSimagId(int simagId) {
        this.simageId = simagId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public TypeBean() {
    }

    public TypeBean(int id, String typename, int imageId, int simagId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.simageId = simagId;
        this.kind = kind;
    }
}
