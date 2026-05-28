import { useState, useEffect } from 'react';
import { getMyOffers, createOffer } from '../api/offers';

export const useOffers = () => {
  const [offers, setOffers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchOffers = async () => {
    try {
      setLoading(true);
      const data = await getMyOffers();
      setOffers(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const addOffer = async (offerData) => {
    const newOffer = await createOffer(offerData);
    setOffers(prev => [newOffer, ...prev]);
    return newOffer;
  };

  useEffect(() => {
    fetchOffers();
  }, []);

  return { offers, loading, error, fetchOffers, addOffer };
};