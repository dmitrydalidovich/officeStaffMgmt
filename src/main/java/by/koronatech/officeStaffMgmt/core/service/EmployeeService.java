package by.koronatech.officeStaffMgmt.core.service;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.CreateEmployeeMapper;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.UpdateEmployeeMapper;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.ViewEmployeeShortMapper;
import by.koronatech.officeStaffMgmt.core.model.Department;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import by.koronatech.officeStaffMgmt.core.repository.DepartmentRepository;
import by.koronatech.officeStaffMgmt.core.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final CreateEmployeeMapper createEmployeeMapper;
    private final UpdateEmployeeMapper updateEmployeeMapper;
    private final ViewEmployeeShortMapper viewEmployeeShortMapper;
    private final DepartmentService departmentService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public DepartmentDetailsDTO getAllEmployeesForDepartment(Long id) {
        DepartmentDetailsDTO dto = departmentService.getDepartmentDetails(id);
        dto.setEmployees(viewEmployeeShortMapper.toDTOs(employeeRepository.findByDepartmentId(id)));

        return dto;
    }

    public EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO){
        Employee employee = createEmployeeMapper.toEntity(employeeDTO);
        Department department = departmentRepository.findByName(employeeDTO.getDepartment()).stream().findFirst().orElseThrow();
        employee.setDepartment(department);
        employeeRepository.save(employee);
        return updateEmployeeMapper.toDTO(employee);
    }

    public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(updateEmployeeMapper::toDTO);
    }

    public EmployeeResponseDTO getAllEmployeeById(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()){
            throw new NoSuchElementException("Employee with id=" + id + " is not found");
        }

        return updateEmployeeMapper.toDTO(employeeOptional.get());
    }

    public String deleteEmployee(long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.deleteById(employee.getId());
        return "Done";
    }

    public EmployeeResponseDTO updateEmployee(EmployeeResponseDTO employeeDTO) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeDTO.getId());

        Employee employee;

        if (employeeOptional.isPresent()){
            employee = employeeOptional.get();
            employee = updateEmployeeMapper.merge(employee, employeeDTO);
            Optional<Department> optionalDepartment = departmentRepository.findByName(employeeDTO.getDepartment()).stream().findFirst();

            if (optionalDepartment.isEmpty()){
                throw new NoSuchElementException("Department with name=" + employeeDTO.getDepartment() + " does not exist");
            }

            employee.setDepartment(optionalDepartment.get());
            employeeRepository.save(employee);
        }
        else{
            throw new NoSuchElementException("Employee with id=" + employeeDTO.getId() + " does not exist");
        }

        return updateEmployeeMapper.toDTO(employee);
    }

    public EmployeeResponseDTO assingEmployeeAsManager(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee;

        if (employeeOptional.isPresent()){
            employee = employeeOptional.get();

            if (employee.isManager()){
                throw new IllegalStateException("Specified employee with id=" + id + " is already manager");
            }

            employee.setManagerFlag(true);

            employeeRepository.save(employee);
        }
        else{
            throw new NoSuchElementException("Employee with id=" + id + " does not exist");
        }

        return updateEmployeeMapper.toDTO(employee);
    }
}
