package model;

import interfaces.Identifiable;
import jakarta.persistence.*; // Importuri pentru baza de date

@Entity
@Table(name = "purchases")
public class Purchase implements Identifiable<String> {

    @Id
    private String id;

    // RELAȚIA 1: O cumpărătură este făcută de un singur Client
    @ManyToOne
    @JoinColumn(name = "customer_id") // Cheia străină în tabel
    private Customer customer;

    // RELAȚIA 2: O cumpărătură este făcută într-un singur Magazin
    @ManyToOne
    @JoinColumn(name = "shop_id") // Cheia străină în tabel
    private Shop shop;

    private double amount;

    public Purchase() {
    }

    public Purchase(String id, Customer customer, Shop shop, double amount) {
        this.id = id;
        this.customer = customer;
        this.shop = shop;
        this.amount = amount;
    }

    // --- GETTERS AND SETTERS ---

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Modificat: lucrăm cu obiectul Customer, nu cu String
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Modificat: lucrăm cu obiectul Shop, nu cu String
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}