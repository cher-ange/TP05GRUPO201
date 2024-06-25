package ar.edu.unju.fi.tp05grupo201.mapper;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "subjects", source = "subjectDtos")
    Student toEntity(StudentDto studentDto);

    @InheritInverseConfiguration
    StudentDto toDto(Student student);

    List<Student> toEntities(List<StudentDto> studentDtos);

    @InheritInverseConfiguration
    List<StudentDto> toDtos(List<Student> students);
}
