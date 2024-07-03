package ar.edu.unju.fi.tp05grupo201.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.model.Career;

@Mapper(componentModel = "spring")
public interface CareerMapper {

   @Mapping(target = "students", ignore = true)
   Career toEntity(CareerDto careerDto);

   @InheritInverseConfiguration
   @Mapping(target = "students", ignore = true)
   CareerDto toDto(Career career);

//    List<Career> ToEntities(List<CareerDto> careerDtos);
//
//    @InheritInverseConfiguration
//    List<CareerDto> toDtos(List<Career> careers);

//    Set<Career> toEntities(Set<CareerDto> careerDtos);
//
//    @InheritInverseConfiguration
//    Set<CareerDto> toDtos(Set<Career> careers);
}