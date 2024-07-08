package com.aeon.hadog.controller;

import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
import com.aeon.hadog.base.dto.adoptPost.ListAdoptPostDTO;
import com.aeon.hadog.base.dto.response.ResponseDTO;
import com.aeon.hadog.service.AdoptPostService;
import com.aeon.hadog.service.AmazonS3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/adoptPost")
public class AdoptPostController {
    private final AdoptPostService adoptPostService;

    private final AmazonS3Service amazonS3Service;

    @PostMapping
    public ResponseEntity<ResponseDTO> uploadAdoptPost(@AuthenticationPrincipal String userId, @Valid @RequestPart AdoptPostDTO adoptPostDTO, @RequestPart(value = "images", required = false) List<MultipartFile> images){

        // s3 upload
        String folderName = "adoptPost";
        List<String> imageUrls = amazonS3Service.uploadImages(folderName, images);
        adoptPostDTO.setImageUrls(imageUrls);

        Long postId = adoptPostService.uploadPost(userId, adoptPostDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "입양 공고 작성 완료", postId));
    }

    @GetMapping("/post")
    public ResponseEntity<ResponseDTO> getPost(@RequestParam Long postId){
        AdoptPostDTO result = adoptPostService.getPostDetail(postId);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "입양 공고 상세 불러오기 완료", result));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getPostList(@RequestParam(defaultValue="0") int page){
        Page<ListAdoptPostDTO> result = adoptPostService.getList(page);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "입양 공고 목록 불러오기 완료", result));
    }
    @PatchMapping
    public ResponseEntity<ResponseDTO> modifyAdoptStatus(@AuthenticationPrincipal String userId, @RequestParam Long adoptPostId, @RequestParam boolean isAdopt){
        boolean result = adoptPostService.modifyAdoptStatus(userId, adoptPostId, isAdopt);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO<>(200, true, "입양 상태 수정 완료", result));
    }
}
