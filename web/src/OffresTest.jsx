import { useEffect, useState } from 'react';
import axios from 'axios';

function OffresTest() {
    const [offres, setOffres] = useState([]);
    const [erreur, setErreur] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8080/api/offres')
            .then(response => setOffres(response.data))
            .catch(error => {
                console.error(error);
                setErreur('Impossible de contacter l’API. Vérifie que Spring tourne sur le port 8080.');
            });
    }, []);

    return (
        <div>
            <h2>🍽️ Offres test (depuis l’API Spring)</h2>
            {erreur && <p style={{ color: 'red' }}>{erreur}</p>}
            <ul>
                {offres.map(offre => (
                    <li key={offre.id}>{offre.titre}</li>
                ))}
            </ul>
        </div>
    );
}

export default OffresTest;