import { Link } from 'react-router-dom';
import { formatDateTime, isActive } from '../../utils/dateHelpers';
import { Button } from '../common/Button';

export const OfferCard = ({ offer }) => {
  const active = isActive(offer.startRetrieval, offer.endRetrieval);
  return (
    <div className="card">
      <h3>{offer.titre || offer.title}</h3>
      <p>{offer.description?.substring(0, 100)}</p>
      <p><strong>Prix :</strong> {offer.price === 0 ? 'Gratuit' : `${offer.price} €`}</p>
      <p><strong>Restant :</strong> {offer.quantityRemaining} / {offer.quantity}</p>
      <p><strong>Retrait :</strong> {formatDateTime(offer.startRetrieval)} - {formatDateTime(offer.endRetrieval)}</p>
      <p><strong>Lieu :</strong> {offer.location}</p>
      <p><strong>Statut :</strong> {active ? 'Active' : 'Terminée'}</p>
      <Link to={`/offers/${offer.id}`}><Button>Voir détails</Button></Link>
    </div>
  );
};