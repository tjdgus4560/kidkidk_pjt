import { useState } from 'react';

import styles from './ParentFundSaving.module.css';
import ParentFund from './ParentFund';
import ParentSaving from './ParentSaving';

export default function ParentFundSaving() {
    const [left, setLeft] = useState(0);
    const [selectedToggle, setSelectedToggle] = useState(0);

    function Toggle({ num, title }) {
        const isSelected = selectedToggle === num;

        const handleToggleClick = (num) => {
            setSelectedToggle(num);

            if (num === 0) {
                setLeft(0);
            } else if (num === 1) {
                setLeft(190);
            }
        };

        const textClass = isSelected ? styles.textselected : styles.textunselected;
        const lineClass = isSelected ? styles.lineselected : styles.lineunselected;

        return (
            <div className={`${textClass}`} onClick={() => handleToggleClick(num)}>
                {title}
                <div className={`${lineClass}`} />
            </div>
        );
    }
    return (
        <div className={styles.ParentFundSavingContainer}>
            <div className={styles.ParentFundSavingContainerStart}>
                <div className={styles.toggle}>
                    <Toggle num={0} title={'투자'} />
                    <Toggle num={1} title={'적금'} />
                    <div className={styles.circle} style={{ left: `${left}px` }}></div>
                </div>
                <div className={styles.content}>
                    {selectedToggle === 0 && <ParentFund />}
                    {selectedToggle === 1 && <ParentSaving />}
                </div>
            </div>
        </div>
    );
}
