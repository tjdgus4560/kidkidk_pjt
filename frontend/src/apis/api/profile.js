import { serverAxios } from '../util/commons';

const server = serverAxios();

const url = '/users/profile';

async function profileCreate(user, success, fail) {
    // console.log(user)
    await server.post(`${url}/create`, user).then(success).catch(fail);
}

async function profileLogin(loginProfile, success, fail) {
    await server.post(`${url}/login`, loginProfile).then(success).catch(fail);
}

async function profileSelectAll(userId, success, fail) {
    // console.log(user)
    await server.get(`${url}/selectAll/${userId}`).then(success).catch(fail);
}

async function profileUpdate(updateUser, success, fail) {
    // console.log(updateUser)
    await server.put(`${url}/update`, updateUser).then(success).catch(fail);
}

async function profileDelete(profile, success, fail) {
    // console.log(profile)
    await server.delete(`${url}/delete/${profile.profileId}`).then(success).catch(fail);
}

async function transferToFundMoney(coinIn, success, fail) {
    await server.post(`${url}/transfer`, coinIn).then(success).catch(fail);
}

async function getChildList(userId, success, fail) {
    await server.get(`${url}/childlist/${userId}`).then(success).catch(fail);
}

async function getChild(profile, success, fail) {
    await server.get(`${url}/getchild/${profile.profileId}`).then(success).catch(fail);
}

export { profileCreate, profileLogin, profileSelectAll, profileUpdate, profileDelete, transferToFundMoney, getChild ,getChildList,};
