package ar.edu.unju.fi.tp05grupo201.mapper;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import ar.edu.unju.fi.tp05grupo201.model.Subject;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectMapper {

    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "career", ignore = true)
    Subject toEntity(SubjectDto subjectDto);

    @InheritInverseConfiguration
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "career", ignore = true)
    SubjectDto toDto(Subject subject);
}
