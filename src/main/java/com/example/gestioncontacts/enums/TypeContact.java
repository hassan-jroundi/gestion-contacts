package com.example.gestioncontacts.enums;

public enum TypeContact {

    F("F", "freelance"),
    E("E", "employe");

    private String key;
    private String name;

    private TypeContact(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return getKey();
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
