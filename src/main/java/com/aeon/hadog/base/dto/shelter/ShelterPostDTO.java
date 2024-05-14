package com.aeon.hadog.base.dto.shelter;

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

    private String title;

    private List<String> imageUrls;

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
