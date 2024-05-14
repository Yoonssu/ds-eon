package com.aeon.hadog.base.dto.shelter;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ListShelterPostDTO {
    private String title;

    private String sex;

    private String age;
}
