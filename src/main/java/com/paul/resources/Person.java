package com.paul.resources;

public class Person {
    private String name;
    private String lastname;

    Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    Person() {
        this.lastname = "<Unknown>";
        this.name = "<Unknown>";
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getLastname() {
        return lastname;
    }

    void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return name + " " + lastname;
    }
}
