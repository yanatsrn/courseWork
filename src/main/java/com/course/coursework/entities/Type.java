package com.course.coursework.entities;

import java.util.Objects;

public class Type {

    private int typeId;
    private String body;
    private String salon;
    private String color;
    private boolean isParkingHelper;

    public Type() {
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isParkingHelper() {
        return isParkingHelper;
    }

    public void setParkingHelper(boolean parkingHelper) {
        isParkingHelper = parkingHelper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return typeId == type.typeId && isParkingHelper == type.isParkingHelper && Objects.equals(body, type.body) && Objects.equals(salon, type.salon) && Objects.equals(color, type.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, body, salon, color, isParkingHelper);
    }

    @Override
    public String toString() {
        if (isParkingHelper())
            return body + " " + salon + " " + color + " С парктроником";
        else
            return body + " " + salon + " " + color + " Без парктроника";
    }
}
