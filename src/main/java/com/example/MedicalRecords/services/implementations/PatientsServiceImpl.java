package com.example.MedicalRecords.services.implementations;

import com.example.MedicalRecords.dto.DoctorDTO;
import com.example.MedicalRecords.dto.MedicalRecordDTO;
import com.example.MedicalRecords.dto.PatientDTO;
import com.example.MedicalRecords.exceptions.InvalidDataException;
import com.example.MedicalRecords.services.PatientsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.MedicalRecords.data.entities.*;
import com.example.MedicalRecords.data.dao.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PatientsServiceImpl implements PatientsService {

    private final PatientsRepository patientsRepository;

    private final DoctorsRepository doctorsRepository;

    private final MedicalRecordsRepository medicalRecordsRepository;

    private final DiagnoseRepository diagnoseRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PatientDTO> getAllPatients() {
         List<Patient> patients = (List<Patient>) patientsRepository.findAll();
         return patients.stream()
                  .map(this::convertToPatientDTO)
                  .collect(Collectors.toList());
    }

    @Override
    public void addOrUpdatePatient (PatientDTO currentPatientDTO) throws InvalidDataException {
        this.checkPatientEGN(currentPatientDTO);
        currentPatientDTO.setPatientSpecialId(getUser().getUsername());

        Patient existingPatient = patientsRepository
                .findByPatientSpecialId(currentPatientDTO.getPatientSpecialId());

        Doctor currentPatientGp = doctorsRepository
                .findByDoctorSpecialId(currentPatientDTO.getGp().getDoctorSpecialId());

        if(currentPatientGp != null) {
            DoctorDTO currentPatientGpDTO = this.convertToDoctorDTO(currentPatientGp);

            if (existingPatient == null) {
                this.addNewPatient(currentPatientDTO, currentPatientGpDTO);
            } else {
                this.updatePatient(currentPatientDTO, existingPatient, currentPatientGpDTO);
            }
        } else {
            throw new InvalidDataException("Грешно на ID лекар! Моля, въведете валидно ID.");
        }
    }

    private void checkPatientEGN(PatientDTO currentPatientDTO) throws InvalidDataException {
        String regex = "[0-9]+";
        if(!currentPatientDTO.getEgn().matches(regex)) {
            throw new InvalidDataException("Грешно ЕГН! Моля, въведете валидно.");
        }
    }

    private void addNewPatient(PatientDTO currentPatientDTO, DoctorDTO currentPatientGpDTO) {
        this.addToGpList(this.setPatientGP(currentPatientDTO, currentPatientGpDTO), currentPatientGpDTO );
    }

    private void updatePatient(PatientDTO currentPatientDTO, Patient existingPatient, DoctorDTO currentPatientGpDTO) {
        currentPatientDTO.setId(existingPatient.getId());

        boolean isSameGp = existingPatient.getGp().getDoctorSpecialId()
                           .equals(currentPatientDTO.getGp().getDoctorSpecialId());
        if (!isSameGp) {
            this.removeFromGpList(existingPatient);
            this.addToGpList(this.setPatientGP(currentPatientDTO, currentPatientGpDTO), currentPatientGpDTO);
        }
    }

    private Patient setPatientGP(PatientDTO currentPatientDTO, DoctorDTO currentPatientGpDTO) {
        currentPatientDTO.setGp(this.convertToDoctor(currentPatientGpDTO));
        Patient patient = this.convertToPatient(currentPatientDTO);
        patientsRepository.save(patient);

        return patient;
    }

    private void addToGpList(Patient currentPatient, DoctorDTO currentPatientGpDTO) {
        currentPatientGpDTO.getPatients().add(currentPatient);
        doctorsRepository.save(this.convertToDoctor(currentPatientGpDTO));
    }

    private void removeFromGpList(Patient existingPatient) {
        Doctor oldGp = doctorsRepository
                .findByDoctorSpecialId(existingPatient.getGp().getDoctorSpecialId());
        oldGp.getPatients().remove(existingPatient);
        doctorsRepository.save(oldGp);
    }

    public void deletePatient(String patientSpecialId) {
        Patient patient = patientsRepository.findByPatientSpecialId(patientSpecialId);
        patient.setDeleted(true);

        patientsRepository.save(patient);
    }

    @Override
    public List<PatientDTO> getSearchResults(PatientDTO patientDTO) {
        String result = patientDTO.getFirstName();
        List <Patient> resultPatients;

        if(result.contains(" ")) {
            String firstName = result.substring(0, patientDTO.getFirstName().indexOf(" "));
            String thirdName = result.substring(patientDTO.getFirstName().indexOf(" ") + 1);

            resultPatients = patientsRepository.findAllByFirstNameAndThirdName(firstName, thirdName);
        } else {
            resultPatients = patientsRepository.findAllByFirstNameOrThirdNameOrEgn(result, result, result);
        }
        return resultPatients.stream().map(this::convertToPatientDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addMedicalRecord(String patientSpecialId, MedicalRecordDTO medicalRecordDTO) throws InvalidDataException {
        User currentUser = this.getUser();

        Doctor doctor = doctorsRepository.findByDoctorSpecialId(currentUser.getUsername());
        Patient patient = patientsRepository.findByPatientSpecialId(patientSpecialId);

        Diagnose diagnose = diagnoseRepository.findByDiagnoseName(medicalRecordDTO.getDiagnose().getDiagnoseName());

        diagnose.setDiagnosePatientsAmount(diagnose.getDiagnosePatientsAmount() + 1);
        medicalRecordDTO.setPatient(patient);
        medicalRecordDTO.setDoctor(doctor);
        medicalRecordDTO.setDiagnose(diagnose);

        MedicalRecord medicalRecord = this.convertToMedicalRecord(medicalRecordDTO);
        patient.getMedicalRecords().add(medicalRecord);
        medicalRecordsRepository.save(medicalRecord);
        patientsRepository.save(patient);
        diagnoseRepository.save(diagnose);
    }

    @Override
    public User getUser() throws InvalidDataException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User)authentication.getPrincipal();

        if (currentUser == null) {
            throw new InvalidDataException("Грешно ID на пациент! Моля, обърнете се към поддръжка.");
        }
        return currentUser;
    }

    @Override
    public PatientDTO getPatient(String patientSpecialId) {
        return convertToPatientDTO(patientsRepository.findByPatientSpecialId(patientSpecialId));
    }

    @Override
    public PatientDTO getPatient() throws InvalidDataException {

        if(patientsRepository.findByPatientSpecialId(this.getUser().getUsername()) == null) {
            throw new InvalidDataException("Моля, въведете пациентските си данни!");
        }
        else {
            return convertToPatientDTO(patientsRepository.findByPatientSpecialId(this.getUser().getUsername()));
        }
    }

    private PatientDTO convertToPatientDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    private DoctorDTO convertToDoctorDTO(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    private Patient convertToPatient(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }

    private MedicalRecord convertToMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        return modelMapper.map(medicalRecordDTO, MedicalRecord.class);
    }

    private Doctor convertToDoctor(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }
}




//private DoctorDTO setPatientGP(PatientDTO currentPatient) throws InvalidDataException {
    /*    Doctor currentPatientGP = doctorsRepository.findByDoctorSpecialId(currentPatient.getGp().getDoctorSpecialId());
        if(currentPatientGP != null) {
            if (patient.getGp() == null || !this.hasExistingPatientSameGP(currentPatientGP, patient)) {
                patient.setGp(currentPatientGP);
                currentPatientGP.getPatients().add(patient);
                currentPatientGP.setPatientsAmount(currentPatientGP.getPatientsAmount() + 1);
            }
        } else {
            throw new InvalidDataException("Грешно на ID лекар! Моля, въведете валидно ID.");
        }
        doctorsRepository.save(currentPatientGP);
*/

// }

/*
    private void setPatientGP(Patient currentPatient, Patient patient)  throws InvalidDataException {
        Doctor currentPatientGP = doctorsRepository.findByDoctorSpecialId(currentPatient.getGp().getDoctorSpecialId());
        if(currentPatientGP != null) {
            if (patient.getGp() != null) {
                this.setExistingPatientGP(currentPatientGP, patient);
            }

            //  patient.setGp(currentPatientGP);
            doctorsRepository.save(currentPatientGP);
            currentPatientGP.getPatients().add(patient);
            currentPatientGP.setPatientsAmount(currentPatientGP.getPatientsAmount() + 1);
        } else {
            throw new InvalidDataException("Грешно на ID лекар! Моля, въведете валидно ID.");
        }
    }

    private void setExistingPatientGP(Doctor currentPatientGP, Patient patient) {
        Doctor oldGP = doctorsRepository.findByDoctorSpecialId(patient.getGp().getDoctorSpecialId());
        if (!currentPatientGP.getDoctorSpecialId().equals(oldGP.getDoctorSpecialId())) {
            oldGP.getPatients().remove(patient);
            oldGP.setPatientsAmount(oldGP.getPatientsAmount() - 1);
        }
    }

    */

       /* currentPatient.setFirstName(currentPatient.getFirstName());
        currentPatient.setSecondName(currentPatient.getSecondName());
        currentPatient.setThirdName(currentPatient.getThirdName());
        currentPatient.setEgn(currentPatient.getEgn());
        currentPatient.setInsurancePaid(currentPatient.isInsurancePaid());

        patientsRepository.save(patient);

        this.checkPatientEGN(currentPatient);
       this.setPatientGP(currentPatient, patient);*/

  /*   Doctor d = doctorsRepository
                .findByDoctorSpecialId(currentPatientDTO.getGp().getDoctorSpecialId());

        currentPatientDTO.setGp(d);
        Patient p = this.convertToPatient(currentPatientDTO);
        patientsRepository.save(p);

        d.getPatients().add(p);
        doctorsRepository.save(d);*/