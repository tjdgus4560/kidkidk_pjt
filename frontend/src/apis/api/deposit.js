import { serverAxios } from '@util/commons';

const server = serverAxios();

const url = '/deposit';

async function getHistory(childId, success, fail) {
    await server.get(`${url}/history/${childId}`).then(success).catch(fail);
}

export { getHistory };
