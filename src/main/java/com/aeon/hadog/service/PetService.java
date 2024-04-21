package com.aeon.hadog.service;

import com.aeon.hadog.base.dto.PetDTO;
import com.aeon.hadog.domain.Pet;
import com.aeon.hadog.repository.PetRepository;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public Long registerPet(String userId, PetDTO petDTO) {

        Pet pet = Pet.builder()
                .name(petDTO.getName())
                .breed(petDTO.getBreed())
                .sex(petDTO.getSex())
                .neuter(petDTO.getNeuter())
                .image(petDTO.getImage())
                .age(petDTO.getAge())
                .user(userRepository.findById(userId).get())
                .build();

        petRepository.save(pet);

        return pet.getPetId();
    }

    public Long updatePet(Long petId, PetDTO petDTO) {

        Pet pet = petRepository.findById(petId).get();

        pet.setName(petDTO.getName());
        pet.setBreed(petDTO.getBreed());
        pet.setSex(petDTO.getSex());
        pet.setNeuter(petDTO.getNeuter());
        pet.setImage(petDTO.getImage());
        pet.setAge(petDTO.getAge());

        petRepository.save(pet);

        return pet.getPetId();
    }

    public PetDTO viewPet(Long petId) {
        Pet pet = petRepository.findById(petId).get();
        PetDTO petDTO = PetDTO.builder()
                .name(pet.getName())
                .breed(pet.getBreed())
                .sex(pet.getSex())
                .neuter(pet.getNeuter())
                .image(pet.getImage())
                .age(pet.getAge())
                .build();

        return petDTO;
    }

}
