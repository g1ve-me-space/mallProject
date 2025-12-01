package model; // Asigură-te că pachetul corespunde folderului tău

import interfaces.Identifiable;
import jakarta.persistence.*; // Importurile necesare pentru JPA
import java.util.ArrayList;
import java.util.List;

@Entity // Spune Spring-ului că aceasta este o tabelă în baza de date [cite: 327]
@Table(name = "malls") // Opțional: Numele tabelei
public class Mall implements Identifiable<String> {

    @Id // Cheia primară [cite: 329]
    private String id;

    private String name;
    private String city;
    private String address;

    // Relația: Un Mall are mai multe etaje [cite: 347]
    // mappedBy = "mall" înseamnă că legătura este gestionată de câmpul 'mall' din clasa Floor
    @OneToMany(mappedBy = "mall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Floor> floors = new ArrayList<>();

    public Mall() {}

    // --- GETTERS & SETTERS ---

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Floor> getFloors() { return floors; }
    public void setFloors(List<Floor> floors) {
        this.floors = floors;
        // Când setăm lista de etaje, actualizăm și referința din etaj către acest mall
        if(floors != null) {
            for(Floor f : floors) {
                f.setMall(this);
            }
        }
    }
}