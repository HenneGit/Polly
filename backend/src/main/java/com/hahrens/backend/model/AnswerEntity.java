package com.hahrens.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tb_answer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "answerText")
    private String answerText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="questionEntity_id",referencedColumnName = "id", nullable = false)
    private QuestionEntity questionEntity;

}
