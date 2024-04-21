package com.aeon.hadog.base.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PetDTO {

    private String name;
    private String breed;
    private Boolean sex;
    private Boolean neuter;
    private String image;
    private Integer age;

}
