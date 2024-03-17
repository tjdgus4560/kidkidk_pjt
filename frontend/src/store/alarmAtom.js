import { atom } from "recoil";
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const sseState = atom({
    key : "sse",
    default: null,
    effects_UNSTABLE: [persistAtom],
})

export const lastEventIdState = atom({
    key : "lastEventId",
    default : null,
    effects_UNSTABLE: [persistAtom],
})

export const notificationsState = atom({
    key : "notifications",
    default : [],
    effects_UNSTABLE: [persistAtom],
})

export const childListState = atom({
    key : "childList",
    default : [],
    effects_UNSTABLE: [persistAtom],
})