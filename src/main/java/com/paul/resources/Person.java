package com.paul.resources;

public class Person {
    private String name;
    private String lastname;

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Person() {
        this.lastname = "<Unknown>";
        this.name = "<Unknown>";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return name + " " + lastname;
    }
}
