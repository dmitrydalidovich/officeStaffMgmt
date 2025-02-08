package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeShortResponseDTO;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ViewEmployeeShortMapperTest {
    private ViewEmployeeShortMapper viewEmployeeShortMapper;

    @BeforeEach
    void setUp() {
        viewEmployeeShortMapper = new ViewEmployeeShortMapper();
    }

    @Test
    void testToDTO() {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .managerFlag(true)
                .salary(BigDecimal.TEN)
                .build();

        EmployeeShortResponseDTO dto = viewEmployeeShortMapper.toDTO(employee);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Doe John", dto.getFullName());
        assertTrue(dto.getManager());
        assertEquals(BigDecimal.TEN, dto.getSalary());
    }

    @Test
    void testToEntity() {
        EmployeeShortResponseDTO dto = EmployeeShortResponseDTO.builder()
                .id(2L)
                .fullName("Smith Alice")
                .manager(false)
                .salary(BigDecimal.TEN)
                .build();

        Employee employee = viewEmployeeShortMapper.toEntity(dto);

        assertNotNull(employee);
        assertEquals("Smith", employee.getLastName());
        assertEquals("Alice", employee.getFirstName());
        assertFalse(employee.getManagerFlag());
        assertEquals(BigDecimal.TEN, employee.getSalary());
    }

    @Test
    void testMerge() {
        Employee employee = Employee.builder()
                .id(3L)
                .firstName("Bob")
                .lastName("Johnson")
                .managerFlag(false)
                .salary(BigDecimal.TEN)
                .build();

        EmployeeShortResponseDTO dto = EmployeeShortResponseDTO.builder()
                .id(3L)
                .fullName("Adams Charlie")
                .manager(true)
                .salary(BigDecimal.ONE)
                .build();

        Employee mergedEmployee = viewEmployeeShortMapper.merge(employee, dto);

        assertNotNull(mergedEmployee);
        assertEquals(3L, mergedEmployee.getId());
        assertEquals("Adams", mergedEmployee.getLastName());
        assertEquals("Charlie", mergedEmployee.getFirstName());
        assertTrue(mergedEmployee.getManagerFlag());
        assertEquals(BigDecimal.ONE, mergedEmployee.getSalary());
    }

}