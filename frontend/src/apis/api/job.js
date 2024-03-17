import { serverAxios } from '@util/commons';
import { func } from 'prop-types';

const server = serverAxios();

const url = '/job';

// 현재 로그인 중인 자식의 id 값을 가져오는 API가 필요할듯

async function getJob(childId, success, fail) {
    await server.get(`${url}/retrieve/${childId}`).then(success).catch(fail);
}

async function getJobReservation(childId, success, fail) {
    await server.get(`${url}/retrieve/reservation/${childId}`).then(success).catch(fail);
}

async function deleteJob(childId, success, fail) {
    await server.delete(`${url}/delete/${childId}`).then(success).catch(fail);
}

async function deleteJobReservation(childId, success, fail) {
    await server.delete(`${url}/reservation/delete/${childId}`).then(success).catch(fail);
}

async function createJob(createJobData, success, fail) {
    await server.post(`${url}/create`, JSON.stringify(createJobData)).then(success).catch(fail);
}

async function createJobReservation(createJobReservationData, success, fail) {
    await server.post(`${url}/reservation/create`, JSON.stringify(createJobReservationData)).then(success).catch(fail);
}

async function modifyJobReservation(modifyJobReservationData, success, fail) {
    await server.put(`${url}/reservation/modify`, JSON.stringify(modifyJobReservationData)).then(success).catch(fail);
}

export {
    getJob,
    getJobReservation,
    deleteJob,
    deleteJobReservation,
    createJob,
    createJobReservation,
    modifyJobReservation,
};
