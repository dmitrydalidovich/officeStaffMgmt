package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.core.mapper.BaseMapper;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class UpdateEmployeeMapper extends BaseMapper<Employee, EmployeeResponseDTO> {
    @Override
    public EmployeeResponseDTO toDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .fullName(employee.getLastName() + " " + employee.getFirstName())
                .manager(employee.getManagerFlag())
                .department(employee.getDepartment().getName())
                .salary(employee.getSalary())
                .build();
    }

    @Override
    public Employee toEntity(EmployeeResponseDTO employeeResponseDTO) {
        String[] name = getNameStrings(employeeResponseDTO);
        return Employee.builder().lastName(name[0]).firstName(name[1])
                .managerFlag(employeeResponseDTO.getManager()).salary(employeeResponseDTO.getSalary()).build();
    }

    @Override
    public Employee merge(Employee employee, EmployeeResponseDTO employeeResponseDTO) {
        String[] name = this.getNameStrings(employeeResponseDTO);

        employee.setManagerFlag(employeeResponseDTO.getManager());
        employee.setSalary(employeeResponseDTO.getSalary());
        employee.setLastName(name[0]);
        employee.setFirstName(name[1]);
        return employee;
    }


}
