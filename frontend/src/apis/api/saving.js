import { serverAxios } from '@util/commons';

const server = serverAxios();

const url = '/saving';

async function getSavingHistory(childId, success, fail) {
    await server.get(`${url}/history/${childId}`).then(success).catch(fail);
}

async function getSaving(childId, success, fail) {
    await server.get(`${url}/info/${childId}`).then(success).catch(fail);
}

async function postSaving(Saving, success, fail) {
    await server.post(`${url}/create`, Saving).then(success).catch(fail);
}

export { getSaving, getSavingHistory, postSaving };
