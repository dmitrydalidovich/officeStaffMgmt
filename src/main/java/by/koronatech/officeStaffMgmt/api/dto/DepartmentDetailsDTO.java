package by.koronatech.officeStaffMgmt.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDetailsDTO {
    private Long id;
    private String name;
    private List<EmployeeShortResponseDTO> employees;
}
