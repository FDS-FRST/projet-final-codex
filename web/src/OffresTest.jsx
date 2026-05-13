import { useEffect, useState } from 'react';
import axios from 'axios';

function OffresTest() {
    const [offres, setOffres] = useState([]);
    const [erreur, setErreur] = useState('');
    const [chargement, setChargement] = useState(true);

    useEffect(() => {
        axios.get('http://localhost:8080/api/offers')
            .then(response => {
                setOffres(Array.isArray(response.data) ? response.data : []);
            })
            .catch(error => {
                console.error(error);
                setErreur('Impossible de contacter l’API. Vérifie que Spring tourne sur le port 8080.');
            })
            .finally(() => {
                setChargement(false);
            });
    }, []);

    return (
        <div>
            <h2>🍽️ Offres test (depuis l’API Spring)</h2>

            {chargement && <p>Chargement des offres...</p>}
            {erreur && <p style={{ color: 'red' }}>{erreur}</p>}

            {!chargement && !erreur && offres.length === 0 && (
                <p>Aucune offre disponible.</p>
            )}

            <ul>
                {offres.map(offre => (
                    <li key={offre.id}>
                        <strong>{offre.title}</strong>
                        {offre.description ? ` - ${offre.description}` : ''}
                        {typeof offre.price !== 'undefined' ? ` (${offre.price} €)` : ''}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default OffresTest;