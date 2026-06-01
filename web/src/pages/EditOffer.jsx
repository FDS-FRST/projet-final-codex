import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { OfferForm } from '../components/offers/OfferForm';
import { getOfferById, updateOffer } from '../api/offers';

function toDateTimeLocal(value) {
  if (!value) return '';
  const date = new Date(value);
  const tzOffset = date.getTimezoneOffset() * 60000;
  return new Date(date.getTime() - tzOffset).toISOString().slice(0, 16);
}

export const EditOffer = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadOffer = async () => {
      try {
        setLoading(true);
        setError('');
        const offer = await getOfferById(id);
        setInitialData({
          title: offer.titre || offer.title,
          description: offer.description || '',
          quantity: offer.quantity,
          price: offer.price,
          startRetrieval: toDateTimeLocal(offer.startRetrieval),
          endRetrieval: toDateTimeLocal(offer.endRetrieval),
          location: offer.location,
        });
      } catch (err) {
        setError(err.message || "Impossible de charger l'offre");
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      loadOffer();
    }
  }, [id]);

  const handleSubmit = async (offerData) => {
    try {
      setError('');
      const payload = {
        titre: offerData.title,
        description: offerData.description,
        quantity: Number(offerData.quantity),
        quantityRemaining: Number(offerData.quantity),
        price: Number(offerData.price),
        startRetrieval: offerData.startRetrieval,
        endRetrieval: offerData.endRetrieval,
        location: offerData.location,
      };
      await updateOffer(id, payload);
      navigate(`/offers/${id}`);
    } catch (err) {
      setError(err.message || "Impossible de modifier l'offre");
    }
  };

  if (loading) {
    return (
      <div className="container">
        <p>Chargement...</p>
      </div>
    );
  }

  return (
    <div className="container">
      <h1>Modifier l'offre</h1>
      {error && <p style={{ color: 'red' }}>Erreur : {error}</p>}
      {initialData && (
        <OfferForm
          onSubmit={handleSubmit}
          initialData={initialData}
          submitLabel="Enregistrer les modifications"
        />
      )}
    </div>
  );
};
