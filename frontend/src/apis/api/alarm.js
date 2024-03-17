import axios from "axios";
import { serverAxios } from '@util/commons';

const server = serverAxios();

const url = '/alarm';

async function sendAlarm(subId, pubName, title, content, require, childId, amount) {
    const alarm = {
        subId : subId,
        pubName : pubName,
        title : title,
        content : content,
        require : require,
        childId : childId,
        amount : amount,
    }
    await axios.post('https://notification.silvstone.xyz/publish', alarm)
        .then(() => {
            console.log(alarm);
        })
        .catch(()=>{
            console.log("Sending Failed")
        });
}

async function jobDone(childId, success, fail) {
    const dto = {
        childId : childId,
    }
    await server.put(`${url}/job`, dto).then(success).catch(fail);
}

async function acceptExchange(childId, amount, success, fail){
    const dto = {
        amount : amount,
        childId : childId,
    }
    await server.put(`${url}/exchange`, dto).then(success).catch(fail);
}

export { sendAlarm, jobDone, acceptExchange};
