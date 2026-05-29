import { useNavigate } from 'react-router-dom';
import { useOffers } from '../hooks/useOffers';
import { useAuth } from '../hooks/useAuth';
import { OfferForm } from '../components/offers/OfferForm';
import { useState } from 'react';

export const CreateOffer = () => {
  const { addOffer } = useOffers();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (offerData) => {
    setErrorMessage('');

    if (!user || !user.id) {
      setErrorMessage('Utilisateur non identifié. Veuillez vous reconnecter.');
      return;
    }

    const quantity = Number(offerData.quantity);
    const price = Number(offerData.price);

    if (isNaN(quantity) || quantity <= 0) {
      setErrorMessage('La quantité doit être un nombre positif');
      return;
    }

    if (isNaN(price) || price < 0) {
      setErrorMessage('Le prix doit être un nombre positif ou zéro');
      return;
    }

    if (!offerData.startRetrieval || !offerData.endRetrieval) {
      setErrorMessage('Les dates de retrait sont obligatoires');
      return;
    }

    // Correction : utiliser 'titre' au lieu de 'title'
    const formattedData = {
      description: offerData.description,
      endRetrieval: offerData.endRetrieval,  // Enlevez le ':00' si nécessaire
      location: offerData.location,
      price: price,
      quantity: quantity,
      quantityRemaining: quantity,
      startRetrieval: offerData.startRetrieval,  // Enlevez le ':00' si nécessaire
      titre: offerData.title,  // ← Changement important ici
      offererId: Number(user.id)  // Conversion en nombre
    };

    console.log('📤 Envoi des données :', formattedData);

    try {
      await addOffer(formattedData);
      navigate('/');
    } catch (err) {
      console.error('❌ Erreur création offre:', err);
      setErrorMessage(err.message);
    }
  };

  return (
    <div className="container">
      <h1>Créer une offre</h1>
      {errorMessage && (
        <div style={{
          backgroundColor: '#fee',
          border: '1px solid #fcc',
          color: '#c00',
          padding: '0.75rem',
          borderRadius: '0.375rem',
          marginBottom: '1rem'
        }}>
          ❌ {errorMessage}
        </div>
      )}
      <OfferForm onSubmit={handleSubmit} />
    </div>
  );
};