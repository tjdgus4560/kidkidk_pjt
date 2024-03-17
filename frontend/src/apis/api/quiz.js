import { serverAxios } from '@util/commons';

const server = serverAxios();

const url = '/quiz';

async function getQuiz(childId, success, fail) {
    await server.get(`${url}`).then(success).catch(fail);
}

export { getQuiz };