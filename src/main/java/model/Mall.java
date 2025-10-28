package model;

import java.util.List;

public class Mall {
    private String id;
    private String name;
    private String city;
    private List<Floor> floors;

    public Mall() {}

    public Mall(String id, String name, String city, List<Floor> floors) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.floors = floors;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<Floor> getFloors() { return floors; }
    public void setFloors(List<Floor> floors) { this.floors = floors; }
}
