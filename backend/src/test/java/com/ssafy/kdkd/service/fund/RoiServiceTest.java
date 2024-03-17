package com.ssafy.kdkd.service.fund;

import com.ssafy.kdkd.domain.entity.fund.Roi;
import com.ssafy.kdkd.domain.entity.user.Child;
import com.ssafy.kdkd.repository.fund.RoiRepository;
import com.ssafy.kdkd.service.user.ChildService;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class RoiServiceTest {

    @Autowired ChildService childService;
    @Autowired RoiService roiService;
    @Autowired RoiRepository roiRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("투자성공률 업데이트(최초)")
    @Transactional
    public void 투자성공률_업데이트_최초() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        Optional<Roi> findRoi = roiRepository.findById(childId);
        roiService.updateRoi(findRoi, 0, child);
        em.flush();
        em.clear();

        //then
        Roi existingRoi = roiRepository.findById(childId).get();
        boolean result = existingRoi.getSuccess() == 0 && existingRoi.getCount() == 1 && existingRoi.getId() == childId;

        assertTrue("roi가 생성되어야 합니다.", result);
    }

    @Test
    @DisplayName("투자성공률 업데이트(수정)")
    @Transactional
    public void 투자성공률_업데이트_수정() throws Exception {
        //given
        Long childId = 2L;
        Child child = childService.findChild(childId).get();

        //when
        Optional<Roi> findRoi = roiRepository.findById(childId);
        roiService.updateRoi(findRoi, 1, child);
        em.flush();
        em.clear();

        //then
        Roi existingRoi = roiRepository.findById(childId).get();
        boolean result = existingRoi.getSuccess() == 4 && existingRoi.getCount() == 4 && existingRoi.getId() == childId;

        assertTrue("roi가 업데이트 되어야 합니다.", result);
    }

}