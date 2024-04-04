package com.aeon.hadog.domain;

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
@Table(name="dog_name")
public class DogName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogNameId;

    @Column(nullable=false)
    private String name;
}
