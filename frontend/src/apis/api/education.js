import { serverAxios } from '@util/commons';

const server = serverAxios();

const url = '/education';

async function getEducation(success, fail) {
    await server.get(`${url}`).then(success).catch(fail);
}

export { getEducation };