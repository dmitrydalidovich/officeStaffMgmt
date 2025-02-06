package by.koronatech.officeStaffMgmt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class EmployeeShortResponseDTO implements FullNameProvider {
    private Long id;
    private String fullName;
    private Float salary;
    private Boolean manager;
}
