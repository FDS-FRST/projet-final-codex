import { OfferCard } from './OfferCard';

export const OfferList = ({ offers, loading, error }) => {
  if (loading) return <p>Chargement...</p>;
  if (error) return <p style={{ color: 'red' }}>Erreur : {error}</p>;
  if (offers.length === 0) return <p>Aucune offre trouvée.</p>;
  return <div>{offers.map(offer => <OfferCard key={offer.id} offer={offer} />)}</div>;
};