package ar.edu.unju.fi.tp05grupo201.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.unju.fi.tp05grupo201.mapper.CareerMapper;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.tp05grupo201.dto.CareerDto;
import ar.edu.unju.fi.tp05grupo201.model.Career;
import ar.edu.unju.fi.tp05grupo201.model.Student;
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
    private final CareerMapper careerMapper;
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
                "Career with code " + code + " wasn't found"
            );
        }

        List<Student> listOfStudents = optionalCareer.get().getStudents();
        
        for (int i = 0; i < listOfStudents.size(); i++) {
            optionalCareer.get().removeStudent(listOfStudents.get(i));
        }

        optionalCareer.get().setState(false);
        careerRepository.save(optionalCareer.get());
    }

    /**
     * Get a list of careers by state
     */
    @Override
    public List<CareerDto> getCareersByState(boolean state) {
//        return careerMapper.toDtos(careerRepository.findCareersByState(state).stream().collect(Collectors.toSet()));
        return careerRepository.findCareersByState(true).stream()
                .map(careerMapper::toDto)
                .collect(Collectors.toList());
    }
}
