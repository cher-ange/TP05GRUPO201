package ar.edu.unju.fi.tp05grupo201.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import ar.edu.unju.fi.tp05grupo201.dto.TeacherDto;
import ar.edu.unju.fi.tp05grupo201.model.Teacher;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    Teacher toEntity(TeacherDto teacherDto);

    @InheritInverseConfiguration
    TeacherDto toDto(Teacher teacher);
}
