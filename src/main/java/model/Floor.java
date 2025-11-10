package model;

import interfaces.Identifiable;

public class Floor implements Identifiable<String> {

    private String id;
    private int number;
    // Potentially other fields like mallId, etc.

    // --- CONSTRUCTORS ---
    // (Your constructors go here)

    // --- GETTERS AND SETTERS ---

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}