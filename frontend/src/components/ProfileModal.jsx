import Modal from 'react-modal';
import styles from './ProfileModal.module.css';

export default function ProfileModal({ children, modalIsOpen, closeModal }) {
    return (
        <Modal
            appElement={document.getElementById('root')}
            isOpen={modalIsOpen}
            // onAfterOpen={afterOpenModal}
            onRequestClose={closeModal}
            className={styles.modalFrame}
        >
            {children}
        </Modal>
    );
}
