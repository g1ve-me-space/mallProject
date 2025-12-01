package model;

import interfaces.Identifiable;
import jakarta.persistence.*; // Importurile pentru baza de date

@Entity // Transformă clasa în tabelă [cite: 327]
@Table(name = "floors")
public class Floor implements Identifiable<String> {

    @Id
    private String id;

    private int number;

    // Relația: Mai multe etaje aparțin unui singur Mall [cite: 347]
    @ManyToOne
    @JoinColumn(name = "mall_id") // Aceasta va fi coloana Foreign Key în tabelul 'floors'
    private Mall mall;

    public Floor() {}

    // --- GETTERS AND SETTERS ---

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    // Getter și Setter pentru relația cu Mall
    public Mall getMall() { return mall; }
    public void setMall(Mall mall) { this.mall = mall; }
}