package by.koronatech.officeStaffMgmt.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeShortResponseDTO implements FullNameProvider {
    private Long id;
    private String fullName;
    private BigDecimal salary;
    private Boolean manager;
}
