import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { getCookie, setCookie } from '../../apis/util/cookieUtil';
import { getUserInfo } from '../../apis/api/user';
import { useRecoilState } from 'recoil';
import { userInfoState } from '../../store/userInfoAtom';

export default function tokenSave() {
    const navigate = useNavigate();
    const [userInfo, setUserInfo] = useRecoilState(userInfoState);

    async function authRedirect() {
        const access_token = new URL(window.location.href).searchParams.get('access_token');
        const refresh_token = new URL(window.location.href).searchParams.get('refresh_token');
        await setCookie('access_token', access_token);
        await setCookie('refresh_token', refresh_token);

        getUserInfo(
            ({ data }) => {
                setUserInfo(data.userinfo); // 리코일에 불러온 userinfo 저장
            },
            (error) => {
                console.log('error : ', error);
            }
        );
    }

    useEffect(() => {
        authRedirect();
    }, []);

    useEffect(() => {
        console.log('Current user info:', userInfo);
        /**
         * 상태 저장 완료 이후 바로 리다이렉트
         */
        if (userInfo) {
            navigate('/profile');
        }
    }, [userInfo]);

    return (
        <div>
            <p>토큰저장</p>
        </div>
    );
}
