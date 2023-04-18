package com.hahrens.storage.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * a db entity representing a question in a survey.
 */
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

    @Column(name = "order_number")
    private Integer orderNumber;

    @OneToMany(mappedBy = "questionEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<AnswerEntity> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="surveyEntity_id", nullable = false)
    private SurveyEntity surveyEntity;

    /**
     * add an answer to this question.
     * @param answerEntity the answer to add.
     */
    public void addAnswer(AnswerEntity answerEntity) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        answers.add(answerEntity);
    }

    /**
     * remove an answer from this entity.
     * @param answerEntity the answer to remove.
     */
    public void removeAnswer(final AnswerEntity answerEntity) {
        if (answers == null || answers.isEmpty()) {
            return;
        }
        answers.remove(answerEntity);

    }

}
