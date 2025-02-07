package by.koronatech.officeStaffMgmt.api.controller;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.core.model.Employee;
import by.koronatech.officeStaffMgmt.core.repository.EmployeeRepository;
import by.koronatech.officeStaffMgmt.core.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void testGetEmployees() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(1L);
        employeeResponseDTO.setFullName("John");

        Page<EmployeeResponseDTO> employeePage = new PageImpl<>(Collections.singletonList(employeeResponseDTO), pageable, 1);

        when(employeeService.getAllEmployees(pageable)).thenReturn(employeePage);

        mockMvc.perform(get("/api/employees")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].fullName").value("John"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Long employeeId = 1L;
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employeeId);
        employeeResponseDTO.setFullName("John");

        when(employeeService.getAllEmployeeById(employeeId)).thenReturn(employeeResponseDTO);

        mockMvc.perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.fullName").value("John"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFullName("John");

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(1L);
        employeeResponseDTO.setFullName("John");

        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeResponseDTO);

        mockMvc.perform(post("/api/employees")
                        .contentType("application/json")
                        .content("{\"firstName\": \"John\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("John"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;
        when(employeeService.deleteEmployee(employeeId)).thenReturn("Done");

        mockMvc.perform(delete("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Done"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFullName("John Updated");

        EmployeeResponseDTO updatedEmployee = new EmployeeResponseDTO();
        updatedEmployee.setId(1L);
        updatedEmployee.setFullName("John Updated");

        when(employeeService.updateEmployee(any(EmployeeResponseDTO.class))).thenReturn(updatedEmployee);

        mockMvc.perform(patch("/api/employees")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"firstName\": \"John Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("John Updated"));
    }

    @Test
    void testAssignAsManager() throws Exception {
        Long employeeId = 1L;
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employeeId);
        employeeResponseDTO.setManager(true);

        when(employeeService.assingEmployeeAsManager(employeeId)).thenReturn(employeeResponseDTO);

        mockMvc.perform(patch("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.manager").value(true));
    }
}