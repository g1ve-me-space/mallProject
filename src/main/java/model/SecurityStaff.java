package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank; // Import

@Entity
@Table(name = "security_staff")
public class SecurityStaff extends Staff {

    @NotBlank(message = "Badge Number is mandatory!")
    private String badgeNo;

    public SecurityStaff() { super(); }

    public String getBadgeNo() { return badgeNo; }
    public void setBadgeNo(String badgeNo) { this.badgeNo = badgeNo; }
}