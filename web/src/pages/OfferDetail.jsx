import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getOfferById } from '../api/offers';
import { getReservationsForOffer, updateReservationStatus } from '../api/reservations';
import { ReservationList } from '../components/reservations/ReservationList';
import { formatDateTime } from '../utils/dateHelpers';
import { Button } from '../components/common/Button';

export const OfferDetail = () => {
  const { id } = useParams();
  const [offer, setOffer] = useState(null);
  const [reservations, setReservations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const loadData = async () => {
    try {
      const [offerData, reservationsData] = await Promise.all([
        getOfferById(id),
        getReservationsForOffer(id)
      ]);
      setOffer(offerData);
      setReservations(reservationsData);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { loadData(); }, [id]);

  const handleStatusChange = async (reservationId, newStatus) => {
    try {
      await updateReservationStatus(reservationId, newStatus);
      setReservations(prev =>
        prev.map(r => r.id === reservationId ? { ...r, status: newStatus } : r)
      );
    } catch (err) {
      alert('Erreur mise à jour');
    }
  };

  if (loading) return <p>Chargement...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (!offer) return <p>Offre non trouvée</p>;

  return (
    <div className="container">
      <Link to="/">&larr; Retour</Link>
      <div className="card">
        <h1>{offer.title}</h1>
        <p>{offer.description}</p>
        <p><strong>Prix :</strong> {offer.price === 0 ? 'Gratuit' : `${offer.price} €`}</p>
        <p><strong>Quantité :</strong> {offer.quantityRemaining} / {offer.quantity}</p>
        <p><strong>Lieu :</strong> {offer.location}</p>
        <p><strong>Créneau :</strong> {formatDateTime(offer.startRetrieval)} - {formatDateTime(offer.endRetrieval)}</p>
      </div>
      <h2>Réservations</h2>
      <ReservationList reservations={reservations} onStatusChange={handleStatusChange} />
    </div>
  );
};