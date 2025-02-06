package by.koronatech.officeStaffMgmt.core.mapper.officeStaff;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDTO;
import by.koronatech.officeStaffMgmt.core.mapper.BaseMapper;
import by.koronatech.officeStaffMgmt.core.model.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ViewDepartmentMapper extends BaseMapper<Department, DepartmentDTO> {

    @Override
    public DepartmentDTO toDTO(Department department) {
        return DepartmentDTO.builder().id(department.getId()).name(department.getName()).build();
    }

    @Override
    public Department toEntity(DepartmentDTO departmentDTO) {
        return Department.builder().id(departmentDTO.getId()).name(departmentDTO.getName()).build();
    }

    @Override
    public Department merge(Department department, DepartmentDTO departmentDTO) {
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());
        return department;
    }
}
