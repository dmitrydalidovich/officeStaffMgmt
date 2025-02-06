package by.koronatech.officeStaffMgmt.core.service;

import by.koronatech.officeStaffMgmt.api.dto.DepartmentDTO;
import by.koronatech.officeStaffMgmt.api.dto.DepartmentDetailsDTO;
import by.koronatech.officeStaffMgmt.api.dto.EmployeeShortResponseDTO;
import by.koronatech.officeStaffMgmt.core.mapper.officeStaff.ViewDepartmentMapper;
import by.koronatech.officeStaffMgmt.core.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final ViewDepartmentMapper departmentMapper;
    @Getter
    private final List<Department> departmentsCacheRepo = getDepartmentArrayList();

    public List<DepartmentDTO> getDepartments(){
        return departmentMapper.toDTOs(this.departmentsCacheRepo);
    }

    public DepartmentDTO getDepartment(Long id){
        return departmentMapper.toDTO(this.departmentsCacheRepo.stream().filter(
                department -> department.getId().equals(id)).findFirst().orElseThrow());
    }

    public DepartmentDetailsDTO getDepartmentDetails(Long id){
        Department department = departmentsCacheRepo.stream().filter(d -> d.getId().equals(id)).findFirst().orElseThrow();
        return DepartmentDetailsDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    private static ArrayList<Department> getDepartmentArrayList() {
        return new ArrayList<>(
                List.of(
                        Department.builder().id(1L).name("Marketing").build(),
                        Department.builder().id(2L).name("Sales").build(),
                        Department.builder().id(3L).name("R&D").build(),
                        Department.builder().id(4L).name("Logistics").build(),
                        Department.builder().id(5L).name("Administrative").build(),
                        Department.builder().id(6L).name("Accounting").build(),
                        Department.builder().id(7L).name("IT").build()
                )
        );
    }

}
