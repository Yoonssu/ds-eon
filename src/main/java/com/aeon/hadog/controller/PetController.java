package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.PetDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.domain.Pet;
import com.aeon.hadog.service.AmazonS3Service;
import com.aeon.hadog.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final AmazonS3Service amazonS3Service;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@AuthenticationPrincipal String userId, @RequestBody PetDTO petDTO) {

        try {
            Long petId = petService.registerPet(userId, petDTO);

            return ResponseEntity.ok().body(new ResponseDTO<>(200, true, "반려견 등록 성공", petDTO));

        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO<>(400, false, "반려견 등록 실패: "+e.getMessage(), null));
        }
    }

    @PutMapping("/update/{petId}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long petId, @RequestBody PetDTO petDTO) {

        try {
            petService.updatePet(petId, petDTO);

            return ResponseEntity.ok().body(new ResponseDTO<>(200, true, "반려견 정보 수정 성공", petDTO));

        } catch(Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO<>(400, false, "반려견 정보 수정 실패: "+e.getMessage(), null));
        }

    }

    @GetMapping("/info/{petId}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long petId) {

        try {
            PetDTO petDTO = petService.viewPet(petId);

            return ResponseEntity.ok().body(new ResponseDTO<>(200, true, "반려견 정보 조회 성공", petDTO));

        } catch(Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO<>(400, false, "반려견 정보 조회 실패: "+e.getMessage(), null));
        }
    }

    //test
    @PostMapping("/uploadImage")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) throws Exception{
        String url = amazonS3Service.uploadImage(file);
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

}
