package com.aeon.hadog.base.dto.mypage;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyPageDTO {

    private String name;
    private String id;
    private String password;
    private String nickname;
    private String email;
}
