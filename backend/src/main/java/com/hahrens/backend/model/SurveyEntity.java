package com.hahrens.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_survey")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "descriptopn")
    private String description;

    @OneToMany(mappedBy = "surveyEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<QuestionEntity> questionEntities = new ArrayList<>();

}
