package ar.edu.unju.fi.tp05grupo201.mapper;

import java.util.Set;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.model.Career;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface CareerMapper {

    @Mapping(target = "subjects", source = "subjectDtos")
    Career toEntity(CareerDto careerDto);

    @InheritInverseConfiguration
    CareerDto toDto(Career career);

    Set<Career> toEntities(Set<CareerDto> careerDtos);

    @InheritInverseConfiguration
    Set<CareerDto> toDtos(Set<Career> careers);
}