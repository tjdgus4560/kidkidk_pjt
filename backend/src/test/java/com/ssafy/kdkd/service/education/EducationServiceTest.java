package com.ssafy.kdkd.service.education;

import com.ssafy.kdkd.domain.common.Category;
import com.ssafy.kdkd.domain.entity.education.Education;

import jakarta.persistence.EntityManager;

import static com.ssafy.kdkd.domain.entity.education.Education.createEducation;
import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class EducationServiceTest {

    @Autowired EducationService educationService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("교육 생성 & 조회")
    @Transactional
    public void 교육_생성_조회() throws Exception {
        //given
        int size = 100;

        for (int i = 0; i < size; i++) {
            createEducationList();
        }
        em.flush();

        //when
        List<Education> educations = educationService.findAll();
        int findCount = educations.size();

        //then
        System.out.println("===== 교육 출력 =====");
        for (Education education : educations) {
            System.out.println("education_id: " + education.getId() +
                " category: " + education.getCategory() +
                " content: " + education.getContent());
        }
        System.out.println("===== 교육 출력 =====");
        assertEquals("조회된 교육 자료의 갯수는 100개 입니다.", size, findCount);
    }

    private Education createEducationList() {
        Category category = Category.values()[ThreadLocalRandom.current().nextInt(Category.values().length)];
        String content = "비트코인의 가치는 전통적인 통화와 달리 중앙 정부나 금융 기관에 의해 직접적으로 제어되지 않습니다. 대신, 여러 요인에 의해 결정되며, 이는 공급과 수요의 법칙, 시장 참여자들의 심리, 전 세계적인 경제 상황, 기술적 변화, 정치적 사건 등 다양한 외부 요인들에 의해 영향을 받습니다."
            + " "
            + "비트코인의 공급은 알고리즘에 의해 사전에 정의되어 있어, 총 공급량은 2,100만 개로 제한되어 있습니다. 이는 채굴 과정을 통해 새로운 비트코인이 시장에 공급되며, 시간이 지남에 따라 새로운 비트코인을 채굴하기 위한 난이도는 증가하고 보상은 감소합니다. 이러한 제한된 공급량은 인플레이션에 대한 저항력을 제공하며, 시간이 지남에 따라 가치가 증가할 가능성을 내포하고 있습니다."
            + " "
            + "한편, 비트코인의 수요는 투자자들의 관심, 사용 가능한 거래소의 수, 비트코인을 받아들이는 상점과 서비스 제공 업체의 수, 그리고 전반적인 사용자 기반의 성장에 의해 영향을 받습니다. 특히, 금융 위기, 화폐 가치 하락, 정부 규제 변경과 같은 거시 경제적 사건은 비트코인을 대체 투자 수단으로 보는 시각을 강화시켜 수요를 증가시킬 수 있습니다."
            + " "
            + "또한, 비트코인의 가치는 기술적 진보와 네트워크의 보안성에도 영향을 받습니다. 비트코인 네트워크를 지원하는 블록체인 기술의 발전은 거래의 효율성과 보안성을 향상시켜, 비트코인에 대한 신뢰도를 높이고 궁극적으로 가치 상승을 불러올 수 있습니다. 반면, 해킹 사건이나 보안 취약점이 발견될 경우, 이는 비트코인의 가치에 부정적인 영향을 미칠 수 있습니다."
            + " "
            + "정리하자면, 비트코인의 가치는 복잡한 상호 작용의 결과로, 다양한 경제적, 기술적, 사회적 요인들에 의해 결정됩니다. 이로 인해 비트코인 시장은 매우 변동성이 크며, 투자자들은 이러한 요인들을 면밀히 모니터링하며 투자 결정을 내려야 합니다.";
        Education education = createEducation(category, content);
        em.persist(education);
        return education;
    }

}
