package by.koronatech.officeStaffMgmt.api.controller;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.core.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void testGetEmployees() {
        Pageable pageable = PageRequest.of(0, 10);
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(1L);
        employeeResponseDTO.setFullName("John");

        Page<EmployeeResponseDTO> employeePage = new PageImpl<>(Collections.singletonList(employeeResponseDTO), pageable, 1);

        when(employeeService.getAllEmployees(pageable)).thenReturn(employeePage);

        Page<EmployeeResponseDTO> result = employeeController.getEmployees(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFullName());
        verify(employeeService, times(1)).getAllEmployees(pageable);
    }

    @Test
    void testGetEmployeeById() {
        Long employeeId = 1L;
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employeeId);
        employeeResponseDTO.setFullName("John");

        when(employeeService.getAllEmployeeById(employeeId)).thenReturn(employeeResponseDTO);

        EmployeeResponseDTO result = employeeController.getEmployeeById(employeeId);

        assertEquals(employeeId, result.getId());
        assertEquals("John", result.getFullName());
        verify(employeeService, times(1)).getAllEmployeeById(employeeId);
    }

    @Test
    void testCreateEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFullName("John");

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(1L);
        employeeResponseDTO.setFullName("John");

        when(employeeService.createEmployee(employeeDTO)).thenReturn(employeeResponseDTO);

        EmployeeResponseDTO result = employeeController.createEmployee(employeeDTO);

        assertEquals(1L, result.getId());
        assertEquals("John", result.getFullName());
        verify(employeeService, times(1)).createEmployee(employeeDTO);
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;
        when(employeeService.deleteEmployee(employeeId)).thenReturn("Done");

        String result = employeeController.deleteEmployee(employeeId);

        assertEquals("Done", result);
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void testUpdateEmployee() {
        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFullName("John Updated");

        EmployeeResponseDTO updatedEmployee = new EmployeeResponseDTO();
        updatedEmployee.setId(1L);
        updatedEmployee.setFullName("John Updated");

        when(employeeService.updateEmployee(employeeDTO)).thenReturn(updatedEmployee);

        EmployeeResponseDTO result = employeeController.updateEmployee(employeeDTO);

        assertEquals(1L, result.getId());
        assertEquals("John Updated", result.getFullName());
        verify(employeeService, times(1)).updateEmployee(employeeDTO);
    }

    @Test
    void testAssignAsManager() {
        Long employeeId = 1L;
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employeeId);
        employeeResponseDTO.setManager(true);

        when(employeeService.assingEmployeeAsManager(employeeId)).thenReturn(employeeResponseDTO);

        EmployeeResponseDTO result = employeeController.assignAsManager(employeeId);

        assertEquals(employeeId, result.getId());
        assertTrue(result.getManager());
        verify(employeeService, times(1)).assingEmployeeAsManager(employeeId);
    }
}