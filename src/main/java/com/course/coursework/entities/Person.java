package com.course.coursework.entities;

import java.util.Objects;

public abstract class Person {

    int personID;
    String surname;
    String name;
    int age;
    String phone;
    String mail;

    public Person() {
    }

    public Person(int personID, String surname, String name, int age, String phone, String mail) {
        this.personID = personID;
        this.surname = surname;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.mail = mail;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID == person.personID && age == person.age && Objects.equals(surname, person.surname) && Objects.equals(name, person.name) && Objects.equals(phone, person.phone) && Objects.equals(mail, person.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, surname, name, age, phone, mail);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
