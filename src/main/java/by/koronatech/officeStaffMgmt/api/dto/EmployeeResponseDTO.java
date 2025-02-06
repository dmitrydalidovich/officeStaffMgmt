package by.koronatech.officeStaffMgmt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class EmployeeResponseDTO implements FullNameProvider {
    private Long id;
    private String fullName;
    private Float salary;
    private String department;
    private Boolean manager;
}
