package com.example.mallproject.model;

import java.time.LocalDate;
import java.util.List;

public class Tenant {
    private String id;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private List<Store> stores;

    // Constructors
    public Tenant() {}

    public Tenant(String id, String companyName, String contactPerson, String email) {}

    // Getters and Setters
    public String getId() { return ""; }
    public void setId(String id) {}

    public String getCompanyName() { return ""; }
    public void setCompanyName(String companyName) {}

    public String getContactPerson() { return ""; }
    public void setContactPerson(String contactPerson) {}

    public String getEmail() { return ""; }
    public void setEmail(String email) {}

    public String getPhoneNumber() { return ""; }
    public void setPhoneNumber(String phoneNumber) {}

    public LocalDate getContractStartDate() { return null; }
    public void setContractStartDate(LocalDate contractStartDate) {}

    public LocalDate getContractEndDate() { return null; }
    public void setContractEndDate(LocalDate contractEndDate) {}

    public List<Store> getStores() { return null; }
    public void setStores(List<Store> stores) {}

    // Business methods
    public void addStore(Store store) {}
    public boolean isContractActive() { return false; }
    public long getRemainingContractDays() { return 0; }
}