import { Button } from '../common/Button';

export const ReservationStatusButton = ({ reservation, onStatusChange }) => {
  const handleMark = (newStatus) => onStatusChange(reservation.id, newStatus);

  if (reservation.status === 'RETIREE' || reservation.status === 'NON_RETIREE' || reservation.status === 'NON_RETIRER') {
    return <p>Déjà traité</p>;
  }

  return (
    <div style={{ display: 'flex', gap: '0.5rem', marginTop: '0.5rem' }}>
      <Button onClick={() => handleMark('RETIREE')}>Retirée</Button>
      <Button onClick={() => handleMark('NON_RETIREE')} variant="danger">Non retirée</Button>
    </div>
  );
};