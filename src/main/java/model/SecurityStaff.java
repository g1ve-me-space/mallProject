package model;

public class SecurityStaff extends Staff {
    private String badgeNo;

    public SecurityStaff() {}

    public SecurityStaff(String id, String name, String badgeNo) {
        super(id, name);
        this.badgeNo = badgeNo;
    }

    public String getBadgeNo() { return badgeNo; }
    public void setBadgeNo(String badgeNo) { this.badgeNo = badgeNo; }
}
