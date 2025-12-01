package model;

import interfaces.Identifiable;
import jakarta.persistence.*; // Importuri JPA esențiale

@Entity
@Table(name = "staff")
@Inheritance(strategy = InheritanceType.JOINED) // ⚠️ Aceasta definește strategia de moștenire
public abstract class Staff implements Identifiable<String> {

    @Id
    private String id;

    private String name;

    public Staff() {}

    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}