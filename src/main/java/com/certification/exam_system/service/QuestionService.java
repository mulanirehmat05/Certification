package com.certification.exam_system.service;

import com.certification.exam_system.dto.exam.QuestionOptionRequest;
import com.certification.exam_system.dto.exam.QuestionOptionResponse;
import com.certification.exam_system.dto.exam.QuestionRequest;
import com.certification.exam_system.dto.exam.QuestionResponse;
import com.certification.exam_system.entity.Certification;
import com.certification.exam_system.entity.Question;
import com.certification.exam_system.entity.QuestionOption;
import com.certification.exam_system.entity.QuestionType;
import com.certification.exam_system.repository.CertificationRepository;
import com.certification.exam_system.repository.QuestionOptionRepository;
import com.certification.exam_system.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final CertificationRepository certificationRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            QuestionOptionRepository questionOptionRepository,
            CertificationRepository certificationRepository
    ) {
        this.questionRepository = questionRepository;
        this.questionOptionRepository = questionOptionRepository;
        this.certificationRepository = certificationRepository;
    }

    // =========================================================
    // CREATE QUESTION
    // =========================================================

    @Transactional
    public QuestionResponse createQuestion(
            QuestionRequest request
    ) {

        // Validate certification
        Certification certification =
                certificationRepository
                        .findById(request.getCertificationId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification not found"
                                )
                        );

        // Validate question/options
        validateOptions(request);

        // Create question
        Question question = new Question();

        question.setCertification(certification);
        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setDifficulty(request.getDifficulty());
        question.setMarks(request.getMarks());
        question.setActive(request.getActive());

        // Save question first so it gets an ID
        Question savedQuestion =
                questionRepository.save(question);

        // Save options
        for (QuestionOptionRequest optionRequest
                : request.getOptions()) {

            QuestionOption option =
                    new QuestionOption();

            option.setQuestion(savedQuestion);

            option.setOptionLabel(
                    normalizeLabel(
                            optionRequest.getOptionLabel()
                    )
            );

            option.setOptionText(
                    optionRequest.getOptionText()
            );

            option.setCorrect(
                    Boolean.TRUE.equals(
                            optionRequest.getCorrect()
                    )
            );

            questionOptionRepository.save(option);
        }

        return mapToResponse(savedQuestion);
    }


    // =========================================================
    // GET ALL QUESTIONS
    // =========================================================

    @Transactional(readOnly = true)
    public List<QuestionResponse> getAllQuestions() {

        return questionRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // GET QUESTION BY ID
    // =========================================================

    @Transactional(readOnly = true)
    public QuestionResponse getQuestionById(
            Long id
    ) {

        Question question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Question not found"
                                )
                        );

        return mapToResponse(question);
    }


    // =========================================================
    // GET QUESTIONS BY CERTIFICATION
    // =========================================================

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionsByCertification(
            Long certificationId
    ) {

        if (!certificationRepository.existsById(
                certificationId
        )) {

            throw new IllegalArgumentException(
                    "Certification not found"
            );
        }

        return questionRepository
                .findByCertificationId(certificationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // GET ACTIVE QUESTIONS BY CERTIFICATION
    // =========================================================

    @Transactional(readOnly = true)
    public List<QuestionResponse> getActiveQuestionsByCertification(
            Long certificationId
    ) {

        if (!certificationRepository.existsById(
                certificationId
        )) {

            throw new IllegalArgumentException(
                    "Certification not found"
            );
        }

        return questionRepository
                .findByCertificationIdAndActiveTrue(
                        certificationId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // GET QUESTIONS BY DIFFICULTY
    // =========================================================

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionsByDifficulty(
            Long certificationId,
            String difficulty
    ) {

        if (!certificationRepository.existsById(
                certificationId
        )) {

            throw new IllegalArgumentException(
                    "Certification not found"
            );
        }

        return questionRepository
                .findByCertificationIdAndDifficulty(
                        certificationId,
                        com.certification.exam_system.entity
                                .QuestionDifficulty
                                .valueOf(
                                        difficulty.toUpperCase()
                                )
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // UPDATE QUESTION
    // =========================================================

    @Transactional
    public QuestionResponse updateQuestion(
            Long id,
            QuestionRequest request
    ) {

        // Find existing question
        Question question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Question not found"
                                )
                        );

        // Validate certification
        Certification certification =
                certificationRepository
                        .findById(request.getCertificationId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Certification not found"
                                )
                        );

        // Validate question/options
        validateOptions(request);

        // Update question fields
        question.setCertification(certification);
        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setDifficulty(request.getDifficulty());
        question.setMarks(request.getMarks());
        question.setActive(request.getActive());

        /*
         * IMPORTANT:
         *
         * First delete all old options.
         */
        questionOptionRepository.deleteByQuestionId(id);

        /*
         * IMPORTANT:
         *
         * Force Hibernate to execute the DELETE SQL
         * before inserting A/B/C/D again.
         *
         * Without this, Hibernate may attempt the INSERT
         * while the old A/B/C/D records still exist in MySQL.
         */
        questionOptionRepository.flush();

        /*
         * Insert the new options.
         */
        for (QuestionOptionRequest optionRequest
                : request.getOptions()) {

            QuestionOption option =
                    new QuestionOption();

            option.setQuestion(question);

            option.setOptionLabel(
                    normalizeLabel(
                            optionRequest.getOptionLabel()
                    )
            );

            option.setOptionText(
                    optionRequest.getOptionText()
            );

            option.setCorrect(
                    Boolean.TRUE.equals(
                            optionRequest.getCorrect()
                    )
            );

            questionOptionRepository.save(option);
        }

        /*
         * Save updated question.
         */
        Question updatedQuestion =
                questionRepository.save(question);

        return mapToResponse(updatedQuestion);
    }


    // =========================================================
    // DELETE QUESTION
    // =========================================================

    @Transactional
    public void deleteQuestion(
            Long id
    ) {

        Question question =
                questionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Question not found"
                                )
                        );

        /*
         * Delete options first because QuestionOption
         * has a foreign key to Question.
         */
        questionOptionRepository.deleteByQuestionId(id);

        // Make sure DELETE options executes first
        questionOptionRepository.flush();

        // Delete question
        questionRepository.delete(question);
    }


    // =========================================================
    // VALIDATE OPTIONS
    // =========================================================

    private void validateOptions(
            QuestionRequest request
    ) {

        // Currently only MCQ is supported
        if (request.getQuestionType()
                != QuestionType.MCQ) {

            throw new IllegalArgumentException(
                    "Only MCQ questions are currently supported"
            );
        }

        // MCQ must have exactly 4 options
        if (request.getOptions() == null
                || request.getOptions().size() != 4) {

            throw new IllegalArgumentException(
                    "MCQ question must have exactly 4 options"
            );
        }

        // Exactly one option must be correct
        long correctOptions =
                request.getOptions()
                        .stream()
                        .filter(option ->
                                Boolean.TRUE.equals(
                                        option.getCorrect()
                                )
                        )
                        .count();

        if (correctOptions != 1) {

            throw new IllegalArgumentException(
                    "MCQ question must have exactly one correct option"
            );
        }

        // Option labels must be unique
        long uniqueLabels =
                request.getOptions()
                        .stream()
                        .map(option ->
                                normalizeLabel(
                                        option.getOptionLabel()
                                )
                        )
                        .distinct()
                        .count();

        if (uniqueLabels != 4) {

            throw new IllegalArgumentException(
                    "MCQ option labels must be unique"
            );
        }

        // Make sure labels are not blank
        boolean hasBlankLabel =
                request.getOptions()
                        .stream()
                        .anyMatch(option ->
                                option.getOptionLabel() == null
                                        || option.getOptionLabel()
                                        .trim()
                                        .isEmpty()
                        );

        if (hasBlankLabel) {

            throw new IllegalArgumentException(
                    "MCQ option labels cannot be blank"
            );
        }
    }


    // =========================================================
    // NORMALIZE OPTION LABEL
    // =========================================================

    private String normalizeLabel(
            String label
    ) {

        if (label == null) {
            throw new IllegalArgumentException(
                    "Option label cannot be null"
            );
        }

        return label
                .trim()
                .toUpperCase();
    }


    // =========================================================
    // MAP ENTITY TO RESPONSE
    // =========================================================

    private QuestionResponse mapToResponse(
            Question question
    ) {

        Certification certification =
                question.getCertification();

        List<QuestionOptionResponse> options =
                questionOptionRepository
                        .findByQuestionId(
                                question.getId()
                        )
                        .stream()
                        .map(option ->
                                new QuestionOptionResponse(
                                        option.getId(),
                                        option.getOptionLabel(),
                                        option.getOptionText()
                                )
                        )
                        .toList();

        return new QuestionResponse(
                question.getId(),
                certification.getId(),
                certification.getCode(),
                certification.getName(),
                question.getQuestionText(),
                question.getQuestionType(),
                question.getDifficulty(),
                question.getMarks(),
                question.getActive(),
                options,
                question.getCreatedAt(),
                question.getUpdatedAt()
        );
    }
}

