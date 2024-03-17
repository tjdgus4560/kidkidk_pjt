import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom';

import ParentNav from '../components/ParentNav.jsx';
import ParentMain from '../views/parent/ParentMain.jsx';
import ParentJob from '../views/parent/ParentJob.jsx';
import ParentFundSaving from '../views/parent/ParentFundSaving.jsx';

import ChildNav from '../components/ChildNav.jsx';
import ChildMain from '../views/childMain/ChildMain.jsx';
import ChildRefund from '../views/childMain/ChildRefund.jsx';
import ChildFund from '../views/childFund/ChildFund.jsx';
import ChildSaving from '../views/childSaving/ChildSaving.jsx';
import ChildEdu from '../views/childEdu/ChildEdu.jsx';

import Welcome from '../views/welcome/Welcome.jsx';
import Profile from '../views/profile/Profile.jsx';

import TokenSave from '../views/auth/tokenSave.jsx';

/*
    로그인 여부에 따라 달라져야...
    로그인 되어있으면 해당 프로필의 메인페이지로
    로그인 되어있지 않으면 환영 페이지로

    각 유저에 따라 페이지 렌더링이 동적 렌더링 되어야... -> {users}/parent...로?
    아니면 부모는 아이페이지 권한을 막는 식으로?
*/

const isLoggedIn = false; // 로그인 여부에 따라 조건 설정

const router = createBrowserRouter([
    {
        path: '/',
        element: isLoggedIn ? <Navigate to="/parent" /> : <Welcome />,
    },
    {
        path: '/welcome',
        element: <Welcome />,
    },
    {
        path: '/tokensave',
        element: <TokenSave />,
    },
    {
        path: '/profile',
        element: <Profile />,
    },
    {
        path: '/parent',
        element: <ParentNav />,
        children: [
            {
                path: '/parent',
                element: <ParentMain />,
            },
            {
                path: '/parent/main',
                element: <ParentMain />,
            },
            {
                path: '/parent/job',
                element: <ParentJob />,
            },
            {
                path: '/parent/fundsaving',
                element: <ParentFundSaving />,
            },
        ],
    },
    {
        path: '/child',
        element: <ChildNav />,
        children: [
            {
                path: '/child',
                element: <ChildMain />,
            },
            {
                path: '/child/refund',
                element: <ChildRefund />,
            },
            {
                path: '/child/main',
                element: <ChildMain />,
            },
            {
                path: '/child/fund',
                element: <ChildFund />,
            },
            {
                path: '/child/saving',
                element: <ChildSaving />,
            },
            {
                path: '/child/edu',
                element: <ChildEdu />,
            },
        ],
    },
]);

function App() {
    return (
        <>
            <RouterProvider router={router} />
        </>
    );
}

export default App;
