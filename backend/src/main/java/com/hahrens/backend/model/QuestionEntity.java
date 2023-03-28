package com.hahrens.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_question")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "question")
    private String question;

    @Column(name = "descriptopn")
    private String description;

    @OneToMany(mappedBy = "questionEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<AnswerEntity> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="surveyEntity_id", referencedColumnName = "id", nullable = false)
    private SurveyEntity surveyEntity;

}
