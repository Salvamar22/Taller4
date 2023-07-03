import React from 'react'

const playlist = ({title}) => {
    return (
        <div className='h-10 bg-slate-800 m-1 rounded-md'>
            {/* <h2>${props}</h2> */}
            <button className='w-full h-full font-bold text-white '>${title}</button>
        </div>
    )
}

export default playlist
