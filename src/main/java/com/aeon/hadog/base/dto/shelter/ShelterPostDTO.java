package com.aeon.hadog.base.dto.shelter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShelterPostDTO {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    private List<String> imageUrls;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;


    @NotBlank(message = "성별은 필수 입력 값입니다.")
    private String sex;

    @NotBlank(message = "색상은 필수 입력 값입니다.")
    private String color;

    @NotBlank(message = "나이는 필수 입력 값입니다.")
    private String age;

    @NotNull
    private Float weight;

    @NotBlank(message = "공고 번호는 필수 입력 값입니다.")
    private String adoptNum;

    @NotBlank(message = "발견 장소는 필수 입력 값입니다.")
    private String findLocation;

    @NotBlank(message = "보호소명은 필수 입력 값입니다.")
    private String center;

    @NotBlank(message = "보호소 번호는 필수 입력 값입니다.")
    private String centerPhone;

    @NotBlank(message = "부서명은 필수 입력 값입니다.")
    private String department;

    @NotBlank(message = "부서 번호는 필수 입력 값입니다.")
    private String departmentPhone;

}
