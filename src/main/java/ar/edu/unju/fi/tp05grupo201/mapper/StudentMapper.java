package ar.edu.unju.fi.tp05grupo201.mapper;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "career", ignore = true)
    Student toEntity(StudentDto studentDto);

    @InheritInverseConfiguration    
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "career", ignore = true)
    StudentDto toDto(Student student);
}
