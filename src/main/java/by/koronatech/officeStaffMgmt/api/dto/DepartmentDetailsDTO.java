package by.koronatech.officeStaffMgmt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DepartmentDetailsDTO {
    private Long id;
    private String name;
    private List<EmployeeShortResponseDTO> employees;
}
