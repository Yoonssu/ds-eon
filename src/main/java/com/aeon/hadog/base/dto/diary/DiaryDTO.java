package com.aeon.hadog.base.dto.diary;

import com.aeon.hadog.domain.EmotionTrack;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DiaryDTO {
    @NotBlank(message = "감정은 필수입니다.")
    private EmotionTrack emotionTrack;

    @NotBlank(message = "날짜은 필수입니다.")
    private LocalDateTime diaryDate;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

}
