package model;

// It must extend the base Staff class
public class SecurityStaff extends Staff {

    private String badgeNo;

    public SecurityStaff() {
        super();
    }

    // getId, setId, getName, and setName are inherited from Staff.

    public String getBadgeNo() {
        return badgeNo;
    }

    public void setBadgeNo(String badgeNo) {
        this.badgeNo = badgeNo;
    }
}