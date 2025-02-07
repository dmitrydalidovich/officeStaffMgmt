package by.koronatech.officeStaffMgmt.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements FullNameProvider {
    private String fullName;
    private BigDecimal salary;
    private String department;
    private Boolean manager;
}
