package com.aeon.hadog.base.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequestDTO {

    private String id;
    private String password;
}
