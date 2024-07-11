package com.aeon.hadog.base.dto.adoptPost;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ListAdoptPostDTO {
    private String thumbnail;

    private String name;
    private String breed;

    private String age;

    private String duration;
}
