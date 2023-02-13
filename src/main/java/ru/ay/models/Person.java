package ru.ay.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Person {

    private int id;

    @NotEmpty(message = "Имя не может быть пустым")
    private String name;

    @Min(value = 1900, message = "Год рождения не должен быть меньше 1900")
    private int birthYear;

    public Person() {
    }

    public Person(int id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

}
