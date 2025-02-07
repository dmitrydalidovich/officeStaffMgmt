package by.koronatech.officeStaffMgmt.api.controller;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDTO;
import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeShortResponseDTO;
import by.koronatech.officeStaffMgmt.core.service.DepartmentService;
import by.koronatech.officeStaffMgmt.core.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Slf4j
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    @GetMapping
    public Page<DepartmentDTO> getDepartments(Pageable pageable){

        log.info("Get departents");
        return departmentService.getDepartments(pageable);
    }

    @GetMapping("/{id}")
    public DepartmentDetailsDTO getDepartmentDetails(@PathVariable long id){
        log.info("Get department with id ="+id);
        return employeeService.getAllEmployeesForDepartment(id);
    }
}
