package com.aeon.hadog.base.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JoinRequestDTO {

    private String name;

    private String id;

    private String password;

    private String nickname;

    private String email;
}
