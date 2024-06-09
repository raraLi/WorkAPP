package com.example.workapp.db;

// TypeBean类，用于存储类型数据
public class TypeBean {
    // 实例变量（成员变量）
    int id; // 类型的唯一标识符
    String typename; // 类型的名称（如“餐饮”、“交通”等）
    int imageId; // 类型的默认图片资源ID
    int simageId; // 类型的选中状态图片资源ID（可能是高亮或选中的图标）
    int kind; // 类型的种类（如0代表支出，1代表收入）

    // getter方法，用于获取实例变量的值
    public int getId() {
        return id;
    }

    public String getTypename() {
        return typename;
    }

    public int getImageId() {
        return imageId;
    }

    public int getSImagId() {
        return simageId;
    }

    // setter方法，用于设置实例变量的值

    // 构造函数，用于创建TypeBean对象并初始化其成员变量
    public TypeBean(int id, String typename, int imageId, int simagId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.simageId = simagId; // 注意这里变量名
        this.kind = kind;
    }
}