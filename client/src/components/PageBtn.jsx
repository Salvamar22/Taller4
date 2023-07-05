import React from 'react';

const PageBtn = ({ onNext, onPrev }) => {
    return (
        <div className=' bg-whitemt-2 m-2 flex justify-between gap-2'>
            <button className='prev bg-white' onClick={onPrev}>
                Anterior
            </button>
            <button className='next bg-white' onClick={onNext}>
                Siguiente
            </button>
        </div>
    );
};

export default PageBtn;
