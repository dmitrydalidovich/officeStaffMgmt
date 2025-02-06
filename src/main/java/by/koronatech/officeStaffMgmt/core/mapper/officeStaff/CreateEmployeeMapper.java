package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.core.mapper.BaseMapper;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateEmployeeMapper extends BaseMapper<Employee, EmployeeDTO> {

    @Override
    public EmployeeDTO toDTO(Employee employee) {
        return EmployeeDTO.builder()
                .department(employee.getDepartment().getName())
                .fullName(employee.getLastName() + " " + employee.getFirstName())
                .manager(employee.getManager())
                .salary(employee.getSalary())
                .build();
    }

    @Override
    public Employee toEntity(EmployeeDTO employeeDTO) {
        String[] name = this.getNameStrings(employeeDTO);
        return Employee.builder().lastName(name[0]).firstName(name[1]).manager(employeeDTO.getManager()).salary(employeeDTO.getSalary()).build();
    }

    @Override
    public Employee merge(Employee employee, EmployeeDTO employeeDTO) {
        String[] name = this.getNameStrings(employeeDTO);

        employee.setManager(employeeDTO.getManager());
        employee.setSalary(employeeDTO.getSalary());
        employee.setLastName(name[0]);
        employee.setFirstName(name[1]);
        return employee;
    }


}
