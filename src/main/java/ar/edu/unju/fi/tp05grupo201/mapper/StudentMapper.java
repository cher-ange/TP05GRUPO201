package ar.edu.unju.fi.tp05grupo201.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.edu.unju.fi.tp05grupo201.dto.StudentDto;
import ar.edu.unju.fi.tp05grupo201.model.Student;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	@Mappings({

			@Mapping(source = "id", target = "id"),
			@Mapping(source = "personId", target = "personId"),
			@Mapping(source = "universityId", target = "universityId"),
			@Mapping(source = "name", target = "name"),
			@Mapping(source = "lastName", target = "lastName"),
			@Mapping(source = "email", target = "email"),
			@Mapping(source = "phone", target = "phone"),
			@Mapping(source = "birthdate", target = "birthdate"),
			@Mapping(source = "address", target = "address"),
			@Mapping(source = "state", target = "state"),
			@Mapping(source = "career", target = "career"),
			@Mapping(target = "subjects", ignore = true)
	})
	StudentDto convertStudentToStudentDto(Student s);

	@InheritInverseConfiguration
	@Mapping(target = "subjects", ignore = true)
	Student convertStudentDtoToStudent(StudentDto sdto);
	
	List<StudentDto> convertStudentToStudentDto(List<Student> listS);
	
	List<Student> convertStudentDtoToStudent(List<StudentDto> listSDTO);
}
