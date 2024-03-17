import { serverAxios } from '@util/commons';

const server = serverAxios();

const url = '/fund';

// 투자 항목 아이 선택
async function createSubmit(fundSubmit, success, fail) {
    await server.post(`${url}/child/submit`, fundSubmit).then(success).catch(fail);
}

async function createFund(fundResrvation, success, fail) {
    await server.post(`${url}/create`, fundResrvation).then(success).catch(fail);
}

async function createFundReservation(fundResrvation, success, fail) {
    await server.post(`${url}/reservation/create`, fundResrvation).then(success).catch(fail);
}

// 투자 항목 조회
async function getFund(childId, success, fail) {
    await server.get(`${url}/confirm/${childId}`).then(success).catch(fail);
}

// 투자 내역 조회
async function getFundHistory(childId, success, fail) {
    await server.get(`${url}/history/confirm/${childId}`).then(success).catch(fail);
}

async function getFundReservation(childId, success, fail) {
    await server.get(`${url}/reservation/confirm/${childId}`).then(success).catch(fail);
}

// 투자 성공률 조회
async function getRoi(childId, success, fail) {
    await server.get(`${url}/roi/${childId}`).then(success).catch(fail);
}

// 투자 상태 조회
async function getStatus(childId, success, fail) {
    await server.get(`${url}/status/confirm/${childId}`).then(success).catch(fail);
}

async function updateFundReservation(fundResrvation, success, fail) {
    await server.put(`${url}/reservation/modify`, fundResrvation).then(success).catch(fail);
}

async function deleteFund(childId, success, fail) {
    await server.delete(`${url}/delete/${childId}`).then(success).catch(fail);
}

async function deleteFundReservation(childId, success, fail) {
    await server.delete(`${url}/reservation/delete/${childId}`).then(success).catch(fail);
}

// 투자 뉴스 조회
async function getFundNews(childId, success, fail) {
    await server.get(`${url}/news/${childId}`).then(success).catch(fail);
}

// 투자 뉴스 : 투자 베팅에 대한 힌트 등록
async function createFundNews(newsData, success, fail) {
    await server.post(`${url}/news`, JSON.stringify(newsData)).then(success).catch(fail);
}

// 투자 뉴스에 대한 해당 당일의 정답 등록
async function createFundAnswer(answerData, success, fail) {
    await server.post(`${url}/parent/submit`, JSON.stringify(answerData)).then(success).catch(fail);
}

async function transferToCoin(coinIn, success, fail) {
    await server.post(`${url}/transfer`, coinIn).then(success).catch(fail);
}

export {
    createFund,
    createFundReservation,
    updateFundReservation,
    deleteFund,
    deleteFundReservation,
    getFund,
    getFundHistory,
    getFundReservation,
    getRoi,
    getStatus,
    getFundNews,
    createFundNews,
    createFundAnswer,
    transferToCoin,
    createSubmit,
};
