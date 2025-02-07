package by.koronatech.officeStaffMgmt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class EmployeeResponseDTO implements FullNameProvider {
    private Long id;
    private String fullName;
    private BigDecimal salary;
    private String department;
    private Boolean manager;
}
