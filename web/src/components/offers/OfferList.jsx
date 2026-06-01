import { OfferCard } from './OfferCard';

export const OfferList = ({ offers, loading, error }) => {
  const safeOffers = Array.isArray(offers) ? offers : [];

  if (loading) return <p>Chargement...</p>;
  if (error) return <p style={{ color: 'red' }}>Erreur : {error}</p>;
  if (safeOffers.length === 0) return <p>Aucune offre trouvée.</p>;
  return <div>{safeOffers.map(offer => <OfferCard key={offer.id} offer={offer} />)}</div>;
};