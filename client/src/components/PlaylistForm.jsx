import React, { useState } from 'react';
import services from '../services/user.services';

const PlaylistForm = ({ onCancel }) => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Realiza la petición al servidor aquí
            // Por ejemplo, utilizando la función `fetch` o `axios`
            const response = await services.createPlaylist(name, description);

            if (response.status === 200) {

                // La petición fue exitosa, muestra el alert
                alert('¡La playlist se creó exitosamente!');
            } else {
                // La petición no fue exitosa, muestra un mensaje de error
                alert('Hubo un error al crear la playlist.');
            }

            // También puedes restablecer los valores del formulario después de enviar los datos
            setName('');
            setDescription('');
        } catch (error) {
            // Si ocurre un error con la petición, muestra un mensaje de error
            console.error('Error al realizar la petición:', error);
            alert('Hubo un error al crear la playlist.');
        }
    };

    const handleCancel = () => {
        // Llama a la función onCancel proporcionada para ocultar el formulario
        onCancel();
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className='flex justify-center'>
                <label className=' text-white p-2' htmlFor="name">Nombre:</label>
                <input className=' rounded-md' type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div className='p-2'>
                <label className=' text-white p-2' htmlFor="description">Descripción:</label>
                <textarea className='rounded-md' id="description" value={description} onChange={(e) => setDescription(e.target.value)} />
            </div>
            <div className='flex justify-between'>
                <button className=' text-white' type="submit">Guardar</button>
                <button className=' text-white' type="button" onClick={handleCancel}>Cancelar</button>
            </div>
        </form>
    );
};

export default PlaylistForm;