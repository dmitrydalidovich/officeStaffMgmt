package by.koronatech.officeStaffMgmt.core.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Long id;
    private String name;
}
