package com.example.MedicalRecords.services.implementations;
import com.example.MedicalRecords.data.dao.*;
import com.example.MedicalRecords.dto.DoctorDTO;
import com.example.MedicalRecords.dto.SpecialtyDTO;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import com.example.MedicalRecords.services.DoctorsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.MedicalRecords.data.entities.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DoctorsServiceImpl implements DoctorsService {
    private final DoctorsRepository doctorsRepository;

    private final SpecialtyRepository specialtyRepository;

    private final ModelMapper modelMapper;

    @Override
    public void addOrUpdateDoctor(DoctorDTO currentDoctorDTO) throws InvalidDataException {
        currentDoctorDTO.setDoctorSpecialId(getUser().getUsername());
        Doctor existingDoctor = doctorsRepository.
                findByDoctorSpecialId(currentDoctorDTO.getDoctorSpecialId());

        if (existingDoctor != null) {
            currentDoctorDTO.setId(existingDoctor.getId());
        }
        currentDoctorDTO.setSpecialties(currentDoctorDTO.getSpecialties());
        doctorsRepository.save(this.convertToDoctor(currentDoctorDTO));
    }

    @Override
    public Doctor findDoctorById(long id) {
        return doctorsRepository.findById(id).get();
    }

    public void deleteDoctor(String doctorSpecialId) {
        Doctor doctor = doctorsRepository.findByDoctorSpecialId(doctorSpecialId);
        doctor.setDeleted(true);

        doctorsRepository.save(doctor);
    }

    @Override
    public User getUser() throws InvalidDataException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User)authentication.getPrincipal();

        if (currentUser == null) {
            throw new InvalidDataException("Грешно ID на лекар! Моля, обърнете се към поддръжка.");
        }
        return currentUser;
    }

    @Override
    public DoctorDTO getDoctor() throws InvalidDataException {
       return this.convertToDoctorDTO(doctorsRepository.findByDoctorSpecialId(this.getUser().getUsername()));
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorsRepository.findAll()
                .stream()
                .map(this::convertToDoctorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> getSearchResults(DoctorDTO doctorDTO) {
        String result = doctorDTO.getFirstName();
        List <Doctor> resultDoctors;

        if(result.contains(" ")) {
            String firstName = result.substring(0, doctorDTO.getFirstName().indexOf(" "));
            String thirdName = result.substring(doctorDTO.getFirstName().indexOf(" ") + 1);

            resultDoctors = doctorsRepository.findAllByFirstNameAndThirdName(firstName, thirdName);
        } else {
            resultDoctors = doctorsRepository.findAllByFirstNameOrThirdName(result, result);
        }
        return resultDoctors.stream().map(this::convertToDoctorDTO)
                .collect(Collectors.toList());
    }

    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::convertToSpecialtyDTO)
                .collect(Collectors.toList());
    }

    private SpecialtyDTO convertToSpecialtyDTO(Specialty specialty) {
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }

    private DoctorDTO convertToDoctorDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    private Doctor convertToDoctor(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }
}
