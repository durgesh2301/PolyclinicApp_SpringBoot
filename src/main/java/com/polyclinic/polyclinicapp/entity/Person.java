package com.polyclinic.polyclinicapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Person(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id, String name) {
    public Person(String name) {
        this(null, name);
    }

    public Person() {
        this(null, null);
    }
}
