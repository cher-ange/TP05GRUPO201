package ar.edu.unju.fi.tp05grupo201.mapper;

import java.util.Set;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface TeacherMapper {


    Teacher toEntity(TeacherDto teacherDto);

    @InheritInverseConfiguration
    TeacherDto toDto(Teacher teacher);

    Set<Teacher> toEntities(Set<TeacherDto> teacherDtos);

    @InheritInverseConfiguration
    Set<TeacherDto> toDtos(Set<Teacher> teachers);
}
