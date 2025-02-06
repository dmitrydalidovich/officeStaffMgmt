package by.koronatech.officeStaffMgmt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO implements FullNameProvider {
    private String fullName;
    private Float salary;
    private String department;
    private Boolean manager;
}
