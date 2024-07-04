package ar.edu.unju.fi.tp05grupo201.service;


import java.util.List;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;

public interface ICareerService {
    
    CareerDto createCareer();

    void addCareer(CareerDto careerDto);

    void addSubjectToCareer(long careerId, long subjectId);
    
    CareerDto getCareerById(long id);
    
    CareerDto getCareerByCode(String code);

    List<CareerDto> getCareersByState(boolean state);
    
    void deleteCareer(String code);

    void deleteSubjectFromCareer(long careerId, long subjectId);    
}