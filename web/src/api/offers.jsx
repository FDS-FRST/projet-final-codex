import { fetchGet, fetchPost } from './client';

export async function getMyOffers() {
  return fetchGet('/offers/me');
}

export async function getOfferById(id) {
  return fetchGet(`/offers/${id}`);
}

export async function createOffer(offerData) {
  return fetchPost('/offers', offerData);
}