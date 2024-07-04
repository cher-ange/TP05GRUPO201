package ar.edu.unju.fi.tp05grupo201.mapper;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import ar.edu.unju.fi.tp05grupo201.model.AttendanceType;
import ar.edu.unju.fi.tp05grupo201.model.Subject;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {


    @Mapping(target = "students", ignore = true)
    Subject toEntity(SubjectDto subjectDto);

    @InheritInverseConfiguration
    SubjectDto toDto(Subject subject);

    List<Subject> toEntities(List<SubjectDto> subjectDtos);

    @InheritInverseConfiguration
    List<SubjectDto> toDtos(List<Subject> subjects);

    @Named("EnumToString")
    default String enumToString(AttendanceType attendanceType) {
        return attendanceType.getValue();
    }
}
