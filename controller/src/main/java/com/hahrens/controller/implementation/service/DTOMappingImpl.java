package com.hahrens.controller.implementation.service;

import com.hahrens.backend.model.AnswerEntity;
import com.hahrens.backend.model.QuestionEntity;
import com.hahrens.backend.model.SurveyEntity;
import com.hahrens.backend.repository.AnswerEntityRepository;
import com.hahrens.backend.repository.QuestionEntityRepository;
import com.hahrens.backend.repository.SurveyEntityRepository;
import com.hahrens.controller.api.model.dto.AnswerDTO;
import com.hahrens.controller.api.model.dto.DTOEntityInterface;
import com.hahrens.controller.api.model.dto.QuestionDTO;
import com.hahrens.controller.api.model.dto.SurveyDTO;
import com.hahrens.controller.api.service.DTOMapping;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
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
        init();
    }

    //public for texting.
    public void init() {
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
    public void persistDTOs(final Collection<? extends DTOEntityInterface> DTOs, final Class<? extends DTOEntityInterface> clazz) {
        Collection<? extends DTOEntityInterface> cachedDTOs = null;
        if (clazz.equals(AnswerDTO.class)) {
            cachedDTOs = answerDTOMapping.values();
        }
        if (clazz.equals(QuestionDTO.class)) {
            cachedDTOs = questionDTOMapping.values();
        }
        if (clazz.equals(SurveyDTO.class)) {
            cachedDTOs = surveyDTOMapping.values();
        }
        if (cachedDTOs != null) {
            //delete something
            if (DTOs.size() < cachedDTOs.size()) {
                List<? extends Comparable<?>> toDelete = cachedDTOs.stream().map(DTOEntityInterface::getPrimaryKey).collect(Collectors.toList());
                List<? extends Comparable<?>> dtos = DTOs.stream().map(DTOEntityInterface::getPrimaryKey).toList();
                toDelete.removeIf(dtos::contains);
                for (DTOEntityInterface cachedDTO : cachedDTOs) {
                    if (toDelete.contains(cachedDTO.getPrimaryKey())) {
                        removeEntity(cachedDTO);
                    }
                }
            }
            //there is something to create.
            if (DTOs.size() > cachedDTOs.size()) {
                Collection<DTOEntityInterface> toCreate = new ArrayList<>();
                for (DTOEntityInterface dto : DTOs) {
                    if (!cachedDTOs.contains(dto)) {
                        toCreate.add(dto);
                    }
                }
                DTOs.removeIf(toCreate::contains);
                toCreate.forEach(this::addEntity);
            }
            DTOs.forEach(this::updateEntity);
            answerDTOMapping.clear();
        }

    }

    private void removeEntity(final DTOEntityInterface dtoEntityInterface) {
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

    private void addEntity(final DTOEntityInterface dtoEntityInterface) {
        if (dtoEntityInterface instanceof AnswerDTO answerDTO) {
            addAnswer(answerDTO);
        }
        if (dtoEntityInterface instanceof QuestionDTO questionDTO) {
            addQuestion(questionDTO);
        }
        if (dtoEntityInterface instanceof SurveyDTO surveyDTO) {
            surveyEntityRepository.save(SurveyEntity.builder().name(surveyDTO.getName()).description(surveyDTO.getDescription()).build());
        }
    }

    private void updateEntity(final DTOEntityInterface dtoEntityInterface) {
        if (dtoEntityInterface instanceof AnswerDTO answerDTO) {
            Long entityIdForDTOPk = getEntityIdForDTOPk(answerDTO.getPrimaryKey(), answerDTOMapping);
            AnswerEntity answerEntity = answerEntityRepository.findById(entityIdForDTOPk).orElse(null);
            if (answerEntity != null) {
                answerEntity.setAnswerText(answerDTO.getAnswerText());
            }
        }
        if (dtoEntityInterface instanceof QuestionDTO questionDTO) {
            Long entityIdForDTOPk = getEntityIdForDTOPk(questionDTO.getPrimaryKey(), answerDTOMapping);
            QuestionEntity questionEntity = questionEntityRepository.findById(entityIdForDTOPk).orElse(null);
            if (questionEntity != null) {
                questionEntity.setQuestion(questionDTO.getQuestion());
                questionEntity.setDescription(questionEntity.getDescription());
                questionEntity.setName(questionDTO.getName());
            }
        }
        if (dtoEntityInterface instanceof SurveyDTO surveyDTO) {
            Long entityIdForDTOPk = getEntityIdForDTOPk(surveyDTO.getPrimaryKey(), answerDTOMapping);
            SurveyEntity surveyEntity = surveyEntityRepository.findById(entityIdForDTOPk).orElse(null);
            if (surveyEntity != null) {
                surveyEntity.setDescription(surveyDTO.getDescription());
                surveyEntity.setName(surveyDTO.getName());
            }
        }
    }


    private void addQuestion(final QuestionDTO questionDTO) {
        if (questionDTO == null) {
            return;
        }
        Long surveyPk = getEntityIdForDTOPk(questionDTO.getSurveyPk(), surveyDTOMapping);
        SurveyEntity surveyEntity = surveyEntityRepository.findById(surveyPk).orElse(null);
        if (surveyEntity != null) {
            QuestionEntity question = QuestionEntity.builder() //
                    .question(questionDTO.getQuestion()) //
                    .description(questionDTO.getDescription()) //
                    .name(questionDTO.getName()) //
                    .surveyEntity(surveyEntity).build();
            QuestionEntity newQuestion = questionEntityRepository.save(question);
            newQuestion.setSurveyEntity(surveyEntity);
            surveyEntity.addQuestion(question);
        }
    }

    private void addAnswer(final AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return;
        }
        Long questionId = getEntityIdForDTOPk(answerDTO.getQuestionPk(), questionDTOMapping);
        QuestionEntity question = questionEntityRepository.findById(questionId).orElse(null);
        if (question != null) {
            AnswerEntity answer = AnswerEntity.builder().answerText(answerDTO.getAnswerText()).questionEntity(question).build();
            AnswerEntity newAnswer = answerEntityRepository.save(answer);
            newAnswer.setQuestionEntity(question);
            question.addAnswer(newAnswer);
        }
    }

    private void removeAnswer(final AnswerDTO answerDTO) {
        Long entityIdForDTOPk = getEntityIdForDTOPk(answerDTO.getPrimaryKey(), answerDTOMapping);
        answerEntityRepository.deleteById(entityIdForDTOPk);
    }

    private void removeQuestion(final QuestionDTO questionDTO) {
        Long entityIdForDTOPk = getEntityIdForDTOPk(questionDTO.getPrimaryKey(), questionDTOMapping);
        questionEntityRepository.deleteById(entityIdForDTOPk);
    }

    private void removeSurvey(final SurveyDTO surveyDTO) {
        Long entityIdForDTOPk = getEntityIdForDTOPk(surveyDTO.getPrimaryKey(), answerDTOMapping);
        surveyEntityRepository.deleteById(entityIdForDTOPk);
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
