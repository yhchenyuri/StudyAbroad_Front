package com.example.front.constant;

public enum OrderStatus {

    待付款("warning", "待付款"),
    已完款("success", "已確認"),
    退費("danger", "退費"),
    已取消("danger", "已取消");

    private final String color;
    private final String label;

    OrderStatus(String color, String label) {
        this.color = color;
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }
}