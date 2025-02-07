package by.koronatech.officeStaffMgmt.core.service;

import static org.junit.jupiter.api.Assertions.*;
import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeShortResponseDTO;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.CreateEmployeeMapper;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.UpdateEmployeeMapper;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.ViewEmployeeShortMapper;
import by.koronatech.officeStaffMgmt.core.model.Department;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import by.koronatech.officeStaffMgmt.core.repository.DepartmentRepository;
import by.koronatech.officeStaffMgmt.core.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private CreateEmployeeMapper createEmployeeMapper;

    @Mock
    private UpdateEmployeeMapper updateEmployeeMapper;

    @Mock
    private ViewEmployeeShortMapper viewEmployeeShortMapper;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testGetAllEmployeesForDepartment() {
        Long departmentId = 1L;
        Department department = new Department();
        department.setId(departmentId);
        department.setName("IT");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartment(department);

        EmployeeShortResponseDTO dto = new EmployeeShortResponseDTO();
        dto.setId(1L);
        dto.setFullName("John Doe");

        when(departmentService.getDepartmentDetails(departmentId)).thenReturn(new DepartmentDetailsDTO());
        when(employeeRepository.findByDepartmentId(departmentId)).thenReturn(Collections.singletonList(employee));
        when(viewEmployeeShortMapper.toDTOs(any())).thenReturn(Collections.singletonList(dto));

        DepartmentDetailsDTO result = employeeService.getAllEmployeesForDepartment(departmentId);

        assertNotNull(result);
        verify(departmentService, times(1)).getDepartmentDetails(departmentId);
        verify(employeeRepository, times(1)).findByDepartmentId(departmentId);
        verify(viewEmployeeShortMapper, times(1)).toDTOs(any());
    }

    @Test
    void testCreateEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFullName("John Doe");
        employeeDTO.setDepartment("IT");

        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartment(department);

        when(createEmployeeMapper.toEntity(employeeDTO)).thenReturn(employee);
        when(departmentRepository.findByName("IT")).thenReturn(Collections.singletonList(department));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(updateEmployeeMapper.toDTO(employee)).thenReturn(new EmployeeResponseDTO());

        EmployeeResponseDTO result = employeeService.createEmployee(employeeDTO);

        assertNotNull(result);
        verify(createEmployeeMapper, times(1)).toEntity(employeeDTO);
        verify(departmentRepository, times(1)).findByName("IT");
        verify(employeeRepository, times(1)).save(employee);
        verify(updateEmployeeMapper, times(1)).toDTO(employee);
    }

    @Test
    void testGetAllEmployees() {
        Pageable pageable = mock(Pageable.class);
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        Page<Employee> employeePage = new PageImpl<>(Collections.singletonList(employee));
        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);
        when(updateEmployeeMapper.toDTO(employee)).thenReturn(new EmployeeResponseDTO());

        Page<EmployeeResponseDTO> result = employeeService.getAllEmployees(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(employeeRepository, times(1)).findAll(pageable);
        verify(updateEmployeeMapper, times(1)).toDTO(employee);
    }

    @Test
    void testGetAllEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(updateEmployeeMapper.toDTO(employee)).thenReturn(new EmployeeResponseDTO());

        EmployeeResponseDTO result = employeeService.getAllEmployeeById(employeeId);

        assertNotNull(result);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(updateEmployeeMapper, times(1)).toDTO(employee);
    }

    @Test
    void testGetAllEmployeeById_NotFound() {
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.getAllEmployeeById(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        String result = employeeService.deleteEmployee(employeeId);

        assertEquals("Done", result);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.deleteEmployee(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testUpdateEmployee() {
        Long employeeId = 1L;
        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(employeeId);
        employeeDTO.setFullName("John Doe");
        employeeDTO.setDepartment("IT");

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(departmentRepository.findByName("IT")).thenReturn(Collections.singletonList(department));
        when(updateEmployeeMapper.merge(employee, employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(updateEmployeeMapper.toDTO(employee)).thenReturn(employeeDTO);

        EmployeeResponseDTO result = employeeService.updateEmployee(employeeDTO);

        assertNotNull(result);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(departmentRepository, times(1)).findByName("IT");
        verify(updateEmployeeMapper, times(1)).merge(employee, employeeDTO);
        verify(employeeRepository, times(1)).save(employee);
        verify(updateEmployeeMapper, times(1)).toDTO(employee);
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Long employeeId = 1L;
        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.updateEmployee(employeeDTO));
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testAssingEmployeeAsManager() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setManagerFlag(false);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(updateEmployeeMapper.toDTO(employee)).thenReturn(new EmployeeResponseDTO());

        EmployeeResponseDTO result = employeeService.assingEmployeeAsManager(employeeId);

        assertNotNull(result);
        assertTrue(employee.isManager());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(employee);
        verify(updateEmployeeMapper, times(1)).toDTO(employee);
    }

    @Test
    void testAssingEmployeeAsManager_AlreadyManager() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setManagerFlag(true);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        assertThrows(IllegalStateException.class, () -> employeeService.assingEmployeeAsManager(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void testAssingEmployeeAsManager_NotFound() {
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.assingEmployeeAsManager(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }
}