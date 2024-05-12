package com.aeon.hadog.base.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyPageUserDTO {

    private String name;
    private String id;
    private String password;
    private String nickname;
    private String email;
}
