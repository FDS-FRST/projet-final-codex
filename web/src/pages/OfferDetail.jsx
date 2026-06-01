import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { getOfferById, deleteOffer } from '../api/offers';

export const OfferDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();

  const [offre, setOffre] = useState(null);
  const [chargement, setChargement] = useState(true);
  const [erreur, setErreur] = useState('');
  const [showConfirm, setShowConfirm] = useState(false);
  const [suppression, setSuppression] = useState(false);

  useEffect(() => {
    const chargerOffre = async () => {
      try {
        setChargement(true);
        setErreur('');
        const donnees = await getOfferById(id);
        setOffre(donnees);
      } catch (err) {
        setErreur(err.message || 'Erreur lors du chargement de l\'offre');
      } finally {
        setChargement(false);
      }
    };

    if (id) {
      chargerOffre();
    }
  }, [id]);

  const handleSupprimer = async () => {
    setSuppression(true);
    try {
      await deleteOffer(id);
      navigate('/');
    } catch (err) {
      setErreur(err.message || 'Impossible de supprimer l\'offre');
    } finally {
      setSuppression(false);
      setShowConfirm(false);
    }
  };

  if (chargement) {
    return (
      <div className="container">
        <div style={{ textAlign: 'center', padding: '2rem' }}>⏳ Chargement...</div>
      </div>
    );
  }

  if (erreur || !offre) {
    return (
      <div className="container">
        <div
          style={{
            backgroundColor: '#fee',
            border: '1px solid #fcc',
            color: '#c00',
            padding: '2rem',
            borderRadius: '0.5rem',
            textAlign: 'center',
          }}
        >
          <h2>❌ {erreur || 'Offre non trouvée'}</h2>
          <button onClick={() => navigate('/')}>Retour à l'accueil</button>
        </div>
      </div>
    );
  }

  const estProprietaire = user && offre && String(user.id) === String(offre.offererId);

  return (
    <div className="container">
      <button onClick={() => navigate(-1)} style={{ marginBottom: '1rem' }}>
        ← Retour
      </button>

      <div style={{ border: '1px solid #ddd', borderRadius: '0.5rem', padding: '1.5rem' }}>
        <h1>{offre.titre || offre.title}</h1>
        <p><strong>Description:</strong> {offre.description}</p>
        <p><strong>Quantité:</strong> {offre.quantity}</p>
        <p><strong>Quantité restante:</strong> {offre.quantityRemaining}</p>
        <p><strong>Prix:</strong> {offre.price === 0 ? 'Gratuit' : `${offre.price} €`}</p>
        <p><strong>Lieu:</strong> {offre.location}</p>
        <p><strong>Début retrait:</strong> {new Date(offre.startRetrieval).toLocaleString()}</p>
        <p><strong>Fin retrait:</strong> {new Date(offre.endRetrieval).toLocaleString()}</p>

        {estProprietaire && (
          <div style={{ marginTop: '1rem' }}>
            {!showConfirm ? (
              <button
                onClick={() => setShowConfirm(true)}
                style={{
                  backgroundColor: '#dc2626',
                  color: 'white',
                  padding: '0.5rem 1rem',
                  border: 'none',
                  borderRadius: '0.375rem',
                  cursor: 'pointer',
                }}
              >
                🗑️ Supprimer
              </button>
            ) : (
              <div style={{ backgroundColor: '#fee', padding: '1rem', borderRadius: '0.375rem' }}>
                <p>Supprimer cette offre ?</p>
                <button onClick={handleSupprimer} disabled={suppression}>
                  Oui
                </button>
                <button onClick={() => setShowConfirm(false)}>Non</button>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};