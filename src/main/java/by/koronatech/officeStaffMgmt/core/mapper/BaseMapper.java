package by.koronatech.officeStaffMgmt.core.mapper;

import by.koronatech.officeStaffMgmt.api.dto.EmployeeDTO;
import by.koronatech.officeStaffMgmt.api.dto.FullNameProvider;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@MapperConfig(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public abstract class BaseMapper<E, D> {
    public abstract D toDTO(E e);
    public abstract E toEntity(D d);

    public List<D> toDTOs(List<E> eList){
        return eList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<E> toEntities(List<D> dList){
        return dList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public abstract E merge(@MappingTarget E e, D d);

    protected String[] getNameStrings(D employeeDTO) {
        String[] name = {};

        if (employeeDTO instanceof FullNameProvider provider){
            name = provider.getFullName().trim().split(" ");

            if (name.length != 2) {
                throw new IllegalArgumentException("Invalid name '" + provider.getFullName() + "' for employee");
            }
        }

        return name;
    }
}
