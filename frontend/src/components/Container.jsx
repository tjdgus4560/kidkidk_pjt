import classNames from "classnames";
import styles from "./Container.module.css";

/* classNames는 CSS 클래스를 조건부로 설정할 때 매우 유용한 라이브러리
    설치 -> $ npm install classnames
    import -> import classNames from 'classnames';
 */

function Container({ className, children }) {
  return (
    <div className={classNames(styles.container, className)}>{children}</div>
  );
}

export default Container;
