package ar.edu.unju.fi.tp05grupo201.service;

import java.util.List;

import ar.edu.unju.fi.tp05grupo201.model.Career;

public interface ICareerService {
    
    Career createCareer();
    
    Career getCareerById(long id);
    
    Career getCareerByCode(String code);

    List<Career> getCareersByState(boolean state);

    void addCareer(Career career);

    void addSubjectToCareer(long careerId, long subjectId);
    
    void deleteCareer(String code);

    void deleteSubjectFromCareer(long careerId, long subjectId);    
}