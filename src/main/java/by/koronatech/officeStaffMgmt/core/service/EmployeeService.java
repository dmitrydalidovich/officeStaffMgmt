package by.koronatech.officeStaffMgmt.core.service;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeShortResponseDTO;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.CreateEmployeeMapper;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.UpdateEmployeeMapper;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.ViewDepartmentMapper;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.ViewEmployeeShortMapper;
import by.koronatech.officeStaffMgmt.core.model.Department;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final CreateEmployeeMapper createEmployeeMapper;
    private final UpdateEmployeeMapper updateEmployeeMapper;
    private final ViewEmployeeShortMapper viewEmployeeShortMapper;
    private final DepartmentService departmentService;

    private final List<Employee> employeesCacheRepo = getEmployees();

    public DepartmentDetailsDTO getAllEmployeesForDepartment(Long id) {
        DepartmentDetailsDTO dto = departmentService.getDepartmentDetails(id);
        dto.setEmployees(viewEmployeeShortMapper.toDTOs(employeesCacheRepo.stream()
                .filter(employee -> employee.getDepartment().getId().equals(id))
                .collect(Collectors.toList())));

        return dto;
    }

    public EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO){
        Employee employee = createEmployeeMapper.toEntity(employeeDTO);
        Department department = departmentService.getDepartmentsCacheRepo()
                .stream().filter(d -> d.getName().equals(employeeDTO.getDepartment())).findFirst().orElseThrow();

        employee.setDepartment(department);
        employee.setId(this.getNextEmployeeId());
        this.employeesCacheRepo.add(employee);
        return updateEmployeeMapper.toDTO(employee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {

        return updateEmployeeMapper.toDTOs(employeesCacheRepo);
    }

    public EmployeeResponseDTO getAllEmployeeById(long id) {
        return updateEmployeeMapper.toDTO(this.employeesCacheRepo.stream()
                .filter(employee -> employee.getId().equals(id)).findFirst().orElseThrow());
    }

    private Long getNextEmployeeId(){
        return this.employeesCacheRepo.stream().max(Comparator.comparingLong(Employee::getId)).orElseThrow().getId() + 1L;
    }

    public String deleteEmployee(long id) {
        Employee employee = this.employeesCacheRepo.stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElseThrow();

        this.employeesCacheRepo.removeIf(employee1 -> employee1.getId().equals(id));
        return "Done";
    }

    public EmployeeResponseDTO updateEmployee(EmployeeResponseDTO employeeDTO) {
        Optional<Employee> employeeOptional = this.employeesCacheRepo.stream()
                .filter(e -> e.getId().equals(employeeDTO.getId())).findFirst();

        Employee employee;

        if (employeeOptional.isPresent()){
            employee = employeeOptional.get();
            int index = this.employeesCacheRepo.indexOf(employee);

            employee = this.updateEmployeeMapper.merge(employee, employeeDTO);
            Optional<Department> department = this.departmentService.getDepartmentsCacheRepo().stream()
                    .filter(d -> d.getName().equals(employeeDTO.getDepartment())).findFirst();

            if (department.isPresent()){
                employee.setDepartment(department.get());
            }
            else{
                throw new NoSuchElementException("Department with name=" + employeeDTO.getDepartment() + " does not exist");
            }

            this.employeesCacheRepo.set(index, employee);
        }
        else{
            throw new NoSuchElementException("Employee with id=" + employeeDTO.getId() + " does not exist");
        }

        return this.updateEmployeeMapper.toDTO(employee);
    }

    public EmployeeResponseDTO assingEmployeeAsManager(long id) {
        Optional<Employee> employeeOptional = this.employeesCacheRepo.stream()
                .filter(e -> e.getId().equals(id)).findFirst();

        Employee employee;

        if (employeeOptional.isPresent()){
            employee = employeeOptional.get();
            int index = this.employeesCacheRepo.indexOf(employee);

            if (employee.getManager()){
                throw new IllegalStateException("Specified employee with id=" + id + " is already manager");
            }

            employee.setManager(true);

            this.employeesCacheRepo.set(index, employee);
        }
        else{
            throw new NoSuchElementException("Employee with id=" + id + " does not exist");
        }

        return this.updateEmployeeMapper.toDTO(employee);
    }

    private static ArrayList<Employee> getEmployees() {
        Department department1 = Department.builder().id(1L).name("Marketing").build();
        Department department2 = Department.builder().id(2L).name("Sales").build();
        Department department3 = Department.builder().id(3L).name("R&D").build();
        Department department4 = Department.builder().id(4L).name("Logistics").build();
        Department department5 = Department.builder().id(5L).name("Administrative").build();
        Department department6 = Department.builder().id(6L).name("Accounting").build();
        Department department7 = Department.builder().id(7L).name("IT").build();

        return new ArrayList<>(
                List.of(
                        Employee.builder().id(1L).lastName("Sidorova").firstName("Katia").salary(100.0f).manager(false).department(department1).build(),
                        Employee.builder().id(2L).lastName("Ivanov").firstName("Petr").salary(200.0f).manager(false).department(department1).build(),
                        Employee.builder().id(3L).lastName("Petrov").firstName("Alex").salary(300.0f).manager(true).department(department1).build(),
                        Employee.builder().id(4L).lastName("Samoilova").firstName("Sonia").salary(130.0f).manager(true).department(department2).build(),
                        Employee.builder().id(5L).lastName("Dmitrov").firstName("Dmitry").salary(140.0f).manager(false).department(department3).build(),
                        Employee.builder().id(6L).lastName("Fedorov").firstName("Maksim").salary(150.0f).manager(false).department(department4).build(),
                        Employee.builder().id(7L).lastName("Smith").firstName("Jonh").salary(170.0f).manager(true).department(department5).build(),
                        Employee.builder().id(8L).lastName("Ivanova").firstName("Katia").salary(180.0f).manager(true).department(department6).build()
                ));
    }

}
