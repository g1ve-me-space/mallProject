package model;

import interfaces.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;     // Import necesar
import jakarta.validation.constraints.NotBlank;  // Import necesar
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer implements Identifiable<String> {

    @Id
    private String id;

    @NotBlank(message = "Numele este obligatoriu!")
    private String name;

    @NotBlank(message = "Valuta este obligatorie!") // <--- ADAPTAREA NECESARĂ
    private String currency;

    @NotBlank(message = "Email-ul este obligatoriu!")
    @Email(message = "Formatul email-ului nu este valid!")
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();

    public Customer() {}

    // --- GETTERS & SETTERS (Rămân la fel) ---
    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Purchase> getPurchases() { return purchases; }
    public void setPurchases(List<Purchase> purchases) { this.purchases = purchases; }
}