import { recoilPersist } from 'recoil-persist';
import { atom } from 'recoil';

const { persistAtom } = recoilPersist();

export const getChildIds = atom({
    key: 'childIds',
    default: [], // 객체 배열 { childId: '', nickName: '' },{ childId: '', nickName: '' }...
    effects_UNSTABLE: [persistAtom],
});

export const childIdAtom = atom({
    key: 'parentChildId',
    default: null,
    effects_UNSTABLE: [persistAtom],
});

export const childNickNameAtom = atom({
    key: 'parentChildNickName',
    default: null,
    effects_UNSTABLE: [persistAtom],
});
