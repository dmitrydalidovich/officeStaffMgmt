package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.core.model.Department;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UpdateEmployeeMapperTest {

    private UpdateEmployeeMapper updateEmployeeMapper;

    @BeforeEach
    void setUp() {
        updateEmployeeMapper = new UpdateEmployeeMapper();
    }

    @Test
    void testToDTO() {
        Department department = new Department();
        department.setName("HR");

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Johnson")
                .managerFlag(false)
                .salary(BigDecimal.TEN)
                .department(department)
                .build();

        EmployeeResponseDTO dto = updateEmployeeMapper.toDTO(employee);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Johnson Alice", dto.getFullName());
        assertFalse(dto.getManager());
        assertEquals("HR", dto.getDepartment());
        assertEquals(BigDecimal.TEN, dto.getSalary());
    }

    @Test
    void testToEntity() {
        EmployeeResponseDTO dto = EmployeeResponseDTO.builder()
                .id(1L)
                .fullName("Johnson Alice")
                .manager(false)
                .salary(BigDecimal.TEN)
                .build();

        Employee employee = updateEmployeeMapper.toEntity(dto);

        assertNotNull(employee);
        assertEquals("Johnson", employee.getLastName());
        assertEquals("Alice", employee.getFirstName());
        assertFalse(employee.getManagerFlag());
        assertEquals(BigDecimal.TEN, employee.getSalary());
    }

    @Test
    void testMerge() {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Bob")
                .lastName("Smith")
                .managerFlag(true)
                .salary(BigDecimal.ONE)
                .build();

        EmployeeResponseDTO dto = EmployeeResponseDTO.builder()
                .id(1L)
                .fullName("Johnson Alice")
                .manager(false)
                .salary(BigDecimal.TEN)
                .build();

        Employee mergedEmployee = updateEmployeeMapper.merge(employee, dto);

        assertNotNull(mergedEmployee);
        assertEquals("Johnson", mergedEmployee.getLastName());
        assertEquals("Alice", mergedEmployee.getFirstName());
        assertFalse(mergedEmployee.getManagerFlag());
        assertEquals(BigDecimal.TEN, mergedEmployee.getSalary());
    }
}