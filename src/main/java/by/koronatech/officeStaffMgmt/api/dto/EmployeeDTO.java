package by.koronatech.officeStaffMgmt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO implements FullNameProvider {
    private String fullName;
    private BigDecimal salary;
    private String department;
    private Boolean manager;
}
