package com.course.coursework.entities;

import java.util.Objects;

public class Car {

    private int carId;
    private String name;
    private String country;

    public Car() {
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId && Objects.equals(name, car.name) && Objects.equals(country, car.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, name, country);
    }

    @Override
    public String toString() {
        return name + " " + country;
    }
}
