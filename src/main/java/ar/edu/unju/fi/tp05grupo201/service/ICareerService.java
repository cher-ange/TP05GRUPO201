package ar.edu.unju.fi.tp05grupo201.service;


import java.util.Set;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;

public interface ICareerService {
    
    CareerDto createCareer();

    void addCareer(CareerDto careerDto);
    
    CareerDto getCareerById(long id);
    
    CareerDto getCareerByCode(String code);
    
    void deleteCareer(String code);
    
    Set<CareerDto> getCareersByState(boolean state);
}