package by.koronatech.officeStaffMgmt.api.controller;

import by.koronatech.officeStaffMgmt.api.controller.DepartmentController;
import by.koronatech.officeStaffMgmt.api.dto.DepartmentDTO;
import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.core.service.DepartmentService;
import by.koronatech.officeStaffMgmt.core.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class DepartmentControllerTest {

    @MockitoBean
    private DepartmentService departmentService;

    @MockitoBean
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentController departmentController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDepartments() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setName("IT");

        Page<DepartmentDTO> departmentPage = new PageImpl<>(Collections.singletonList(departmentDTO), pageable, 1);

        when(departmentService.getDepartments(any(Pageable.class))).thenReturn(departmentPage);

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("IT"));

        verify(departmentService, times(1)).getDepartments(any(Pageable.class));
    }

    @Test
    void testGetDepartmentDetails() throws Exception {
        Long departmentId = 1L;
        DepartmentDetailsDTO departmentDetailsDTO = new DepartmentDetailsDTO();
        departmentDetailsDTO.setId(departmentId);
        departmentDetailsDTO.setName("IT");

        when(employeeService.getAllEmployeesForDepartment(departmentId)).thenReturn(departmentDetailsDTO);

        mockMvc.perform(get("/api/departments/{id}", departmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value("IT"));

        verify(employeeService, times(1)).getAllEmployeesForDepartment(departmentId);
    }

    @Test
    void testGetDepartmentDetails_NotFound() throws Exception {
        Long departmentId = 1L;

        when(employeeService.getAllEmployeesForDepartment(departmentId))
                .thenThrow(new NoSuchElementException("Department not found"));

        mockMvc.perform(get("/api/departments/{id}", departmentId))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        verify(employeeService, times(1)).getAllEmployeesForDepartment(departmentId);
    }
}