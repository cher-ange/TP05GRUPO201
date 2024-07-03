package ar.edu.unju.fi.tp05grupo201.mapper;

import ar.edu.unju.fi.tp05grupo201.dto.SubjectDto;
import ar.edu.unju.fi.tp05grupo201.model.Subject;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "students", ignore = true)
    Subject toEntity(SubjectDto subjectDto);

    @InheritInverseConfiguration
    @Mapping(target = "students", ignore = true)
    SubjectDto toDto(Subject subject);

//    List<Subject> toEntities(List<SubjectDto> subjectDtos);

//    @InheritInverseConfiguration
//    List<SubjectDto> toDtos(List<Subject> subjects);

    Set<Subject> toSetEntities(Set<SubjectDto> subjectDtos);
//
    @InheritInverseConfiguration
    Set<SubjectDto> toSetDtos(Set<Subject> subjects);
}
