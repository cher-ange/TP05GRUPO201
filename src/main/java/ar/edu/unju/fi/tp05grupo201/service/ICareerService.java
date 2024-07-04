package ar.edu.unju.fi.tp05grupo201.service;

import java.util.List;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.model.Career;

public interface ICareerService {
    
    CareerDto createCareer();
    
    CareerDto getCareerById(long id);
    
    CareerDto getCareerByCode(String code);

    List<Career> getCareersByState(boolean state);

    void addCareer(Career career);

    void addSubjectToCareer(long careerId, long subjectId);
    
    void deleteCareer(long careerId);

    void deleteSubjectFromCareer(long careerId, long subjectId);    
}