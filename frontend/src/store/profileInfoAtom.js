import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const profileInfoState = atom({
    key: 'profileInfo',
    default: { profileId: '', nickname: '', type: '', coin: '', fundMoney: '', parentId: '', childId: '' },
    effects_UNSTABLE: [persistAtom],
});

export const parentProfileState = atom({
    key: 'parentProfile',
    default: {nickname : ''},
    effects_UNSTABLE: [persistAtom],
})
