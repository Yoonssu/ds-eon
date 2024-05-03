package com.aeon.hadog.base.dto.diary;

import com.aeon.hadog.domain.EmotionTrack;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UploadDiaryDTO {
    private EmotionTrack emotionTrack;
    private LocalDateTime diaryDate;
    private String content;

}
