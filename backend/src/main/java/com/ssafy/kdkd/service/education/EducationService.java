package com.ssafy.kdkd.service.education;

import com.ssafy.kdkd.domain.entity.education.Education;
import com.ssafy.kdkd.repository.education.EducationRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;

    public List<Education> findAll() {
        return educationRepository.findAll();
    }

}
