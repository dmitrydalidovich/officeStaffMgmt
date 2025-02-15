package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeShortResponseDTO;
import by.koronatech.officeStaffMgmt.core.mapper.BaseMapper;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class ViewEmployeeShortMapper extends BaseMapper<Employee, EmployeeShortResponseDTO> {

    @Override
    public EmployeeShortResponseDTO toDTO(Employee employee) {
        return EmployeeShortResponseDTO.builder()
                .id(employee.getId())
                .fullName(employee.getLastName() + " " + employee.getFirstName())
                .manager(employee.getManagerFlag())
                .salary(employee.getSalary())
                .build();
    }

    @Override
    public Employee toEntity(EmployeeShortResponseDTO employeeShortResponseDTO) {
        String[] name = getNameStrings(employeeShortResponseDTO);
        return Employee.builder().lastName(name[0]).firstName(name[1]).managerFlag(employeeShortResponseDTO.getManager()).salary(employeeShortResponseDTO.getSalary()).build();
    }

    @Override
    public Employee merge(Employee employee, EmployeeShortResponseDTO employeeShortResponseDTO) {
        String[] name = getNameStrings(employeeShortResponseDTO);

        employee.setId(employeeShortResponseDTO.getId());
        employee.setManagerFlag(employeeShortResponseDTO.getManager());
        employee.setSalary(employeeShortResponseDTO.getSalary());
        employee.setLastName(name[0]);
        employee.setFirstName(name[1]);
        return employee;
    }

}
