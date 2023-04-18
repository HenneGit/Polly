package com.hahrens.controller.implementation.service;

import com.hahrens.storage.model.AnswerEntity;
import com.hahrens.storage.model.QuestionEntity;
import com.hahrens.storage.model.SurveyEntity;
import com.hahrens.storage.repository.AnswerEntityRepository;
import com.hahrens.storage.repository.QuestionEntityRepository;
import com.hahrens.storage.repository.SurveyEntityRepository;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.DTOEntityInterface;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.DTOMapping;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DTOMappingImpl implements DTOMapping {

    private final QuestionEntityRepository questionEntityRepository;

    private final AnswerEntityRepository answerEntityRepository;

    private final SurveyEntityRepository surveyEntityRepository;

    private final Map<Long, AnswerDTO> answerDTOMapping;

    private final Map<Long, SurveyDTO> surveyDTOMapping;

    private final Map<Long, QuestionDTO> questionDTOMapping;

    public DTOMappingImpl(QuestionEntityRepository questionEntityRepository, AnswerEntityRepository answerEntityRepository, SurveyEntityRepository surveyEntityRepository) {
        this.questionEntityRepository = questionEntityRepository;
        this.answerEntityRepository = answerEntityRepository;
        this.surveyEntityRepository = surveyEntityRepository;
        answerDTOMapping = new HashMap<>();
        surveyDTOMapping = new HashMap<>();
        questionDTOMapping = new HashMap<>();
        load();
    }

    //public for testing.
    public void load() {
        List<SurveyEntity> surveyEntities = surveyEntityRepository.findAll();
        for (SurveyEntity surveyEntity : surveyEntities) {
            surveyDTOMapping.put(surveyEntity.getId(), DTOFactory.getSurveyDTO(surveyEntity));
        }
        List<QuestionEntity> questions = questionEntityRepository.findAll();
        for (QuestionEntity questionEntity : questions) {
            questionDTOMapping.put(questionEntity.getId(), DTOFactory.getQuestionDTO(questionEntity, surveyDTOMapping.get(questionEntity.getSurveyEntity().getId()).getPrimaryKey()));
        }
        List<AnswerEntity> answers = answerEntityRepository.findAll();
        for (AnswerEntity answerEntity : answers) {
            answerDTOMapping.put(answerEntity.getId(), DTOFactory.getAnswerDTO(answerEntity, questionDTOMapping.get(answerEntity.getQuestionEntity().getId()).getPrimaryKey()));
        }
    }

    @Override
    public Collection<AnswerDTO> getAnswers() {
        return answerDTOMapping.values();
    }

    @Override
    public Collection<QuestionDTO> getQuestions() {
        return questionDTOMapping.values();
    }

    @Override
    public Collection<SurveyDTO> getSurveys() {
        return surveyDTOMapping.values();
    }

    @Override
    public void removeEntity(final DTOEntityInterface dtoEntityInterface) {
        if (dtoEntityInterface instanceof AnswerDTO answerDTO) {
            removeAnswer(answerDTO);
        }
        if (dtoEntityInterface instanceof QuestionDTO questionDTO) {
            removeQuestion(questionDTO);
        }
        if (dtoEntityInterface instanceof SurveyDTO surveyDTO) {
            removeSurvey(surveyDTO);
        }
    }

    @Override
    public DTOEntityInterface addEntity(final DTOEntityInterface dtoEntityInterface) {
        if (dtoEntityInterface instanceof AnswerDTO answerDTO) {
            return addAnswer(answerDTO);
        }
        if (dtoEntityInterface instanceof QuestionDTO questionDTO) {
            return addQuestion(questionDTO);
        }
        if (dtoEntityInterface instanceof SurveyDTO surveyDTO) {
            SurveyEntity save = surveyEntityRepository.save(SurveyEntity.builder().name(surveyDTO.getName()).description(surveyDTO.getDescription()).build());
            SurveyDTO surveyDTO1 = DTOFactory.getSurveyDTO(save);
            surveyDTOMapping.put(save.getId(), surveyDTO1);
            return surveyDTO1;
        }
        return null;
    }

    @Override
    public DTOEntityInterface updateEntity(final DTOEntityInterface dtoEntityInterface) {
        if (dtoEntityInterface instanceof AnswerDTO answerDTO) {
            Long entityIdForDTOPk = getEntityIdForDTOPk(answerDTO.getPrimaryKey(), answerDTOMapping);
            if (entityIdForDTOPk == null) {
                //todo PkNotFoundException
                return null;
            }
            AnswerEntity answerEntity = answerEntityRepository.findById(entityIdForDTOPk).orElse(null);
            if (answerEntity != null) {
                answerEntity.setAnswerText(answerDTO.getAnswerText());
                answerEntityRepository.save(answerEntity);
                answerDTOMapping.replace(entityIdForDTOPk, answerDTO);
                return answerDTO;
            }
        }
        if (dtoEntityInterface instanceof QuestionDTO questionDTO) {
            Long entityIdForDTOPk = getEntityIdForDTOPk(questionDTO.getPrimaryKey(), questionDTOMapping);
            if (entityIdForDTOPk == null) {
                //todo PkNotFoundException
                return null;
            }
            QuestionEntity questionEntity = questionEntityRepository.findById(entityIdForDTOPk).orElse(null);
            if (questionEntity != null) {
                questionEntity.setQuestion(questionDTO.getQuestion());
                questionEntity.setDescription(questionDTO.getDescription());
                questionEntity.setName(questionDTO.getName());
                questionEntity.setOrderNumber(questionDTO.getOrderNumber());
                questionEntityRepository.save(questionEntity);
                questionDTOMapping.replace(entityIdForDTOPk, questionDTO);
                return questionDTO;
            }
        }
        if (dtoEntityInterface instanceof SurveyDTO surveyDTO) {
            Long entityIdForDTOPk = getEntityIdForDTOPk(surveyDTO.getPrimaryKey(), surveyDTOMapping);
            if (entityIdForDTOPk == null) {
                //todo PkNotFoundException
                return null;
            }
            SurveyEntity surveyEntity = surveyEntityRepository.findById(entityIdForDTOPk).orElse(null);
            if (surveyEntity != null) {
                surveyEntity.setDescription(surveyDTO.getDescription());
                surveyEntity.setName(surveyDTO.getName());
                surveyEntityRepository.save(surveyEntity);
                surveyDTOMapping.replace(entityIdForDTOPk, surveyDTO);
                return surveyDTO;
            }
        }
        return null;
    }

    private QuestionDTO addQuestion(final QuestionDTO questionDTO) {
        if (questionDTO == null) {
            return null;
        }
        Long surveyPk = getEntityIdForDTOPk(questionDTO.getSurveyPk(), surveyDTOMapping);
        SurveyEntity surveyEntity = surveyEntityRepository.findById(surveyPk).orElse(null);
        if (surveyEntity != null) {
            QuestionEntity question = QuestionEntity.builder() //
                    .question(questionDTO.getQuestion()) //
                    .description(questionDTO.getDescription()) //
                    .name(questionDTO.getName())
                    .orderNumber(questionDTO.getOrderNumber())//
                    .surveyEntity(surveyEntity).build();
            QuestionEntity newQuestion = questionEntityRepository.save(question);
            newQuestion.setSurveyEntity(surveyEntity);
            surveyEntity.addQuestion(question);
            QuestionDTO questionDTO1 = DTOFactory.getQuestionDTO(newQuestion, questionDTO.getSurveyPk());
            questionDTOMapping.put(newQuestion.getId(), questionDTO1);
            return questionDTO1;
        }
        return null;
    }

    private AnswerDTO addAnswer(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return null;
        }
        Long questionId = getEntityIdForDTOPk(answerDTO.getQuestionPk(), questionDTOMapping);
        QuestionEntity question = questionEntityRepository.findById(questionId).orElse(null);
        if (question != null) {
            AnswerEntity answer = AnswerEntity.builder().answerText(answerDTO.getAnswerText()).questionEntity(question).build();
            AnswerEntity newAnswer = answerEntityRepository.save(answer);
            newAnswer.setQuestionEntity(question);
            question.addAnswer(newAnswer);
            AnswerEntity save = answerEntityRepository.save(newAnswer);
            AnswerDTO answerDTO1 = DTOFactory.getAnswerDTO(save, answerDTO.getQuestionPk());
            answerDTOMapping.put(save.getId(), answerDTO1);
            return answerDTO1;
        }
        return null;
    }

    private void removeAnswer(final AnswerDTO answerDTO) {
        Long entityIdForDTOPk = getEntityIdForDTOPk(answerDTO.getPrimaryKey(), answerDTOMapping);
        answerEntityRepository.deleteById(entityIdForDTOPk);
        answerDTOMapping.remove(entityIdForDTOPk);
    }

    private void removeQuestion(final QuestionDTO questionDTO) {
        Long entityIdForDTOPk = getEntityIdForDTOPk(questionDTO.getPrimaryKey(), questionDTOMapping);
        questionEntityRepository.deleteById(entityIdForDTOPk);
        questionDTOMapping.remove(entityIdForDTOPk);
    }

    private void removeSurvey(final SurveyDTO surveyDTO) {
        Long entityIdForDTOPk = getEntityIdForDTOPk(surveyDTO.getPrimaryKey(), surveyDTOMapping);
        surveyEntityRepository.deleteById(entityIdForDTOPk);
        surveyDTOMapping.remove(entityIdForDTOPk);
    }

    private Long getEntityIdForDTOPk(final Comparable<?> primaryKey, Map<Long, ? extends
            DTOEntityInterface> mapping) {
        if (primaryKey == null) {
            return null;
        }
        Set<? extends Map.Entry<Long, ? extends DTOEntityInterface>> entries = mapping.entrySet();
        for (Map.Entry<Long, ? extends DTOEntityInterface> entry : entries) {
            if (entry.getValue().getPrimaryKey().equals(primaryKey)) {
                return entry.getKey();
            }
        }
        return null;
    }


}
