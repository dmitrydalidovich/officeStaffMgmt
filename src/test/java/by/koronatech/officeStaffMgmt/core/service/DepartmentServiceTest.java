package by.koronatech.officeStaffMgmt.core.service;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDTO;
import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.ViewDepartmentMapper;
import by.koronatech.officeStaffMgmt.core.model.Department;
import by.koronatech.officeStaffMgmt.core.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ViewDepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;
    private DepartmentDTO departmentDTO;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Finance");

        departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("Finance");
    }

    @Test
    void testGetDepartments() {
        Pageable pageable = mock(Pageable.class);
        Page<Department> departmentPage = new PageImpl<>(List.of(department));
        when(departmentRepository.findAll(pageable)).thenReturn(departmentPage);
        when(departmentMapper.toDTO(department)).thenReturn(departmentDTO);

        Page<DepartmentDTO> result = departmentService.getDepartments(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Finance", result.getContent().get(0).getName());
    }

    @Test
    void testGetDepartmentDetailsFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        DepartmentDetailsDTO result = departmentService.getDepartmentDetails(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Finance", result.getName());
    }

    @Test
    void testGetDepartmentDetailsNotFound() {
        when(departmentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> departmentService.getDepartmentDetails(2L));
    }
}