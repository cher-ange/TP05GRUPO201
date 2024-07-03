package ar.edu.unju.fi.tp05grupo201.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher toEntity(TeacherDto teacherDto);

    @InheritInverseConfiguration
    TeacherDto toDto(Teacher teacher);

//    Set<Teacher> toEntities(Set<TeacherDto> teacherDtos);
//
//    @InheritInverseConfiguration
//    Set<TeacherDto> toDtos(Set<Teacher> teachers);
}
