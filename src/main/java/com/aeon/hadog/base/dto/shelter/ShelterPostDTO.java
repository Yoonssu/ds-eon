package com.aeon.hadog.base.dto.shelter;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShelterPostDTO {

    private String title;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String sex;

    private String color;

    private String age;

    private Float weight;

    private String adoptNum;

    private String findLocation;

    private String center;

    private String centerPhone;

    private String department;

    private String departmentPhone;

}
