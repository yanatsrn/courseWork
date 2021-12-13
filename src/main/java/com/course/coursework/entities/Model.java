package com.course.coursework.entities;

import java.util.Objects;

public class Model {

    private int modelId;
    private String name;
    private String year;
    private int distance;
    private String fuel;
    private String fuelConsumption;
    private String transmission;
    private int price;
    private boolean isBought;
    private int carId;
    private int typeId;

    public Model() {
    }

    public Model(String name) {
        this.name = name;
    }

    public Model(int modelId, String name, String year, int distance, String fuel, String fuelConsumption, String transmission, int price) {
        this.modelId = modelId;
        this.name = name;
        this.year = year;
        this.distance = distance;
        this.fuel = fuel;
        this.fuelConsumption = fuelConsumption;
        this.transmission = transmission;
        this.price = price;
    }

    public Model(int modelId, String name, String year, int distance, String fuel, String fuelConsumption, String transmission, int price, boolean isBought, int carId, int typeId) {
        this.modelId = modelId;
        this.name = name;
        this.year = year;
        this.distance = distance;
        this.fuel = fuel;
        this.fuelConsumption = fuelConsumption;
        this.transmission = transmission;
        this.price = price;
        this.isBought = isBought;
        this.carId = carId;
        this.typeId = typeId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return modelId == model.modelId && distance == model.distance && price == model.price && isBought == model.isBought && carId == model.carId && typeId == model.typeId && Objects.equals(name, model.name) && Objects.equals(year, model.year) && Objects.equals(fuel, model.fuel) && Objects.equals(fuelConsumption, model.fuelConsumption) && Objects.equals(transmission, model.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelId, name, year, distance, fuel, fuelConsumption, transmission, price, isBought, carId, typeId);
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelId=" + modelId +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", distance=" + distance +
                ", fuel='" + fuel + '\'' +
                ", fuelConsumption='" + fuelConsumption + '\'' +
                ", transmission='" + transmission + '\'' +
                ", price=" + price +
                ", isBought=" + isBought +
                ", carId=" + carId +
                ", typeId=" + typeId +
                '}';
    }

    public static class Builder {
        //обязательные
        public String name;
        public String year;
        public String fuel;
        public String fuelConsumption;
        public int price;
        //необязательные
        public int distance = 0;
        public String transmission = "Механика";
        public boolean isBought = false;
        public int carId = 1;
        public int typeId = 1;

        //конструктор с обязательными параметрами
        public Builder(String name, String year, String fuel, String fuelConsumption, int price) {
            this.name = name;
            this.year = year;
            this.fuel = fuel;
            this.fuelConsumption = fuelConsumption;
            this.price = price;
        }

        //методы для необязательных параметров
        public Builder distance(int parameter) {
            distance = parameter;
            return this;
        }

        public Builder transmission(String parameter) {
            transmission = parameter;
            return this;
        }

        public Builder isBought(Boolean parameter) {
            isBought = parameter;
            return this;
        }

        public Builder carId(int parameter) {
            carId = parameter;
            return this;
        }

        public Builder typeId(int parameter) {
            typeId = parameter;
            return this;
        }

        public Model build() {
            return new Model(this);

        }
    }

    private Model(Builder builder) {
        name = builder.name;
        year = builder.year;
        distance = builder.distance;
        fuel = builder.fuel;
        fuelConsumption = builder.fuelConsumption;
        transmission = builder.transmission;
        price = builder.price;
        isBought = builder.isBought;
        carId = builder.carId;
        typeId = builder.typeId;
    }
}

