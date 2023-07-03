import React from 'react';
import { Button } from 'react-bootstrap';

// eslint-disable-next-line react/prop-types
const LogoutBtn = ({ onButton }) => {

    return (
        <div>
            <Button variant="outline-danger" onClick={onButton}>
                Logout
            </Button>{' '}
        </div>
    );
};

export default LogoutBtn;
