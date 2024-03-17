import styles from './WelcomePageSecond.module.css';

import welcomeJob from '@images/welcomeJob.jpg';
import welcomeFund from '@images/welcomeFund.png';
import welcomeSaving from '@images/welcomeSaving.jpg';
import welcomeEdu from '@images/welcomeEdu.png';

export default function WelcomePageFirst() {
    const contentJob = `직업에 
알맞은 업무를 
성취하고 
아이가 스스로
용돈을 벌도록
해주세요!`;
    const contentSaving = `적금을
해보면서
아이에게
적금의
중요성과
금리 개념을
일깨워주세요!`;
    const contentFund = `간단한
투자를 통해
아이에게
주식 시장을
체험할 수
있도록 해주세요!`;
    const contentEdu = `아이가
경제와 더
친숙해질 수
있도록
공부방 콘텐츠를
제공하고 있어요!`;
    return (
        <>
            <div className={styles.welcomePageSecondContainer}>
                <div className={styles.welcomeJob}>
                    <p>아이에게 직업을 만들어주세요</p>
                    <div className={styles.welcomeImgContainer}>
                        <img src={welcomeJob} alt="직업 설명" />
                        <p>{contentJob}</p>
                    </div>
                </div>
                <div className={styles.welcomeSaving}>
                    <p>아이에게 적금을 알려주세요</p>
                    <div className={styles.welcomeImgContainer}>
                        <img src={welcomeSaving} alt="직업 설명" />
                        <p>{contentSaving}</p>
                    </div>
                </div>
                <div className={styles.welcomeFund}>
                    <p>아이에게 투자를 알려주세요</p>
                    <div className={styles.welcomeImgContainer}>
                        <img src={welcomeFund} alt="직업 설명" />
                        <p>{contentFund}</p>
                    </div>
                </div>
                <div className={styles.welcomeEdu}>
                    <p>아이에게 맞춤 경제 교육을 시작하세요</p>
                    <div className={styles.welcomeImgContainer}>
                        <img src={welcomeEdu} alt="직업 설명" />
                        <p>{contentEdu}</p>
                    </div>
                </div>
            </div>
        </>
    );
}
