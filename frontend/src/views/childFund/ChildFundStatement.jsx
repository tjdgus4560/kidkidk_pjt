import styles from './ChildFundStatement.module.css';
import ChildFundStatementTable from './ChildFundStatementTable.jsx';
import React, { useState, useEffect } from 'react';
import { getFundHistory } from '@api/fund.js';
import { useRecoilValue } from 'recoil';
import { profileInfoState } from '../../store/profileInfoAtom.js';

export default function ChildFundStatement() {
    const profileInfo = useRecoilValue(profileInfoState);
    const childId = profileInfo.profileId;
    const [statementdata, setStatementdata] = useState([]);

    useEffect(() => {
        getFundHistory(
            childId,
            (success) => {
                if (success.data.FundHistory) {
                    setStatementdata(success.data.FundHistory);
                    // console.log(success.data.FundHistory);
                } else {
                    console.log('No getFundHistory data available.');
                }
            },
            (fail) => {
                console.log(fail);
            }
        );
        return () => {
            // console.log('ChildManagement userEffect return');
        };
    }, []);

    return (
        <div className={styles.stateContainer}>
            <div className={styles.stateTitle}>나의 투자 내역</div>
            <div className={styles.scrollContainer}>
                <div className={styles.scrollContent}>
                    {statementdata.length > 0 ? (
                        <ChildFundStatementTable data={statementdata} />
                    ) : (
                        <div className={styles.noContent}>현재 투자 내역이 없습니다.</div>
                    )}
                </div>
            </div>
        </div>
    );
}
