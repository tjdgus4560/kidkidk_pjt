import { recoilPersist } from 'recoil-persist';
import { atom } from 'recoil';

const { persistAtom } = recoilPersist();

export const getJobData = atom({
    key: 'job',
    default: { name: '', task: '', taskAmount: '', wage: '', doneCount: '', childId: '' },
    effects_UNSTABLE: [persistAtom],
});

export const getJobReservationData = atom({
    key: 'reservationJob',
    default: { state: '', name: '', task: '', taskAmount: '', wage: '', childId: '' },
    effects_UNSTABLE: [persistAtom],
});
