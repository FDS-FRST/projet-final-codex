import { Link } from 'react-router-dom';
import { useOffers } from '../hooks/useOffers';
import { OfferList } from '../components/offers/OfferList';
import { Button } from '../components/common/Button';

export const Dashboard = () => {
  const { offers, loading, error } = useOffers();

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h1>Mes offres</h1>
        <Link to="/create-offer"><Button>+ Nouvelle offre</Button></Link>
      </div>
      <OfferList offers={offers} loading={loading} error={error} />
    </div>
  );
};