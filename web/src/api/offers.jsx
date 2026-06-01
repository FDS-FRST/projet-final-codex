// src/api/offers.js
import { fetchGet, fetchPost, fetchPatch, fetchDelete } from './client';

export const getMyOffers = async () => {
  return fetchGet('/offers/me');
};

// ⚠️ Cette fonction DOIT exister
export const getOfferById = async (id) => {
  console.log(`API: Récupération offre ${id}`);
  return fetchGet(`/offers/${id}`);
};

export const createOffer = async (offerData) => {
  return fetchPost('/offers', offerData);
};

export const updateOffer = async (id, offerData) => {
  return fetchPatch(`/offers/${id}`, offerData);
};

export const deleteOffer = async (id) => {
  return fetchDelete(`/offers/${id}`);
};