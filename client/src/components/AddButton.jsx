import React from 'react'
import LogoutBtn from '../components/logoutBtn'

const AddButton = () => {
    return (
        <div className='w-3/4 justify-between flex absolute'>
            <button className=' bg-slate-500 w-36 h-10 rounded-md' >+ Nueva playlist </button>
            <LogoutBtn />
        </div>
    )
}

export default AddButton
