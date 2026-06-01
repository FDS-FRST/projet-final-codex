import { ReservationStatusButton } from './ReservationStatusButton';
import { formatDateTime } from '../../utils/dateHelpers';

export const ReservationList = ({ reservations, onStatusChange }) => {
  if (!reservations.length) return <p>Aucune réservation.</p>;
  return (
    <div>
      {reservations.map(res => (
        <div key={res.id} className="card">
          <p><strong>Étudiant :</strong> {res.student?.name || res.studentId}</p>
          <p><strong>Date :</strong> {formatDateTime(res.reservationDate)}</p>
          <p><strong>Statut :</strong> {res.status}</p>
          <ReservationStatusButton reservation={res} onStatusChange={onStatusChange} />
        </div>
      ))}
    </div>
  );
};