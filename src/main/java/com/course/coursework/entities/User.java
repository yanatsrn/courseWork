package com.course.coursework.entities;

import java.util.Objects;

public class User extends Person {

    private int userId;
    private String login;
    private String password;
    private RoleType role;
    private int discountSum;
    private int personId;

    public User() {
    }

    public User(String surname, String name, int age, String phone, String mail, int userId, String login, int discountSum) {
        this.surname = surname;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.mail = mail;
        this.userId = userId;
        this.login = login;
        this.discountSum = discountSum;
    }

    public User(int userId, String login, String password, RoleType role, int discountSum, int personId) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.role = role;
        this.discountSum = discountSum;
        this.personId = personId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public int getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(int discountSum) {
        this.discountSum = discountSum;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return userId == user.userId && discountSum == user.discountSum && personId == user.personId && Objects.equals(login, user.login) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, login, password, role, discountSum, personId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", discountSum=" + discountSum +
                ", personId=" + personId +
                '}';
    }
}
