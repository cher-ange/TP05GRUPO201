package ar.edu.unju.fi.tp05grupo201.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.model.Career;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CareerMapper {

   @Mapping(target = "students", ignore = true)
   @Mapping(target = "subjects", ignore = true)
   Career toEntity(CareerDto careerDto);

   @InheritInverseConfiguration
   @Mapping(target = "students", ignore = true)
   @Mapping(target = "subjects", ignore = true)
   CareerDto toDto(Career career);
}