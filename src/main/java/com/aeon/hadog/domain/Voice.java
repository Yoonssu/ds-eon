package com.aeon.hadog.domain;

import com.aeon.hadog.base.Enum.VoiceAge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="voice")
public class Voice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voiceId;

    @Column(nullable=false)
    private String sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private VoiceAge age;

    @Column(nullable=false)
    private String word;
}
