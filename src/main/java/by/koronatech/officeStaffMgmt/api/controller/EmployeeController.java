package by.koronatech.officeStaffMgmt.api.controller;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeResponseDTO;
import by.koronatech.officeStaffMgmt.core.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponseDTO> getEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable long id){
        return employeeService.getAllEmployeeById(id);
    }

    @PostMapping
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeService.createEmployee(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable long id){
        return employeeService.deleteEmployee(id);
    }

    @PatchMapping
    public EmployeeResponseDTO updateEmployee(@RequestBody EmployeeResponseDTO employeeDTO){
        return employeeService.updateEmployee(employeeDTO);
    }

    @PatchMapping("/{id}")
    public EmployeeResponseDTO assignAsManager(@PathVariable long id){
        return employeeService.assingEmployeeAsManager(id);
    }
}
