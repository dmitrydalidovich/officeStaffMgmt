package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.core.model.Department;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreateEmployeeMapperTest {

    private CreateEmployeeMapper createEmployeeMapper;

    @BeforeEach
    void setUp() {
        createEmployeeMapper = new CreateEmployeeMapper();
    }

    @Test
    void testToDTO() {
        Department department = new Department();
        department.setName("IT");

        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .managerFlag(true)
                .salary(BigDecimal.TEN)
                .department(department)
                .build();

        EmployeeDTO dto = createEmployeeMapper.toDTO(employee);

        assertNotNull(dto);
        assertEquals("IT", dto.getDepartment());
        assertEquals("Doe John", dto.getFullName());
        assertTrue(dto.getManager());
        assertEquals(BigDecimal.TEN, dto.getSalary());
    }

    @Test
    void testToEntity() {
        EmployeeDTO dto = EmployeeDTO.builder()
                .fullName("Doe John")
                .manager(true)
                .salary(BigDecimal.ONE)
                .build();

        Employee employee = createEmployeeMapper.toEntity(dto);

        assertNotNull(employee);
        assertEquals("Doe", employee.getLastName());
        assertEquals("John", employee.getFirstName());
        assertTrue(employee.getManagerFlag());
        assertEquals(BigDecimal.ONE, employee.getSalary());
    }

    @Test
    void testMerge() {
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Smith")
                .managerFlag(false)
                .salary(BigDecimal.TEN)
                .build();

        EmployeeDTO dto = EmployeeDTO.builder()
                .fullName("Doe John")
                .manager(true)
                .salary(BigDecimal.TEN)
                .build();

        Employee mergedEmployee = createEmployeeMapper.merge(employee, dto);

        assertNotNull(mergedEmployee);
        assertEquals("Doe", mergedEmployee.getLastName());
        assertEquals("John", mergedEmployee.getFirstName());
        assertTrue(mergedEmployee.getManagerFlag());
        assertEquals(BigDecimal.TEN, mergedEmployee.getSalary());
    }
}