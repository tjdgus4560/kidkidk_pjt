import { recoilPersist } from 'recoil-persist';
import { atom } from 'recoil';

const { persistAtom } = recoilPersist();

export const userInfoState = atom({
    key: 'userInfo',
    default: { userId: '', accessToken: '', email: '' },
    effects_UNSTABLE: [persistAtom],
});

// recoil-persist를 사용해 상태를 로컬 스토리지에 저장
export const childState = atom({
    key: 'childState', // 상태를 식별할 때 사용할 고유키
    default: {}, // 디폴트 값 설정
    effects_UNSTABLE: [persistAtom],
});
