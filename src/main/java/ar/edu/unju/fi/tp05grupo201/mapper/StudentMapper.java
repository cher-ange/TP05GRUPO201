package ar.edu.unju.fi.tp05grupo201.mapper;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class, CareerMapper.class})
public interface StudentMapper {

    // @Mapping(target = "subjects", ignore = true)
    // @Mapping(target = "career", ignore = true)
    Student toEntity(StudentDto studentDto);

    @InheritInverseConfiguration
    // @Mapping(target = "subjects", ignore = true)
    // @Mapping(target = "career", ignore = true)
    StudentDto toDto(Student student);

//    List<Student> toEntities(List<StudentDto> studentDtos);
//
//    @InheritInverseConfiguration
//    List<StudentDto> toDtos(List<Student> students);
//
//    Set<Student> toEntities(Set<StudentDto> studentDtos);
//
//    @InheritInverseConfiguration
//    Set<StudentDto> toDtos(Set<Student> students);
}
