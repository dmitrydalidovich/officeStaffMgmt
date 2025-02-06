package by.koronatech.officeStaffMgmt.core.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Float salary;
    private Department department;
    private Boolean manager;

    public boolean isManager(){
        return this.manager;
    }

}
