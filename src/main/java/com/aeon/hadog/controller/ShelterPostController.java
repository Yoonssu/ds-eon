package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.base.dto.shelter.ListShelterPostDTO;
import com.aeon.hadog.base.dto.shelter.ShelterPostDTO;
import com.aeon.hadog.service.ShelterPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/shelterPost")
public class ShelterPostController {

    private final ShelterPostService shelterPostService;

    @PostMapping
    public ResponseEntity<ResponseDTO> uploadShelterPost(@RequestBody ShelterPostDTO shelterPostDTO){
        Long postId = shelterPostService.uploadPost(shelterPostDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "보호소 공고 작성 완료", postId));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getPost(@RequestParam Long postId){
        ShelterPostDTO result = shelterPostService.getPost(postId);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "보호소 공고 상세 불러오기 완료", result));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getPostList(@RequestParam(defaultValue="0") int page){
        Page<ListShelterPostDTO> result = shelterPostService.getList(page);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "보호소 공고 목록 불러오기 완료", result));
    }
}
