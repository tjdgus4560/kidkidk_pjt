import { serverAxios } from '@util/commons';

const server = serverAxios();

const url = '/users';

async function getChild(childId, success, fail) {
    await server.get(`${url}/profile/getchild/${childId}`).then(success).catch(fail);
}

// 아이 주머니에서 투자 주머니로 송금
async function createChargeFund(chargeFund, success, fail) {
    await server.post(`${url}/profile/transfer`, chargeFund).then(success).catch(fail);
}

// 아이 테이블 주머니, 투자 주머니 잔고 수정
async function updateChild(updateChildMoney, success, fail) {
    await server.put(`${url}/profile/child/update`, updateChildMoney).then(success).catch(fail);
}

export { getChild, createChargeFund, updateChild };
