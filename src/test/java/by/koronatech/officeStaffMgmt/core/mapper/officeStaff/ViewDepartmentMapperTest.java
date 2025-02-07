package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDTO;
import by.koronatech.officeStaffMgmt.core.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewDepartmentMapperTest {
    private ViewDepartmentMapper viewDepartmentMapper;

    @BeforeEach
    void setUp() {
        viewDepartmentMapper = new ViewDepartmentMapper();
    }

    @Test
    void testToDTO() {
        Department department = Department.builder()
                .id(1L)
                .name("Finance")
                .build();

        DepartmentDTO dto = viewDepartmentMapper.toDTO(department);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Finance", dto.getName());
    }

    @Test
    void testToEntity() {
        DepartmentDTO dto = DepartmentDTO.builder()
                .id(2L)
                .name("HR")
                .build();

        Department department = viewDepartmentMapper.toEntity(dto);

        assertNotNull(department);
        assertEquals(2L, department.getId());
        assertEquals("HR", department.getName());
    }

    @Test
    void testMerge() {
        Department department = Department.builder()
                .id(3L)
                .name("IT")
                .build();

        DepartmentDTO dto = DepartmentDTO.builder()
                .id(3L)
                .name("Operations")
                .build();

        Department mergedDepartment = viewDepartmentMapper.merge(department, dto);

        assertNotNull(mergedDepartment);
        assertEquals(3L, mergedDepartment.getId());
        assertEquals("Operations", mergedDepartment.getName());
    }

}