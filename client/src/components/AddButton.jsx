import React, { useState } from 'react';
import { useUserContext } from '../context/userContext';
import LogoutBtn from '../components/logoutBtn';
import { useNavigate } from 'react-router-dom';
import PlaylistForm from './PlaylistForm';

const AddButton = ({onPlaylistAdded}) => {
    const { logout } = useUserContext();
    const navigate = useNavigate();

    const [showForm, setShowForm] = useState(false);

    const handleButtonClick = () => {
        setShowForm(true);
        onPlaylistAdded();
    };

    const handleLogout = () => {
        logout();
        navigate("/login");
        console.log('Logout');
    };

    const handleLogoutClick = () => {
        handleLogout();
    };

    const handleCancelForm = () => {
        setShowForm(false);
    };
    


    return (
        <div className='w-3/4 justify-between flex absolute'>
            <button className='bg-slate-500 w-36 h-10 rounded-md' onClick={handleButtonClick}>Nueva playlist</button>
            {showForm && <PlaylistForm onCancel={handleCancelForm} />}
            <LogoutBtn onButton={handleLogoutClick} />
        </div>
    );
};

export default AddButton;
