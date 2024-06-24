package ar.edu.unju.fi.tp05grupo201.service.imp;


import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.mapper.CareerMapperImpl;
import ar.edu.unju.fi.tp05grupo201.model.Career;
import ar.edu.unju.fi.tp05grupo201.model.Subject;

import ar.edu.unju.fi.tp05grupo201.repository.CareerRepository;
import ar.edu.unju.fi.tp05grupo201.service.ICareerService;
import lombok.AllArgsConstructor;

/**
 * Career service implementation
 */
@Service
@AllArgsConstructor
public class CareerServiceImp implements ICareerService {

    
    /**
     * Dependencies
     */
    private final CareerRepository careerRepository;
    private final CareerMapperImpl careerMapper;
    private final CareerDto careerDto;

    /**
     * Create a career
     * @return Career
     */
    @Override
    public CareerDto createCareer() {
        return careerDto;
    }

    /**
     * Add a career
     */
    @Override
    public void addCareer(CareerDto careerDto) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(careerDto.getCode());


        /**
         * Condition to re-enable a Career that was 'deleted'
         */
        if (optionalCareer.isPresent()) {

            careerDto.setId(optionalCareer.get().getId());
            careerDto.setState(true);
        }

        careerRepository.save(careerMapper.toEntity(careerDto));
    }

    /**
     * Get a career by id
     * @return Career
     */
    @Override
    public CareerDto getCareerById(long id) {

        Optional<Career> optionalCareer = careerRepository.findById(id);
        
        if (optionalCareer.isEmpty()) {
            throw new NoSuchElementException(
                "GET: Career with id " + id + "wasn't found"
            );
        }


        return careerMapper.toDto(optionalCareer.get());
    }

    /**
     * Get a career by code
     * @return Career
     */
    @Override
    public CareerDto getCareerByCode(String code) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(code);

        if (optionalCareer.isEmpty()) {
            throw new NoSuchElementException(
                "GET: Career with code: " + code + " wasn't found"
            );
        }


        return careerMapper.toDto(optionalCareer.get());
    }

    /**
     * Delete a career by code
     */
    @Override
    public void deleteCareer(String code) {
        Optional<Career> optionalCareer = careerRepository.findCareerByCode(code);

        if (optionalCareer.isEmpty()) {
            throw new IllegalArgumentException(
                "DELETE: Career with code " + code + " wasn't found"
            );
        }

        /**
         * Update the relationship that has with Subject
         */
        for (Subject subject : optionalCareer.get().getSubjects()) {
            optionalCareer.get().removeSubject(subject);
        }

        optionalCareer.get().setState(false);
        careerRepository.save(optionalCareer.get());
    }

    /**
     * Get a list of careers by state
     */
    @Override
    public Set<CareerDto> getCareersByState(boolean state) {
        return careerMapper.toDtos(careerRepository.findCareersByState(state).stream().collect(Collectors.toSet()));
    }
}
