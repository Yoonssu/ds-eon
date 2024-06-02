package com.aeon.hadog.base.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class commentDTO {

    @NotNull
    private Long commentId;

    @NotNull
    private String userId;

    @NotNull
    private LocalDateTime createdDate;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;
}
