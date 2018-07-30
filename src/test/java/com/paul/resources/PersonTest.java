package com.paul.resources;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void testSetAndGetName() {
        Person p = new Person();
        p.setName("John");
        assertEquals("The name is not what is expected.", "John", p.getName());
    }

    @Test
    public void testSetAndGetLastName() {
        Person p = new Person();
        p.setLastname("Doe");
        assertEquals("The lastname is not what is expected.", "Doe", p.getLastname());
    }

    @Test
    public void testDefaultConstructor() {
        Person p = new Person();
        assertEquals("The name is not what is expected.", "<Unknown>", p.getName());
        assertEquals("The lastname is not what is expected.", "<Unknown>", p.getLastname());
    }

    @Test
    public void testConstructor() {
        Person p = new Person("John", "Doe");
        assertEquals("The name is not what is expected.", "John", p.getName());
        assertEquals("The lastname is not what is expected.", "Doe", p.getLastname());
    }

    @Test
    public void testToStringOfDefaultConstructor() {
        Person p = new Person();
        String expectedString = "<Unknown> <Unknown>";
        assertEquals("toString method does not build expected string", expectedString, p.toString());
    }

    @Test
    public void testToStringOfConstructor() {
        Person p = new Person("John", "Doe");
        String expectedString = "John Doe";
        assertEquals("toString method does not build expected string", expectedString, p.toString());
    }
}