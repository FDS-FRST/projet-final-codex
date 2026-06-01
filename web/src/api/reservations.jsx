import { fetchGet, fetchPatch } from './client';

export async function getReservationsForOffer(offerId) {
  return fetchGet(`/offers/${offerId}/reservations`);
}

export async function updateReservationStatus(reservationId, status) {
  return fetchPatch(`/reservations/${reservationId}`, { status });
}