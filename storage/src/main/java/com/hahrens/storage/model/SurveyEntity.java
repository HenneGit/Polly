package com.hahrens.storage.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * db entity representing a survey.
 */
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

    /**
     * add a question to this survey.
     * @param questionEntity the question to add.
     */
    public void addQuestion(final QuestionEntity questionEntity) {
        if (questionEntities == null) {
            questionEntities = new ArrayList<>();
        }
        questionEntities.add(questionEntity);
    }

    public void removeQuestion(final QuestionEntity questionEntity) {
        if (questionEntities == null || questionEntities.isEmpty()) {
            return;
        }
        questionEntities.remove(questionEntity);

    }


}
