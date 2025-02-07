package by.koronatech.officeStaffMgmt.core.service;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDTO;
import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.ViewDepartmentMapper;
import by.koronatech.officeStaffMgmt.core.model.Department;
import by.koronatech.officeStaffMgmt.core.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentService {

    @Autowired
    private final DepartmentRepository departmentRepository;
    private final ViewDepartmentMapper departmentMapper;

    public Page<DepartmentDTO> getDepartments(Pageable pageRequest){
        return departmentRepository.findAll(pageRequest).map(departmentMapper::toDTO);
    }

    public DepartmentDetailsDTO getDepartmentDetails(Long id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()){
            throw new NoSuchElementException("Department with id=" + id + " is not found");
        }

        Department department = optionalDepartment.get();
        return DepartmentDetailsDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }
}
