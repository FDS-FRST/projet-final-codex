import { useState, useEffect, useCallback } from 'react';
import { getMyOffers, createOffer, getOfferById, deleteOffer } from '../api/offers';

export const useOffers = () => {
  const [offers, setOffers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchOffers = async () => {
    try {
      setLoading(true);
      const data = await getMyOffers();
      setOffers(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const addOffer = async (offerData) => {
    const newOffer = await createOffer(offerData);
    setOffers(prev => [newOffer, ...(Array.isArray(prev) ? prev : [])]);
    return newOffer;
  };

  const recupererOffreParId = useCallback((id) => {
    return getOfferById(id);
  }, []);

  const supprimerOffre = useCallback((id) => {
    return deleteOffer(id);
  }, []);

  useEffect(() => {
    fetchOffers();
  }, []);

  return {
    offers,
    loading,
    error,
    fetchOffers,
    addOffer,
    recupererOffreParId,
    supprimerOffre,
  };
};